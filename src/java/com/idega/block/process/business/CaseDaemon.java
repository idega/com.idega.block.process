package com.idega.block.process.business;

import java.util.Collection;

import java.rmi.RemoteException;

/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author
 * @version 1.0
 */

public interface CaseDaemon extends com.idega.idegaweb.IWService,com.idega.business.IBOService{

  /**
   * Returns a Collection of CaseCode objects
   */
  public Collection getAssociatedCaseCodes()throws RemoteException;


  /**
   * Get the associated CaseBusiness implementation
   */
  public CaseBusiness getCaseBusiness()throws RemoteException;


}