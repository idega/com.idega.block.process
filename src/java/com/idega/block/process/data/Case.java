package com.idega.block.process.data;

import com.idega.user.data.Group;

public interface Case extends com.idega.data.IDOEntity,com.idega.core.data.ICTreeNode
{
 public com.idega.block.process.data.Case getParentCase();

 public java.lang.String getStatus();
 public java.sql.Timestamp getCreated();
 public void setCaseStatus(com.idega.block.process.data.CaseStatus p0);
 public java.lang.String getIDColumnName();
 public void setStatus(java.lang.String p0);
 public void setOwner(com.idega.user.data.User p0);
 public com.idega.user.data.User getOwner();
 public void setParentCase(com.idega.block.process.data.Case p0);
 public com.idega.block.process.data.CaseCode getCaseCode();
 public java.lang.String getCode();
 public void setCode(java.lang.String p0);
 public void setCreated(java.sql.Timestamp p0);
 public void initializeAttributes();
 public void setCaseCode(com.idega.block.process.data.CaseCode p0);
 public com.idega.block.process.data.CaseStatus getCaseStatus();

	public Group getHandler();
	public int getHandlerId();
	public void setHandler(Group handler);
	public void setHandler(int handlerGroupID);



}
