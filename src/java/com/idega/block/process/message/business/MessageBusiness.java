/*
 * $Id: MessageBusiness.java,v 1.3 2006/04/25 12:55:13 thomas Exp $
 * Created on Oct 18, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.business;

import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.data.Case;
import com.idega.block.process.message.data.Message;
import com.idega.business.IBOService;
import com.idega.data.IDOException;
import com.idega.user.data.Group;
import com.idega.user.data.User;


/**
 * <p>
 * TODO laddi Describe Type MessageBusiness
 * </p>
 *  Last modified: $Date: 2006/04/25 12:55:13 $ by $Author: thomas $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.3 $
 */
public interface MessageBusiness extends IBOService, CaseBusiness {

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#deleteMessage
	 */
	public void deleteMessage(Object messagePK) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#markMessageAsRead
	 */
	public void markMessageAsRead(Message message) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#isMessageRead
	 */
	public boolean isMessageRead(Message message) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#flagMessageAsInactive
	 */
	public void flagMessageAsInactive(User performer, Message message) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#flagMessagesAsInactive
	 */
	public void flagMessagesAsInactive(User performer, String[] msgKeys) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#getMessage
	 */
	public Message getMessage(Object messagePK) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#getMessage
	 */
	public Message getMessage(String messageType, Object messagePK) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#getNumberOfMessages
	 */
	public int getNumberOfMessages(String messageType, User user) throws IDOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#getNumberOfNewMessages
	 */
	public int getNumberOfNewMessages(String messageType, User user) throws IDOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#getNumberOfMessages
	 */
	public int getNumberOfMessages(String messageType, User user, Collection groups) throws IDOException,
			java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#findMessages
	 */
	public Collection findMessages(String messageType, User user) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#findMessages
	 */
	public Collection findMessages(String messageType, User user, int numberOfEntries, int startingEntry)
			throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#findMessages
	 */
	public Collection findMessages(String messageType, User user, Collection groups, int numberOfEntries,
			int startingEntry) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#findMessages
	 */
	public Collection findMessages(String messageType, Group group) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#findMessages
	 */
	public Collection findMessages(String messageType, Group group, int numberOfEntries, int startingEntry)
			throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#createMessage
	 */
	public Message createMessage(String messageType) throws CreateException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#createMessage
	 */
	public Message createMessage(MessageValue msgValue) throws CreateException, java.rmi.RemoteException;
	
	/**
	 * @see com.idega.block.process.message.business.MessageBusinessBean#createUserMessage
	 */
	public Message createUserMessage(Case parentCase, User receiver, String subject, String body, boolean sendLetter) throws CreateException, java.rmi.RemoteException;	
	
}