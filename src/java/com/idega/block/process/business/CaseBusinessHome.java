/**
 * 
 */
package com.idega.block.process.business;



import com.idega.business.IBOHome;

/**
 * @author bluebottle
 *
 */
public interface CaseBusinessHome extends IBOHome {
	public CaseBusiness create() throws javax.ejb.CreateException,
			java.rmi.RemoteException;

}
