/*
 * $Id: MessageBusinessHomeImpl.java,v 1.2 2005/10/18 13:29:25 laddi Exp $
 * Created on Oct 18, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.business;

import com.idega.business.IBOHomeImpl;


/**
 * <p>
 * TODO laddi Describe Type MessageBusinessHomeImpl
 * </p>
 *  Last modified: $Date: 2005/10/18 13:29:25 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.2 $
 */
public class MessageBusinessHomeImpl extends IBOHomeImpl implements MessageBusinessHome {

	protected Class getBeanInterfaceClass() {
		return MessageBusiness.class;
	}

	public MessageBusiness create() throws javax.ejb.CreateException {
		return (MessageBusiness) super.createIBO();
	}
}
