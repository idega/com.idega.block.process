/*
 * $Id: MessageTypeManager.java,v 1.2 2006/03/23 15:40:27 thomas Exp $
 * Created on Oct 12, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.message.data.MessageHome;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.repository.data.Instantiator;
import com.idega.repository.data.Singleton;
import com.idega.repository.data.SingletonRepository;


/**
 * Last modified: $Date: 2006/03/23 15:40:27 $ by $Author: thomas $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.2 $
 */
public class MessageTypeManager implements Singleton {

	private String defaultMessageType = null;
	
	private Map messageTypeDataMap = null;
	
	private static Instantiator instantiator = new Instantiator() { public Object getInstance() { return new MessageTypeManager();}};

	public static MessageTypeManager getInstance() {
		return (MessageTypeManager) SingletonRepository.getRepository().getInstance(MessageTypeManager.class,instantiator);
	}

	/**
	 * Uses the default message type.
	 * Works only if the default message type was set before.
	 * 
	 * @return the default message home 
	 * @throws IDOLookupException
	 */
	public MessageHome getMessageHome() throws IDOLookupException {
		return getMessageHome(defaultMessageType);
	}
	
	public MessageHome getMessageHome(CaseCode code) throws IDOLookupException {
		return getMessageHome(code.getCode());
	}
	
	/**
	 * 
	 * Uses the default message type if the specified message type is null.
	 * Works only if a default message type was set before.
	 * 
	 * @param messageType
	 * @return
	 * @throws IDOLookupException
	 */
	public MessageHome getMessageHome(String messageType) throws IDOLookupException {
		if (messageType == null) {
			messageType = defaultMessageType;
		}
		Class dataClass = getDataClass(messageType);
		if (dataClass == null) {
			throw new IDOLookupException("[MessageTypeManager]: An entry for message of type "+ messageType +" could not be found");
		}
		return getHome(dataClass);
	}

	public void addDataClassForType(CaseCode code, Class messageData) {
		addDataClassForType(code.getCode(), messageData);
	}

	/**
	 * Sets the specified message type as default. 
	 * The default message type remains accessible by the specified message type.
	 * 
	 * @param messageType
	 * @param messageData
	 */
	public void addDataClassForDefaultType(String messageType, Class messageData) {
		defaultMessageType = messageType;
		addDataClassForType(messageType, messageData);
	}
	
	
	public void addDataClassForType(String messageType, Class messageData) {
		getMessageTypeDataMap().put(messageType, messageData);
	}
	
	private MessageHome getHome(Class dataClass) throws IDOLookupException {
		return (MessageHome) IDOLookup.getHome(dataClass);
	}
	
	private Class getDataClass(String messageType) {
		return (Class) getMessageTypeDataMap().get(messageType);
	}
	
	private Map getMessageTypeDataMap() {
		if (messageTypeDataMap == null) {
			messageTypeDataMap = new HashMap();
		}
		return messageTypeDataMap;
	}
	
	public Collection getMessageCodes() {
		if (messageTypeDataMap != null) {
			return messageTypeDataMap.keySet();
		}
		return new ArrayList();
	}
}