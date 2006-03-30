/*
 * $Id: TicketBusinessHomeImpl.java,v 1.1 2006/03/30 11:21:39 thomas Exp $
 * Created on Mar 28, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.security.business;

import com.idega.business.IBOHomeImpl;


/**
 * 
 *  Last modified: $Date: 2006/03/30 11:21:39 $ by $Author: thomas $
 * 
 * @author <a href="mailto:thomas@idega.com">thomas</a>
 * @version $Revision: 1.1 $
 */
public class TicketBusinessHomeImpl extends IBOHomeImpl implements TicketBusinessHome {

	protected Class getBeanInterfaceClass() {
		return TicketBusiness.class;
	}

	public TicketBusiness create() throws javax.ejb.CreateException {
		return (TicketBusiness) super.createIBO();
	}
}
