package com.idega.block.process.presentation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.idega.block.process.business.CaseManagersProvider;
import com.idega.block.process.business.CasesRetrievalManager;
import com.idega.block.process.business.ProcessConstants;
import com.idega.block.process.presentation.beans.CaseListPropertiesBean;
import com.idega.block.process.presentation.beans.CasePresentation;
import com.idega.block.process.presentation.beans.CasesSearchCriteriaBean;
import com.idega.block.process.presentation.beans.CasesSearchResultsHolder;
import com.idega.block.process.presentation.beans.GeneralCasesListBuilder;
import com.idega.core.builder.business.BuilderServiceFactory;
import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.IWBaseComponent;
import com.idega.presentation.IWContext;
import com.idega.presentation.ListNavigator;
import com.idega.presentation.paging.PagedDataCollection;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

/**
 * Case list component.
 * 
 * @author donatas
 *
 */
public class UICasesList extends IWBaseComponent {

	public static final String	COMPONENT_TYPE = "com.idega.UICasesList",
								DYNAMIC_CASES_NAVIGATOR = "dynamic_pager_cases_search";
	
	@Autowired
	private CaseManagersProvider caseManagersProvider;
	
	private String type, specialBackPage;
	
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
	private boolean showCreatorColumn = true;
	private boolean showAttachmentStatistics;
	private boolean showOnlyCreatorInContacts;
	private boolean onlySubscribedCases;
	private boolean useJavascriptForPageSwitching = true;
	private boolean showLegend, showAllCases;
	private boolean showLogExportButton,
					showCaseStatus = true;
	
	public boolean isShowLogExportButton() {
		return showLogExportButton;
	}

	public void setShowLogExportButton(boolean showLogExportButton) {
		this.showLogExportButton = showLogExportButton;
	}

	private List<String>	caseStatusesToShow,
							caseStatusesToHide,
							caseCodes,
							customColumns;
	
	private String	instanceId,
					componentId,
					commentsManagerIdentifier,
					searchResultsId,
					dateCustomValueVariable,
					dateCustomLabelLocalizationKey,
					casesListCustomizer;
	
	@SuppressWarnings("rawtypes")
	private Map userCasesPageMap;

	private Integer getPageSizeFromSession(IWContext iwc) {
		String key = "userCases";
		Object pageSizeOb = iwc.getSessionAttribute(ListNavigator.PARAMETER_NUMBER_OF_ENTRIES + "_" + key);
		if (pageSizeOb instanceof Integer) {
			return (Integer) pageSizeOb;
		}
		return null;
	}
	
	private Integer getPageFromSession(IWContext iwc) {
		String key = "userCases";
		Object pageOb = iwc.getSessionAttribute(ListNavigator.PARAMETER_CURRENT_PAGE + "_" + key);
		if (pageOb instanceof Integer) {
			return (Integer) pageOb;
		}
		return null;
	}
	
	@Override
	protected void initializeComponent(FacesContext context) {
		super.initializeComponent(context);
		
		IWContext iwc = IWContext.getIWContext(context);
		
		GeneralCasesListBuilder listBuilder = ELUtil.getInstance().getBean(GeneralCasesListBuilder.SPRING_BEAN_IDENTIFIER);
		
		PagedDataCollection<CasePresentation> cases = iwc.isLoggedOn() || CasesRetrievalManager.CASE_LIST_TYPE_PUBLIC.equals(getType()) ? getCases(iwc) : null;
		
		UIComponent casesListComponent = null;
		CaseListPropertiesBean properties = new CaseListPropertiesBean();
		
		Object settings = iwc.getSessionAttribute(GeneralCasesListBuilder.USER_CASES_SEARCH_SETTINGS_ATTRIBUTE);
		int pageSize = getPageSize();
		int page = getPage();
		int foundResults = -1;
		boolean showStatistics = isShowStatistics();
		if (settings instanceof CasesSearchCriteriaBean) {
			CasesSearchCriteriaBean searchSettings = (CasesSearchCriteriaBean) settings;
			showStatistics = searchSettings.isShowStatistics();
			pageSize = searchSettings.getPageSize();
			page = searchSettings.getPage();
			foundResults = searchSettings.getFoundResults();
		}
		
		Integer pageSizeFromSesion = getPageSizeFromSession(iwc);
		if (pageSizeFromSesion != null && pageSizeFromSesion > 0) {
			pageSize = pageSizeFromSesion;
			setPageSize(pageSize);
		}
		Integer pageFromSession = getPageFromSession(iwc);
		if (pageFromSession != null && pageFromSession > 0) {
			page = pageFromSession;
			setPage(page);
		}
		
		properties.setType(getType());
		properties.setUsePDFDownloadColumn(isUsePDFDownloadColumn());
		properties.setAllowPDFSigning(isAllowPDFSigning());
		properties.setShowStatistics(showStatistics);
		properties.setHideEmptySection(isHideEmptySection());
		properties.setPageSize(pageSize);
		properties.setPage(page);
		if (foundResults > 0)
			properties.setFoundResults(foundResults);
		properties.setInstanceId(getInstanceId());
		properties.setComponentId(getComponentId());
		properties.setShowCaseNumberColumn(isShowCaseNumberColumn());
		properties.setShowCreationTimeInDateColumn(isShowCreationTimeInDateColumn());
		properties.setCaseCodes(getCaseCodes());
		properties.setStatusesToShow(getCaseStatusesToShow());
		properties.setStatusesToHide(getCaseStatusesToHide());
		properties.setCommentsManagerIdentifier(getCommentsManagerIdentifier());
		properties.setShowCreatorColumn(isShowCreatorColumn());
		properties.setDateCustomValueVariable(getDateCustomValueVariable());
		properties.setDateCustomLabelLocalizationKey(getDateCustomLabelLocalizationKey());
		properties.setShowAttachmentStatistics(isShowAttachmentStatistics());
		properties.setShowOnlyCreatorInContacts(isShowOnlyCreatorInContacts());
		properties.setOnlySubscribedCases(isOnlySubscribedCases());
		properties.setUseJavascriptForPageSwitching(getUseJavascriptForPageSwitching());
		properties.setShowLogExportButton(isShowLogExportButton());
		properties.setSpecialBackPage(getSpecialBackPage());
		properties.setShowCaseStatus(isShowCaseStatus());
		properties.setCustomColumns(getCustomColumns());
		properties.setCasesListCustomizer(getCasesListCustomizer());
		
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
		Integer pageSizeFromSession = getPageSizeFromSession(iwc);
		if (pageSizeFromSession != null && pageSizeFromSession > 0)
			setPageSize(pageSizeFromSession);
		Integer pageFromSession = getPageFromSession(iwc);
		if (pageFromSession != null && pageFromSession > 0) 
			setPage(pageFromSession);
		
		CasesSearchResultsHolder casesSearcher = null;
		try {
			casesSearcher = ELUtil.getInstance().getBean(CasesSearchResultsHolder.SPRING_BEAN_IDENTIFIER);
		} catch(Exception e) {}
		String id = iwc.getRequestURI();

		if (casesSearcher != null && (casesSearcher.isSearchResultStored(id) || casesSearcher.isSearchResultStored(getSearchResultsId()))) {
			setType(ProcessConstants.CASE_LIST_TYPE_SEARCH_RESULTS);
			if (getPageSize() <= 0) {
				setPageSize(20);
			}
			if (!iwc.getApplicationSettings().getBoolean(DYNAMIC_CASES_NAVIGATOR, Boolean.TRUE))
				setPageSize(-1);
			if (getPage() <= 0) {
				setPage(1);
			}
			
			Collection<CasePresentation> cases = casesSearcher.getSearchResults(id);
			if (ListUtil.isEmpty(cases) && !StringUtil.isEmpty(getSearchResultsId())) {
				cases = casesSearcher.getSearchResults(getSearchResultsId());
			}
			CasesSearchCriteriaBean criterias = casesSearcher.getSearchCriteria(id);
			criterias = criterias == null ? casesSearcher.getSearchCriteria(getSearchResultsId()) : criterias;
			if (ListUtil.isEmpty(cases) || (criterias != null && !criterias.isAllDataLoaded())) {
				cases = getReLoadedCases(criterias);
			}
			if (ListUtil.isEmpty(cases))
				return null;
			
			if (getPageSize() > 0) {
				if (getPage() != criterias.getPage() || getPageSize() != criterias.getPageSize()) {
					criterias.setPage(getPage());
					criterias.setPageSize(getPageSize());
					cases = getReLoadedCases(criterias);
				}
				criterias.setPageSize(getPageSize());
			}
			return new PagedDataCollection<CasePresentation>(cases);
		}
		
		User user = iwc.isLoggedOn() ? iwc.getCurrentUser() : null;
		return getCaseManagersProvider().getCaseManager().getCases(user, getType(), iwc.getCurrentLocale(), getCaseCodes(),
				getCaseStatusesToHide(), getCaseStatusesToShow(), (getPage() - 1) * getPageSize(), getPageSize(), isOnlySubscribedCases(), isShowAllCases());
	}
	
	private Collection<CasePresentation> getReLoadedCases(CasesSearchCriteriaBean criterias) {
		try {
			return getCaseManagersProvider().getCaseManager().getReLoadedCases(criterias);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		if (caseManagersProvider == null) {
			ELUtil.getInstance().autowire(this);
		}
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

	@SuppressWarnings("rawtypes")
	public Map getUserCasesPageMap() {
		return userCasesPageMap;
	}

	public void setUserCasesPageMap(@SuppressWarnings("rawtypes") Map userCasesPageMap) {
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

	public void setCaseCodes(List<String> caseCodes) {
		this.caseCodes = caseCodes;
	}

	public List<String> getCaseCodes() {
		return caseCodes != null ? caseCodes : (caseCodes = new ArrayList<String>());
	}

	public void setCaseCodes(String caseCodes) {
		this.caseCodes = StringUtil.getValuesFromString(caseCodes, CoreConstants.COMMA);
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

	public boolean isShowCreatorColumn() {
		return showCreatorColumn;
	}

	public void setShowCreatorColumn(boolean showCreatorColumn) {
		this.showCreatorColumn = showCreatorColumn;
	}

	public String getDateCustomValueVariable() {
		return dateCustomValueVariable;
	}

	public void setDateCustomValueVariable(String dateCustomValueVariable) {
		this.dateCustomValueVariable = dateCustomValueVariable;
	}

	public String getDateCustomLabelLocalizationKey() {
		return dateCustomLabelLocalizationKey;
	}

	public void setDateCustomLabelLocalizationKey(String dateCustomLabelLocalizationKey) {
		this.dateCustomLabelLocalizationKey = dateCustomLabelLocalizationKey;
	}

	public boolean isShowAttachmentStatistics() {
		return showAttachmentStatistics;
	}

	public void setShowAttachmentStatistics(boolean showAttachmentStatistics) {
		this.showAttachmentStatistics = showAttachmentStatistics;
	}

	public boolean isShowOnlyCreatorInContacts() {
		return showOnlyCreatorInContacts;
	}

	public void setShowOnlyCreatorInContacts(boolean showOnlyCreatorInContacts) {
		this.showOnlyCreatorInContacts = showOnlyCreatorInContacts;
	}

	public boolean isOnlySubscribedCases() {
		return onlySubscribedCases;
	}

	public void setOnlySubscribedCases(boolean onlySubscribedCases) {
		this.onlySubscribedCases = onlySubscribedCases;
	}

	public String getSearchResultsId() {
		return searchResultsId;
	}

	public void setSearchResultsId(String searchResultsId) {
		this.searchResultsId = searchResultsId;
	}
	
	public boolean getUseJavascriptForPageSwitching() {
		return this.useJavascriptForPageSwitching;
	}
	
	public void setUseJavascriptForPageSwitching(boolean useJavascriptForPageSwitching) {
		this.useJavascriptForPageSwitching = useJavascriptForPageSwitching;
	}

	public boolean isShowLegend() {
		return showLegend;
	}

	public void setShowLegend(boolean showLegend) {
		this.showLegend = showLegend;
	}

	public boolean isShowAllCases() {
		return showAllCases;
	}

	public void setShowAllCases(boolean showAllCases) {
		this.showAllCases = showAllCases;
	}

	public String getSpecialBackPage() {
		return specialBackPage;
	}

	public void setSpecialBackPage(String specialBackPage) {
		this.specialBackPage = specialBackPage;
	}

	public boolean isShowCaseStatus() {
		return showCaseStatus;
	}

	public void setShowCaseStatus(boolean showCaseStatus) {
		this.showCaseStatus = showCaseStatus;
	}

	public List<String> getCustomColumns() {
		return customColumns;
	}

	public void setCustomColumns(List<String> customColumns) {
		this.customColumns = customColumns;
	}

	public String getCasesListCustomizer() {
		return casesListCustomizer;
	}

	public void setCasesListCustomizer(String casesListCustomizer) {
		this.casesListCustomizer = casesListCustomizer;
	}
}