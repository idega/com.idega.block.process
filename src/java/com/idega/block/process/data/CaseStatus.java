package com.idega.block.process.data;


public interface CaseStatus extends com.idega.data.IDOEntity
{
 public void setStatus(java.lang.String p0);
 public java.lang.String getDescription();
 public java.lang.Class getPrimaryKeyClass();
 public void setDescription(java.lang.String p0);
 public void setDefaultValues();
 public void setDescription(java.lang.String p0,java.util.Locale p1);
 public java.lang.String getDescription(java.util.Locale p0);
 public java.lang.String getDescriptionLocalizedKey();
 public java.lang.String getStatus();
 public void setDescriptionLocalizedKey(java.lang.String p0);
 public void setAssociatedCaseCode(com.idega.block.process.data.CaseCode p0);
 public com.idega.block.process.data.CaseCode getAssociatedCaseCode();
}
