package com.idega.block.process.business;


public interface CaseSession extends com.idega.business.IBOSession
{
 public java.util.Collection getAllCases()throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCases(com.idega.block.process.data.CaseCode p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCases(com.idega.block.process.data.CaseCode p0,com.idega.block.process.data.CaseStatus p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllCases(com.idega.block.process.data.CaseCode p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllCases(java.lang.String p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case createCase(java.lang.String p0)throws java.rmi.RemoteException,javax.ejb.CreateException, java.rmi.RemoteException;
 public java.util.Collection getAllCases(java.lang.String p0,java.lang.String p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public com.idega.block.process.business.CaseBusiness getCaseBusiness()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCases(java.lang.String p0,java.lang.String p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case createCase(com.idega.block.process.data.CaseCode p0)throws java.rmi.RemoteException,javax.ejb.CreateException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCases(java.lang.String p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCases()throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllCases(com.idega.block.process.data.CaseCode p0,com.idega.block.process.data.CaseStatus p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
}
