package com.idega.block.process.data;


public interface CaseHome extends com.idega.data.IDOHome
{
 public Case create() throws javax.ejb.CreateException, java.rmi.RemoteException;
 public Case findByPrimaryKey(Object pk) throws javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection findSubCasesUnder(com.idega.block.process.data.Case p0)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.user.data.User p0)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.user.data.User p0,com.idega.block.process.data.CaseCode p1,com.idega.block.process.data.CaseStatus p2)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.user.data.User p0,java.lang.String p1,java.lang.String p2)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.user.data.User p0,com.idega.block.process.data.CaseCode p1)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection findAllCasesByUser(com.idega.user.data.User p0,java.lang.String p1)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.lang.String getCaseStatusCancelled() throws java.rmi.RemoteException;
 public java.lang.String getCaseStatusGranted() throws java.rmi.RemoteException;
 public int countSubCasesUnder(com.idega.block.process.data.Case p0)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public java.lang.String getCaseStatusInactive() throws java.rmi.RemoteException;
 public java.lang.String getCaseStatusOpen() throws java.rmi.RemoteException;
 public java.lang.String getCaseStatusDenied() throws java.rmi.RemoteException;
 public java.lang.String getCaseStatusReview() throws java.rmi.RemoteException;
 public java.lang.String getCaseStatusPreliminary() throws java.rmi.RemoteException;
 public java.lang.String getCaseStatusContract() throws java.rmi.RemoteException;
 public java.lang.String getCaseStatusReady() throws java.rmi.RemoteException;
 public java.lang.String getCaseStatusRedeem() throws java.rmi.RemoteException;
 public java.lang.String getCaseStatusError() throws java.rmi.RemoteException;
public java.util.Collection findAllCasesForUserExceptCodes(com.idega.user.data.User p0,CaseCode[] codes)throws javax.ejb.FinderException,java.rmi.RemoteException;

}