package com.idega.block.process.data;


public interface CaseLog extends com.idega.data.IDOEntity
{
 public com.idega.block.process.data.Case getCase();
 public int getCaseId();
 public com.idega.block.process.data.CaseStatus getCaseStatusAfter();
 public com.idega.block.process.data.CaseStatus getCaseStatusBefore();
 public java.lang.String getIDColumnName();
 public com.idega.user.data.User getPerformer();
 public int getPerformerId();
 public java.lang.String getStatusAfter();
 public java.lang.String getStatusBefore();
 public java.sql.Timestamp getTimeStamp();
 public void initializeAttributes();
 public void setCase(com.idega.block.process.data.Case p0);
 public void setCase(int p0);
 public void setCaseStatusAfter(com.idega.block.process.data.CaseStatus p0);
 public void setCaseStatusAfter(java.lang.String p0);
 public void setCaseStatusBefore(com.idega.block.process.data.CaseStatus p0);
 public void setCaseStatusBefore(java.lang.String p0);
 public void setPerformer(com.idega.user.data.User p0);
 public void setPerformer(int p0);
 public void setTimeStamp(java.sql.Timestamp p0);
}
