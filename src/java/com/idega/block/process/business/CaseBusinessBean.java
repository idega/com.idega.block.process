package com.idega.block.process.business;

import com.idega.business.IBOServiceBean;
import com.idega.block.process.data.*;

import com.idega.data.*;
import com.idega.core.data.*;
import com.idega.user.data.*;
import com.idega.util.idegaTimestamp;

import java.rmi.RemoteException;
import javax.ejb.*;
import java.util.Collection;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */

public class CaseBusinessBean extends IBOServiceBean implements CaseBusiness{

  private static final String CASE_STATUS_OPEN_KEY = "UBEH";
  private static final String CASE_STATUS_INACTIVE_KEY = "TYST";
  private static final String CASE_STATUS_GRANTED_KEY = "BVJD";
  private static final String CASE_STATUS_DENIED_KEY = "AVSL";
  private static final String CASE_STATUS_REVIEW_KEY = "OMPR";
  private static final String CASE_STATUS_CANCELLED_KEY = "UPPS";


  public CaseBusinessBean() {
  }

  public CaseBusiness getCaseBusiness(String caseCode){
    try{
      CaseCode code = this.getCaseCodeHome().findByPrimaryKey(caseCode);
      return this.getCaseBusiness(code);
    }
    catch(Exception e){
      throw new EJBException(e.getMessage());
    }
  }

  public CaseBusiness getCaseBusiness(CaseCode code){
    /**
     * @todo: implement
     */
    try{
      ICObject handler = code.getBusinessHandler();
      if(handler!=null){
	Class objClass = handler.getObjectClass();
	return (CaseBusiness)this.getServiceInstance(objClass);
      }
      return this;
    }
    catch(ClassNotFoundException cnfe){
      throw new EJBException(cnfe.getMessage());
    }
    catch(RemoteException rme){
      throw new EJBException(rme.getMessage());
    }
  }


  public Case createCase(int userID,String caseCode)throws CreateException,RemoteException{
    try{
      User user = this.getUserHome().findByPrimaryKey(new Integer(userID));
      CaseCode code = this.getCaseCode(caseCode);
      return createCase(user,code);
    }
    catch(FinderException fe){
      throw new CreateException(fe.getMessage());
    }
  }

  public Case createCase(User user,CaseCode code)throws CreateException,RemoteException{
    try{
      Case newCase = this.getCaseHome().create();
      newCase.setOwner(user);
      newCase.setCaseCode(code);
      newCase.setCreated(new idegaTimestamp().getTimestamp());
      newCase.store();
      return newCase;
    }
    catch(IDOStoreException se){
      throw new CreateException(se.getMessage());
    }
  }


  /**
   * Creates a new case that is a result of the previous case with the same case code.
   */
  public Case createSubCase(Case oldCase)throws CreateException,RemoteException{
    return createSubCase(oldCase,oldCase.getCaseCode());
  }

  /**
   * Creates a new case with a specified case code that is a result of the previous case .
   */
  public Case createSubCase(Case oldCase,CaseCode newCaseCode)throws CreateException,RemoteException{
    try{
      Case newCase = this.getCaseHome().create();
      newCase.setOwner(oldCase.getOwner());
      newCase.setCaseCode(newCaseCode);
      newCase.setCreated(new idegaTimestamp().getTimestamp());
      newCase.store();
      return newCase;
    }
    catch(IDOStoreException se){
      throw new CreateException(se.getMessage());
    }
  }


  /**
   * Gets all the active Cases for the User
   */
  public Collection getAllActiveCasesForUser(User user)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user);
  }


  /**
   * Gets all the active Cases for the User with a specificed code
   */
  public Collection getAllActiveCasesForUser(User user,CaseCode code)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user,code);
  }

  /**
   * Gets all the active Cases for the User with a specificed code
   */
  public Collection getAllActiveCasesForUser(User user,String caseCode)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user,caseCode);
  }

  /**
   * Gets all the active Cases for the User with a specificed code and status
   */
  public Collection getAllActiveCasesForUser(User user,CaseCode code,CaseStatus status)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user,code,status);
  }

  /**
   * Gets all the active Cases for the User with a specificed code and status
   */
  public Collection getAllActiveCasesForUser(User user,String caseCode,String caseStatus)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user,caseCode,caseStatus);
  }


  /**
   * Gets all the Cases for the User
   */
  public Collection getAllCasesForUser(User user)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user);
  }


  /**
   * Gets all the Cases for the User with a specificed code
   */
  public Collection getAllCasesForUser(User user,CaseCode code)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user,code);
  }


  /**
   * Gets all the Cases for the User with a specificed code
   */
  public Collection getAllCasesForUser(User user,String caseCode)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user,caseCode);
  }



  /**
   * Gets all the Cases for the User with a specificed code and status
   */
  public Collection getAllCasesForUser(User user,String caseCode,String caseStatus)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user,caseCode,caseStatus);
  }

  /**
   * Gets all the Cases for the User with a specificed code and status
   */
  public Collection getAllCasesForUser(User user,CaseCode code,CaseStatus status)throws FinderException,RemoteException{
    return this.getCaseHome().findAllCasesByUser(user,code,status);
  }

  public Case getCase(int caseID)throws RemoteException,FinderException{
    return getCaseHome().findByPrimaryKey(new Integer(caseID));
  }

  public CaseCode getCaseCode(String caseCode)throws RemoteException,FinderException{
    return getCaseCodeHome().findByPrimaryKey(caseCode);
  }

  protected UserHome getUserHome()throws RemoteException{
    return (UserHome)com.idega.data.IDOLookup.getHome(User.class);
  }

  protected User getUser(int userID)throws RemoteException,FinderException{
    return this.getUserHome().findByPrimaryKey(new Integer(userID));
  }

  protected CaseHome getCaseHome()throws RemoteException{
    return (CaseHome)com.idega.data.IDOLookup.getHome(Case.class);
  }

  protected CaseCodeHome getCaseCodeHome()throws RemoteException{
    return (CaseCodeHome)com.idega.data.IDOLookup.getHome(CaseCode.class);
  }

  protected CaseStatusHome getCaseStatusHome()throws RemoteException{
    return (CaseStatusHome)com.idega.data.IDOLookup.getHome(CaseStatus.class);
  }


  public CaseStatus getCaseStatusOpen()throws RemoteException{
    try{
      return this.getCaseStatusHome().findByPrimaryKey(CASE_STATUS_OPEN_KEY);
    }
    catch(FinderException e){
      throw new EJBException("CaseStatus "+CASE_STATUS_OPEN_KEY+" is not installed or does not exist");
    }
  }

  public CaseStatus getCaseStatusGranted()throws RemoteException{
    try{
      return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_GRANTED_KEY);
    }
    catch(FinderException e){
      throw new EJBException("CaseStatus "+this.CASE_STATUS_GRANTED_KEY+" is not installed or does not exist");
    }
  }


  public CaseStatus getCaseStatusDenied()throws RemoteException{
    try{
      return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_DENIED_KEY);
    }
    catch(FinderException e){
      throw new EJBException("CaseStatus "+this.CASE_STATUS_DENIED_KEY+" is not installed or does not exist");
    }
  }

  public CaseStatus getCaseStatusReview()throws RemoteException{
    try{
      return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_REVIEW_KEY);
    }
    catch(FinderException e){
      throw new EJBException("CaseStatus "+this.CASE_STATUS_REVIEW_KEY+" is not installed or does not exist");
    }
  }

  public CaseStatus getCaseStatusCancelled()throws RemoteException{
    try{
      return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_CANCELLED_KEY);
    }
    catch(FinderException e){
      throw new EJBException("CaseStatus "+this.CASE_STATUS_CANCELLED_KEY+" is not installed or does not exist");
    }
  }

}