package com.idega.block.process.data;


public interface CaseCode extends com.idega.data.IDOEntity
{
 public java.lang.String getCode() throws java.rmi.RemoteException;
 public com.idega.core.component.data.ICObject getBusinessHandler() throws java.rmi.RemoteException;
 public java.lang.Class getPrimaryKeyClass() throws java.rmi.RemoteException;
 public void setBusinessHandler(com.idega.core.component.data.ICObject p0) throws java.rmi.RemoteException;
 public void addAssociatedCaseStatus(com.idega.block.process.data.CaseStatus p0) throws java.rmi.RemoteException;
 public void setDefaultValues() throws java.rmi.RemoteException;
 public void setDescription(java.lang.String p0) throws java.rmi.RemoteException;
 public void setCode(java.lang.String p0) throws java.rmi.RemoteException;
 public java.util.Collection getAssociatedCaseStatuses() throws java.rmi.RemoteException;
 public void removeAssociatedCaseStatus(com.idega.block.process.data.CaseStatus p0) throws java.rmi.RemoteException;
 public java.lang.String getDescription() throws java.rmi.RemoteException;
 public java.lang.String getDescriptionLocalizedKey() throws java.rmi.RemoteException;
 public void setDescriptionLocalizedKey(java.lang.String p0) throws java.rmi.RemoteException;
}
