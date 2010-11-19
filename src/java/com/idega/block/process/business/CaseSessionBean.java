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

	private static final long serialVersionUID = 6636891629945158620L;

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
    return getCaseBusiness().createCase(getOldUser(getCurrentUser()),code);
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
  public Collection<Case> getAllActiveCases()throws FinderException,RemoteException{
    return getCaseBusiness().getAllActiveCasesForUser(getOldUser(getCurrentUser()));
  }

  /**
   * Gets all active cases for the current user with a specified caseCode
   */
  public Collection<Case> getAllActiveCases(CaseCode code)throws FinderException,RemoteException{
    return getCaseBusiness().getAllActiveCasesForUser(getOldUser(getCurrentUser()),code);
  }

  /**
   * Gets all active cases for the current user with a specified caseCode
   */
  public Collection<Case> getAllActiveCases(String caseCode)throws FinderException,RemoteException{
    return getCaseBusiness().getAllActiveCasesForUser(getOldUser(getCurrentUser()),caseCode);
  }

  /**
   * Gets all active cases for the current user with a specified caseCode and caseStatus
   */
  public Collection<Case> getAllActiveCases(CaseCode caseCode,CaseStatus caseStatus)throws FinderException,RemoteException{
    return getCaseBusiness().getAllActiveCasesForUser(getOldUser(getCurrentUser()),caseCode,caseStatus);
  }

  /**
   * Gets all active cases for the current user with a specified caseCode and caseStatus
   */
  public Collection<Case> getAllActiveCases(String caseCode,String caseStatus)throws FinderException,RemoteException{
    return getCaseBusiness().getAllActiveCasesForUser(getOldUser(getCurrentUser()),caseCode,caseStatus);
  }

  /**
   * Gets all cases for the current user
   */
  public Collection<Case> getAllCases()throws FinderException,RemoteException{
    return getCaseBusiness().getAllCasesForUser(getOldUser(getCurrentUser()));
  }

  /**
   * Gets all for the current user with a specified caseCode
   */
  public Collection<Case> getAllCases(CaseCode code)throws FinderException,RemoteException{
    return getCaseBusiness().getAllCasesForUser(getOldUser(getCurrentUser()),code);
  }

  /**
   * Gets all cases for the current user with a specified caseCode
   */
  public Collection<Case> getAllCases(String caseCode)throws FinderException,RemoteException{
    return getCaseBusiness().getAllCasesForUser(getOldUser(getCurrentUser()),caseCode);
  }

  /**
   * Gets all cases for the current user with a specified caseCode and caseStatus
   */
  public Collection<Case> getAllCases(CaseCode caseCode,CaseStatus caseStatus)throws FinderException,RemoteException{
    return getCaseBusiness().getAllActiveCasesForUser(getOldUser(getCurrentUser()),caseCode,caseStatus);
  }

  /**
   * Gets all cases for the current user with a specified caseCode and caseStatus
   */
  public Collection<Case> getAllCases(String caseCode,String caseStatus)throws FinderException,RemoteException{
    return getCaseBusiness().getAllCasesForUser(getOldUser(getCurrentUser()),caseCode,caseStatus);
  }

}