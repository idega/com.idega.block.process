/*
 * $Id: TicketValidatorSessionListener.java,v 1.1 2006/03/30 11:21:39 thomas Exp $
 * Created on Mar 28, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.security;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * 
 *  Last modified: $Date: 2006/03/30 11:21:39 $ by $Author: thomas $
 * 
 * @author <a href="mailto:thomas@idega.com">thomas</a>
 * @version $Revision: 1.1 $
 */
public class TicketValidatorSessionListener implements HttpSessionListener {
	
	private TicketValidator ticketValidator = null;

		/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		if (ticketValidator == null) {
			ticketValidator = TicketValidator.getInstance();
		}
		HttpSession session = sessionEvent.getSession();
		ticketValidator.removeTicket(session);
	}
}
