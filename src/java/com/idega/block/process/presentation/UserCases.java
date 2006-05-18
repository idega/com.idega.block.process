/*
 * $Id: UserCases.java,v 1.23 2006/05/18 16:53:05 thomas Exp $
 * Created on Sep 25, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.presentation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.FinderException;
import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.business.CaseCodeManager;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseStatus;
import com.idega.block.process.message.business.MessageTypeManager;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.core.accesscontrol.business.CredentialBusiness;
import com.idega.core.builder.data.ICPage;
import com.idega.event.IWPageEventListener;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWException;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.ListNavigator;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.util.IWTimestamp;


/**
 * Last modified: $Date: 2006/05/18 16:53:05 $ by $Author: thomas $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.23 $
 */
public class UserCases extends CaseBlock implements IWPageEventListener {
	
	private static final String PARAMETER_CASE_PK = "uc_case_pk";
	
	private Collection iHiddenCaseCodes;
	private Map pageMap;
	private int iMaxNumberOfEntries = -1;
	private int iMaxNumberOfLetters = -1;
	private int iMaxNumberOfHandlerLetters = -1;
	private int iNumberOfEntriesShown = -1;

	/* (non-Javadoc)
	 * @see com.idega.block.process.presentation.CaseBlock#present(com.idega.presentation.IWContext)
	 */
	protected void present(IWContext iwc) throws Exception {
		if (!iwc.isLoggedOn()) {
			add(new Text("No user logged on..."));
			return;
		}

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("caseElement");
		layer.setID("userCases");
		
		Layer headerLayer = new Layer(Layer.DIV);
		headerLayer.setStyleClass("caseHeader");
		layer.add(headerLayer);
		
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
		
		Layer headingLayer = new Layer(Layer.DIV);
		headingLayer.setStyleClass("caseHeading");
		headingLayer.add(new Text(getHeading()));
		headerLayer.add(headingLayer);
		
		layer.add(getCaseTable(iwc, navigator.getStartingEntry(iwc), this.iMaxNumberOfEntries != -1 ? this.iMaxNumberOfEntries : navigator.getNumberOfEntriesPerPage(iwc)));
		
		add(layer);
	}
	
	protected String getHeading() {
		return getResourceBundle().getLocalizedString("user_cases", "User cases");
	}
	
	private Table2 getCaseTable(IWContext iwc, int startingEntry, int numberOfEntries) throws RemoteException {
		Table2 table = new Table2();
		table.setStyleClass("caseTable");
		table.setStyleClass("ruler");
		table.setWidth("100%");
		table.setCellpadding(0);
		table.setCellspacing(0);
		
		Collection cases = getCases(iwc, startingEntry, numberOfEntries);

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
		Iterator iter = cases.iterator();
		while (iter.hasNext()) {
			row = group.createRow();
			Case userCase = (Case) iter.next();
			if (iRow == 1) {
				row.setStyleClass("firstRow");
			}
			else if (!iter.hasNext()) {
				row.setStyleClass("lastRow");
			}

			try {
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
				String status = caseBusiness.getLocalizedCaseStatusDescription(caseStatus, iwc.getCurrentLocale());
				
				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				cell.setStyleClass("casesCount");
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
					credentialBusiness.addCredentialsToLink(link, iwc);
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
				if (page != null) {
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
					credentialBusiness.addCredentialsToLink(link, iwc);
					cell.add(link);
					addNonBrakingSpace = false;
				}
				
				if (caseBusiness.canDeleteCase(userCase)) {
					Link link = new Link(getBundle(iwc).getImage("delete.png", getResourceBundle().getLocalizedString("delete_case", "Delete case")));
					link.setStyleClass("caseDelete");
					link.setEventListener(UserCases.class);
					link.setToolTip(getResourceBundle().getLocalizedString("delete_case", "Delete case"));
					link.addParameter(PARAMETER_CASE_PK, userCase.getPrimaryKey().toString());
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
		
		return table;
	}

	protected Collection getCases(IWContext iwc, int startingEntry, int numberOfEntries) {
		try {
			return getBusiness().getAllCasesForUserExceptCodes(iwc.getCurrentUser(), getUserHiddenCaseCodes(), startingEntry, numberOfEntries);
		}
		catch (FinderException fe) {
			log(fe);
			return new ArrayList();
		}
		catch (RemoteException re) {
			log(re);
			return new ArrayList();
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
	
	private CaseCode[] getUserHiddenCaseCodes(){
		if (this.iHiddenCaseCodes == null){
			this.iHiddenCaseCodes = new ArrayList();
		}
		
		this.iHiddenCaseCodes.addAll(MessageTypeManager.getInstance().getMessageCodes());
		if (this.iHiddenCaseCodes.isEmpty()) {
			return null;
		}
		
		if (this.iHiddenCaseCodes.isEmpty()) {
			return null;
		}
		
		CaseCode[] codes = new CaseCode[this.iHiddenCaseCodes.size()];
		
		int index = 0;
		Iterator iter = this.iHiddenCaseCodes.iterator();
		while (iter.hasNext()) {
			String code = (String) iter.next();
			try {
				codes[index++] = getBusiness().getCaseCode(code);
			}
			catch (FinderException fe) {
				fe.printStackTrace();
				return null;
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		
		return codes;
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
	
	public void setHideCaseCode(CaseCode caseCode) {
		if (this.iHiddenCaseCodes == null) {
			this.iHiddenCaseCodes = new ArrayList();
		}
		this.iHiddenCaseCodes.add(caseCode);
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
		if (iwc.isParameterSet(PARAMETER_CASE_PK)) {
			try {
				Case userCase = getCaseBusiness(iwc).getCase(iwc.getParameter(PARAMETER_CASE_PK));
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
	
	private CredentialBusiness getCredentialBusiness(IWApplicationContext iwac) {
		try {
			return (CredentialBusiness) IBOLookup.getServiceInstance(iwac, CredentialBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException();
		}
	}
	
	public void setMaximumHandlerLength(int maxNumberOfHandlerLetters) {
		this.iMaxNumberOfHandlerLetters = maxNumberOfHandlerLetters;
	}
	
	public void setNumberOfEntriesShownPerPage(int numberOfEntriesShown) {
		this.iNumberOfEntriesShown = numberOfEntriesShown;
	}
}