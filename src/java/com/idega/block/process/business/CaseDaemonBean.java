package com.idega.block.process.business;

import com.idega.idegaweb.*;

import java.util.*;
import com.idega.block.process.data.*;
import javax.ejb.EJBHome;
import javax.ejb.Handle;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author
 * @version 1.0
 */

public abstract class CaseDaemonBean extends com.idega.business.IBOTimedServiceBean implements IWService, CaseDaemon{

  protected CaseDaemonBean() {
  }
  public String getServiceName() {
    return "CaseDeamon";
  }

  public final void executeTimedService() {
    executeCaseProcessing();
  }


  /**
   * Returns a Collection of CaseCode objects
   */
  public abstract Collection getAssociatedCaseCodes()throws RemoteException;



  public CaseBusiness getGeneralCaseBusiness()throws RemoteException{
    return (CaseBusiness)getServiceInstance(CaseBusiness.class);
  }


  /**
   * Can be overrided in subclasses for specific implementation
   */
  public void executeCaseProcessing() {
    processAllPendingCases();
  }


  /**
   * The default implementation
   */
  protected void processAllPendingCases() {
    Iterator iter = getAllPendingCases().iterator();
    while (iter.hasNext()) {
      Case item = (Case)iter.next();
      if(isCaseProcessable(item)){
        onCaseProcess(item);
      }
    }
  }

  /**
   * Override this method if using the default implementation
   */
  public Collection getAllPendingCases(){
    return null;
  }

  /**
   * Override this method if using the default implementation
   */
  public boolean isCaseProcessable(Case theCase){
    return true;
  }

  /**
   * Override this method if using the default implementation
   */
  public abstract void onCaseProcess(Case theCase);

}