package com.idega.block.process.data;


public interface CaseStatus extends com.idega.data.IDOEntity
{
 public void setStatus(java.lang.String p0) throws java.rmi.RemoteException;
 public java.lang.String getDescription() throws java.rmi.RemoteException;
 public java.lang.Class getPrimaryKeyClass() throws java.rmi.RemoteException;
 public void setDescription(java.lang.String p0) throws java.rmi.RemoteException;
 public void setDefaultValues() throws java.rmi.RemoteException;
 public void setDescription(java.lang.String p0,java.util.Locale p1) throws java.rmi.RemoteException;
 public java.lang.String getDescription(java.util.Locale p0) throws java.rmi.RemoteException;
 public java.lang.String getDescriptionLocalizedKey() throws java.rmi.RemoteException;
 public java.lang.String getStatus() throws java.rmi.RemoteException;
 public void setDescriptionLocalizedKey(java.lang.String p0) throws java.rmi.RemoteException;
 public void setAssociatedCaseCode(com.idega.block.process.data.CaseCode p0) throws java.rmi.RemoteException;
 public com.idega.block.process.data.CaseCode getAssociatedCaseCode() throws java.rmi.RemoteException;
}
