/*
 * $Id: CaseBusinessHomeImpl.java,v 1.14 2006/03/30 11:19:50 thomas Exp $
 * Created on Mar 27, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import com.idega.business.IBOHomeImpl;


/**
 * 
 *  Last modified: $Date: 2006/03/30 11:19:50 $ by $Author: thomas $
 * 
 * @author <a href="mailto:thomas@idega.com">thomas</a>
 * @version $Revision: 1.14 $
 */
public class CaseBusinessHomeImpl extends IBOHomeImpl implements CaseBusinessHome {

	protected Class getBeanInterfaceClass() {
		return CaseBusiness.class;
	}

	public CaseBusiness create() throws javax.ejb.CreateException {
		return (CaseBusiness) super.createIBO();
	}
}
