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

  public CaseDaemonBean() {
  }
  public String getServiceName() {
    return "CaseDeamon";
  }

  public void executeTimedService() {
    processAllPendingCases();
  }

  /**
   * Can be overrided in subclasses for specific implementation
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

  public Collection getAllPendingCases(){
    return null;
  }

  public boolean isCaseProcessable(Case theCase){
    return true;
  }

  public abstract void onCaseProcess(Case theCase);

}