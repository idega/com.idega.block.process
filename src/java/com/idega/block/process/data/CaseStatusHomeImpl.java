package com.idega.block.process.data;


public class CaseStatusHomeImpl extends com.idega.data.IDOFactory implements CaseStatusHome
{
 protected Class getEntityInterfaceClass(){
  return CaseStatus.class;
 }


 public CaseStatus create() throws javax.ejb.CreateException{
  return (CaseStatus) super.createIDO();
 }


 public CaseStatus findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
  return (CaseStatus) super.findByPrimaryKeyIDO(pk);
 }



}