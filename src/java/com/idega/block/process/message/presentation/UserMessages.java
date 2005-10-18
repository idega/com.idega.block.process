/*
 * $Id: UserMessages.java,v 1.2 2005/10/18 13:29:25 laddi Exp $
 * Created on Oct 13, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.presentation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.FinderException;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.message.business.MessageValue;
import com.idega.block.process.message.data.Message;
import com.idega.business.IBORuntimeException;
import com.idega.core.builder.data.ICPage;
import com.idega.data.IDOException;
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
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.Form;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
import com.idega.util.text.Name;


/**
 * Last modified: $Date: 2005/10/18 13:29:25 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.2 $
 */
public class UserMessages extends MessageBlock {
	
	private CaseCode messageCode;
	private ICPage iViewerPage;

	/* (non-Javadoc)
	 * @see com.idega.block.process.presentation.CaseBlock#present(com.idega.presentation.IWContext)
	 */
	protected void present(IWContext iwc) throws Exception {
		MessageValue value = new MessageValue();
		value.setReceiver(iwc.getCurrentUser());
		value.setSubject("Test subject");
		value.setBody("This is a test message");
		getMessageBusiness().createMessage(value);
		
		Form form = new Form();
		form.setEventListener(UserMessages.class);
		
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("caseElement");
		layer.setID("userMessages");
		
		Layer headerLayer = new Layer(Layer.DIV);
		headerLayer.setStyleClass("caseHeader");
		layer.add(headerLayer);
		
		Layer navigationLayer = new Layer(Layer.DIV);
		navigationLayer.setStyleClass("caseNavigation");
		headerLayer.add(navigationLayer);
		
		ListNavigator navigator = new ListNavigator("userMessages", getMessageCount(iwc));
		navigationLayer.add(navigator);
		
		Layer headingLayer = new Layer(Layer.DIV);
		headingLayer.setStyleClass("caseHeading");
		headingLayer.add(new Text(getHeading()));
		headerLayer.add(headingLayer);
		
		layer.add(getCaseTable(iwc, navigator.getStartingEntry(iwc), navigator.getNumberOfEntriesPerPage(iwc)));
		
		form.add(layer);
		add(form);
	}
	
	protected String getHeading() {
		return getResourceBundle().getLocalizedString("user_messages", "User messages");
	}
	
	private Table2 getCaseTable(IWContext iwc, int startingEntry, int numberOfEntries) throws RemoteException {
		Table2 table = new Table2();
		table.setStyleClass("caseTable");
		table.setStyleClass("ruler");
		table.setWidth("100%");
		table.setCellpadding(0);
		table.setCellspacing(0);
		
		Collection cases = getMessages(iwc, startingEntry, numberOfEntries);

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("messageCount");
		cell.add(new Text(getResourceBundle().getLocalizedString("message_number", "Nr.")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("messageSubject");
		cell.add(new Text(getResourceBundle().getLocalizedString("subject", "Subject")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("messsageSender");
		cell.add(new Text(getResourceBundle().getLocalizedString("sender", "Sender")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("messageDate");
		cell.add(new Text(getResourceBundle().getLocalizedString("date", "Date")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("messageDelete");
		cell.add(Text.getNonBrakingSpace());
		
		group = table.createBodyRowGroup();
		int iRow = 1;
		int messageNumber = startingEntry + 1;
		
		Iterator iter = cases.iterator();
		while (iter.hasNext()) {
			row = group.createRow();
			Message message = (Message) iter.next();
			if (!getMessageBusiness().isMessageRead(message)) {
				row.setStyleClass("newMessage");
			}
			User sender = message.getSender();
			IWTimestamp created = new IWTimestamp(message.getCreated());

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("messageCount");
			cell.add(new Text(String.valueOf(messageNumber)));
			
			cell = row.createCell();
			cell.setStyleClass("messageSubject");
			
			Link link = new Link(new Text(message.getSubject() != null ? message.getSubject() : getResourceBundle().getLocalizedString("message.no_subject", "No subject")));
			link.addParameter(PARAMETER_MESSAGE_PK, message.getPrimaryKey().toString());
			if (iViewerPage != null) {
				link.setPage(iViewerPage);
			}
			else {
				link.setWindowToOpen(MessageWindow.class);
			}
			cell.add(link);

			cell = row.createCell();
			cell.setStyleClass("messsageSender");
			if (sender != null) {
				Name name = new Name(sender.getFirstName(), sender.getMiddleName(), sender.getLastName());
				cell.add(new Text(name.getName(iwc.getCurrentLocale())));
			}
			else {
				cell.add(new Text("-"));
			}

			cell = row.createCell();
			cell.setStyleClass("messageDate");
			cell.add(new Text(created.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT)));
			
			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("messageDelete");
			
			CheckBox box = new CheckBox(PARAMETER_MESSAGE_PK, message.getPrimaryKey().toString());
			box.setStyleClass("checkbox");
			cell.add(box);

			if (iRow % 2 == 0) {
				row.setStyleClass("evenRow");
			}
			else {
				row.setStyleClass("oddRow");
			}
			
			iRow++;
			messageNumber++;
		}
		
		return table;
	}

	protected Collection getMessages(IWContext iwc, int startingEntry, int numberOfEntries) {
		try {
			return getMessageBusiness().findMessages(messageCode.getCode(), iwc.getCurrentUser(), numberOfEntries, startingEntry);
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
	
	protected int getMessageCount(IWContext iwc) {
		try {
			return getMessageBusiness().getNumberOfMessages(messageCode.getCode(), iwc.getCurrentUser());
		}
		catch (IDOException ie) {
			ie.printStackTrace();
			return 0;
		}
		catch (RemoteException re) {
			log(re);
			return 0;
		}
	}
	
	public void setViewerPage(ICPage page) {
		iViewerPage = page;
	}

	public boolean actionPerformed(IWContext iwc) throws IWException {
		if (iwc.isParameterSet(PARAMETER_MESSAGE_PK)) {
			try {
				String[] messagePKs = iwc.getParameterValues(PARAMETER_MESSAGE_PK);
				for (int i = 0; i < messagePKs.length; i++) {
					getMessageBusiness().deleteMessage(messagePKs[i]);
				}
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

	public void setMessageType(CaseCode code) {
		messageCode = code;
	}
}
