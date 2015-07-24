/*
 * $Id: UserCases.java,v 1.55 2009/06/30 09:35:57 valdas Exp $
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
import com.idega.block.process.business.CasesRetrievalManager;
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
 * Last modified: $Date: 2009/06/30 09:35:57 $ by $Author: valdas $
 *
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.55 $
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

	public UserCases() {
		setHideEmptySection(Boolean.TRUE);
	}

	private Map<Object, Object> pageMap;

	@SuppressWarnings("unused")
	private int iMaxNumberOfEntries = -1;
	@SuppressWarnings("unused")
	private int iNumberOfEntriesShown = 20;

	@Override
	protected void present(IWContext iwc) throws Exception {
	}

	protected String getHeading() {
		return getResourceBundle().getLocalizedString("user_cases", "User cases");
	}

	protected ICPage getPage(String caseCode, String caseStatus) {
		if (this.pageMap != null) {
			Object object = this.pageMap.get(caseCode);
			if (object instanceof ICPage)
				return (ICPage) object;
			if (object instanceof Map) {
				Map<?, ?> statusMap = (Map<?, ?>) object;
				return (ICPage) statusMap.get(caseStatus);
			}
		}
		return null;
	}

	public void setHideCaseCode(String caseCode) {
		getHiddenCaseCodes().add(caseCode);
	}

	private Set<String> getHiddenCaseCodes() {
		if (hiddenCaseCodes == null)
			hiddenCaseCodes = new HashSet<String>();

		return hiddenCaseCodes;
	}

	public void setPage(String caseCode, String caseStatus, ICPage page) {
		if (this.pageMap == null) {
			this.pageMap = new HashMap<Object, Object>();
		}

		@SuppressWarnings("unchecked")
		Map<Object, Object> statusMap = (Map<Object, Object>) this.pageMap.get(caseCode);
		if (statusMap == null) {
			statusMap = new HashMap<Object, Object>();
		}
		statusMap.put(caseStatus, page);
		this.pageMap.put(caseCode, statusMap);
	}

	public void setPage(String caseCode, ICPage page) {
		if (this.pageMap == null) {
			this.pageMap = new HashMap<Object, Object>();
		}

		this.pageMap.put(caseCode, page);
	}

	@Override
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

		CaseManagerState caseHandlerState = WFUtil.getBeanInstance(CaseManagerState.beanIdentifier);

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
		GeneralCaseManagerViewBuilder processorView = WFUtil.getBeanInstance(GeneralCaseManagerViewBuilder.SPRING_BEAN_IDENTIFIER);
		UIComponent view = null;
		try {
			view = processorView.getCaseManagerView(iwc, CasesRetrievalManager.CASE_LIST_TYPE_USER);
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
		//layer.setID("userCases");
		layer.getID();
		layer.setMarkupAttribute("class", "userCases");
		
		UICasesList list = getCasesList(iwc, layer.getId());
		layer.add(list);

		add(layer);
	}

	@Override
	public boolean showCheckBox() {
		return false;
	}

	@Override
	public String getCasesProcessorType() {
		return CasesRetrievalManager.CASE_LIST_TYPE_USER;
	}

	@Override
	public boolean showCheckBoxes() {
		return showCheckBox();
	}

	@Override
	public Map<Object, Object> getUserCasesPageMap() {
		return pageMap;
	}

}