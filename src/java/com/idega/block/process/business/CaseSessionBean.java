package com.idega.block.process.business;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseStatus;
import com.idega.business.IBOSessionBean;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author
 * @version 1.0
 */

public class CaseSessionBean extends IBOSessionBean {

  private CaseBusiness caseBusiness;

  public CaseSessionBean() {
  }

  public CaseBusiness getCaseBusiness()throws RemoteException{
    if(this.caseBusiness==null){
      this.caseBusiness = (CaseBusiness)this.getServiceInstance(CaseBusiness.class);
    }
    return this.caseBusiness;
  }

  /**
   * Creates a case for the current user
   */
  public Case createCase(CaseCode code)throws CreateException,RemoteException{
    //return getCaseBusiness().createCase(Converter.convertToNewUser(getCurrentUser()),code);
    return getCaseBusiness().createCase(getCurrentUser(),code);
  }


  /**
   * Creates a case for the default user
   */
  public Case createCase(String caseCode)throws CreateException,RemoteException{
    try{
      return this.createCase(this.getCaseBusiness().getCaseCode(caseCode));
    }
    catch(FinderException fe){
      throw new CreateException(fe.getMessage());
    }
  }

  /**
   * Gets all active cases for the current user
   */
  public Collection getAllActiveCases()throws FinderException,RemoteException{
    //return getCaseBusiness().getAllActiveCasesForUser(Converter.convertToNewUser(getCurrentUser()));
    return getCaseBusiness().getAllActiveCasesForUser(getCurrentUser());
  }


  /**
   * Gets all active cases for the current user with a specified caseCode
   */
  public Collection getAllActiveCases(CaseCode code)throws FinderException,RemoteException{
    //return getCaseBusiness().getAllActiveCasesForUser(Converter.convertToNewUser(getCurrentUser()),code);
    return getCaseBusiness().getAllActiveCasesForUser(getCurrentUser(),code);
  }


  /**
   * Gets all active cases for the current user with a specified caseCode
   */
  public Collection getAllActiveCases(String caseCode)throws FinderException,RemoteException{
    //return getCaseBusiness().getAllActiveCasesForUser(Converter.convertToNewUser(getCurrentUser()),caseCode);
    return getCaseBusiness().getAllActiveCasesForUser(getCurrentUser(),caseCode);
  }

  /**
   * Gets all active cases for the current user with a specified caseCode and caseStatus
   */
  public Collection getAllActiveCases(CaseCode caseCode,CaseStatus caseStatus)throws FinderException,RemoteException{
    return getCaseBusiness().getAllActiveCasesForUser(getCurrentUser(),caseCode,caseStatus);
  }

  /**
   * Gets all active cases for the current user with a specified caseCode and caseStatus
   */
  public Collection getAllActiveCases(String caseCode,String caseStatus)throws FinderException,RemoteException{
    return getCaseBusiness().getAllActiveCasesForUser(getCurrentUser(),caseCode,caseStatus);
  }



  /**
   * Gets all cases for the current user
   */
  public Collection getAllCases()throws FinderException,RemoteException{
    return getCaseBusiness().getAllCasesForUser(getCurrentUser());
  }


  /**
   * Gets all for the current user with a specified caseCode
   */
  public Collection getAllCases(CaseCode code)throws FinderException,RemoteException{
    return getCaseBusiness().getAllCasesForUser(getCurrentUser(),code);
  }


  /**
   * Gets all cases for the current user with a specified caseCode
   */
  public Collection getAllCases(String caseCode)throws FinderException,RemoteException{
    return getCaseBusiness().getAllCasesForUser(getCurrentUser(),caseCode);
  }

  /**
   * Gets all cases for the current user with a specified caseCode and caseStatus
   */
  public Collection getAllCases(CaseCode caseCode,CaseStatus caseStatus)throws FinderException,RemoteException{
    return getCaseBusiness().getAllActiveCasesForUser(getCurrentUser(),caseCode,caseStatus);
  }

  /**
   * Gets all cases for the current user with a specified caseCode and caseStatus
   */
  public Collection getAllCases(String caseCode,String caseStatus)throws FinderException,RemoteException{
    return getCaseBusiness().getAllCasesForUser(getCurrentUser(),caseCode,caseStatus);
  }








}