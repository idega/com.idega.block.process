/*
 * $Id: CaseBMPBean.java,v 1.10 2002/07/15 14:27:39 tryggvil Exp $
 *
 * Copyright (C) 2002 Idega hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 *
 */
package com.idega.block.process.data;
import java.util.Collection;
import java.util.Iterator;
import java.sql.Timestamp;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.idega.util.idegaTimestamp;
//import com.idega.core.user.data.User;
import com.idega.user.data.User;
import com.idega.data.*;
import com.idega.core.ICTreeNode;
/**
 *
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public final class CaseBMPBean extends com.idega.data.GenericEntity implements Case, com.idega.core.ICTreeNode
{
	public static final String TABLE_NAME = "PROC_CASE";
	static final String CASE_CODE = "CASE_CODE";
	static final String CASE_STATUS = "CASE_STATUS";
	static final String CREATED = "CREATED";
	static final String PARENT_CASE = "PARENT_CASE_ID";
	static final String USER = "USER_ID";
	static final String PK_COLUMN = TABLE_NAME + "_ID";
	public void initializeAttributes()
	{
		addAttribute(getIDColumnName());
		addAttribute(CASE_CODE, "Case Code", true, true, String.class, 7, super.MANY_TO_ONE, CaseCode.class);
		addAttribute(CASE_STATUS, "Case status", true, true, String.class, 4, super.MANY_TO_ONE, CaseStatus.class);
		addAttribute(CREATED, "Created when", Timestamp.class);
		addAttribute(PARENT_CASE, "Parent case", true, true, Integer.class, super.MANY_TO_ONE, Case.class);
		addAttribute(USER, "Owner", true, true, Integer.class, super.MANY_TO_ONE, User.class);
	}
	public String getIDColumnName()
	{
		return PK_COLUMN;
	}
	public String getEntityName()
	{
		return (TABLE_NAME);
	}
	protected boolean doInsertInCreate()
	{
		return true;
	}
	public void insertStartData()
	{
		try
		{
			//CaseHome chome = (CaseHome)IDOLookup.getHome(Case.class);
			CaseCodeHome cchome = (CaseCodeHome) IDOLookup.getHome(CaseCode.class);
			CaseStatusHome cshome = (CaseStatusHome) IDOLookup.getHome(CaseStatus.class);
			CaseCode code = cchome.create();
			code.setCode("GARENDE");
			code.setDescription("General Case");
			code.store();
			CaseStatus status = cshome.create();
			status.setStatus("UBEH");
			status.setDescription("Open");
			status.store();
			status.setAssociatedCaseCode(code);
			status.store();
			status = cshome.create();
			status.setStatus("TYST");
			status.setDescription("Inactive");
			status.store();
			status.setAssociatedCaseCode(code);
			status.store();
			status = cshome.create();
			status.setStatus("BVJD");
			status.setDescription("Granted");
			status.store();
			status.setAssociatedCaseCode(code);
			status.store();
			status = cshome.create();
			status.setStatus("AVSL");
			status.setDescription("Denied");
			status.store();
			status.setAssociatedCaseCode(code);
			status.store();
			status = cshome.create();
			status.setStatus("OMPR");
			status.setDescription("Review");
			status.store();
			status.setAssociatedCaseCode(code);
			status.store();
			status = cshome.create();
			status.setStatus("KOUT");
			status.setDescription("Contract sent");
			status.store();
			status.setAssociatedCaseCode(code);
			status.store();
			status = cshome.create();
			status.setStatus("UPPS");
			status.setDescription("Cancelled");
			status.store();
			status.setAssociatedCaseCode(code);
			status.store();
			status = cshome.create();
			status.setStatus("PREL");
			status.setDescription("Preliminary Accepted");
			status.store();
			status.setAssociatedCaseCode(code);
			status.store();
			/*      status = cshome.create();
			      status.setStatus("PREL");
			      status.setDescription("Preliminary Accepted in school");
			      status.store();
			      status.setAssociatedCaseCode(code);
			      status.store();
			
			      status = cshome.create();
			      status.setStatus("PLAC");
			      status.setDescription("Accepted and placed in school group");
			      status.store();
			      status.setAssociatedCaseCode(code);
			      status.store();
			*/
		}
		catch (Exception e)
		{
			System.err.println("Error inserting start data for com.idega.block.process.Case");
			e.printStackTrace();
		}
	}
	public void setDefaultValues()
	{
		//System.out.println("Case : Calling setDefaultValues()");
		setCreated(idegaTimestamp.getTimestampRightNow());
	}
	protected CaseHome getCaseHome()
	{
		return (CaseHome) this.getEJBHome();
	}
	public void setCode(String caseCode)
	{
		setColumn(this.CASE_CODE, caseCode);
	}
	public String getCode()
	{
		return (this.getStringColumnValue(CASE_CODE));
	}
	public void setCaseCode(CaseCode caseCode)
	{
		setColumn(this.CASE_CODE, caseCode);
	}
	public CaseCode getCaseCode()
	{
		return (CaseCode) (this.getColumnValue(CASE_CODE));
	}
	public void setCaseStatus(CaseStatus status)
	{
		setColumn(this.CASE_STATUS, status);
	}
	public CaseStatus getCaseStatus()
	{
		return (CaseStatus) (this.getColumnValue(CASE_STATUS));
	}
	public void setStatus(String status)
	{
		setColumn(this.CASE_STATUS, status);
	}
	public String getStatus()
	{
		return (this.getStringColumnValue(CASE_STATUS));
	}
	public void setCreated(Timestamp statusChanged)
	{
		setColumn(this.CREATED, statusChanged);
	}
	public Timestamp getCreated()
	{
		return ((Timestamp) getColumnValue(CREATED));
	}
	public void setParentCase(Case theCase)
	{
		//throw new java.lang.UnsupportedOperationException("setParentCase() not implemented yet");
		this.setColumn(this.PARENT_CASE, theCase);
	}
	public Case getParentCase()
	{
		//return (Case)super.getParentNode();
		return (Case) getColumnValue(this.PARENT_CASE);
	}
	public void setOwner(User owner)
	{
		super.setColumn(USER, owner);
	}
	public User getOwner()
	{
		return (User) this.getColumnValue(this.USER);
	}
	public ICTreeNode getParentNode()
	{
		return this.getParentCase();
	}
	public ICTreeNode getChildAtIndex(int childIndex)
	{
		try
		{
			return this.getCaseHome().findByPrimaryKey(new Integer(childIndex));
		}
		catch (Exception e)
		{
			throw new EJBException(e.getMessage());
		}
	}
	public int getChildCount()
	{
		try
		{
			return this.getCaseHome().countSubCasesUnder(this);
		}
		catch (Exception e)
		{
			throw new EJBException(e.getMessage());
		}
	}
	public Iterator getChildren()
	{
		try
		{
			return this.getCaseHome().findSubCasesUnder(this).iterator();
		}
		catch (Exception e)
		{
			throw new EJBException(e.getMessage());
		}
	}
	public int getSiblingCount()
	{
		try
		{
			return this.getParentCase().getChildCount();
		}
		catch (Exception e)
		{
			throw new EJBException(e.getMessage());
		}
	}
	public Collection ejbFindAllCasesByUser(User user) throws FinderException, RemoteException
	{
		return (Collection) super.idoFindPKsBySQL(
			"select * from " + this.TABLE_NAME + " where " + this.USER + "=" + user.getPrimaryKey().toString());
	}
	public Collection ejbFindAllCasesByUser(User user, CaseCode caseCode) throws FinderException, RemoteException
	{
		return ejbFindAllCasesByUser(user, caseCode.getCode());
	}
	public Collection ejbFindAllCasesByUser(User user, String caseCode) throws FinderException, RemoteException
	{
		return (Collection) super.idoFindPKsBySQL(
			"select * from "
				+ this.TABLE_NAME
				+ " where "
				+ this.USER
				+ "="
				+ user.getPrimaryKey().toString()
				+ " and "
				+ this.CASE_CODE
				+ "='"
				+ caseCode
				+ "'");
	}
	public Collection ejbFindAllCasesByUser(User user, CaseCode caseCode, CaseStatus caseStatus)
		throws FinderException, RemoteException
	{
		return ejbFindAllCasesByUser(user, caseCode.getCode(), caseStatus.getStatus());
	}
	public Collection ejbFindAllCasesByUser(User user, String caseCode, String caseStatus)
		throws FinderException, RemoteException
	{
		return (Collection) super.idoFindPKsBySQL(
			"select * from "
				+ this.TABLE_NAME
				+ " where "
				+ this.USER
				+ "="
				+ user.getPrimaryKey().toString()
				+ " and "
				+ this.CASE_CODE
				+ "='"
				+ caseCode
				+ "'"
				+ " and "
				+ this.CASE_STATUS
				+ "='"
				+ caseStatus
				+ "'");
	}
	public Collection ejbFindSubCasesUnder(Case theCase) throws FinderException, RemoteException
	{
		return (Collection) super.idoFindPKsBySQL(
			"select * from " + this.TABLE_NAME + " where " + this.PARENT_CASE + "=" + theCase.getID());
	}
	public int ejbHomeCountSubCasesUnder(Case theCase) throws RemoteException
	{
		try
		{
			return super.getNumberOfRecords(
				"select count(*) from " + this.TABLE_NAME + " where " + this.PARENT_CASE + "=" + theCase.getID());
		}
		catch (java.sql.SQLException sqle)
		{
			throw new EJBException(sqle.getMessage());
		}
	}
	public int getNodeID()
	{
		return this.getID();
	}
	public String getNodeName()
	{
		return getName();
	}
	public boolean isLeaf()
	{
		return (this.getChildCount() == 0);
	}
	public int getIndex(ICTreeNode node)
	{
		return node.getNodeID();
	}
	public boolean getAllowsChildren()
	{
		return true;
	}
}
