package com.idega.block.process.data;


public interface CaseCodeHome extends com.idega.data.IDOHome
{
 public CaseCode create() throws javax.ejb.CreateException, java.rmi.RemoteException;
 public CaseCode findByPrimaryKey(Object pk) throws javax.ejb.FinderException, java.rmi.RemoteException;

}