package com.idega.block.process.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author <a href="tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */

public class CaseStatusBMPBean extends GenericEntity implements CaseStatus{

  public static final String TABLE_NAME = "PROC_CASE_STATUS";

  private static final String CASE_STATUS = "CASE_STATUS";
  private static final String CASE_STATUS_DESC = "CASE_STATUS_DESC";
  private static final String CASE_STATUS_DESC_LOC_KEY = "CASE_STATUS_DESC_LOC_KEY";

  private static final String LOC_KEY_PREFIX = "case.status";

  public void initializeAttributes() {
    addAttribute(CASE_STATUS,"Status",String.class,4);
    this.setAsPrimaryKey(CASE_STATUS,true);
    addAttribute(CASE_STATUS_DESC,"Description",String.class,1000);
    addAttribute(CASE_STATUS_DESC_LOC_KEY,"Localized Description Key",String.class);
  }
  public String getEntityName() {
    return TABLE_NAME;
  }

  public String getIDColumnName(){
    return CASE_STATUS;
  }

  public Class getPrimaryKeyClass(){
    return String.class;
  }

  public void setDefaultValues(){
    String sCode = this.getStatus();
    if(sCode!=null){
      this.setDescriptionLocalizedKey(LOC_KEY_PREFIX+sCode);
    }
  }

  public void setStatus(String status) {
    String sKey = this.getDescriptionLocalizedKey();
    if(sKey!=null){
      this.setDescriptionLocalizedKey(LOC_KEY_PREFIX+status);
    }
    setColumn(CaseStatusBMPBean.CASE_STATUS,status);
  }

  public String getStatus() {
    return(this.getStringColumnValue(CASE_STATUS));
  }


  public void setDescription(String desc) {
    setColumn(CaseStatusBMPBean.CASE_STATUS_DESC,desc);
  }

  public String getDescription() {
    return(this.getStringColumnValue(CASE_STATUS_DESC));
  }

  public void setDescriptionLocalizedKey(String key) {
    setColumn(CaseStatusBMPBean.CASE_STATUS_DESC_LOC_KEY,key);
  }

  public String getDescriptionLocalizedKey() {
    return(this.getStringColumnValue(CASE_STATUS_DESC_LOC_KEY));
  }

    /**
     * @todo: Implement
     */
  public void setDescription(String desc,Locale locale) {
    setDescription(desc);
  }

    /**
     * @todo: Implement
     */
  public String getDescription(Locale inLocale) {
    return getDescription();
  }


    /**
     * @todo: Implement
     */
  public void setAssociatedCaseCode(CaseCode caseCode) {
    //throw new java.lang.UnsupportedOperationException("Method setAssociatedCaseCode(caseCode) not yet implemented.");
    try{
      super.idoAddTo(caseCode);
    }
    catch(Exception e){

    }
  }

    /**
     * @todo: Implement
     */
  public CaseCode getAssociatedCaseCode() {
    //throw new java.lang.UnsupportedOperationException("Method getAssociatedCaseCode() not yet implemented.");
    try{
      Collection c = super.idoGetRelatedEntities(CaseCode.class);
      Iterator iter = c.iterator();
      return (CaseCode)iter.next();
    }
    catch(Exception e){
      throw new RuntimeException("No CaseCode defined for CaseStatus: "+this.getStatus());
    }
  }




  public Collection ejbFindAllStatuses()throws FinderException{
    return super.idoFindAllIDsBySQL();
  }

  /**
   * @todo: implement better
   */
  public Collection ejbFindGlobalStatuses()throws FinderException{
    return ejbFindAllStatuses();
  }

}
