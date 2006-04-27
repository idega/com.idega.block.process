/*
 * $Id: TicketBusiness.java,v 1.2 2006/04/27 20:08:05 thomas Exp $
 * Created on Mar 28, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.security.business;

import com.idega.block.process.data.Case;
import com.idega.business.IBOService;


/**
 * 
 *  Last modified: $Date: 2006/04/27 20:08:05 $ by $Author: thomas $
 * 
 * @author <a href="mailto:thomas@idega.com">thomas</a>
 * @version $Revision: 1.2 $
 */
public interface TicketBusiness extends IBOService {

	/**
	 * @see com.idega.block.process.security.business.TicketBusinessBean#getNameForEncodedTicket
	 */
	public String getNameForEncodedTicket() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.security.business.TicketBusinessBean#getEncodedTicket
	 */
	public String getEncodedTicket(Case theCase) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.security.business.TicketBusinessBean#validateTicket
	 */
	public boolean validateTicket(String socialsecurity, String ticket) throws java.rmi.RemoteException;
}
