package com.idega.block.process.data;


public class CaseCodeHomeImpl extends com.idega.data.IDOFactory implements CaseCodeHome
{
 protected Class getEntityInterfaceClass(){
  return CaseCode.class;
 }


 public CaseCode create() throws javax.ejb.CreateException{
  return (CaseCode) super.createIDO();
 }


 public CaseCode findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
  return (CaseCode) super.findByPrimaryKeyIDO(pk);
 }



}