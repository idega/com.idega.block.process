package com.idega.block.process.business;


public class CaseSessionHomeImpl extends com.idega.business.IBOHomeImpl implements CaseSessionHome
{
 protected Class getBeanInterfaceClass(){
  return CaseSession.class;
 }


 public CaseSession create() throws javax.ejb.CreateException{
  return (CaseSession) super.createIBO();
 }



}