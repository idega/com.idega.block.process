/*
 * $Id: UserCases.java,v 1.1 2005/10/16 12:50:53 laddi Exp $
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
import java.util.Iterator;
import javax.ejb.FinderException;
import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.business.CaseCodeManager;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.message.business.MessageTypeManager;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.ListNavigator;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Text;
import com.idega.util.IWTimestamp;


/**
 * Last modified: $Date: 2005/10/16 12:50:53 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1 $
 */
public class UserCases extends CaseBlock {
	
	private Collection iHiddenCaseCodes;

	/* (non-Javadoc)
	 * @see com.idega.block.process.presentation.CaseBlock#present(com.idega.presentation.IWContext)
	 */
	protected void present(IWContext iwc) throws Exception {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("caseElement");
		
		Layer headerLayer = new Layer(Layer.DIV);
		headerLayer.setStyleClass("caseHeader");
		layer.add(headerLayer);
		
		Layer navigationLayer = new Layer(Layer.DIV);
		navigationLayer.setStyleClass("caseNavigation");
		headerLayer.add(navigationLayer);
		
		ListNavigator navigator = new ListNavigator("userCases", getCaseCount(iwc));
		navigationLayer.add(navigator);
		
		Layer headingLayer = new Layer(Layer.DIV);
		headingLayer.setStyleClass("caseHeading");
		headingLayer.add(new Text(getResourceBundle().getLocalizedString("user_cases", "User cases")));
		headerLayer.add(headingLayer);
		
		layer.add(getCaseTable(iwc, navigator.getStartingEntry(iwc), navigator.getNumberOfEntriesPerPage(iwc)));
		
		add(layer);
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
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("casesStatus");
		cell.add(new Text(getResourceBundle().getLocalizedString("status", "Status")));
		
		group = table.createBodyRowGroup();
		int iRow = 1;
		
		Iterator iter = cases.iterator();
		while (iter.hasNext()) {
			row = group.createRow();
			Case userCase = (Case) iter.next();

			try {
				CaseBusiness caseBusiness = CaseCodeManager.getInstance().getCaseBusinessOrDefault(userCase.getCaseCode(), iwc);
				String description = caseBusiness.getLocalizedCaseDescription(userCase, iwc.getCurrentLocale());
				IWTimestamp created = new IWTimestamp(userCase.getCreated());
				String handler = "-";
				if (userCase.getHandler() != null) {
					if (!userCase.getHandler().getPrimaryKey().equals(userCase.getOwner().getPrimaryKey())) {
						handler = getUserBusiness().getNameOfGroupOrUser(userCase.getHandler());
					}
				}
				String status = caseBusiness.getLocalizedCaseStatusDescription(userCase.getCaseStatus(), iwc.getCurrentLocale());
				
				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				cell.setStyleClass("casesCount");
				cell.add(new Text(userCase.getPrimaryKey().toString()));
				
				cell = row.createCell();
				cell.setStyleClass("casesDescription");
				cell.add(new Text(description));

				cell = row.createCell();
				cell.setStyleClass("casesDate");
				cell.add(new Text(created.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)));

				cell = row.createCell();
				cell.setStyleClass("casesHandler");
				cell.add(new Text(handler));
				
				cell = row.createCell();
				cell.setStyleClass("lastColumn");
				cell.setStyleClass("casesStatus");
				cell.add(new Text(status));

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

	private Collection getCases(IWContext iwc, int startingEntry, int numberOfEntries) {
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
	
	private int getCaseCount(IWContext iwc) {
		try {
			return getBusiness().getNumberOfCasesForUserExceptCodes(iwc.getCurrentUser(), getUserHiddenCaseCodes());
		}
		catch (RemoteException re) {
			log(re);
			return 0;
		}
	}
	
	private CaseCode[] getUserHiddenCaseCodes(){
		if (iHiddenCaseCodes == null){
			iHiddenCaseCodes = new ArrayList();
		}
		
		iHiddenCaseCodes.addAll(MessageTypeManager.getInstance().getMessageCodes());
		if (iHiddenCaseCodes.isEmpty()) {
			return null;
		}
		
		CaseCode[] codes = new CaseCode[iHiddenCaseCodes.size()];
		
		int index = 0;
		Iterator iter = iHiddenCaseCodes.iterator();
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
	
	public void setHideCaseCode(CaseCode caseCode) {
		if (iHiddenCaseCodes == null) {
			iHiddenCaseCodes = new ArrayList();
		}
		iHiddenCaseCodes.add(caseCode);
	}
	
}