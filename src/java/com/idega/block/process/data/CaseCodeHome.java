package com.idega.block.process.data;


public interface CaseCodeHome extends com.idega.data.IDOHome
{
 public CaseCode create() throws javax.ejb.CreateException;
 public CaseCode findByPrimaryKey(Object pk) throws javax.ejb.FinderException;
 public java.util.Collection findAllCaseCodes()throws javax.ejb.FinderException;

}