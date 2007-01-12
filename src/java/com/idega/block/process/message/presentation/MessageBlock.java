/*
 * $Id$
 * Created on Oct 18, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.presentation;

import com.idega.block.process.message.business.MessageBusiness;
import com.idega.block.process.presentation.CaseBlock;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.presentation.IWContext;


public abstract class MessageBlock extends CaseBlock {

	protected static final String PARAMETER_MESSAGE_PK = "um_message_pk";

	private MessageBusiness messageBusiness;
	
	public void main(IWContext iwc) throws Exception {
		this.messageBusiness = getMessageBusiness(iwc);
		super.main(iwc);
	}

	protected MessageBusiness getMessageBusiness() {
		return this.messageBusiness;
	}

	private MessageBusiness getMessageBusiness(IWApplicationContext iwac) {
		try {
			return (MessageBusiness) IBOLookup.getServiceInstance(iwac, MessageBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}