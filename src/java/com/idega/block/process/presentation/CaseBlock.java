/*
 * $Id: CaseBlock.java,v 1.3.2.1 2007/01/12 19:32:32 idegaweb Exp $
 * Created on Sep 24, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.presentation;

import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.business.CaseConstants;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.user.business.UserBusiness;


/**
 * Last modified: $Date: 2007/01/12 19:32:32 $ by $Author: idegaweb $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.3.2.1 $
 */
public abstract class CaseBlock extends Block {

	private CaseBusiness business;
	private UserBusiness userBusiness;
	
	private IWBundle iwb;
	private IWResourceBundle iwrb;
	
	public void main(IWContext iwc) throws Exception {
		initialize(iwc);
		present(iwc);
	}

	protected abstract void present(IWContext iwc) throws Exception;

	public String getBundleIdentifier() {
		return CaseConstants.IW_BUNDLE_IDENTIFIER;
	}

	private void initialize(IWContext iwc) {
		setResourceBundle(getResourceBundle(iwc));
		setBundle(getBundle(iwc));
		this.business = getCaseBusiness(iwc);
		this.userBusiness = getUserBusiness(iwc);
	}
	
	protected IWBundle getBundle() {
		return this.iwb;
	}
	
	protected IWResourceBundle getResourceBundle() {
		return this.iwrb;
	}
	
	protected CaseBusiness getBusiness() {
		return this.business;
	}

	protected CaseBusiness getCaseBusiness(IWApplicationContext iwac) {
		try {
			return (CaseBusiness) IBOLookup.getServiceInstance(iwac, CaseBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
	
	protected UserBusiness getUserBusiness() {
		return this.userBusiness;
	}
	
	private UserBusiness getUserBusiness(IWApplicationContext iwac) {
		try {
			return (UserBusiness) IBOLookup.getServiceInstance(iwac, UserBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
	private void setBundle(IWBundle iwb) {
		this.iwb = iwb;
	}
	
	private void setResourceBundle(IWResourceBundle iwrb) {
		this.iwrb = iwrb;
	}
}