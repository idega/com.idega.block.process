package com.idega.block.process.data;


public interface CaseLog extends com.idega.data.IDOEntity
{
 public java.lang.String getIDColumnName() throws java.rmi.RemoteException;
 public java.lang.String getStatusAfter() throws java.rmi.RemoteException;
 public void setCaseStatusAfter(java.lang.String p0) throws java.rmi.RemoteException;
 public void setCaseStatusBefore(com.idega.block.process.data.CaseStatus p0) throws java.rmi.RemoteException;
 public java.lang.String getStatusBefore() throws java.rmi.RemoteException;
 public void initializeAttributes() throws java.rmi.RemoteException;
 public com.idega.user.data.User getPerformer() throws java.rmi.RemoteException;
 public void setPerformer(int p0) throws java.rmi.RemoteException;
 public void setPerformer(com.idega.user.data.User p0) throws java.rmi.RemoteException;
 public void setCase(int p0) throws java.rmi.RemoteException;
 public int getCaseId() throws java.rmi.RemoteException;
 public int getPerformerId() throws java.rmi.RemoteException;
 public java.sql.Timestamp getTimeStamp() throws java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusBefore() throws java.rmi.RemoteException;
 public void setCaseStatusBefore(java.lang.String p0) throws java.rmi.RemoteException;
 public void setCase(com.idega.block.process.data.Case p0) throws java.rmi.RemoteException;
 public void setTimeStamp(java.sql.Timestamp p0) throws java.rmi.RemoteException;
 public void setCaseStatusAfter(com.idega.block.process.data.CaseStatus p0) throws java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatusAfter() throws java.rmi.RemoteException;
 public com.idega.block.process.data.Case getCase() throws java.rmi.RemoteException;
}
