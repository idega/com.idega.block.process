/*
 * $Id: UserCases.java,v 1.50 2009/02/13 13:52:42 valdas Exp $
 * Created on Sep 25, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.presentation;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.business.CaseCodeManager;
import com.idega.block.process.business.CaseManager;
import com.idega.block.process.data.Case;
import com.idega.block.process.presentation.beans.CaseManagerState;
import com.idega.block.process.presentation.beans.GeneralCaseManagerViewBuilder;
import com.idega.business.IBORuntimeException;
import com.idega.core.builder.data.ICPage;
import com.idega.event.IWPageEventListener;
import com.idega.idegaweb.IWException;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Text;
import com.idega.webface.WFUtil;


/**
 * Last modified: $Date: 2009/02/13 13:52:42 $ by $Author: valdas $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.50 $
 */
public class UserCases extends CaseBlock implements IWPageEventListener {
	
	private static final String PARAMETER_UC_CASE_PK = "uc_case_pk";
	public static final String PARAMETER_CASE_PK = "prm_case_pk";
	public static final String PARAMETER_ACTION = "cp_prm_action";
	
	public static final int ACTION_VIEW = 1;
	public static final int ACTION_CASE_MANAGER_VIEW = 8;
	
	private static final String caseManagerFacet = "caseManager";
	
	public static final String pageType = "cases";
	
	private Set<String> hiddenCaseCodes;
	private Map pageMap;
	private int iMaxNumberOfEntries = -1;
	/*private int iMaxNumberOfLetters = -1;
	private int iMaxNumberOfHandlerLetters = -1;*/
	private int iNumberOfEntriesShown = 20;
	
	private boolean addCredentialsToExernalUrls=false;
	
	private boolean usePDFDownloadColumn = true;
	private boolean allowPDFSigning = true;
	private boolean showStatistics;
	private boolean hideEmptySection = true;
	private boolean showCaseNumberColumn = true;
	private boolean showCaseCreationDateColumn = true;
	
	private int page = 1;
	private int pageSize = 20;

	@Override
	protected void present(IWContext iwc) throws Exception {
	}
	
	protected String getHeading() {
		return getResourceBundle().getLocalizedString("user_cases", "User cases");
	}
	
	public boolean isUsePDFDownloadColumn() {
		return usePDFDownloadColumn;
	}

	public void setUsePDFDownloadColumn(boolean usePDFDownloadColumn) {
		this.usePDFDownloadColumn = usePDFDownloadColumn;
	}

	public boolean isHideEmptySection() {
		return hideEmptySection;
	}

	public void setHideEmptySection(boolean hideEmptySection) {
		this.hideEmptySection = hideEmptySection;
	}
		
	@SuppressWarnings("unchecked")
	protected ICPage getPage(String caseCode, String caseStatus) {
		if (this.pageMap != null) {
			Object object = this.pageMap.get(caseCode);
			if (object != null) {
				if (object instanceof ICPage) {
					return (ICPage) object;
				}
				else if (object instanceof Map) {
					Map statusMap = (Map) object;
					return (ICPage) statusMap.get(caseStatus);
				}
			}
		}
		return null;
	}
	
	public void setHideCaseCode(String caseCode) {
		getHiddenCaseCodes().add(caseCode);
	}
	
	private Set<String> getHiddenCaseCodes() {
		
		if(hiddenCaseCodes == null)
			hiddenCaseCodes = new HashSet<String>();
		
		return hiddenCaseCodes;
	}
	
	@SuppressWarnings("unchecked")
	public void setPage(String caseCode, String caseStatus, ICPage page) {
		if (this.pageMap == null) {
			this.pageMap = new HashMap();
		}
		
		Map statusMap = (Map) this.pageMap.get(caseCode);
		if (statusMap == null) {
			statusMap = new HashMap();
		}
		statusMap.put(caseStatus, page);
		this.pageMap.put(caseCode, statusMap);
	}
	
	@SuppressWarnings("unchecked")
	public void setPage(String caseCode, ICPage page) {
		if (this.pageMap == null) {
			this.pageMap = new HashMap();
		}
		
		this.pageMap.put(caseCode, page);
	}

	public boolean actionPerformed(IWContext iwc) throws IWException {
		if (iwc.isParameterSet(PARAMETER_UC_CASE_PK)) {
			try {
				Case userCase = getCaseBusiness(iwc).getCase(iwc.getParameter(PARAMETER_UC_CASE_PK));
				CaseBusiness caseBusiness = CaseCodeManager.getInstance().getCaseBusinessOrDefault(userCase.getCaseCode(), iwc);
				caseBusiness.deleteCase(userCase, iwc.getCurrentUser());
				return true;
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		return false;
	}
	
	public void setMaximumNumberOfEntries(int maxNumberOfEntries) {
		this.iMaxNumberOfEntries = maxNumberOfEntries;
	}

	
	public void setMaximumNumberOfLetters(int maxNumberOfLetters) {
		//this.iMaxNumberOfLetters = maxNumberOfLetters;
	}
	
	public void setMaximumHandlerLength(int maxNumberOfHandlerLetters) {
		//this.iMaxNumberOfHandlerLetters = maxNumberOfHandlerLetters;
	}
	
	public void setNumberOfEntriesShownPerPage(int numberOfEntriesShown) {
		this.iNumberOfEntriesShown = numberOfEntriesShown;
	}

	
	/**
	 * @return the addCredentialsToExernalUrls
	 */
	public boolean isAddCredentialsToExernalUrls() {
		return addCredentialsToExernalUrls;
	}

	
	/**
	 * @param addCredentialsToExernalUrls the addCredentialsToExernalUrls to set
	 */
	public void setAddCredentialsToExernalUrls(boolean addCredentialsToExernalUrls) {
		this.addCredentialsToExernalUrls = addCredentialsToExernalUrls;
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

	@Override
	public void encodeBegin(FacesContext fc) throws IOException {
		super.encodeBegin(fc);
		
		IWContext iwc = IWContext.getIWContext(fc);
		
		if (!iwc.isLoggedOn()) {
			add(new Text("No user logged on..."));
			return;
		}
		
		try {
			display(iwc);
		
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			Logger.getLogger(getClassName()).log(Level.SEVERE, "Exception while displaying CasesProcessor", e);
		}
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		super.encodeChildren(context);
		
		CaseManagerState caseHandlerState = (CaseManagerState)WFUtil.getBeanInstance(CaseManagerState.beanIdentifier);
		
		if(caseHandlerState.getShowCaseHandler()) {
			
			UIComponent facet = getFacet(caseManagerFacet);
			renderChild(context, facet);
		}
	}
	
	protected void display(IWContext iwc) throws Exception {
		Integer action = null;
		if (iwc.isParameterSet(UserCases.PARAMETER_ACTION)) {
			try {
				action = Integer.parseInt(iwc.getParameter(UserCases.PARAMETER_ACTION));
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		if (action == null) {
			showList(iwc);
			return;
		}
		
		switch (action) {
			case ACTION_CASE_MANAGER_VIEW:
				showProcessorForBpm(iwc);
				break;
	
			default:
				showList(iwc);
				break;
		}
	}
	
	private void showProcessorForBpm(IWContext iwc) throws NullPointerException {
		GeneralCaseManagerViewBuilder processorView = (GeneralCaseManagerViewBuilder) WFUtil.getBeanInstance(GeneralCaseManagerViewBuilder.SPRING_BEAN_IDENTIFIER);
		UIComponent view = null;
		try {
			view = processorView.getCaseManagerView(iwc, CaseManager.CASE_LIST_TYPE_USER);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		if (view == null) {
			return;
		}
		
		add(view);
	}
	
	private void showList(IWContext iwc) throws RemoteException {

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("caseElement");
		layer.setID("userCases");
		
		UICasesList list = (UICasesList)iwc.getApplication().createComponent(UICasesList.COMPONENT_TYPE);
		list.setType(CaseManager.CASE_LIST_TYPE_USER);
		list.setUserCasesPageMap(pageMap);
		list.setAddCredentialsToExernalUrls(addCredentialsToExernalUrls);
		list.setUsePDFDownloadColumn(usePDFDownloadColumn);
		list.setAllowPDFSigning(allowPDFSigning);
		list.setShowStatistics(showStatistics);
		list.setHideEmptySection(hideEmptySection);
		list.setPageSize(getPageSize());
		list.setPage(getPage());
		list.setComponentId(layer.getId());
		list.setInstanceId(getBuilderService(iwc).getInstanceId(this));
		list.setShowCaseNumberColumn(showCaseNumberColumn);
		list.setShowCaseCreationDateColumn(showCaseCreationDateColumn);
		
		layer.add(list);
		
		add(layer);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isShowCaseNumberColumn() {
		return showCaseNumberColumn;
	}

	public void setShowCaseNumberColumn(boolean showCaseNumberColumn) {
		this.showCaseNumberColumn = showCaseNumberColumn;
	}

	public boolean isShowCaseCreationDateColumn() {
		return showCaseCreationDateColumn;
	}

	public void setShowCaseCreationDateColumn(boolean showCaseCreationDateColumn) {
		this.showCaseCreationDateColumn = showCaseCreationDateColumn;
	}
	
}