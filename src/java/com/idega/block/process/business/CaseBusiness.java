package com.idega.block.process.business;


public interface CaseBusiness extends com.idega.business.IBOService
{
 public void changeCaseStatus(int p0,java.lang.String p1,com.idega.user.data.User p2)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public void changeCaseStatus(com.idega.block.process.data.Case p0,java.lang.String p1,com.idega.user.data.User p2)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case createCase(com.idega.user.data.User p0,com.idega.block.process.data.CaseCode p1)throws javax.ejb.CreateException,java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case createCase(int p0,java.lang.String p1)throws javax.ejb.CreateException,java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case createSubCase(com.idega.block.process.data.Case p0)throws javax.ejb.CreateException,java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case createSubCase(com.idega.block.process.data.Case p0,com.idega.block.process.data.CaseCode p1)throws javax.ejb.CreateException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.user.data.User p0,com.idega.block.process.data.CaseCode p1)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.user.data.User p0)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.user.data.User p0,com.idega.block.process.data.CaseCode p1,com.idega.block.process.data.CaseStatus p2)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.user.data.User p0,java.lang.String p1)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllActiveCasesForUser(com.idega.user.data.User p0,java.lang.String p1,java.lang.String p2)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForGroup(com.idega.user.data.Group p0)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForGroupExceptCodes(com.idega.user.data.Group p0,com.idega.block.process.data.CaseCode[] p1)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.user.data.User p0,java.lang.String p1,java.lang.String p2)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.user.data.User p0)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.user.data.User p0,com.idega.block.process.data.CaseCode p1)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.user.data.User p0,com.idega.block.process.data.CaseCode p1,com.idega.block.process.data.CaseStatus p2)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUser(com.idega.user.data.User p0,java.lang.String p1)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUserAndGroupsExceptCodes(com.idega.user.data.User p0,java.util.Collection p1,com.idega.block.process.data.CaseCode[] p2,int p3,int p4)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getAllCasesForUserExceptCodes(com.idega.user.data.User p0,com.idega.block.process.data.CaseCode[] p1,int p2,int p3)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.Case getCase(int p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseCode getCaseCode(java.lang.String p0)throws java.rmi.RemoteException,javax.ejb.FinderException, java.rmi.RemoteException;
 public java.util.Collection getCaseLogsByCaseAndDatesAndStatusChange(java.lang.String p0,java.sql.Timestamp p1,java.sql.Timestamp p2,java.lang.String p3,java.lang.String p4)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getCaseLogsByCaseAndDatesAndStatusChange(com.idega.block.process.data.CaseCode p0,java.sql.Timestamp p1,java.sql.Timestamp p2,java.lang.String p3,java.lang.String p4)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getCaseLogsByCaseCodeAndDates(com.idega.block.process.data.CaseCode p0,java.sql.Timestamp p1,java.sql.Timestamp p2)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getCaseLogsByCaseCodeAndDates(java.lang.String p0,java.sql.Timestamp p1,java.sql.Timestamp p2)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getCaseLogsByDates(java.sql.Timestamp p0,java.sql.Timestamp p1)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getCaseLogsByDatesAndStatusChange(java.sql.Timestamp p0,java.sql.Timestamp p1,java.lang.String p2,java.lang.String p3)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public java.util.Collection getCaseLogsByDatesAndStatusChange(java.sql.Timestamp p0,java.sql.Timestamp p1,com.idega.block.process.data.CaseStatus p2,com.idega.block.process.data.CaseStatus p3)throws javax.ejb.FinderException,java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatus(java.lang.String p0)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusCancelled()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusContract()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusDeleted()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusDenied()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusError()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusGranted()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusInactive()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusMoved()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusOpen()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusPending()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusPlaced()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusPreliminary()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusReady()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusRedeem()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusReview()throws java.rmi.RemoteException, java.rmi.RemoteException;
 public com.idega.user.data.User getLastModifier(com.idega.block.process.data.Case p0) throws java.rmi.RemoteException;
 public java.lang.String getLocalizedCaseDescription(com.idega.block.process.data.CaseCode p0,java.util.Locale p1)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public java.lang.String getLocalizedCaseDescription(com.idega.block.process.data.Case p0,java.util.Locale p1)throws java.rmi.RemoteException, java.rmi.RemoteException;
 public java.lang.String getLocalizedCaseStatusDescription(com.idega.block.process.data.CaseStatus p0,java.util.Locale p1) throws java.rmi.RemoteException;
 public int getNumberOfCasesForUserAndGroupsExceptCodes(com.idega.user.data.User p0,java.util.Collection p1,com.idega.block.process.data.CaseCode[] p2) throws java.rmi.RemoteException;
 public int getNumberOfCasesForUserExceptCodes(com.idega.user.data.User p0,com.idega.block.process.data.CaseCode[] p1) throws java.rmi.RemoteException;
}
