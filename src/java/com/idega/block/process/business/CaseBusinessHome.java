/*
 * $Id: CaseBusinessHome.java,v 1.6 2005/01/22 15:55:09 laddi Exp $
 * Created on 22.1.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;



import com.idega.business.IBOHome;


/**
 * Last modified: $Date: 2005/01/22 15:55:09 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.6 $
 */
public interface CaseBusinessHome extends IBOHome {

	public CaseBusiness create() throws javax.ejb.CreateException, java.rmi.RemoteException;

}
