package com.idega.block.process.data;


public interface CaseLogHome extends com.idega.data.IDOHome
{
 public CaseLog create() throws javax.ejb.CreateException;
 public CaseLog findByPrimaryKey(Object pk) throws javax.ejb.FinderException;
 public java.util.Collection findAllCaseLogsByCase(com.idega.block.process.data.Case p0)throws javax.ejb.FinderException,java.rmi.RemoteException;
 public java.util.Collection findAllCaseLogsByCaseAndDate(java.lang.String p0,java.sql.Timestamp p1,java.sql.Timestamp p2)throws javax.ejb.FinderException;
 public java.util.Collection findAllCaseLogsByCaseAndDateAndStatusChange(java.lang.String p0,java.sql.Timestamp p1,java.sql.Timestamp p2,java.lang.String p3,java.lang.String p4)throws javax.ejb.FinderException;
 public java.util.Collection findAllCaseLogsByDate(java.sql.Timestamp p0,java.sql.Timestamp p1)throws javax.ejb.FinderException;
 public java.util.Collection findAllCaseLogsByDateAndStatusChange(java.sql.Timestamp p0,java.sql.Timestamp p1,java.lang.String p2,java.lang.String p3)throws javax.ejb.FinderException;
 public CaseLog findLastCaseLogForCase(com.idega.block.process.data.Case p0)throws javax.ejb.FinderException;

}