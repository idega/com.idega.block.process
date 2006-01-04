/*
 * $Id: CaseBusinessHomeImpl.java,v 1.12 2006/01/04 14:18:36 gimmi Exp $
 * Created on Jan 4, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import com.idega.business.IBOHomeImpl;


/**
 * <p>
 * TODO gimmi Describe Type CaseBusinessHomeImpl
 * </p>
 *  Last modified: $Date: 2006/01/04 14:18:36 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.12 $
 */
public class CaseBusinessHomeImpl extends IBOHomeImpl implements CaseBusinessHome {

	protected Class getBeanInterfaceClass() {
		return CaseBusiness.class;
	}

	public CaseBusiness create() throws javax.ejb.CreateException {
		return (CaseBusiness) super.createIBO();
	}
}
