package com.idega.block.process.business;

import javax.ejb.*;

public interface CaseBusiness extends com.idega.business.IBOService
{
 public com.idega.block.process.data.Case createCase(com.idega.core.user.data.User p0,com.idega.block.process.data.CaseCode p1)throws java.rmi.RemoteException,javax.ejb.CreateException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.core.user.data.User p0,java.lang.String p1,java.lang.String p2)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case createSubCase(com.idega.block.process.data.Case p0,com.idega.block.process.data.CaseCode p1)throws java.rmi.RemoteException,javax.ejb.CreateException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseCode getCaseCode(java.lang.String p0)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.core.user.data.User p0,com.idega.block.process.data.CaseCode p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.core.user.data.User p0,java.lang.String p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.core.user.data.User p0,com.idega.block.process.data.CaseCode p1,com.idega.block.process.data.CaseStatus p2)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public com.idega.block.process.business.CaseBusiness getCaseBusiness(com.idega.block.process.data.CaseCode p0) throws java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.core.user.data.User p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case createSubCase(com.idega.block.process.data.Case p0)throws java.rmi.RemoteException,javax.ejb.CreateException, java.rmi.RemoteException;
 public com.idega.block.process.business.CaseBusiness getCaseBusiness(java.lang.String p0) throws java.rmi.RemoteException;
 public com.idega.block.process.data.Case createCase(int p0,java.lang.String p1)throws java.rmi.RemoteException,javax.ejb.CreateException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.core.user.data.User p0,com.idega.block.process.data.CaseCode p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.core.user.data.User p0,java.lang.String p1)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case getCase(int p0)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.core.user.data.User p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.core.user.data.User p0,com.idega.block.process.data.CaseCode p1,com.idega.block.process.data.CaseStatus p2)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.core.user.data.User p0,java.lang.String p1,java.lang.String p2)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
}
