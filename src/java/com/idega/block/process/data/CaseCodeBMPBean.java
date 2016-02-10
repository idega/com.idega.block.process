package com.idega.block.process.data;

import java.util.Collection;

import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.core.component.data.ICObject;
import com.idega.data.GenericEntity;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORuntimeException;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author <a href="tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */

public class CaseCodeBMPBean extends GenericEntity implements CaseCode{

	private static final long serialVersionUID = -4149323672646413739L;

	public static final String	TABLE_NAME = "PROC_CASE_CODE",
								CASE_CODE = "CASE_CODE";

  private static final String CASE_CODE_DESC = "CASE_CODE_DESC";
  private static final String CASE_CODE_DESC_LOC_KEY = "CASE_CODE_DESC_LOC_KEY";
  private static final String CASE_CODE_HANDLER = "CASE_CODE_HANDLER_ID";

  private static final String LOC_KEY_PREFIX = "case.code";

  public CaseCodeBMPBean() {
  }
  @Override
public void initializeAttributes() {
    addAttribute(CASE_CODE,"Code",String.class,7);
    this.setAsPrimaryKey(CASE_CODE,true);
    addAttribute(CASE_CODE_DESC,"Description",String.class,1000);
    addAttribute(CASE_CODE_DESC_LOC_KEY,"Description Localized Key",String.class);
    this.addManyToOneRelationship(CASE_CODE_HANDLER,"Business Handler Object",ICObject.class);
    addManyToManyRelationShip(CaseStatus.class);
  }
  @Override
public String getEntityName() {
    return TABLE_NAME;
  }

  @Override
public Class getPrimaryKeyClass(){
    return String.class;
  }

  @Override
public String getIDColumnName(){
    return CASE_CODE;
  }

  @Override
public void setDefaultValues(){
    String sCode = this.getCode();
    if(sCode!=null){
      this.setDescriptionLocalizedKey(LOC_KEY_PREFIX+sCode);
    }
  }

  @Override
public void setCode(String caseCode) {
    String sKey = this.getDescriptionLocalizedKey();
    if(sKey!=null){
      this.setDescriptionLocalizedKey(LOC_KEY_PREFIX+caseCode);
    }
    setColumn(CaseCodeBMPBean.CASE_CODE,caseCode);
  }

  @Override
public String getCode() {
    return(this.getStringColumnValue(CASE_CODE));
  }

  @Override
public void setDescription(String desc) {
    setColumn(CaseCodeBMPBean.CASE_CODE_DESC,desc);
  }

  @Override
public String getDescription() {
    return(this.getStringColumnValue(CASE_CODE_DESC));
  }

  @Override
public void setBusinessHandler(ICObject obj) {
    setColumn(CaseCodeBMPBean.CASE_CODE_HANDLER,obj);
  }

  @Override
public void setDescriptionLocalizedKey(String key) {
    setColumn(CaseCodeBMPBean.CASE_CODE_DESC_LOC_KEY,key);
  }

  @Override
public String getDescriptionLocalizedKey() {
    return(this.getStringColumnValue(CASE_CODE_DESC_LOC_KEY));
  }
  @Override
public ICObject getBusinessHandler() {
    return (ICObject) (this.getColumnValue(CASE_CODE_HANDLER));
  }

  protected CaseHome getCaseHome() {
    try {
			return (CaseHome)IDOLookup.getHome(Case.class);
		}
		catch (IDOLookupException e) {
			throw new IDORuntimeException(e.getMessage());
		}
  }

    /**
     * @todo: Implement
     */
  @Override
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
  @Override
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
  @Override
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


  public Collection ejbFindAllCaseCodes()throws FinderException{
    return super.idoFindAllIDsBySQL();
  }

}