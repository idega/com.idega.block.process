/*
 * $Id: CaseBusinessHomeImpl.java,v 1.3 2004/12/06 21:35:23 laddi Exp $
 * Created on 5.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;



import com.idega.business.IBOHomeImpl;


/**
 * Last modified: $Date: 2004/12/06 21:35:23 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.3 $
 */
public class CaseBusinessHomeImpl extends IBOHomeImpl implements CaseBusinessHome {

	protected Class getBeanInterfaceClass() {
		return CaseBusiness.class;
	}

	public CaseBusiness create() throws javax.ejb.CreateException {
		return (CaseBusiness) super.createIBO();
	}

}
