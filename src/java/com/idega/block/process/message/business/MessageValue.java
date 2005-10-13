/*
 * $Id: MessageValue.java,v 1.1 2005/10/13 18:20:38 laddi Exp $
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
 * Last modified: $Date: 2005/10/13 18:20:38 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1 $
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
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public Group getHandler() {
		return handler;
	}
	
	public void setHandler(Group handler) {
		this.handler = handler;
	}
	
	public Case getParentCase() {
		return parentCase;
	}
	
	public void setParentCase(Case parentCase) {
		this.parentCase = parentCase;
	}
	
	public User getReceiver() {
		return receiver;
	}
	
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	public User getSender() {
		return sender;
	}
	
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageType() {
		return messageType;
	}
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
}