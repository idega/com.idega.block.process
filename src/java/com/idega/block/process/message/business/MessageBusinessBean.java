/*
 * $Id: MessageBusinessBean.java,v 1.10 2008/06/27 11:55:54 alexis Exp $ Created on Oct 12,
 * 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package com.idega.block.process.message.business;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

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
import com.idega.idegaweb.IWCacheManager;
import com.idega.idegaweb.IWUserContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;

/**
 * Last modified: $Date: 2008/06/27 11:55:54 $ by $Author: alexis $
 *
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.10 $
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



	@Override
	public void deleteMessage(Object messagePK) throws FinderException {
		Message message = getMessage(messagePK);
		User owner = message.getOwner();
		changeCaseStatus(message, getCaseStatusDeleted().getStatus(), message.getOwner());
		IWCacheManager.getInstance(getIWMainApplication()).invalidateCacheWithPartialKey(MessageConstants.CACHE_KEY, "_" + owner.getPrimaryKey().toString());
	}

	@Override
	public void markMessageAsRead(Message message) {
		User owner = message.getOwner();
		changeCaseStatus(message, getCaseStatusGranted().getPrimaryKey().toString(), owner);
		IWCacheManager.getInstance(getIWMainApplication()).invalidateCacheWithPartialKey(MessageConstants.CACHE_KEY, "_" + owner.getPrimaryKey().toString());
	}

	@Override
	public boolean isMessageRead(Message message) {
		if ((message.getCaseStatus()).equals(getCaseStatusGranted())) {
			return true;
		}
		return false;
	}

	@Override
	public void flagMessageAsInactive(User performer, Message message) {
		String newCaseStatus = getCaseStatusInactive().getStatus();
		changeCaseStatus(message, newCaseStatus, performer);
	}

	@Override
	public void flagMessagesAsInactive(User performer, String[] msgKeys) throws FinderException {
		String newCaseStatus = getCaseStatusInactive().getStatus();
		flagMessagesWithStatus(performer, msgKeys, newCaseStatus);
	}

	private void flagMessagesWithStatus(User performer, String[] msgKeys, String status) throws FinderException {
		for (int i = 0; i < msgKeys.length; i++) {
			super.changeCaseStatus(Integer.parseInt(msgKeys[i]), status, performer);
		}
	}

	@Override
	public Message getMessage(Object messagePK) throws FinderException {
		Case theCase = getCase(new Integer(messagePK.toString()).intValue());
		return getMessage(theCase.getCode(), messagePK);
	}

	@Override
	public Message getMessage(String messageType, Object messagePK) throws FinderException {
		return getMessageHome(messageType).findByPrimaryKey(messagePK);
	}

	@Override
	public int getNumberOfMessages(String messageType, User user) throws IDOException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).getNumberOfMessages(user, validStatuses);
	}

	@Override
	public int getNumberOfMessages(
			String messageType,
			IWUserContext iwuc,
			Collection<com.idega.user.data.bean.User> receivers,
			Boolean onlyForParentCaseCreator,
			Set<String> parentCasesNotHavingCaseCode
	) throws IDOException, RemoteException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		try {
			return getMessageHome(messageType).getNumberOfMessages(iwuc, receivers, validStatuses, onlyForParentCaseCreator, parentCasesNotHavingCaseCode);
		} catch (FinderException e) {}
		return 0;
	}

	@Override
	public int getNumberOfNewMessages(String messageType, User user) throws IDOException {
		String[] validStatuses = { getCaseStatusOpen().getStatus() };
		return getMessageHome(messageType).getNumberOfMessages(user, validStatuses);
	}

	@Override
	public int getNumberOfMessages(String messageType, User user, Collection groups) throws IDOException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).getNumberOfMessages(user, groups, validStatuses);
	}

	@Override
	public Collection findMessages(String messageType, User user) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(user, validStatuses);
	}

	@Override
	public Collection findMessages(String messageType, User user, int numberOfEntries, int startingEntry) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(user, validStatuses, numberOfEntries, startingEntry);
	}

	@Override
	public Collection findMessages(IWUserContext iwuc, String messageType, Collection<com.idega.user.data.bean.User> receivers, Boolean onlyForParentCaseCreator,
			Set<String> parentCasesNotHavingCaseCode, int numberOfEntries, int startingEntry) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(iwuc, receivers, validStatuses, onlyForParentCaseCreator, parentCasesNotHavingCaseCode, numberOfEntries, startingEntry);
	}

	@Override
	public Collection findMessages(String messageType, User user, Collection groups, int numberOfEntries, int startingEntry) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(user, groups, validStatuses, numberOfEntries, startingEntry);
	}

	@Override
	public Collection findMessages(String messageType, Group group) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(group, validStatuses);
	}

	@Override
	public Collection findMessages(String messageType, Group group, int numberOfEntries, int startingEntry) throws FinderException {
		String[] validStatuses = { getCaseStatusOpen().getStatus(), getCaseStatusGranted().getStatus() };
		return getMessageHome(messageType).findMessages(group, validStatuses, numberOfEntries, startingEntry);
	}

	@Override
	public Message createMessage(String messageType) throws CreateException {
		return getMessageHome(messageType).create();
	}

	@Override
	public Message createMessage(MessageValue msgValue) throws CreateException {
		Message message = createMessage(msgValue.getMessageType());
		message.setOwner(msgValue.getReceiver());
		if (msgValue.getSender() != null) {
			message.setSender(msgValue.getSender());
		}
		if (msgValue.getHandler() != null) {
			message.setHandler(msgValue.getHandler());
		}
		message.setSubject(msgValue.getSubject());
		message.setBody(msgValue.getBody());
		if (msgValue.getParentCase() != null) {
			message.setParentCase(msgValue.getParentCase());
		}

		try {
			message.store();
			IWCacheManager.getInstance(getIWMainApplication()).invalidateCacheWithPartialKey(MessageConstants.CACHE_KEY, "_" + msgValue.getReceiver().getPrimaryKey().toString());
		}
		catch (IDOStoreException idos) {
			throw new IDOCreateException(idos);
		}
		return message;
	}

	/**
	 * This method is overwritten in CommuneMessageBusiness
	 */
	@Override
	public Message createUserMessage(Case parentCase, User receiver, String subject, String body, boolean sendLetter) throws CreateException {
		MessageValue messageValue = new MessageValue();
		setSimpleMessage(messageValue, parentCase, receiver, subject, body);
		return createMessage(messageValue);
	}

	protected void setSimpleMessage(MessageValue messageValue, Case parentCase, User receiver, String subject, String body) {
		messageValue.setSubject(subject);
		messageValue.setBody(body);
		messageValue.setReceiver(receiver);
		messageValue.setParentCase(parentCase);
	}



	@Override
	public Collection <Message> findMessages(User user, String messageType, String caseId)
			throws FinderException, RemoteException {
		return getMessageHome(messageType).findMessages(user, caseId);
	}



	@Override
	public Collection<Message> findMessagesForUser(String messageType,User user, String status,
			Boolean read) throws FinderException {
		return getMessageHome(messageType).findMessagesForUser(user, status, read);
	}

}