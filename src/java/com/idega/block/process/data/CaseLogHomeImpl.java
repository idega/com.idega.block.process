package com.idega.block.process.data;


public class CaseLogHomeImpl extends com.idega.data.IDOFactory implements CaseLogHome
{
 protected Class getEntityInterfaceClass(){
  return CaseLog.class;
 }


 public CaseLog create() throws javax.ejb.CreateException{
  return (CaseLog) super.createIDO();
 }


public java.util.Collection findAllCaseLogsByCase(com.idega.block.process.data.Case p0)throws javax.ejb.FinderException,java.rmi.RemoteException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseLogBMPBean)entity).ejbFindAllCaseLogsByCase(p0);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

 public CaseLog findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
  return (CaseLog) super.findByPrimaryKeyIDO(pk);
 }



}