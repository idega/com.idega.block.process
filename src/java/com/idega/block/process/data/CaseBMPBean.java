/*
 * $Id: CaseBMPBean.java,v 1.1 2002/06/14 11:04:42 tryggvil Exp $
 *
 * Copyright (C) 2002 Idega hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 *
 */
package com.idega.block.process.data;

import java.util.Collection;
import java.sql.Timestamp;

import javax.ejb.*;
import java.rmi.RemoteException;

import com.idega.util.idegaTimestamp;

import com.idega.core.user.data.User;

/**
 *
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public final class CaseBMPBean extends com.idega.data.TreeableEntityBMPBean implements Case,com.idega.core.ICTreeNode{
  public static final String TABLE_NAME = "PROC_CASE";

  private static final String CASE_CODE = "CASE_CODE";
  private static final String CASE_STATUS = "CASE_STATUS";
  private static final String CREATED = "CREATED";
  private static final String PARENT_CASE = "PARENT_CASE_ID";
  private static final String USER = "USER_ID";


  public void initializeAttributes() {
    addAttribute(getIDColumnName());
    addAttribute(CASE_CODE,"Case Code",true,true,String.class,7,super.MANY_TO_ONE,CaseCode.class);
    addAttribute(CASE_STATUS,"Case status",true,true,String.class,4,super.MANY_TO_ONE,CaseStatus.class);
    addAttribute(CREATED,"Created when",Timestamp.class);
    addAttribute(PARENT_CASE,"Parent case",true,true,Integer.class,super.MANY_TO_ONE,Case.class);
    addAttribute(USER,"Owner",true,true,Integer.class,super.MANY_TO_ONE,User.class);
  }

  public String getEntityName() {
    return(TABLE_NAME);
  }

  protected boolean doInsertInCreate(){
    return true;
  }


  public void setCode(String caseCode) {
    setColumn(this.CASE_CODE,caseCode);
  }

  public String getCode() {
    return(this.getStringColumnValue(CASE_CODE));
  }

  public void setCaseCode(CaseCode caseCode) {
    setColumn(this.CASE_CODE,caseCode);
  }

  public CaseCode getCaseCode() {
    return (CaseCode) (this.getColumnValue(CASE_CODE));
  }


  public void setStatus(CaseStatus status) {
    setColumn(this.CASE_STATUS,status);
  }

  public CaseStatus getCaseStatus() {
    return (CaseStatus)(this.getColumnValue(CASE_STATUS));
  }

  public void setCaseStatus(String status) {
    setColumn(this.CASE_STATUS,status);
  }

  public String getStatus() {
    return(this.getStringColumnValue(CASE_STATUS));
  }

  public void setCreated(Timestamp statusChanged) {
    setColumn(this.CREATED,statusChanged);
  }

  public Timestamp getCreated() {
    return((Timestamp)getColumnValue(CREATED));
  }

  public void setParentCase(Case theCase){
    //throw new java.lang.UnsupportedOperationException("setParentCase() not implemented yet");
    this.setColumn(this.PARENT_CASE,theCase);
  }

  public Case getParentCase(){
    //return (Case)super.getParentNode();
    return (Case)getColumnValue(this.PARENT_CASE);
  }

  public void setOwner(User owner){
    super.setColumn(USER,owner);
  }

  public User getOwner(){
    return (User)this.getColumnValue(this.USER);
  }

  public Collection ejbFindAllCasesByUser(User user)throws FinderException,RemoteException{
    return (Collection)super.idoFindPKsBySQL("select * from "+this.TABLE_NAME+" where "+this.USER+"="+user.getPrimaryKey().toString());
  }

  public Collection ejbFindAllCasesByUser(User user,CaseCode caseCode)throws FinderException,RemoteException{
    return ejbFindAllCasesByUser(user,caseCode.getCode());
  }

  public Collection ejbFindAllCasesByUser(User user,String caseCode)throws FinderException,RemoteException{
    return (Collection)super.idoFindPKsBySQL("select * from "+this.TABLE_NAME+" where "+this.USER+"="+user.getPrimaryKey().toString()+" and "+this.CASE_CODE+"='"+caseCode+"'");
  }

  public Collection ejbFindAllCasesByUser(User user,CaseCode caseCode,CaseStatus caseStatus)throws FinderException,RemoteException{
    return ejbFindAllCasesByUser(user,caseCode.getCode(),caseStatus.getStatus());
  }

  public Collection ejbFindAllCasesByUser(User user,String caseCode,String caseStatus)throws FinderException,RemoteException{
    return (Collection)super.idoFindPKsBySQL("select * from "+this.TABLE_NAME+" where "+this.USER+"="+user.getPrimaryKey().toString()+" and "+this.CASE_CODE+"='"+caseCode+"'"+" and "+this.CASE_STATUS+"='"+caseStatus+"'");
  }

}
