/*
 * $Id: CaseCodeManager.java,v 1.1 2004/10/19 17:20:44 thomas Exp $
 * Created on Oct 19, 2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import java.util.HashMap;
import java.util.Map;
import com.idega.block.process.data.CaseCode;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWApplicationContext;


/**
 * 
 *  Last modified: $Date: 2004/10/19 17:20:44 $ by $Author: thomas $
 * 
 * @author <a href="mailto:thomas@idega.com">thomas</a>
 * @version $Revision: 1.1 $
 */
public class CaseCodeManager {
	
	private static CaseCodeManager caseCodeManager = null;
	
	private Map caseCodeBusinessMap = null;
	
	public static CaseCodeManager getInstance() { 
		if (caseCodeManager == null) {
			caseCodeManager = new CaseCodeManager();
		}
		return caseCodeManager;
	}
	
	
	public CaseBusiness getCaseBusinessOrDefault(CaseCode caseCode, IWApplicationContext iwac) throws IBOLookupException {
		Class businessClass = getBusinessClass(caseCode);
		if (businessClass == null) {
			businessClass = CaseBusiness.class;
		}
		return getBusiness(businessClass, iwac);
	}
	
	
	public CaseBusiness getCaseBusiness(CaseCode caseCode, IWApplicationContext iwac) throws IBOLookupException {
		Class businessClass = getBusinessClass(caseCode);
		if (businessClass == null) {
			throw new  IBOLookupException("[CaseCodeManager]: An entry for case code "+ caseCode.getCode() +" could not be found");
		}
		return getBusiness(businessClass, iwac);
	}

	public void addCaseBusinessForCode(CaseCode caseCode, Class caseBusiness) {
		addCaseBusinessForCode(caseCode.getCode(), caseBusiness);
	}
		
	public void addCaseBusinessForCode(String code, Class caseBusiness) {
		getCaseCodeBusinessMap().put(code, caseBusiness);
	}

	private CaseBusiness getBusiness(Class serviceClass, IWApplicationContext iwac) throws IBOLookupException {
		return (CaseBusiness) IBOLookup.getServiceInstance(iwac, serviceClass);
	}
	
	private Class getBusinessClass(CaseCode caseCode) {
		String code = caseCode.getCode();
		return (Class) getCaseCodeBusinessMap().get(code);
	}
	
	private Map getCaseCodeBusinessMap() {
		if (caseCodeBusinessMap == null) {
			caseCodeBusinessMap = new HashMap();
		}
		return caseCodeBusinessMap;
	}
}
