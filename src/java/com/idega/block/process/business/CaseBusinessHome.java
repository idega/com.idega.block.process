/*
 * $Id: CaseBusinessHome.java,v 1.2 2004/12/02 15:49:23 laddi Exp $
 * Created on 2.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;



import com.idega.business.IBOHome;


/**
 * Last modified: $Date: 2004/12/02 15:49:23 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.2 $
 */
public interface CaseBusinessHome extends IBOHome {

	public CaseBusiness create() throws javax.ejb.CreateException, java.rmi.RemoteException;

}
