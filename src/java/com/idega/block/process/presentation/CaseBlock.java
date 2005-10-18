/*
 * $Id: CaseBlock.java,v 1.2 2005/10/18 13:29:25 laddi Exp $
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
 * Last modified: $Date: 2005/10/18 13:29:25 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.2 $
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
		business = getCaseBusiness(iwc);
		userBusiness = getUserBusiness(iwc);
	}
	
	protected IWBundle getBundle() {
		return iwb;
	}
	
	protected IWResourceBundle getResourceBundle() {
		return iwrb;
	}
	
	protected CaseBusiness getBusiness() {
		return business;
	}

	private CaseBusiness getCaseBusiness(IWApplicationContext iwac) {
		try {
			return (CaseBusiness) IBOLookup.getServiceInstance(iwac, CaseBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
	
	protected UserBusiness getUserBusiness() {
		return userBusiness;
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