/*
 * $Id: MessageBusinessHomeImpl.java,v 1.1 2005/10/13 18:20:38 laddi Exp $
 * Created on Oct 12, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.business;

import com.idega.business.IBOHomeImpl;


/**
 * Last modified: $Date: 2005/10/13 18:20:38 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1 $
 */
public class MessageBusinessHomeImpl extends IBOHomeImpl implements MessageBusinessHome {

	protected Class getBeanInterfaceClass() {
		return MessageBusiness.class;
	}

	public MessageBusiness create() throws javax.ejb.CreateException {
		return (MessageBusiness) super.createIBO();
	}
}