package com.idega.block.process.data;

import com.idega.data.*;

import java.util.Locale;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author <a href="tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */

public class CaseStatusBMPBean extends GenericEntity {

  public static final String TABLE_NAME = "PROC_CASE_STATUS";

  private static final String CASE_STATUS = "CASE_STATUS";
  private static final String CASE_STATUS_DESC = "CASE_STATUS_DESC";

  public void initializeAttributes() {
    addAttribute(CASE_STATUS,"Code",String.class,4);
    addAttribute(CASE_STATUS_DESC,"Description",String.class,1000);
  }
  public String getEntityName() {
    return TABLE_NAME;
  }

  public Class getPrimaryKeyClass(){
    return String.class;
  }

  public void setStatus(String status) {
    setColumn(this.CASE_STATUS,status);
  }

  public String getStatus() {
    return(this.getStringColumnValue(CASE_STATUS));
  }


  public void setDescription(String desc) {
    setColumn(this.CASE_STATUS_DESC,desc);
  }

  public String getDescription() {
    return(this.getStringColumnValue(CASE_STATUS_DESC));
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
    throw new java.lang.UnsupportedOperationException("Method setAssociatedCaseCode(caseCode) not yet implemented.");
  }

    /**
     * @todo: Implement
     */
  public CaseCode getAssociatedCaseCode() {
    throw new java.lang.UnsupportedOperationException("Method getAssociatedCaseCode() not yet implemented.");
  }

}
