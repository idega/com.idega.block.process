/*
 * $Id: CaseBMPBean.java,v 1.21 2002/11/01 02:06:56 palli Exp $
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
import com.idega.util.IWTimestamp;
//import com.idega.core.user.data.User;
import com.idega.user.data.Group;
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
	static final String COLUMN_HANDLER = "HANDLER_GROUP_ID";
	static final String PK_COLUMN = TABLE_NAME + "_ID";
	static final String CASE_STATUS_OPEN_KEY = "UBEH";
	static final String CASE_STATUS_INACTIVE_KEY = "TYST";
	static final String CASE_STATUS_GRANTED_KEY = "BVJD";
	static final String CASE_STATUS_DENIED_KEY = "AVSL";
	static final String CASE_STATUS_REVIEW_KEY = "OMPR";
	static final String CASE_STATUS_CANCELLED_KEY = "UPPS";
	static final String CASE_STATUS_PRELIMINARY_KEY = "PREL";
	static final String CASE_STATUS_CONTRACT_KEY = "KOUT";
	static final String CASE_STATUS_READY_KEY = "KLAR";
	static final String CASE_STATUS_REDEEM_KEY = "CHIN";
	
	
	public void initializeAttributes()
	{
		addAttribute(getIDColumnName());
		addAttribute(CASE_CODE, "Case Code", true, true, String.class, 7, super.MANY_TO_ONE, CaseCode.class);
		addAttribute(CASE_STATUS, "Case status", true, true, String.class, 4, super.MANY_TO_ONE, CaseStatus.class);
		addAttribute(CREATED, "Created when", Timestamp.class);
		addAttribute(PARENT_CASE, "Parent case", true, true, Integer.class, super.MANY_TO_ONE, Case.class);
		addManyToOneRelationship(USER, "Owner", User.class);
		addManyToOneRelationship(COLUMN_HANDLER, "Handler Group/User", Group.class);
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
	/*public void insertStartData()
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
			//      status = cshome.create();
			//      status.setStatus("PREL");
			//      status.setDescription("Preliminary Accepted in school");
			//      status.store();
			//      status.setAssociatedCaseCode(code);
			//      status.store();
			//
			//
			//      status = cshome.create();
			//      status.setStatus("PLAC");
			//      status.setDescription("Accepted and placed in school group");
			//      status.store();
			//      status.setAssociatedCaseCode(code);
			//      status.store();
			//
		}
		catch (Exception e)
		{
			System.err.println("Error inserting start data for com.idega.block.process.Case");
			e.printStackTrace();
		}
	}*/
	public void setDefaultValues()
	{
		//System.out.println("Case : Calling setDefaultValues()");
		setCreated(IWTimestamp.getTimestampRightNow());
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
	public Group getHandler()
	{
		return (Group) this.getColumnValue(this.COLUMN_HANDLER);
	}
	public int getHandlerId()
	{
		return this.getIntColumnValue(this.COLUMN_HANDLER);
	}
	
	public void setHandler(Group handler)
	{
		super.setColumn(COLUMN_HANDLER, handler);
	}
	public void setHandler(int handlerGroupID)
	{
		super.setColumn(COLUMN_HANDLER, handlerGroupID);
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

	/**
	 * Gets all the cases of all casetypes for a user and orders in chronological order
	 */
	public Collection ejbFindAllCasesByUser(User user) throws FinderException, RemoteException
	{
		return (Collection) super.idoFindPKsBySQL(
			"select * from " 
			+ this.TABLE_NAME 
			+ " where " 
			+ this.USER
			+ "=" 
			+ user.getPrimaryKey().toString()
			+ " order by "
			+ CREATED);
	}
	/**
	 * Gets all the cases for a user with a specified caseCode and orders in chronological order
	 */
	public Collection ejbFindAllCasesByUser(User user, CaseCode caseCode) throws FinderException, RemoteException
	{
		return ejbFindAllCasesByUser(user, caseCode.getCode());
	}
	/**
	 * Gets all the cases for a user with a specified caseCode and orders in chronological order
	 */
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
				+ "'"
				+ " order by "
				+ CREATED);
	}

	/**
	 * Gets all the cases for a user with a specified caseStatus and caseCode and orders in chronological order
	 */
	public Collection ejbFindAllCasesByUser(User user, CaseCode caseCode, CaseStatus caseStatus)
		throws FinderException, RemoteException
	{
		return ejbFindAllCasesByUser(user, caseCode.getCode(), caseStatus.getStatus());
	}
	/**
	 * Gets all the cases for a user with a specified caseStatus and caseCode and orders in chronological order
	 */
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
				+ "'"
				+ " order by "
				+ CREATED);
	}
	public Collection ejbFindSubCasesUnder(Case theCase) throws FinderException, RemoteException
	{
		return (Collection) super.idoFindPKsBySQL(
			"select * from " + this.TABLE_NAME + " where " + this.PARENT_CASE + "=" + theCase.getPrimaryKey().toString());
	}
	public int ejbHomeCountSubCasesUnder(Case theCase) throws RemoteException
	{
		try
		{
			return super.getNumberOfRecords(
				"select count(*) from " + this.TABLE_NAME + " where " + this.PARENT_CASE + "=" + theCase.getPrimaryKey().toString());
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
	/**
	 * Returns the cASE_STATUS_CANCELLED_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusCancelled()
	{
		return CASE_STATUS_CANCELLED_KEY;
	} /**
	 * Returns the cASE_STATUS_DENIED_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusDenied()
	{
		return CASE_STATUS_DENIED_KEY;
	}
	/**
	 * Returns the cASE_STATUS_GRANTED_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusGranted()
	{
		return CASE_STATUS_GRANTED_KEY;
	}
	/**
	 * Returns the cASE_STATUS_INACTIVE_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusInactive()
	{
		return CASE_STATUS_INACTIVE_KEY;
	}
	/**
	 * Returns the cASE_STATUS_OPEN_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusOpen()
	{
		return CASE_STATUS_OPEN_KEY;
	}
	/**
	 * Returns the cASE_STATUS_REVIEW_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusReview()
	{
		return CASE_STATUS_REVIEW_KEY;
	}

	/**
	 * Returns the CASE_STATUS_PRELIMINARY_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusPreliminary()
	{
		return CASE_STATUS_PRELIMINARY_KEY;
	}

	/**
	 * Returns the CASE_STATUS_CONTRACT_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusContract()
	{
		return CASE_STATUS_CONTRACT_KEY;
	}

	/**
	 * Returns the CASE_STATUS_READY_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusReady()
	{
		return CASE_STATUS_READY_KEY;
	}

	/**
	 * Returns the CASE_STATUS_REDEEM_KEY.
	 * @return String
	 */
	public String ejbHomeGetCaseStatusRedeem()
	{
		return CASE_STATUS_REDEEM_KEY;
	}

	/**
	 * Gets all the Cases for the User except the ones with one of the CaseCode in the codes[] array and orders in chronological order
	 */
	public Collection ejbFindAllCasesForUserExceptCodes(User user,CaseCode[] codes) throws FinderException, RemoteException
	{
		String notInClause = getIDOUtil().convertArrayToCommaseparatedString(codes);
		return (Collection) super.idoFindPKsBySQL(
			"select * from "
				+ this.TABLE_NAME
				+ " where "
				+ this.USER
				+ "="
				+ user.getPrimaryKey().toString()
				+ " and "
				+ this.CASE_CODE
				+ " not in ("
				+ notInClause
				+ ") order by "
				+ CREATED
				);
	}


}
