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

public abstract class CaseDaemonBean extends com.idega.business.IBOServiceBean implements IWService, Runnable, CaseDaemon{

  public CaseDaemonBean() {
  }
  public String getServiceName() {
    return "CaseDeamon";
  }

  protected void executeService() {
    processAllPendingCases();
  }

  protected void processAllPendingCases() {
    Iterator iter = getAllPendingCases().iterator();
    while (iter.hasNext()) {
      Case item = (Case)iter.next();
      if(isCaseProcessable(item)){
        onCaseProcess(item);
      }
    }
  }

  public Collection getAllPendingCases(){
    return null;
  }

  public boolean isCaseProcessable(Case theCase){
    return true;
  }

  public abstract void onCaseProcess(Case theCase);

  public void startService(IWMainApplication superApplication) {
    /**@todo: Implement this com.idega.idegaweb.IWService method*/
    throw new java.lang.UnsupportedOperationException("Method startService() not yet implemented.");
  }
  public void endService() {
    /**@todo: Implement this com.idega.idegaweb.IWService method*/
    throw new java.lang.UnsupportedOperationException("Method endService() not yet implemented.");
  }
  public void run() {
    /**@todo: Implement this java.lang.Runnable method*/
    throw new java.lang.UnsupportedOperationException("Method run() not yet implemented.");
  }
  public Collection getAssociatedCaseCodes() throws RemoteException {
    /**@todo: Implement this com.idega.block.process.business.CaseDaemon method*/
    throw new java.lang.UnsupportedOperationException("Method getAssociatedCaseCodes() not yet implemented.");
  }
  public CaseBusiness getCaseBusiness() throws RemoteException {
    /**@todo: Implement this com.idega.block.process.business.CaseDaemon method*/
    throw new java.lang.UnsupportedOperationException("Method getCaseBusiness() not yet implemented.");
  }
  public void runNow() throws RemoteException {
    /**@todo: Implement this com.idega.block.process.business.CaseDaemon method*/
    throw new java.lang.UnsupportedOperationException("Method runNow() not yet implemented.");
  }
  public void interrupt() throws RemoteException {
    /**@todo: Implement this com.idega.block.process.business.CaseDaemon method*/
    throw new java.lang.UnsupportedOperationException("Method interrupt() not yet implemented.");
  }
  public String getServiceDescription() {
    /**@todo: Implement this com.idega.business.IBOService method*/
    throw new java.lang.UnsupportedOperationException("Method getServiceDescription() not yet implemented.");
  }
  public String getLocalizedServiceDescription(Locale locale) {
    /**@todo: Implement this com.idega.business.IBOService method*/
    throw new java.lang.UnsupportedOperationException("Method getLocalizedServiceDescription() not yet implemented.");
  }
  public IWApplicationContext getIWApplicationContext() {
    /**@todo: Implement this com.idega.business.IBOService method*/
    throw new java.lang.UnsupportedOperationException("Method getIWApplicationContext() not yet implemented.");
  }

}