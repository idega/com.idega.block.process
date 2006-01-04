/*
 * $Id: CaseBusinessHome.java,v 1.12 2006/01/04 14:18:36 gimmi Exp $
 * Created on Jan 4, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import com.idega.business.IBOHome;


/**
 * <p>
 * TODO gimmi Describe Type CaseBusinessHome
 * </p>
 *  Last modified: $Date: 2006/01/04 14:18:36 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.12 $
 */
public interface CaseBusinessHome extends IBOHome {

	public CaseBusiness create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
