package com.idega.block.process.data;


public interface Case extends com.idega.data.IDOEntity,com.idega.core.data.ICTreeNode
{
 public boolean getAllowsChildren();
 public com.idega.block.process.data.CaseCode getCaseCode();
 public com.idega.block.process.data.CaseStatus getCaseStatus();
 public com.idega.core.data.ICTreeNode getChildAtIndex(int p0);
 public int getChildCount();
 public java.util.Iterator getChildrenIterator();
 public java.lang.String getCode();
 public java.sql.Timestamp getCreated();
 public com.idega.user.data.Group getHandler();
 public int getHandlerId();
 public java.lang.String getIDColumnName();
 public int getIndex(com.idega.core.data.ICTreeNode p0);
 public int getNodeID();
 public java.lang.String getNodeName(java.util.Locale p0);
 public java.lang.String getNodeName();
 public com.idega.user.data.User getOwner();
 public com.idega.block.process.data.Case getParentCase();
 public com.idega.core.data.ICTreeNode getParentNode();
 public int getSiblingCount();
 public java.lang.String getStatus();
 public void initializeAttributes();
 public boolean isLeaf();
 public void setCaseCode(com.idega.block.process.data.CaseCode p0);
 public void setCaseStatus(com.idega.block.process.data.CaseStatus p0);
 public void setCode(java.lang.String p0);
 public void setCreated(java.sql.Timestamp p0);
 public void setHandler(int p0);
 public void setHandler(com.idega.user.data.Group p0);
 public void setOwner(com.idega.user.data.User p0);
 public void setParentCase(com.idega.block.process.data.Case p0);
 public void setStatus(java.lang.String p0);
}
