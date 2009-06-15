package com.idega.block.process.presentation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.idega.block.process.business.CasesRetrievalManager;
import com.idega.block.process.business.CaseManagersProvider;
import com.idega.block.process.business.ProcessConstants;
import com.idega.block.process.presentation.beans.CaseListPropertiesBean;
import com.idega.block.process.presentation.beans.CasePresentation;
import com.idega.block.process.presentation.beans.CasesSearchResultsHolder;
import com.idega.block.process.presentation.beans.GeneralCasesListBuilder;
import com.idega.core.builder.business.BuilderServiceFactory;
import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.IWBaseComponent;
import com.idega.presentation.IWContext;
import com.idega.presentation.paging.PagedDataCollection;
import com.idega.util.CoreConstants;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

/**
 * Case list component.
 * 
 * @author donatas
 *
 */
public class UICasesList extends IWBaseComponent {

	public static final String COMPONENT_TYPE = "com.idega.UICasesList";
	
	@Autowired
	private CaseManagersProvider caseManagersProvider;
	
	private String type;
	
	private int pageSize = 20;
	private int page = 1;
	
	private boolean showCheckBoxes;
	private boolean usePDFDownloadColumn;
	private boolean allowPDFSigning;
	private boolean showStatistics;
	private boolean hideEmptySection;
	private boolean addCredentialsToExernalUrls;
	private boolean showCaseNumberColumn = true;
	private boolean showCreationTimeInDateColumn = true;
	
	private List<String> caseStatusesToShow;
	private List<String> caseStatusesToHide;
	
	private String instanceId;
	private String componentId;
	private String commentsManagerIdentifier;
	
	@SuppressWarnings({"unchecked"})
	private Map userCasesPageMap;

	@Override
	protected void initializeComponent(FacesContext context) {
		super.initializeComponent(context);
		
		IWContext iwc = IWContext.getIWContext(context);
		
		GeneralCasesListBuilder listBuilder = ELUtil.getInstance().getBean(GeneralCasesListBuilder.SPRING_BEAN_IDENTIFIER);
		
		PagedDataCollection<CasePresentation> cases = iwc.isLoggedOn() ? getCases(iwc) : null;
		
		UIComponent casesListComponent = null;
		CaseListPropertiesBean properties = new CaseListPropertiesBean();
		properties.setType(getType());
		properties.setUsePDFDownloadColumn(isUsePDFDownloadColumn());
		properties.setAllowPDFSigning(isAllowPDFSigning());
		properties.setShowStatistics(isShowStatistics());
		properties.setHideEmptySection(isHideEmptySection());
		properties.setPageSize(getPageSize());
		properties.setPage(getPage());
		properties.setInstanceId(getInstanceId());
		properties.setComponentId(getComponentId());
		properties.setShowCaseNumberColumn(isShowCaseNumberColumn());
		properties.setShowCreationTimeInDateColumn(isShowCreationTimeInDateColumn());
		properties.setStatusesToShow(getCaseStatusesToShow());
		properties.setStatusesToHide(getCaseStatusesToHide());
		properties.setCommentsManagerIdentifier(getCommentsManagerIdentifier());
		
		if (CasesRetrievalManager.CASE_LIST_TYPE_USER.equals(getType())) {
			properties.setAddCredentialsToExernalUrls(isAddCredentialsToExernalUrls());
			casesListComponent = listBuilder.getUserCasesList(iwc, cases, getUserCasesPageMap(), properties);
		} else {
			properties.setShowCheckBoxes(isShowCheckBoxes());
			casesListComponent = listBuilder.getCasesList(iwc, cases, properties);
		}
		
		add(casesListComponent);
	}

	/**
	 * Gets paged cases data.
	 * 
	 * @param user User to get cases for.
	 * @return PagedDataCollection of cases.
	 */
	protected PagedDataCollection<CasePresentation> getCases(IWContext iwc) {
		CasesSearchResultsHolder casesSearcher = null;
		try {
			casesSearcher = ELUtil.getInstance().getBean(CasesSearchResultsHolder.SPRING_BEAN_IDENTIFIER);
		} catch(Exception e) {}
		String id = iwc.getRequestURI();
		if (casesSearcher != null && casesSearcher.isSearchResultStored(id)) {
			setType(ProcessConstants.CASE_LIST_TYPE_SEARCH_RESULTS);
			setPageSize(0);
			return new PagedDataCollection<CasePresentation>(casesSearcher.getSearchResults(id));
		}
		
		if (getCaseManagersProvider() == null) {
			ELUtil.getInstance().autowire(this);
		}
		return getCaseManagersProvider().getCaseManager().getCases(iwc.getCurrentUser(), getType(), iwc.getCurrentLocale(), getCaseStatusesToHide(), getCaseStatusesToShow(), (getPage() - 1) * getPageSize(), getPageSize());
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setCaseManagersProvider(CaseManagersProvider caseManagersProvider) {
		this.caseManagersProvider = caseManagersProvider;
	}

	public CaseManagersProvider getCaseManagersProvider() {
		return caseManagersProvider;
	}

	public boolean isShowCheckBoxes() {
		return showCheckBoxes;
	}

	public void setShowCheckBoxes(boolean showCheckBoxes) {
		this.showCheckBoxes = showCheckBoxes;
	}

	public boolean isUsePDFDownloadColumn() {
		return usePDFDownloadColumn;
	}

	public void setUsePDFDownloadColumn(boolean usePDFDownloadColumn) {
		this.usePDFDownloadColumn = usePDFDownloadColumn;
	}

	public boolean isAllowPDFSigning() {
		return allowPDFSigning;
	}

	public void setAllowPDFSigning(boolean allowPDFSigning) {
		this.allowPDFSigning = allowPDFSigning;
	}

	public boolean isShowStatistics() {
		return showStatistics;
	}

	public void setShowStatistics(boolean showStatistics) {
		this.showStatistics = showStatistics;
	}

	public boolean isHideEmptySection() {
		return hideEmptySection;
	}

	public void setHideEmptySection(boolean hideEmptySection) {
		this.hideEmptySection = hideEmptySection;
	}

	public boolean isAddCredentialsToExernalUrls() {
		return addCredentialsToExernalUrls;
	}

	public void setAddCredentialsToExernalUrls(boolean addCredentialsToExernalUrls) {
		this.addCredentialsToExernalUrls = addCredentialsToExernalUrls;
	}

	@SuppressWarnings("unchecked")
	public Map getUserCasesPageMap() {
		return userCasesPageMap;
	}

	@SuppressWarnings("unchecked")
	public void setUserCasesPageMap(Map userCasesPageMap) {
		this.userCasesPageMap = userCasesPageMap;
	}
	
	public void setCaseStatusesToShow(List<String> caseStatusesToShow) {
		this.caseStatusesToShow = caseStatusesToShow;
	}

	public void setCaseStatusesToHide(List<String> caseStatusesToHide) {
		this.caseStatusesToHide = caseStatusesToHide;
	}
	
	public List<String> getCaseStatusesToHide() {
		return caseStatusesToHide != null ? caseStatusesToHide : (caseStatusesToHide = new ArrayList<String>());
	}

	public void setCaseStatusesToHide(String caseStatusesToHide) {
		this.caseStatusesToHide = StringUtil.getValuesFromString(caseStatusesToHide, CoreConstants.COMMA);
	}

	public List<String> getCaseStatusesToShow() {
		return caseStatusesToShow != null ? caseStatusesToShow : (caseStatusesToShow = new ArrayList<String>());
	}

	public void setCaseStatusesToShow(String caseStatusesToShow) {
		this.caseStatusesToShow = StringUtil.getValuesFromString(caseStatusesToShow, CoreConstants.COMMA);
	}

	public String getInstanceId() {
		try {
			return instanceId == null ?  BuilderServiceFactory.getBuilderService(IWMainApplication.getDefaultIWApplicationContext()).getInstanceId(this) : instanceId;
		} catch (RemoteException e) {
			return null;
		}
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getComponentId() {
		return componentId == null ? getId() : componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public boolean isShowCaseNumberColumn() {
		return showCaseNumberColumn;
	}

	public void setShowCaseNumberColumn(boolean showCaseNumberColumn) {
		this.showCaseNumberColumn = showCaseNumberColumn;
	}

	public boolean isShowCreationTimeInDateColumn() {
		return showCreationTimeInDateColumn;
	}

	public void setShowCreationTimeInDateColumn(boolean showCreationTimeInDateColumn) {
		this.showCreationTimeInDateColumn = showCreationTimeInDateColumn;
	}

	public String getCommentsManagerIdentifier() {
		return commentsManagerIdentifier;
	}

	public void setCommentsManagerIdentifier(String commentsManagerIdentifier) {
		this.commentsManagerIdentifier = commentsManagerIdentifier;
	}
	
}
