package com.idega.block.process.data;


public class CaseHomeImpl extends com.idega.data.IDOFactory implements CaseHome
{
 protected Class getEntityInterfaceClass(){
  return Case.class;
 }


 public Case create() throws javax.ejb.CreateException{
  return (Case) super.createIDO();
 }


public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0,java.lang.String p1)throws java.rmi.RemoteException,javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseBMPBean)entity).ejbFindAllCasesByUser(p0,p1);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0)throws java.rmi.RemoteException,javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseBMPBean)entity).ejbFindAllCasesByUser(p0);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0,java.lang.String p1,java.lang.String p2)throws java.rmi.RemoteException,javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseBMPBean)entity).ejbFindAllCasesByUser(p0,p1,p2);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0,com.idega.block.process.data.CaseCode p1)throws java.rmi.RemoteException,javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseBMPBean)entity).ejbFindAllCasesByUser(p0,p1);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findSubCasesUnder(com.idega.block.process.data.Case p0)throws java.rmi.RemoteException,javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseBMPBean)entity).ejbFindSubCasesUnder(p0);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

public java.util.Collection findAllCasesByUser(com.idega.core.user.data.User p0,com.idega.block.process.data.CaseCode p1,com.idega.block.process.data.CaseStatus p2)throws java.rmi.RemoteException,javax.ejb.FinderException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	java.util.Collection ids = ((CaseBMPBean)entity).ejbFindAllCasesByUser(p0,p1,p2);
	this.idoCheckInPooledEntity(entity);
	return this.getEntityCollectionForPrimaryKeys(ids);
}

 public Case findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
  return (Case) super.findByPrimaryKeyIDO(pk);
 }


public int countSubCasesUnder(com.idega.block.process.data.Case p0)throws java.rmi.RemoteException{
	com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
	int theReturn = ((CaseBMPBean)entity).ejbHomeCountSubCasesUnder(p0);
	this.idoCheckInPooledEntity(entity);
	return theReturn;
}


}