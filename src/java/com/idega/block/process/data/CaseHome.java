package com.idega.block.process.data;


public interface CaseHome extends com.idega.data.IDOHome
{
 public Case create() throws javax.ejb.CreateException, java.rmi.RemoteException;
 public Case findByPrimaryKey(Object pk) throws javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0,java.lang.String p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0,java.lang.String p1,java.lang.String p2)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0,com.idega.block.process.data.CaseCode p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection findSubCasesUnder(com.idega.block.process.data.Case p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0,com.idega.block.process.data.CaseCode p1,com.idega.block.process.data.CaseStatus p2)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public int countSubCasesUnder(com.idega.block.process.data.Case p0)throws java.rmi.RemoteException, java.rmi.RemoteException;

}