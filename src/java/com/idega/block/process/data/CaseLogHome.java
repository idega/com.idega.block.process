package com.idega.block.process.data;


public interface CaseLogHome extends com.idega.data.IDOHome
{
 public CaseLog create() throws javax.ejb.CreateException, java.rmi.RemoteException;
 public CaseLog findByPrimaryKey(Object pk) throws javax.ejb.FinderException, java.rmi.RemoteException;
 public CaseLog findLastCaseLogForCase(com.idega.block.process.data.Case p0)throws javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection findAllCaseLogsByCase(com.idega.block.process.data.Case p0)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;

}