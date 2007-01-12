/*
 * $Id: MessageValue.java,v 1.1.2.1 2007/01/12 19:32:44 idegaweb Exp $
 * Created on Oct 12, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.business;

import com.idega.block.process.data.Case;
import com.idega.user.data.Group;
import com.idega.user.data.User;


/**
 * Last modified: $Date: 2007/01/12 19:32:44 $ by $Author: idegaweb $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1.2.1 $
 */
public class MessageValue {

  Case parentCase; 
  User receiver; 
  User sender; 
  Group handler;
  String subject; 
  String body;
  String messageType;

	public String getBody() {
		return this.body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public Group getHandler() {
		return this.handler;
	}
	
	public void setHandler(Group handler) {
		this.handler = handler;
	}
	
	public Case getParentCase() {
		return this.parentCase;
	}
	
	public void setParentCase(Case parentCase) {
		this.parentCase = parentCase;
	}
	
	public User getReceiver() {
		return this.receiver;
	}
	
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	public User getSender() {
		return this.sender;
	}
	
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageType() {
		return this.messageType;
	}
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
}