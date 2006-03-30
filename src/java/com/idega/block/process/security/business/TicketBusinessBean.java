/*
 * $Id: TicketBusinessBean.java,v 1.1 2006/03/30 11:21:39 thomas Exp $
 * Created on Mar 28, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.security.business;

import com.idega.block.process.data.Case;
import com.idega.block.process.security.TicketValidator;
import com.idega.business.IBOServiceBean;


/**
 * 
 *  Last modified: $Date: 2006/03/30 11:21:39 $ by $Author: thomas $
 * 
 * @author <a href="mailto:thomas@idega.com">thomas</a>
 * @version $Revision: 1.1 $
 */
public class TicketBusinessBean extends IBOServiceBean  implements TicketBusiness{
	
	private final static String PARAMETER_ENCODED_TICKET = "ticket";
	
	public String getNameForEncodedTicket() {
		return PARAMETER_ENCODED_TICKET;
	}
	
	public String getEncodedTicket(Case theCase) {
		TicketValidator ticketValidator = TicketValidator.getInstance();
		return ticketValidator.addTicket(theCase);
 	}
	
	public boolean validateTicket(String ticket) {
		TicketValidator ticketValidator = TicketValidator.getInstance();
		return ticketValidator.validateTicket(ticket);
	}
	

}
