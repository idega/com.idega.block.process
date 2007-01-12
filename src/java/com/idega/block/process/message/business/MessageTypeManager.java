/*
 * $Id: MessageTypeManager.java,v 1.1.2.1 2007/01/12 19:32:43 idegaweb Exp $
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


/**
 * Last modified: $Date: 2007/01/12 19:32:43 $ by $Author: idegaweb $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1.2.1 $
 */
public class MessageTypeManager {

	private static MessageTypeManager messageTypeManager = null;
	
	private Map messageTypeDataMap = null;
	
	public static MessageTypeManager getInstance() { 
		if (messageTypeManager == null) {
			messageTypeManager = new MessageTypeManager();
		}
		return messageTypeManager;
	}
	
	public MessageHome getMessageHome(CaseCode code) throws IDOLookupException {
		return getMessageHome(code.getCode());
	}
	
	public MessageHome getMessageHome(String messageType) throws IDOLookupException {
		Class dataClass = getDataClass(messageType);
		if (dataClass == null) {
			throw new IDOLookupException("[MessageTypeManager]: An entry for message of type "+ messageType +" could not be found");
		}
		return getHome(dataClass);
	}

	public void addDataClassForType(CaseCode code, Class messageData) {
		addDataClassForType(code.getCode(), messageData);
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
		if (this.messageTypeDataMap == null) {
			this.messageTypeDataMap = new HashMap();
		}
		return this.messageTypeDataMap;
	}
	
	public Collection getMessageCodes() {
		if (this.messageTypeDataMap != null) {
			return this.messageTypeDataMap.keySet();
		}
		return new ArrayList();
	}
}