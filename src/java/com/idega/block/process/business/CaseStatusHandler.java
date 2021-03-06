/*
 * $Id: CaseStatusHandler.java,v 1.3 2007/05/24 11:35:34 valdas Exp $
 * Created on Oct 19, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.idega.block.process.data.CaseStatus;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.core.builder.presentation.ICPropertyHandler;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObject;
import com.idega.presentation.ui.DropdownMenu;


/**
 * Last modified: $Date: 2007/05/24 11:35:34 $ by $Author: valdas $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.3 $
 */
public class CaseStatusHandler implements ICPropertyHandler {

	/* (non-Javadoc)
	 * @see com.idega.core.builder.presentation.ICPropertyHandler#getDefaultHandlerTypes()
	 */
	public List getDefaultHandlerTypes() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.builder.presentation.ICPropertyHandler#getHandlerObject(java.lang.String, java.lang.String, com.idega.presentation.IWContext)
	 */
	public PresentationObject getHandlerObject(String name, String stringValue, IWContext iwc, boolean oldGenerationHandler, String instanceId, String method) {
		DropdownMenu menu = new DropdownMenu(name);
		menu.addMenuElementFirst("", "Select");
		
		try {
			Collection statuses = getCaseBusiness(iwc).getCaseStatuses();
			Iterator iter = statuses.iterator();
			while (iter.hasNext()) {
				CaseStatus status = (CaseStatus) iter.next();
				menu.addMenuElement(status.getStatus(), status.getDescription() != null ? status.getDescription() : status.getStatus());
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		if (stringValue != null) {
			menu.setSelectedElement(stringValue);
		}
		
		return menu;
	}

	/* (non-Javadoc)
	 * @see com.idega.core.builder.presentation.ICPropertyHandler#onUpdate(java.lang.String[], com.idega.presentation.IWContext)
	 */
	public void onUpdate(String[] values, IWContext iwc) {
	}
	
	protected CaseBusiness getCaseBusiness(IWApplicationContext iwac) {
		try {
			return (CaseBusiness) IBOLookup.getServiceInstance(iwac, CaseBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}