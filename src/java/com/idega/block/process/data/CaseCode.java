package com.idega.block.process.data;

import com.idega.block.process.data.model.CaseCodeModel;
import com.idega.data.IDOEntity;

public interface CaseCode extends IDOEntity, CaseCodeModel {

 @Override
public java.lang.String getCode() ;
 public com.idega.core.component.data.ICObject getBusinessHandler();
 public java.lang.Class getPrimaryKeyClass();
 public void setBusinessHandler(com.idega.core.component.data.ICObject p0);
 public void addAssociatedCaseStatus(com.idega.block.process.data.CaseStatus p0);
 public void setDefaultValues();
 public void setDescription(java.lang.String p0);
 public void setCode(java.lang.String p0);
 public java.util.Collection getAssociatedCaseStatuses();
 public void removeAssociatedCaseStatus(com.idega.block.process.data.CaseStatus p0);
 public java.lang.String getDescription();
 public java.lang.String getDescriptionLocalizedKey();
 public void setDescriptionLocalizedKey(java.lang.String p0);
}
