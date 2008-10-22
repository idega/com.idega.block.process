/*
 * $Id: UserCases.java,v 1.45 2008/10/22 14:49:00 civilis Exp $
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
import java.util.Collection;
import java.util.Collections;
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
import com.idega.block.process.business.CaseManagersProvider;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.presentation.beans.CaseManagerState;
import com.idega.block.process.presentation.beans.GeneralCaseManagerViewBuilder;
import com.idega.block.process.presentation.beans.GeneralCasesListBuilder;
import com.idega.business.IBORuntimeException;
import com.idega.core.builder.data.ICPage;
import com.idega.event.IWPageEventListener;
import com.idega.idegaweb.IWException;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.ListNavigator;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Text;
import com.idega.webface.WFUtil;


/**
 * Last modified: $Date: 2008/10/22 14:49:00 $ by $Author: civilis $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.45 $
 */
public class UserCases extends CaseBlock implements IWPageEventListener {
	
	private static final String PARAMETER_UC_CASE_PK = "uc_case_pk";
	public static final String PARAMETER_CASE_PK = "prm_case_pk";
	public static final String PARAMETER_ACTION = "cp_prm_action";
	
	public static final int ACTION_VIEW = 1;
	public static final int ACTION_CASE_MANAGER_VIEW = 8;
	
	private static final String caseManagerFacet = "caseManager";
	
	public static final String pageType = "cases";
	public static final String TYPE = "UserCases";
	
	private Set<String> hiddenCaseCodes;
	private Map pageMap;
	private int iMaxNumberOfEntries = -1;
	private int iMaxNumberOfLetters = -1;
	private int iMaxNumberOfHandlerLetters = -1;
	private int iNumberOfEntriesShown = -1;
	
	private boolean addCredentialsToExernalUrls=false;
	
	private boolean usePDFDownloadColumn = true;
	private boolean allowPDFSigning = true;

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

	private UIComponent getCasesList(IWContext iwc, int startingEntry, int numberOfEntries) throws RemoteException {
		Collection<Case> cases = getCases(iwc, startingEntry, numberOfEntries);
		
		GeneralCasesListBuilder listBuilder = (GeneralCasesListBuilder) WFUtil.getBeanInstance(GeneralCasesListBuilder.SPRING_BEAN_IDENTIFIER);
		return listBuilder.getUserCasesList(iwc, cases, this.pageMap, TYPE, isAddCredentialsToExernalUrls(), isUsePDFDownloadColumn(), isAllowPDFSigning());
		
		/*
		Table2 table = new Table2();
		table.setStyleClass("caseTable");
		table.setStyleClass("ruler");
		table.setWidth("100%");
		table.setCellpadding(0);
		table.setCellspacing(0);
		
		Collection<Case> cases = getCases(iwc, startingEntry, numberOfEntries);

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("casesCount");
		cell.add(new Text(getResourceBundle().getLocalizedString("case_number", "Nr.")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("casesDescription");
		cell.add(new Text(getResourceBundle().getLocalizedString("case_description", "Case description")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("casesDate");
		cell.add(new Text(getResourceBundle().getLocalizedString("date", "Date")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("casesHandler");
		cell.add(new Text(getResourceBundle().getLocalizedString("handler", "Handler")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("casesStatus");
		cell.add(new Text(getResourceBundle().getLocalizedString("status", "Status")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("casesEdit");
		cell.add(new Text(getResourceBundle().getLocalizedString("view", "View")));
		
		group = table.createBodyRowGroup();
		int iRow = 1;
		
		CredentialBusiness credentialBusiness = getCredentialBusiness(iwc);
		
		Iterator<Case> iter = cases.iterator();
		
		while (iter.hasNext()) {
			row = group.createRow();
			Case userCase = iter.next();
			if (iRow == 1) {
				row.setStyleClass("firstRow");
			}
			else if (!iter.hasNext()) {
				row.setStyleClass("lastRow");
			}

			try {
				
				CaseManager caseManager;
				
				if(userCase.getCaseManagerType() != null)
					caseManager = getCaseHandlersProvider().getCaseHandler(userCase.getCaseManagerType());
				else 
					caseManager = null;
				
				CaseBusiness caseBusiness = CaseCodeManager.getInstance().getCaseBusinessOrDefault(userCase.getCaseCode(), iwc);
				String subject = caseBusiness.getCaseSubject(userCase, iwc.getCurrentLocale());
				String fullSubject = subject;
				if (this.iMaxNumberOfLetters > 0 && this.iMaxNumberOfLetters < subject.length()) {
					subject = subject.substring(0, (this.iMaxNumberOfLetters + 1)) + "...";
				}
				
				IWTimestamp created = new IWTimestamp(userCase.getCreated());
				String handler = "-";
				if (userCase.getHandler() != null) {
					if (!userCase.getHandler().getPrimaryKey().equals(userCase.getOwner().getPrimaryKey())) {
						handler = getUserBusiness().getNameOfGroupOrUser(userCase.getHandler());
					}
				}
				String caseCode = userCase.getCode();
				row.setStyleClass(caseCode);
				CaseStatus caseStatus = userCase.getCaseStatus();
				String status = caseBusiness.getLocalizedCaseStatusDescription(userCase, caseStatus, iwc.getCurrentLocale());
				
				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				cell.setStyleClass("casesCount");
				
				String caseIdentifier;
				
				if(caseManager != null)
					caseIdentifier = caseManager.getProcessIdentifier(userCase);
				else
					caseIdentifier = null;
				
				if(caseIdentifier != null)
					cell.add(new Text(caseIdentifier));
				else
					cell.add(new Text(userCase.getPrimaryKey().toString()));
				
				cell = row.createCell();
				cell.setStyleClass("casesDescription");
				ICPage page = getPage(caseCode, caseStatus.getStatus());
				String caseUrl =  caseBusiness.getUrl(userCase);
				if (page != null) {
					Link link = new Link(subject);
					if (fullSubject.length() != subject.length()) {
						link.setToolTip(fullSubject);
					}
					
					Class eventListener = caseBusiness.getEventListener();
					if (eventListener != null) {
						link.setEventListener(eventListener);
					}
					Map parameters = caseBusiness.getCaseParameters(userCase);
					if (parameters != null) {
						link.setParameter(parameters);
					}
					
					link.addParameter(caseBusiness.getSelectedCaseParameter(), userCase.getPrimaryKey().toString());
					link.setPage(page);
					cell.add(link);
				}
				else if(caseUrl!=null){
					Link link = new Link(subject,caseUrl);
					if (fullSubject.length() != subject.length()) {
						link.setToolTip(fullSubject);
					}
					if(isAddCredentialsToExernalUrls()){
						credentialBusiness.addCredentialsToLink(link, iwc);
					}
					cell.add(link);
				}
				else {
					Text subjectText = new Text(subject);
					if (fullSubject.length() != subject.length()) {
						subjectText.setToolTip(fullSubject);
					}
					cell.add(subjectText);
				}

				cell = row.createCell();
				cell.setStyleClass("casesDate");
				cell.add(new Text(created.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)));

				Text handlerText = new Text("");
				if (this.iMaxNumberOfHandlerLetters > 0 && handler.length() > this.iMaxNumberOfHandlerLetters) {
					handlerText.setToolTip(handler);
					handler = handler.substring(0, this.iMaxNumberOfHandlerLetters + 1) + "...";
				}
				handlerText.addToText(handler);
				
				cell = row.createCell();
				cell.setStyleClass("casesHandler");
				cell.add(handlerText);
				
				cell = row.createCell();
				cell.setStyleClass("casesStatus");
				cell.setStyleClass(caseStatus.getStatus());
				cell.add(new Text(status));

				cell = row.createCell();
				cell.setStyleClass("lastColumn");
				cell.setStyleClass("casesEdit");

				boolean addNonBrakingSpace = true;
				
				if(caseManager != null) {

					List<Link> links = caseManager.getCaseLinks(userCase, "temp_usercases");
					
					if(links != null)
						for (Link link : links)
							cell.add(link);
					
				} else if (page != null) {
					Link link = new Link(getBundle(iwc).getImage("edit.png", getResourceBundle().getLocalizedString("edit_case", "Edit case")));
					link.setStyleClass("caseEdit");
					link.setToolTip(getResourceBundle().getLocalizedString("edit_case", "Edit case"));
					
					Class eventListener = caseBusiness.getEventListener();
					if (eventListener != null) {
						link.setEventListener(eventListener);
					}
					Map parameters = caseBusiness.getCaseParameters(userCase);
					if (parameters != null) {
						link.setParameter(parameters);
					}
					
					link.addParameter(caseBusiness.getSelectedCaseParameter(), userCase.getPrimaryKey().toString());
					link.setPage(page);
					cell.add(link);
					addNonBrakingSpace = false;
				}
				else if(caseUrl != null) {
					Link link = new Link(getBundle(iwc).getImage("edit.png", getResourceBundle().getLocalizedString("edit_case", "Edit case")), caseUrl);
					link.setStyleClass("caseEdit");
					link.setToolTip(getResourceBundle().getLocalizedString("edit_case", "Edit case"));
					if(isAddCredentialsToExernalUrls()){
						credentialBusiness.addCredentialsToLink(link, iwc);
					}
					cell.add(link);
					addNonBrakingSpace = false;
				}
				
				if (caseBusiness.canDeleteCase(userCase)) {
					Link link = new Link(getBundle(iwc).getImage("delete.png", getResourceBundle().getLocalizedString("delete_case", "Delete case")));
					link.setStyleClass("caseDelete");
					link.setEventListener(UserCases.class);
					link.setToolTip(getResourceBundle().getLocalizedString("delete_case", "Delete case"));
					link.addParameter(PARAMETER_UC_CASE_PK, userCase.getPrimaryKey().toString());
					link.setClickConfirmation(getResourceBundle().getLocalizedString("confirm_case_delete", "Are you sure you want to delete this case?"));
					cell.add(link);
					addNonBrakingSpace = false;
				}
				
				if (addNonBrakingSpace) {
					cell.add(Text.getNonBrakingSpace());
				}

				if (iRow % 2 == 0) {
					row.setStyleClass("evenRow");
				}
				else {
					row.setStyleClass("oddRow");
				}
			}
			catch (IBOLookupException ile) {
				ile.printStackTrace();
			}
			
			iRow++;
		}
		
		return table;*/
	}

	protected Collection<Case> getCases(IWContext iwc, int startingEntry, int numberOfEntries) {
		try {
			Collection<Case> cases = getBusiness().getAllCasesForUserExceptCodes(iwc.getCurrentUser(), getUserHiddenCaseCodes(), startingEntry, numberOfEntries);
			return cases;
			
		} catch (FinderException fe) {
			log(fe);
			return Collections.emptyList();
		}
		catch (RemoteException re) {
			log(re);
			return Collections.emptyList();
		}
	}
	
	protected int getCaseCount(IWContext iwc) {
		try {
			return getBusiness().getNumberOfCasesForUserExceptCodes(iwc.getCurrentUser(), getUserHiddenCaseCodes());
		}
		catch (RemoteException re) {
			log(re);
			return 0;
		}
	}
	
	private CaseCode[] getUserHiddenCaseCodes() {
		return getBusiness().getCaseCodesForUserCasesList();
	}
	
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
		this.iMaxNumberOfLetters = maxNumberOfLetters;
	}
	
	public void setMaximumHandlerLength(int maxNumberOfHandlerLetters) {
		this.iMaxNumberOfHandlerLetters = maxNumberOfHandlerLetters;
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

	public CaseManagersProvider getCaseHandlersProvider() {
		
		return (CaseManagersProvider)WFUtil.getBeanInstance(CaseManagersProvider.beanIdentifier);
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
			view = processorView.getCaseManagerView(iwc, TYPE);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		if (view == null) {
			return;
		}
		
		add(view);
	}
	
	protected void showList(IWContext iwc) throws RemoteException {

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("caseElement");
		layer.setID("userCases");
		
		Layer headerLayer = new Layer(Layer.DIV);
		headerLayer.setStyleClass("caseHeader");
		layer.add(headerLayer);
		
		headerLayer.add(new Heading1(getHeading()));
		
		Layer navigationLayer = new Layer(Layer.DIV);
		navigationLayer.setStyleClass("caseNavigation");
		headerLayer.add(navigationLayer);
		
		ListNavigator navigator = new ListNavigator("userCases", getCaseCount(iwc));
		navigator.setFirstItemText(getResourceBundle().getLocalizedString("page", "Page") + ":");
		navigator.setDropdownEntryName(getResourceBundle().getLocalizedString("cases", "cases"));
		if (this.iNumberOfEntriesShown > 0) {
			navigator.setNumberOfEntriesPerPage(this.iNumberOfEntriesShown); 
		}
		navigationLayer.add(navigator);
		
		layer.add(getCasesList(iwc, navigator.getStartingEntry(iwc), this.iMaxNumberOfEntries != -1 ? this.iMaxNumberOfEntries : navigator.getNumberOfEntriesPerPage(iwc)));
		
		add(layer);
	}
}