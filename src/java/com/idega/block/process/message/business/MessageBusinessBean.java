/*
 * $Id: MessageBusinessBean.java,v 1.2 2005/10/14 13:04:53 laddi Exp $ Created on Oct 12,
 * 2005
 * 
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package com.idega.block.process.message.business;

import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.business.CaseBusinessBean;
import com.idega.block.process.data.Case;
import com.idega.block.process.message.data.Message;
import com.idega.block.process.message.data.MessageHome;
import com.idega.business.IBORuntimeException;
import com.idega.data.IDOCreateException;
import com.idega.data.IDOException;
import com.idega.data.IDOLookupException;
import com.idega.data.IDOStoreException;
import com.idega.user.data.Group;
import com.idega.user.data.User;

/**
 * Last modified: $Date: 2005/10/14 13:04:53 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.2 $
 */
public class MessageBusinessBean extends CaseBusinessBean implements MessageBusiness, CaseBusiness {

	private MessageHome getMessageHome(String messageType) {
		try {
			return MessageTypeManager.getInstance().getMessageHome(messageType);
		}
		catch (IDOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	public void deleteMessage(String messageType, Object messagePK) throws FinderException, RemoveException {
		getMessage(messageType, messagePK).remove();
	}

	public void markMessageAsRead(Message message) {
		changeCaseStatus(message, getCaseStatusGranted().getPrimaryKey().toString(), message.getOwner());
	}

	public boolean isMessageRead(Message message) {
		if ((message.getCaseStatus()).equals(getCaseStatusGranted()))
			return true;
		return false;
	}

	public void flagMessageAsInactive(User performer, Message message) {
		String newCaseStatus = getCaseStatusInactive().getStatus();
		changeCaseStatus(message, newCaseStatus, performer);
	}

	public void flagMessagesAsInactive(User performer, String[] msgKeys) throws FinderException {
		String newCaseStatus = getCaseStatusInactive().getStatus();
		flagMessagesWithStatus(performer, msgKeys, newCaseStatus);
	}

	private void flagMessagesWithStatus(User performer, String[] msgKeys, String status) throws FinderException {
		for (int i = 0; i < msgKeys.length; i++) {
			super.changeCaseStatus(Integer.parseInt(msgKeys[i]), status, performer);
		}
	}

	public Message getMessage(Object messagePK) throws FinderException {
		Case theCase = getCase(new Integer(messagePK.toString()).intValue());
		return getMessage(theCase.getCode(), messagePK);
	}
	
	public Message getMessage(String messageType, Object messagePK) throws FinderException {
		return getMessageHome(messageType).findByPrimaryKey(messagePK);
	}

	public int getNumberOfMessages(String messageType, User user) throws IDOException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).getNumberOfMessages(user, validStatuses);
	}

	public int getNumberOfNewMessages(String messageType, User user) throws IDOException {
		String[] validStatuses = { getCaseStatusOpen().getStatus() };
		return getMessageHome(messageType).getNumberOfMessages(user, validStatuses);
	}

	public int getNumberOfMessages(String messageType, User user, Collection groups) throws IDOException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).getNumberOfMessages(user, groups, validStatuses);
	}

	public Collection findMessages(String messageType, User user) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(user, validStatuses);
	}

	public Collection findMessages(String messageType, User user, int numberOfEntries, int startingEntry) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(user, validStatuses, numberOfEntries, startingEntry);
	}

	public Collection findMessages(String messageType, User user, Collection groups, int numberOfEntries, int startingEntry) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(user, groups, validStatuses, numberOfEntries, startingEntry);
	}

	public Collection findMessages(String messageType, Group group) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(group, validStatuses);
	}

	public Collection findMessages(String messageType, Group group, int numberOfEntries, int startingEntry) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(group, validStatuses, numberOfEntries, startingEntry);
	}

	protected Message createMessage(String messageType) throws CreateException {
		return getMessageHome(messageType).create();
	}

	protected Message createMessage(MessageValue msgValue) throws CreateException {
		Message message = createMessage(msgValue.getMessageType());
		message.setOwner(msgValue.getReceiver());
		if (msgValue.getSender() != null) {
			message.setSender(msgValue.getSender());
		}
		if (message.getHandler() != null) {
			message.setHandler(msgValue.getHandler());
		}
		message.setSubject(msgValue.getSubject());
		message.setBody(msgValue.getBody());
		if (message.getParentCase() != null) {
			message.setParentCase(msgValue.getParentCase());
		}

		try {
			message.store();
		}
		catch (IDOStoreException idos) {
			throw new IDOCreateException(idos);
		}
		return message;
	}
}