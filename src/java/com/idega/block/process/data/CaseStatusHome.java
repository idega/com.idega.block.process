package com.idega.block.process.data;


public interface CaseStatusHome extends com.idega.data.IDOHome
{
 public CaseStatus create() throws javax.ejb.CreateException;
 public CaseStatus findByPrimaryKey(Object pk) throws javax.ejb.FinderException;
 public java.util.Collection findAllStatuses()throws javax.ejb.FinderException;
 public java.util.Collection findGlobalStatuses()throws javax.ejb.FinderException;

}