package com.idega.block.process.data;

import com.idega.user.data.Group;

public interface Case extends com.idega.data.IDOEntity,com.idega.core.ICTreeNode
{
 public com.idega.block.process.data.Case getParentCase() throws java.rmi.RemoteException;

 public java.lang.String getStatus() throws java.rmi.RemoteException;
 public java.sql.Timestamp getCreated() throws java.rmi.RemoteException;
 public void setCaseStatus(com.idega.block.process.data.CaseStatus p0) throws java.rmi.RemoteException;
 public java.lang.String getIDColumnName() throws java.rmi.RemoteException;
 public void setStatus(java.lang.String p0) throws java.rmi.RemoteException;
 public void setOwner(com.idega.user.data.User p0) throws java.rmi.RemoteException;
 public com.idega.user.data.User getOwner() throws java.rmi.RemoteException;
 public void setParentCase(com.idega.block.process.data.Case p0) throws java.rmi.RemoteException;
 public com.idega.block.process.data.CaseCode getCaseCode() throws java.rmi.RemoteException;
 public java.lang.String getCode() throws java.rmi.RemoteException;
 public void setCode(java.lang.String p0) throws java.rmi.RemoteException;
 public void setCreated(java.sql.Timestamp p0) throws java.rmi.RemoteException;
 public void initializeAttributes() throws java.rmi.RemoteException;
 public void setCaseCode(com.idega.block.process.data.CaseCode p0) throws java.rmi.RemoteException;
 public com.idega.block.process.data.CaseStatus getCaseStatus() throws java.rmi.RemoteException;

	public Group getHandler() throws java.rmi.RemoteException;
	public int getHandlerId() throws java.rmi.RemoteException;
	public void setHandler(Group handler) throws java.rmi.RemoteException;
	public void setHandler(int handlerGroupID) throws java.rmi.RemoteException;



}
