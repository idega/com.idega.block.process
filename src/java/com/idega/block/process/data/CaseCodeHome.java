package com.idega.block.process.data;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOHome;


public interface CaseCodeHome extends IDOHome
{
 public CaseCode create() throws CreateException;
 public CaseCode findByPrimaryKey(Object pk) throws FinderException;
 public Collection<CaseCode> findAllCaseCodes() throws FinderException;

}