package com.idega.block.process.data;


public class CaseLogHomeImpl extends com.idega.data.IDOFactory implements CaseLogHome
{
 protected Class getEntityInterfaceClass(){
  return CaseLog.class;
 }


 public CaseLog create() throws javax.ejb.CreateException{
  return (CaseLog) super.createIDO();
 }


public java.util.Collection findAllCaseLogsByCase(com.idega.block.process.data.Case p0)throws javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseLogBMPBean)entity).ejbFindAllCaseLogsByCase(p0);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findAllCaseLogsByCaseAndDate(java.lang.String p0,java.sql.Timestamp p1,java.sql.Timestamp p2)throws javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseLogBMPBean)entity).ejbFindAllCaseLogsByCaseAndDate(p0,p1,p2);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findAllCaseLogsByCaseAndDateAndStatusChange(java.lang.String p0,java.sql.Timestamp p1,java.sql.Timestamp p2,java.lang.String p3,java.lang.String p4)throws javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseLogBMPBean)entity).ejbFindAllCaseLogsByCaseAndDateAndStatusChange(p0,p1,p2,p3,p4);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findAllCaseLogsByDate(java.sql.Timestamp p0,java.sql.Timestamp p1)throws javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseLogBMPBean)entity).ejbFindAllCaseLogsByDate(p0,p1);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findAllCaseLogsByDateAndStatusChange(java.sql.Timestamp p0,java.sql.Timestamp p1,java.lang.String p2,java.lang.String p3)throws javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseLogBMPBean)entity).ejbFindAllCaseLogsByDateAndStatusChange(p0,p1,p2,p3);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public CaseLog findLastCaseLogForCase(com.idega.block.process.data.Case p0)throws javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	Object pk = ((CaseLogBMPBean)entity).ejbFindLastCaseLogForCase(p0);
	this.idoCheckInPooledEntity(entity);
	return this.findByPrimaryKey(pk);
}

 public CaseLog findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
  return (CaseLog) super.findByPrimaryKeyIDO(pk);
 }



}