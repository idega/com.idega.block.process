/**
 * NewCaseBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.idega.block.process.webservice;

import java.rmi.RemoteException;
import javax.ejb.FinderException;
import com.idega.block.process.business.CaseBusiness;
import com.idega.business.IBOLookup;
import com.idega.idegaweb.IWMainApplication;

public class NewCaseBindingImpl implements com.idega.block.process.webservice.NewCasePort{
    public java.lang.String createNewCase(com.idega.block.process.webservice._case part_request) throws java.rmi.RemoteException {
    	
		CaseBusiness bus1 = (CaseBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CaseBusiness.class);
		try {
			CaseBusiness business = bus1.getCaseBusiness(part_request.getCode());
			business.createOrUpdateCase(part_request);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return "failed";
		}
		catch (RemoteException e) {
			e.printStackTrace();
			return "failed";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
    	
		return "success";
    	
    }

}
