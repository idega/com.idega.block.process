package com.idega.block.process.data;

import com.idega.data.*;

import java.util.Collection;
import javax.ejb.*;
import com.idega.core.data.ICObject;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author <a href="tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */

public class CaseCodeBMPBean extends GenericEntity implements CaseCode{

  public static final String TABLE_NAME = "PROC_CASE_CODE";

  private static final String CASE_CODE = "CASE_CODE";
  private static final String CASE_CODE_DESC = "CASE_CODE_DESC";
  private static final String CASE_CODE_HANDLER = "CASE_CODE_HANDLER_ID";

  public CaseCodeBMPBean() {
  }
  public void initializeAttributes() {
    addAttribute(CASE_CODE,"Code",String.class,7);
    addAttribute(CASE_CODE_DESC,"Description",String.class,1000);

    this.addManyToOneRelationship(CASE_CODE_HANDLER,"Business Handler Object",ICObject.class);
    addManyToManyRelationShip(CaseStatus.class);
  }
  public String getEntityName() {
    return TABLE_NAME;
  }

  public Class getPrimaryKeyClass(){
    return String.class;
  }

  public void setCode(String caseCode) {
    setColumn(this.CASE_CODE,caseCode);
  }

  public String getCode() {
    return(this.getStringColumnValue(CASE_CODE));
  }

  public void setDescription(String desc) {
    setColumn(this.CASE_CODE_DESC,desc);
  }

  public String getDescription() {
    return(this.getStringColumnValue(CASE_CODE_DESC));
  }

  public void setBusinessHandler(ICObject obj) {
    setColumn(this.CASE_CODE_HANDLER,obj);
  }

  public ICObject getBusinessHandler() {
    return (ICObject) (this.getColumnValue(CASE_CODE_HANDLER));
  }

    /**
     * @todo: Implement
     */
  public void addAssociatedCaseStatus(CaseStatus caseStatus){
    try{
      this.idoAddTo(caseStatus);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

    /**
     * @todo: Implement
     */
  public void removeAssociatedCaseStatus(CaseStatus caseStatus){
    try{
      this.idoRemoveFrom(caseStatus);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

    /**
     * @todo: Implement
     */
  public Collection getAssociatedCaseStatuses(){
      try{
      Collection c = this.idoGetRelatedEntities(CaseStatus.class);
      return c;
    }
    catch(Exception e){
      //e.printStackTrace();
      throw new EJBException(e.getMessage());
    }

  }

}