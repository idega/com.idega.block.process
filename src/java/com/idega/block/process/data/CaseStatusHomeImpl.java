package com.idega.block.process.data;


public class CaseStatusHomeImpl extends com.idega.data.IDOFactory implements CaseStatusHome
{
 protected Class getEntityInterfaceClass(){
  return CaseStatus.class;
 }


 public CaseStatus create() throws javax.ejb.CreateException{
  return (CaseStatus) super.createIDO();
 }


public java.util.Collection findAllStatuses()throws javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseStatusBMPBean)entity).ejbFindAllStatuses();
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findGlobalStatuses()throws javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseStatusBMPBean)entity).ejbFindGlobalStatuses();
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

 public CaseStatus findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
  return (CaseStatus) super.findByPrimaryKeyIDO(pk);
 }



}