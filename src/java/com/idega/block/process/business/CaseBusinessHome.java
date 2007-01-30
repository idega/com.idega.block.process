package com.idega.block.process.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface CaseBusinessHome extends IBOHome {
	public CaseBusiness create() throws CreateException, RemoteException;
}