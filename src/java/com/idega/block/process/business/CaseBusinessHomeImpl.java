package com.idega.block.process.business;


public class CaseBusinessHomeImpl extends com.idega.business.IBOHomeImpl implements CaseBusinessHome
{
 protected Class getBeanInterfaceClass(){
  return CaseBusiness.class;
 }


 public CaseBusiness create() throws javax.ejb.CreateException{
  return (CaseBusiness) super.createIBO();
 }



}