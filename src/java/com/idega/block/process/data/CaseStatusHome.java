package com.idega.block.process.data;


public interface CaseStatusHome extends com.idega.data.IDOHome
{
 public CaseStatus create() throws javax.ejb.CreateException, java.rmi.RemoteException;
 public CaseStatus findByPrimaryKey(Object pk) throws javax.ejb.FinderException, java.rmi.RemoteException;

}