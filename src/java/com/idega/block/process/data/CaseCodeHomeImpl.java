package com.idega.block.process.data;


public class CaseCodeHomeImpl extends com.idega.data.IDOFactory implements CaseCodeHome
{
 protected Class getEntityInterfaceClass(){
  return CaseCode.class;
 }


 public CaseCode create() throws javax.ejb.CreateException{
  return (CaseCode) super.createIDO();
 }


public java.util.Collection findAllCaseCodes()throws javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseCodeBMPBean)entity).ejbFindAllCaseCodes();
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

 public CaseCode findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
  return (CaseCode) super.findByPrimaryKeyIDO(pk);
 }



}