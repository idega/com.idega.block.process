/*
 * $Id: TicketBusinessBean.java,v 1.3 2006/04/27 20:08:05 thomas Exp $
 * Created on Mar 28, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.security.business;

import com.idega.block.process.data.Case;
import com.idega.block.sso.business.TicketValidator;
import com.idega.business.IBOServiceBean;


/**
 * 
 *  Last modified: $Date: 2006/04/27 20:08:05 $ by $Author: thomas $
 * 
 * @author <a href="mailto:thomas@idega.com">thomas</a>
 * @version $Revision: 1.3 $
 */
public class TicketBusinessBean extends IBOServiceBean  implements TicketBusiness{
	
	private final static String PARAMETER_ENCODED_TICKET = "ticket";
	
	public String getNameForEncodedTicket() {
		return PARAMETER_ENCODED_TICKET;
	}
	
	public String getEncodedTicket(Case theCase) {
		String personalId = theCase.getOwner().getPersonalID();
		TicketValidator ticketValidator = TicketValidator.getInstance();
		return ticketValidator.addTicket(personalId);
 	}
	
	public boolean validateTicket(String socialsecurity, String ticket) {
		TicketValidator ticketValidator = TicketValidator.getInstance();
		return ticketValidator.isValid(socialsecurity, ticket);
	}
	

}
