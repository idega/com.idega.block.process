/*
 * $Id: UserMessages.java,v 1.1 2005/10/16 12:50:53 laddi Exp $
 * Created on Oct 13, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.presentation;

import com.idega.block.process.data.CaseCode;
import com.idega.block.process.presentation.CaseBlock;
import com.idega.presentation.IWContext;


/**
 * Last modified: $Date: 2005/10/16 12:50:53 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1 $
 */
public class UserMessages extends CaseBlock {
	
	private CaseCode messageCode;

	/* (non-Javadoc)
	 * @see com.idega.block.process.presentation.CaseBlock#present(com.idega.presentation.IWContext)
	 */
	protected void present(IWContext iwc) throws Exception {
	}
}
