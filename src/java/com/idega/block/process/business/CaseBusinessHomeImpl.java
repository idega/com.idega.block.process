/*
 * $Id: CaseBusinessHomeImpl.java,v 1.7 2005/02/16 11:11:16 laddi Exp $
 * Created on 31.1.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;



import com.idega.business.IBOHomeImpl;


/**
 * Last modified: $Date: 2005/02/16 11:11:16 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.7 $
 */
public class CaseBusinessHomeImpl extends IBOHomeImpl implements CaseBusinessHome {

	protected Class getBeanInterfaceClass() {
		return CaseBusiness.class;
	}

	public CaseBusiness create() throws javax.ejb.CreateException {
		return (CaseBusiness) super.createIBO();
	}

}
