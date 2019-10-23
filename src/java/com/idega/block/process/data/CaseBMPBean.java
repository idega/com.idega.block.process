/*
 * $Id: CaseBMPBean.java,v 1.70 2009/06/23 09:33:27 valdas Exp $
 *
 * Copyright (C) 2002 Idega hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 *
 */
package com.idega.block.process.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import org.springframework.context.ApplicationEvent;

import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.business.ProcessConstants;
import com.idega.block.process.event.CaseDeletedEvent;
import com.idega.block.process.event.CaseModifiedEvent;
import com.idega.business.IBOLookup;
import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOException;
import com.idega.data.IDOQuery;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.data.IDORuntimeException;
import com.idega.data.IDOStoreException;
import com.idega.data.MetaDataCapable;
import com.idega.data.UniqueIDCapable;
import com.idega.data.query.BetweenCriteria;
import com.idega.data.query.Column;
import com.idega.data.query.CountColumn;
import com.idega.data.query.Criteria;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.OR;
import com.idega.data.query.Order;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;
import com.idega.data.query.range.DateRange;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWMainApplication;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.user.data.UserBMPBean;
import com.idega.util.CoreConstants;
import com.idega.util.IWTimestamp;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

/**
 * <p>
 * Main implementation data entity bean for "Case".<br/> Backing SQL table is
 * PROC_CASE.
 * <p>
 * Last modified: $Date: 2009/06/23 09:33:27 $ by $Author: valdas $
 *
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.70 $
 */
public final class CaseBMPBean extends GenericEntity implements Case, UniqueIDCapable, MetaDataCapable {

	private static final long serialVersionUID = -9118580756828123883L;

	public static final String TABLE_NAME = "PROC_CASE";
	public static final String COLUMN_CASE_CODE = "CASE_CODE";
	public static final String COLUMN_CASE_STATUS = "CASE_STATUS";
	public static final String COLUMN_CREATED = "CREATED";
	static final String COLUMN_PARENT_CASE = "PARENT_CASE_ID";
	public static final String COLUMN_USER = "USER_ID";
	static final String COLUMN_CREATOR = "CREATOR_ID";
	static final String COLUMN_HANDLER = "HANDLER_GROUP_ID";
	public static final String PK_COLUMN = TABLE_NAME + "_ID";
	static final String COLUMN_EXTERNAL_ID = "EXTERNAL_ID";
	static final String COLUMN_CASE_NUMBER = "CASE_NUMBER";
	static final String COLUMN_EXTERNAL_HANDLER_ID = "EXTERNAL_HANDLER_ID";
	public static final String COLUMN_CASE_SUBJECT = "CASE_SUBJECT";
	static final String COLUMN_CASE_BODY = "CASE_BODY";
	public static final String COLUMN_CASE_MANAGER_TYPE = "CASE_MANAGER_TYPE";
	public static final String COLUMN_CASE_IDENTIFIER = "CASE_IDENTIFIER";

	public static final String COLUMN_CASE_SUBSCRIBERS = TABLE_NAME + "_SUBSCRIBERS";
	public static final String COLUMN_READ = TABLE_NAME + "READ";
	public static final String COLUMN_CASE_VOTERS = TABLE_NAME + "_VOTERS";

	public static final String CASE_STATUS_OPEN_KEY = "UBEH";
	public static final String CASE_STATUS_INACTIVE_KEY = "TYST";
	public static final String CASE_STATUS_GRANTED_KEY = "BVJD";
	public static final String CASE_STATUS_DENIED_KEY = "AVSL";
	public static final String CASE_STATUS_REVIEW_KEY = "OMPR";
	public static final String CASE_STATUS_CANCELLED_KEY = "UPPS";
	public static final String CASE_STATUS_PRELIMINARY_KEY = "PREL";
	public static final String CASE_STATUS_CONTRACT_KEY = "KOUT";
	public static final String CASE_STATUS_READY_KEY = "KLAR";
	public static final String CASE_STATUS_REDEEM_KEY = "CHIN";
	public static final String CASE_STATUS_ERROR_KEY = "ERRR";
	public static final String CASE_STATUS_MOVED_KEY = "FLYT";
	public static final String CASE_STATUS_PLACED_KEY = "PLAC";
	public static final String CASE_STATUS_DELETED_KEY = "DELE";
	public static final String CASE_STATUS_PENDING_KEY = "PEND";
	public static final String CASE_STATUS_WAITING_KEY = "WAIT";
	public static final String CASE_STATUS_IN_PROCESS_KEY = "INPR";
	public static final String CASE_STATUS_CLOSED = "SHUT";
	public static final String CASE_STATUS_ARCHIVED = "ARCH";
	public static final String CASE_STATUS_LOCKED = "LOCK";
	public static final String CASE_STATUS_GROUPED_KEY = "GROU";
	public static final String CASE_STATUS_CREATED_KEY = "CREA";
	public static final String CASE_STATUS_FINISHED_KEY = "FINI";
	public static final String CASE_STATUS_REPORT = "REPO";
	public static final String CASE_STATUS_OFFERED = "OFFR";
	public static final String CASE_STATUS_ASSIGNED = "ASSI";
	public static final String CASE_STATUS_ON_HOLD = "HOLD";
	public static final String CASE_STATUS_SPAM = "SPAM";
	public static final String CASE_STATUS_RECEIPT = "RECE";
	public static final String CASE_STATUS_ACTION = "ACTI";
	public static final String CASE_STATUS_EXTERNAL_SYSTEM = "EXSY";

	@Override
	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(COLUMN_CASE_CODE, "Case Code", true, true, String.class, 7, MANY_TO_ONE, CaseCode.class);
		addAttribute(COLUMN_CASE_STATUS, "Case status", true, true, String.class, 4, MANY_TO_ONE, CaseStatus.class);
		addAttribute(COLUMN_CREATED, "Created when", Timestamp.class);
		addAttribute(COLUMN_PARENT_CASE, "Parent case", true, true, Integer.class, MANY_TO_ONE, Case.class);
		addManyToOneRelationship(COLUMN_USER, "Owner", User.class);
		addManyToOneRelationship(COLUMN_CREATOR, "Creator", User.class);
		addManyToOneRelationship(COLUMN_HANDLER, "Handler Group/User", Group.class);
		addAttribute(COLUMN_CASE_NUMBER, "Case number", String.class, 30);
		addAttribute(COLUMN_EXTERNAL_ID, "External case id", String.class, 36);
		addManyToOneRelationship(COLUMN_EXTERNAL_HANDLER_ID, "External handler id", User.class);
		addUniqueIDColumn();
		addAttribute(COLUMN_CASE_SUBJECT, "Case subject", String.class);
		addAttribute(COLUMN_CASE_BODY, "Case body", String.class, 4000);
		addAttribute(COLUMN_CASE_MANAGER_TYPE, "Case manager type", String.class);
		addAttribute(COLUMN_CASE_IDENTIFIER, "Case identifier", String.class);
		addAttribute(COLUMN_READ,"Is case read",Boolean.class);
		addMetaDataRelationship();

		addManyToManyRelationShip(User.class, COLUMN_CASE_SUBSCRIBERS);
		addManyToManyRelationShip(User.class, COLUMN_CASE_VOTERS);

		addIndex("IDX_PROC_CASE_2", new String[] { getIDColumnName(), COLUMN_USER });
		addIndex("IDX_PROC_CASE_3", new String[] { getIDColumnName(), COLUMN_CASE_CODE });
		addIndex("IDX_PROC_CASE_4", new String[] { getIDColumnName(), COLUMN_CASE_STATUS });
		addIndex("IDX_PROC_CASE_5", new String[] { getIDColumnName(), COLUMN_CASE_CODE, COLUMN_CASE_STATUS });
		addIndex("IDX_PROC_CASE_6", new String[] { COLUMN_USER, COLUMN_CASE_CODE, COLUMN_CASE_STATUS });
		addIndex("IDX_PROC_CASE_7", new String[] { COLUMN_HANDLER, COLUMN_USER });
		addIndex("IDX_PROC_CASE_8", new String[] { COLUMN_CASE_STATUS, COLUMN_CASE_CODE, COLUMN_CREATED });
		addIndex("IDX_PROC_CASE_9", new String[] { getIDColumnName(), COLUMN_CASE_IDENTIFIER });
		getEntityDefinition().setBeanCachingActiveByDefault(true, 1000);
	}

	@Override
	public String getIDColumnName() {
		return PK_COLUMN;
	}

	@Override
	public String getEntityName() {
		return (TABLE_NAME);
	}

	@Override
	protected boolean doInsertInCreate() {
		return true;
	}

	/*
	 * public void insertStartData() { try { //CaseHome chome =
	 * (CaseHome)IDOLookup.getHome(Case.class); CaseCodeHome cchome =
	 * (CaseCodeHome) IDOLookup.getHome(CaseCode.class); CaseStatusHome cshome =
	 * (CaseStatusHome) IDOLookup.getHome(CaseStatus.class); CaseCode code =
	 * cchome.create(); code.setCode("GARENDE"); code.setDescription("General
	 * Case"); code.store(); CaseStatus status = cshome.create();
	 * status.setStatus("UBEH"); status.setDescription("Open"); status.store();
	 * status.setAssociatedCaseCode(code); status.store(); status =
	 * cshome.create(); status.setStatus("TYST");
	 * status.setDescription("Inactive"); status.store();
	 * status.setAssociatedCaseCode(code); status.store(); status =
	 * cshome.create(); status.setStatus("BVJD");
	 * status.setDescription("Granted"); status.store();
	 * status.setAssociatedCaseCode(code); status.store(); status =
	 * cshome.create(); status.setStatus("AVSL"); status.setDescription("Denied");
	 * status.store(); status.setAssociatedCaseCode(code); status.store(); status =
	 * cshome.create(); status.setStatus("OMPR"); status.setDescription("Review");
	 * status.store(); status.setAssociatedCaseCode(code); status.store(); status =
	 * cshome.create(); status.setStatus("KOUT"); status.setDescription("Contract
	 * sent"); status.store(); status.setAssociatedCaseCode(code); status.store();
	 * status = cshome.create(); status.setStatus("UPPS");
	 * status.setDescription("Cancelled"); status.store();
	 * status.setAssociatedCaseCode(code); status.store(); status =
	 * cshome.create(); status.setStatus("PREL");
	 * status.setDescription("Preliminary Accepted"); status.store();
	 * status.setAssociatedCaseCode(code); status.store(); // status =
	 * cshome.create(); // status.setStatus("PREL"); //
	 * status.setDescription("Preliminary Accepted in school"); // status.store(); //
	 * status.setAssociatedCaseCode(code); // status.store(); // // // status =
	 * cshome.create(); // status.setStatus("PLAC"); //
	 * status.setDescription("Accepted and placed in school group"); //
	 * status.store(); // status.setAssociatedCaseCode(code); // status.store(); // }
	 * catch (Exception e) { System.err.println("Error inserting start data for
	 * com.idega.block.process.Case"); e.printStackTrace(); } }
	 */
	@Override
	public void setDefaultValues() {
		// System.out.println("Case : Calling setDefaultValues()");
		setCreated(IWTimestamp.getTimestampRightNow());
	}

	protected CaseHome getCaseHome() {
		return (CaseHome) this.getEJBLocalHome();
	}

	@Override
	public void setCode(String caseCode) {
		setColumn(CaseBMPBean.COLUMN_CASE_CODE, caseCode);
	}

	@Override
	public String getCode() {
		return (this.getStringColumnValue(COLUMN_CASE_CODE));
	}

	@Override
	public void setCaseCode(CaseCode caseCode) {
		setColumn(CaseBMPBean.COLUMN_CASE_CODE, caseCode);
	}

	@Override
	public CaseCode getCaseCode() {
		return (CaseCode) (this.getColumnValue(COLUMN_CASE_CODE));
	}

	@Override
	public void setCaseStatus(CaseStatus status) {
		setColumn(CaseBMPBean.COLUMN_CASE_STATUS, status);
	}

	@Override
	public String getCaseManagerType() {
		return getStringColumnValue(COLUMN_CASE_MANAGER_TYPE);
	}

	@Override
	public void setCaseManagerType(String type) {
		setColumn(COLUMN_CASE_MANAGER_TYPE, type);
	}

	@Override
	public CaseStatus getCaseStatus() {
		return (CaseStatus) (this.getColumnValue(COLUMN_CASE_STATUS));
	}

	@Override
	public void setStatus(String status) {
		setColumn(CaseBMPBean.COLUMN_CASE_STATUS, status);
	}

	@Override
	public String getStatus() {
		return (this.getStringColumnValue(COLUMN_CASE_STATUS));
	}

	@Override
	public void setCreated(Timestamp statusChanged) {
		setColumn(CaseBMPBean.COLUMN_CREATED, statusChanged);
	}

	@Override
	public Timestamp getCreated() {
		return ((Timestamp) getColumnValue(COLUMN_CREATED));
	}

	@Override
	public void setParentCase(Case theCase) {
		// throw new java.lang.UnsupportedOperationException("setParentCase() not
		// implemented yet");
		this.setColumn(CaseBMPBean.COLUMN_PARENT_CASE, theCase);
	}

	@Override
	public Case getParentCase() {
		// return (Case)super.getParentNode();
		return (Case) getColumnValue(CaseBMPBean.COLUMN_PARENT_CASE);
	}

	@Override
	public void setOwner(User owner) {
		super.setColumn(COLUMN_USER, owner);
	}

	@Override
	public void setCreator(User creator) {
		super.setColumn(COLUMN_CREATOR, creator);
	}

	@Override
	public Group getHandler() {
		return (Group) this.getColumnValue(CaseBMPBean.COLUMN_HANDLER);
	}

	@Override
	public int getHandlerId() {
		return this.getIntColumnValue(CaseBMPBean.COLUMN_HANDLER);
	}

	@Override
	public void setHandler(Group handler) {
		super.setColumn(COLUMN_HANDLER, handler);
	}

	@Override
	public void setHandler(int handlerGroupID) {
		super.setColumn(COLUMN_HANDLER, handlerGroupID);
	}

	@Override
	public User getOwner() {
		return (User) this.getColumnValue(CaseBMPBean.COLUMN_USER);
	}

	@Override
	public User getCreator() {
		return (User) this.getColumnValue(CaseBMPBean.COLUMN_CREATOR);
	}

	@Override
	public void setExternalId(String externalId) {
		setColumn(COLUMN_EXTERNAL_ID, externalId);
	}

	@Override
	public String getExternalId() {
		return getStringColumnValue(COLUMN_EXTERNAL_ID);
	}

	@Override
	public void setCaseNumber(String caseNumber) {
		setColumn(COLUMN_CASE_NUMBER, caseNumber);
	}

	@Override
	public String getCaseNumber() {
		return getStringColumnValue(COLUMN_CASE_NUMBER);
	}

	@Override
	public void setExternalHandler(User user) {
		setColumn(COLUMN_EXTERNAL_HANDLER_ID, user.getPrimaryKey());
	}

	@Override
	public User getExternalHandler() {
		return (User) getColumnValue(COLUMN_EXTERNAL_HANDLER_ID);
	}

	@Override
	public String getSubject() {
		return getStringColumnValue(COLUMN_CASE_SUBJECT);
	}

	@Override
	public void setSubject(String subject) {
		setColumn(COLUMN_CASE_SUBJECT, subject);
	}

	@Override
	public String getBody() {
		return getStringColumnValue(COLUMN_CASE_BODY);
	}

	@Override
	public void setBody(String body) {
		setColumn(COLUMN_CASE_BODY, body);
	}

	@Override
	public String getCaseIdentifier() {
		return getStringColumnValue(COLUMN_CASE_IDENTIFIER);
	}

	@Override
	public void setCaseIdentifier(String caseIdentifier) {
		setColumn(COLUMN_CASE_IDENTIFIER, caseIdentifier);
	}

	@Override
	public Case getParentNode() {
		return this.getParentCase();
	}

	@Override
	public Case getChildAtIndex(int childIndex) {
		try {
			return this.getCaseHome().findByPrimaryKey(new Integer(childIndex));
		}
		catch (Exception e) {
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public int getChildCount() {
		try {
			return this.getCaseHome().countSubCasesUnder(this);
		}
		catch (Exception e) {
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public Iterator<Case> getChildrenIterator() {
		Collection<Case> children = getChildren();
		if (children != null) {
			return children.iterator();
		}

		return null;
	}

	@Override
	public Collection<Case> getChildren() {
		try {
			return this.getCaseHome().findSubCasesUnder(this);
		}
		catch (Exception e) {
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public int getSiblingCount() {
		try {
			return this.getParentCase().getChildCount();
		}
		catch (Exception e) {
			throw new EJBException(e.getMessage());
		}
	}

	/**
	 * Gets the query for finding all the cases for a user ordered in
	 * chronological order
	 */
	protected IDOQuery idoQueryGetAllCasesByUserOrdered(User user) {
		try {
			IDOQuery query = idoQueryGetAllCasesByUser(user);
			query.appendOrderBy(COLUMN_CREATED);
			return query;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	/**
	 * Gets the query for finding all the cases for a group ordered in
	 * chronological order
	 */
	protected IDOQuery idoQueryGetAllCasesByGroupOrdered(Group group) {
		try {
			IDOQuery query = idoQueryGetAllCasesByGroup(group);
			query.appendOrderBy(COLUMN_CREATED);
			return query;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	/**
	 * Gets all the cases of all casetypes for a user and orders in chronological
	 * order
	 */
	public Collection ejbFindAllCasesByUser(User user) throws FinderException {
		return idoFindPKsByQuery(idoQueryGetAllCasesByUserOrdered(user));
		/*
		 * return (Collection) super.idoFindPKsBySQL( "select * from " +
		 * this.TABLE_NAME + " where " + this.COLUMN_USER + "=" +
		 * user.getPrimaryKey().toString() + " order by " + COLUMN_CREATED);
		 */
	}

	/**
	 * Gets all the cases of all casetypes for a group and orders in chronological
	 * order
	 */
	public Collection ejbFindAllCasesByGroup(Group group) throws FinderException {
		return idoFindPKsByQuery(idoQueryGetAllCasesByGroupOrdered(group));
	}

	/**
	 * Gets all the cases for a user with a specified caseCode and orders in
	 * chronological order
	 */
	public Collection ejbFindAllCasesByUser(User user, CaseCode caseCode) throws FinderException {
		return ejbFindAllCasesByUser(user, caseCode.getCode());
	}

	/**
	 * Gets the query for finding all the cases for a user with a specified
	 * caseCode and orders in chronological order
	 */
	protected IDOQuery idoQueryGetAllCasesByUser(User user, String caseCode) {
		try {
			IDOQuery query = idoQueryGetAllCasesByUser(user);
			query.appendAndEqualsQuoted(COLUMN_CASE_CODE, caseCode);
			query.appendOrderBy(COLUMN_CREATED);
			return query;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	/**
	 * Gets all the cases for a user with a specified caseCode and orders in
	 * chronological order
	 */
	public Collection ejbFindAllCasesByUser(User user, String caseCode) throws FinderException {
		return idoFindPKsByQuery(idoQueryGetAllCasesByUser(user, caseCode));
		/*
		 * return (Collection) super.idoFindPKsBySQL( "select * from " +
		 * this.TABLE_NAME + " where " + this.COLUMN_USER + "=" +
		 * user.getPrimaryKey().toString() + " and " + this.COLUMN_CASE_CODE + "='" +
		 * caseCode + "'" + " order by " + COLUMN_CREATED);
		 */
	}

	/**
	 * Gets all the cases for a user with a specified caseStatus and caseCode and
	 * orders in chronological order
	 */
	public Collection ejbFindAllCasesByUser(User user, CaseCode caseCode, CaseStatus caseStatus) throws FinderException {
		return ejbFindAllCasesByUser(user, caseCode.getCode(), caseStatus.getStatus());
	}

	/**
	 * Gets the query for finding all the cases for a user with a specified
	 * caseStatus and caseCode and orders in chronological order
	 */
	protected IDOQuery idoQueryGetAllCasesByUser(User user, String caseCode, String caseStatus) {
		try {
			IDOQuery query = idoQueryGetAllCasesByUser(user);
			query.appendAndEqualsQuoted(COLUMN_CASE_CODE, caseCode);
			query.appendAndEqualsQuoted(COLUMN_CASE_STATUS, caseStatus);
			query.appendOrderBy(COLUMN_CREATED);
			return query;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	/**
	 * Gets all the cases for a user with a specified caseStatus and caseCode and
	 * orders in chronological order
	 */
	public Collection ejbFindAllCasesByUser(User user, String caseCode, String caseStatus) throws FinderException {
		return super.idoFindPKsByQuery(idoQueryGetAllCasesByUser(user, caseCode, caseStatus));
		/*
		 * return (Collection) super.idoFindPKsBySQL( "select * from " +
		 * this.TABLE_NAME + " where " + this.COLUMN_USER + "=" +
		 * user.getPrimaryKey().toString() + " and " + this.COLUMN_CASE_CODE + "='" +
		 * caseCode + "'" + " and " + this.COLUMN_CASE_STATUS + "='" + caseStatus +
		 * "'" + " order by " + COLUMN_CREATED);
		 */
	}

	protected IDOQuery idoQueryGetSubCasesUnder(Case theCase) {
		try {
			IDOQuery query = idoQueryGetSelect();
			query.appendWhereEqualsQuoted(COLUMN_PARENT_CASE, theCase.getPrimaryKey().toString());
			return query;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	public Collection ejbFindSubCasesUnder(Case theCase) throws FinderException {
		return super.idoFindPKsByQuery(idoQueryGetSubCasesUnder(theCase));
	}

	protected IDOQuery idoQueryGetCountSubCasesUnder(Case theCase) {
		try {
			IDOQuery query = idoQueryGetSelectCount();
			query.appendWhereEqualsQuoted(COLUMN_PARENT_CASE, theCase.getPrimaryKey().toString());
			return query;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	public int ejbHomeCountSubCasesUnder(Case theCase) {
		try {
			return super.getNumberOfRecords(idoQueryGetCountSubCasesUnder(theCase).toString());
		}
		catch (java.sql.SQLException sqle) {
			throw new EJBException(sqle.getMessage());
		}
	}

	@Override
	public int getNodeID() {
		return this.getID();
	}

	@Override
	public String getNodeName() {
		return getName();
	}

	@Override
	public String getNodeName(Locale locale) {
		return getNodeName();
	}

	@Override
	public String getNodeName(Locale locale, IWApplicationContext iwac) {
		return getNodeName(locale);
	}

	@Override
	public boolean isLeaf() {
		return (this.getChildCount() == 0);
	}

	@Override
	public int getIndex(Case node) {
		return Integer.parseInt(getId());
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	/**
	 * Returns the cASE_STATUS_CANCELLED_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusCancelled() {
		return CASE_STATUS_CANCELLED_KEY;
	}

	/**
	 * Returns the CASE_STATUS_DELETED_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusDeleted() {
		return CASE_STATUS_DELETED_KEY;
	}

	/**
	 * Returns the cASE_STATUS_DENIED_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusDenied() {
		return CASE_STATUS_DENIED_KEY;
	}

	/**
	 * Returns the cASE_STATUS_GRANTED_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusGranted() {
		return CASE_STATUS_GRANTED_KEY;
	}

	/**
	 * Returns the cASE_STATUS_INACTIVE_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusInactive() {
		return CASE_STATUS_INACTIVE_KEY;
	}

	/**
	 * Returns the cASE_STATUS_OPEN_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusOpen() {
		return CASE_STATUS_OPEN_KEY;
	}

	/**
	 * Returns the cASE_STATUS_REVIEW_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusReview() {
		return CASE_STATUS_REVIEW_KEY;
	}

	/**
	 * Returns the CASE_STATUS_WAITING.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusWaiting() {
		return CASE_STATUS_WAITING_KEY;
	}

	/**
	 * Returns the CASE_STATUS_PRELIMINARY_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusPreliminary() {
		return CASE_STATUS_PRELIMINARY_KEY;
	}

	/**
	 * Returns the CASE_STATUS_PENDING_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusPending() {
		return CASE_STATUS_PENDING_KEY;
	}

	/**
	 * Returns the CASE_STATUS_CONTRACT_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusContract() {
		return CASE_STATUS_CONTRACT_KEY;
	}

	/**
	 * Returns the CASE_STATUS_READY_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusReady() {
		return CASE_STATUS_READY_KEY;
	}

	/**
	 * Returns the CASE_STATUS_REDEEM_KEY.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusRedeem() {
		return CASE_STATUS_REDEEM_KEY;
	}

	/**
	 * Returns the CASE_STATUS_ERROR.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusError() {
		return CASE_STATUS_ERROR_KEY;
	}

	/**
	 * Returns the CASE_STATUS_MOVED.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusMoved() {
		return CASE_STATUS_MOVED_KEY;
	}

	/**
	 * Returns the CASE_STATUS_PLACED.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusPlaced() {
		return CASE_STATUS_PLACED_KEY;
	}

	/**
	 * Returns the CASE_STATUS_IN_PROCESS.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusInProcess() {
		return CASE_STATUS_IN_PROCESS_KEY;
	}

	/**
	 * Returns the CASE_STATUS_CLOSED.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusClosed() {
		return CASE_STATUS_CLOSED;
	}

	public String ejbHomeGetCaseStatusReport() {
		return CASE_STATUS_REPORT;
	}

	/**
	 * Returns the CASE_STATUS_ARCHIVED.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusArchived() {
		return CASE_STATUS_ARCHIVED;
	}

	/**
	 * Returns the CASE_STATUS_LOCKED.
	 *
	 * @return String
	 */
	public String ejbHomeGetCaseStatusLocked() {
		return CASE_STATUS_LOCKED;
	}

	public String ejbHomeGetCaseStatusGrouped() {
		return CASE_STATUS_GROUPED_KEY;
	}

	public String ejbHomeGetCaseStatusCreated() {
		return CASE_STATUS_CREATED_KEY;
	}

	public String ejbHomeGetCaseStatusFinished() {
		return CASE_STATUS_FINISHED_KEY;
	}

	public String ejbHomeGetCaseStatusOffered() {
		return CASE_STATUS_OFFERED;
	}

	protected IDOQuery idoQueryGetAllCasesForUserExceptCodes(User user, CaseCode[] codes) {
		IDOQuery query = idoQueryGetAllCasesByUser(user);
		if (codes != null) {
			String notInClause = getIDOUtil().convertArrayToCommaseparatedString(codes);
			query.appendAnd();
			query.append(COLUMN_CASE_CODE);
			query.appendNotIn(notInClause);
		}
		query.appendAnd();
		query.append(COLUMN_CASE_STATUS);
		query.appendNOTEqual();
		query.appendWithinSingleQuotes(CASE_STATUS_DELETED_KEY);
		query.appendOrderByDescending(COLUMN_CREATED);
		return query;
	}

	protected IDOQuery idoQueryGetAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) {
		String notInClause = getIDOUtil().convertArrayToCommaseparatedString(codes);
		IDOQuery query = idoQueryGetAllCasesByGroup(group);
		query.appendAnd();
		query.append(COLUMN_CASE_CODE);
		query.appendNotIn(notInClause);
		query.appendAnd();
		query.append(COLUMN_CASE_STATUS);
		query.appendNOTEqual();
		query.appendWithinSingleQuotes(CASE_STATUS_DELETED_KEY);
		query.appendOrderBy(COLUMN_CREATED);
		return query;
	}

	/**
	 * Gets all the Cases for the User except the ones with one of the CaseCode in
	 * the codes[] array and orders in chronological order
	 */
	public Collection ejbFindAllCasesForUserExceptCodes(User user, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		IDOQuery query = idoQueryGetAllCasesForUserExceptCodes(user, codes);
		log(Level.INFO, "UserCases: " + query);
		return super.idoFindPKsByQuery(query, numberOfCases, startingCase);
		/*
		 * return (Collection) super.idoFindPKsBySQL( "select * from " +
		 * this.TABLE_NAME + " where " + this.USER + "=" +
		 * user.getPrimaryKey().toString() + " and " + this.CASE_CODE + " not in (" +
		 * notInClause + ") order by " + CREATED );
		 */
	}

	public Collection ejbFindAllCasesForUserByStatuses(User user, String[] statuses, int startingCase, int numberOfCases) throws FinderException {
		SelectQuery query = getUserCaseQuery(user, statuses);
		query.addColumn(new WildCardColumn());

		return idoFindPKsByQuery(query, numberOfCases, startingCase);
	}

	public int ejbHomeGetCountOfAllCasesForUserByStatuses(User user, String[] statuses) throws IDOException {
		SelectQuery query = getUserCaseQuery(user, statuses);
		query.addColumn(new CountColumn(getIDColumnName()));

		return idoGetNumberOfRecords(query);
	}

	private SelectQuery getUserCaseQuery(User user, String[] statuses) {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addCriteria(new MatchCriteria(table, COLUMN_USER, MatchCriteria.EQUALS, user));
		query.addCriteria(new InCriteria(table, COLUMN_CASE_STATUS, statuses));
		query.addOrder(table, COLUMN_CREATED, false);
		return query;
	}

	public Collection ejbFindAllCasesForGroupsByStatuses(Collection groups, String[] statuses, int startingCase, int numberOfCases) throws FinderException {
		SelectQuery query = getGroupCaseQuery(groups, statuses);
		query.addColumn(new WildCardColumn());

		return idoFindPKsByQuery(query, numberOfCases, startingCase);
	}

	public int ejbHomeGetCountOfAllCasesForGroupsByStatuses(Collection groups, String[] statuses) throws IDOException {
		SelectQuery query = getGroupCaseQuery(groups, statuses);
		query.addColumn(new CountColumn(getIDColumnName()));

		return idoGetNumberOfRecords(query);
	}

	private SelectQuery getGroupCaseQuery(Collection groups, String[] statuses) {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addCriteria(new InCriteria(table, COLUMN_HANDLER, groups));
		query.addCriteria(new InCriteria(table, COLUMN_CASE_STATUS, statuses));
		query.addOrder(table, COLUMN_CREATED, false);
		return query;
	}

	public Collection ejbFindAllCasesForGroupsAndUserExceptCodes(User user, Collection groups, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		String[] groupIDs = new String[groups.size()];
		int row = 0;

		Iterator iter = groups.iterator();
		while (iter.hasNext()) {
			Group element = (Group) iter.next();
			groupIDs[row++] = element.getPrimaryKey().toString();
		}

		IDOQuery query = idoQueryGetAllCasesByGroupsOrUserExceptCodes(((Integer) user.getPrimaryKey()).intValue(), groupIDs, codes);
		return super.idoFindPKsByQuery(query, numberOfCases, startingCase);
	}

	/**
	 * Gets all the Cases for the User except the ones with one of the CaseCode in
	 * the codes[] array and orders in chronological order
	 */
	public Collection ejbFindAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) throws FinderException {
		IDOQuery query = idoQueryGetAllCasesForGroupExceptCodes(group, codes);
		return super.idoFindPKsByQuery(query);
	}

	/**
	 * Gets the query for selecting all cases by user.
	 *
	 * @param user
	 *          the cases has to be owned by
	 * @return IDOQuery the resulting query.
	 */
	protected IDOQuery idoQueryGetAllCasesByUser(User user) {
		try {
			IDOQuery query = this.idoQueryGetSelect();
			query.appendWhereEqualsQuoted(COLUMN_USER, user.getPrimaryKey().toString());
			return query;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	/**
	 * <p>Creates query for searching in {@link Case}s table.</p>
	 * @param caseIdentifier - {@link Case#getCaseIdentifier()},
	 * not <code>null</code>.
	 * @return formatted query.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 * @throws IDORuntimeException
	 */
	protected IDOQuery idoQueryGetAllCasesByCaseIdentifier(String caseIdentifier) {
		try {
			return idoQueryGetSelect().appendWhereEqualsQuoted(
					COLUMN_CASE_IDENTIFIER, caseIdentifier);
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	/**
	 * Gets the query for selecting all cases by group.
	 *
	 * @param group
	 *          the cases will be handled by
	 * @return IDOQuery the resulting query.
	 */
	protected IDOQuery idoQueryGetAllCasesByGroup(Group group) {
		try {
			IDOQuery query = this.idoQueryGetSelect();
			query.appendWhereEqualsQuoted(COLUMN_HANDLER, group.getPrimaryKey().toString());
			return query;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	protected IDOQuery idoQueryGetAllCasesByGroupsOrUserExceptCodes(int userID, String[] groupIDs, CaseCode[] codes) {
		IDOQuery query = this.idoQueryGetSelect();
		query.appendWhere().appendLeftParenthesis().appendEquals(COLUMN_USER, userID);
		query.appendOr().append(COLUMN_HANDLER).appendInArray(groupIDs).appendRightParenthesis();
		if (codes != null) {
			String notInClause = getIDOUtil().convertArrayToCommaseparatedString(codes);
			query.appendAnd();
			query.append(COLUMN_CASE_CODE);
			query.appendNotIn(notInClause);
		}
		query.appendOrderByDescending(COLUMN_CREATED);
		return query;
	}

	public int ejbHomeGetNumberOfCasesForUserExceptCodes(User user, CaseCode[] codes) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table, COLUMN_USER, MatchCriteria.EQUALS, user));
		if (codes != null) {
			query.addCriteria(new InCriteria(table, COLUMN_CASE_CODE, codes, true));
		}

		return super.idoGetNumberOfRecords(query);
	}

	public int ejbHomeGetNumberOfCasesByGroupsOrUserExceptCodes(User user, Collection groups, CaseCode[] codes) throws IDOException {
		String[] groupIDs = new String[groups.size()];
		int row = 0;

		Iterator iter = groups.iterator();
		while (iter.hasNext()) {
			Group element = (Group) iter.next();
			groupIDs[row++] = element.getPrimaryKey().toString();
		}

		IDOQuery query = this.idoQueryGetSelect();
		query.appendWhere().appendLeftParenthesis().appendEquals(COLUMN_USER, user.getPrimaryKey().toString());
		query.appendOr().append(COLUMN_HANDLER).appendInArray(groupIDs).appendRightParenthesis();
		if (codes != null) {
			String notInClause = getIDOUtil().convertArrayToCommaseparatedString(codes);
			query.appendAnd();
			query.append(COLUMN_CASE_CODE);
			query.appendNotIn(notInClause);
		}
		query.appendOrderByDescending(COLUMN_CREATED);
		return super.idoGetNumberOfRecords(query);
	}

	/**
	 * Gets the case with externalId='externalId'
	 */
	public Integer ejbFindCaseByExternalId(String externalId) throws FinderException {
		IDOQuery query = idoQueryGetSelect();
		query.appendWhereEqualsQuoted(COLUMN_EXTERNAL_ID, externalId);

		return (Integer) idoFindOnePKByQuery(query);
	}

	/**
	 * Gets the case with externalId='externalId'
	 */
	public Integer ejbFindCaseByUniqueId(String uniqueId) throws FinderException {
		IDOQuery query = idoQueryGetSelect();
		query.appendWhereEqualsQuoted(UNIQUE_ID_COLUMN_NAME, uniqueId);

		return (Integer) idoFindOnePKByQuery(query);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.idega.block.process.data.Case#getUrl()
	 */
	@Override
	public String getUrl() {
		return getMetaData(ProcessConstants.METADATA_KEY_URL);
	}

	@Override
	public String getId(){
		return getPrimaryKey().toString();
	}

	/**
	 * <p>Finds {@link Case#getPrimaryKey()}s by
	 * {@link Case#getCaseIdentifier()}.</p>
	 * @param caseIdentifier - {@link Case#getCaseIdentifier()}, not
	 * <code>null</code>.
	 * @return {@link Collection} of {@link Case#getPrimaryKey()} or
	 * <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 * @throws FinderException
	 */
	public Collection<Integer> ejbFindByCaseIdentifier(String caseIdentifier) throws FinderException {
		return idoFindPKsByQuery(idoQueryGetAllCasesByCaseIdentifier(caseIdentifier));
	}

	public Collection<Integer> ejbFindByCriteria(String caseNumber, String description, Collection<String> owners, String[] statuses, IWTimestamp dateFrom,
			IWTimestamp dateTo, User owner, Collection<Group> groups, boolean simpleCases) throws FinderException {
		return ejbFindByCriteria(
				caseNumber,
				description,
				owners,
				statuses,
				dateFrom,
				dateTo,
				owner,
				groups,
				simpleCases,
				null,
				null,
				null
		);
	}

	public Collection<Integer> ejbFindByCriteria(String caseNumber, String description, Collection<String> owners, String[] statuses, IWTimestamp dateFrom,
			IWTimestamp dateTo, User owner, Collection<Group> groups, boolean simpleCases, Boolean withHandler, List<Integer> exceptOwnersIds, String caseCode) throws FinderException {

		Table casesTable = new Table(this);

		SelectQuery query = new SelectQuery(casesTable);
		query.addColumn(casesTable.getColumn(getIDColumnName()));

		if (owner != null) {
			query.addCriteria(new MatchCriteria(casesTable.getColumn(CaseBMPBean.COLUMN_USER), MatchCriteria.EQUALS, owner.getId()));
		}
		if (!ListUtil.isEmpty(groups)) {
			List<String> groupsIds = new ArrayList<String>(groups.size());
			for (Group group: groups) {
				groupsIds.add(group.getId());
			}

			query.addCriteria(new InCriteria(casesTable.getColumn(COLUMN_HANDLER), groupsIds));
		}
		if (caseNumber != null) {
			Column column = new Column(casesTable, COLUMN_CASE_IDENTIFIER);
			column.setPrefix("lower(");
			column.setPostfix(")");
			query.addCriteria(new MatchCriteria(column, MatchCriteria.LIKE, true, caseNumber));
		}
		if (description != null) {
			Column column = casesTable.getColumn(CaseBMPBean.COLUMN_CASE_SUBJECT);
			column.setPrefix("lower(");
			column.setPostfix(")");
			query.addCriteria(new MatchCriteria(column, MatchCriteria.LIKE, true, description));
		}
		if (!ListUtil.isEmpty(owners) && owner == null) {
			query.addCriteria(new InCriteria(casesTable.getColumn(CaseBMPBean.COLUMN_USER), owners));
		}
		if (statuses != null && statuses.length > 0) {
			query.addCriteria(new InCriteria(casesTable.getColumn(COLUMN_CASE_STATUS), statuses));
		}
		if (dateFrom != null && dateTo != null) {
			query.addCriteria(new BetweenCriteria(casesTable.getColumn(CaseBMPBean.COLUMN_CREATED), new DateRange(dateFrom.getDate(), dateTo.getDate())));
		}
		else {
			if (dateFrom != null) {
				query.addCriteria(new MatchCriteria(casesTable.getColumn(CaseBMPBean.COLUMN_CREATED), MatchCriteria.GREATEREQUAL, dateFrom.getDate()));
			}
			if (dateTo != null) {
				query.addCriteria(new MatchCriteria(casesTable.getColumn(CaseBMPBean.COLUMN_CREATED), MatchCriteria.LESSEQUAL, dateTo.getDate()));
			}
		}
		if (simpleCases) {
			query.addCriteria(new MatchCriteria(casesTable.getColumn(CaseBMPBean.COLUMN_CASE_MANAGER_TYPE), MatchCriteria.IS, MatchCriteria.NULL));
		}
		if (withHandler != null) {
			if (withHandler.booleanValue()) {
				query.addCriteria(new MatchCriteria(casesTable.getColumn(COLUMN_HANDLER), MatchCriteria.ISNOT, MatchCriteria.NULL));
			} else {
				query.addCriteria(new MatchCriteria(casesTable.getColumn(COLUMN_HANDLER), MatchCriteria.IS, MatchCriteria.NULL));
			}
		}
		if (!ListUtil.isEmpty(exceptOwnersIds)) {
			query.addCriteria(new InCriteria(casesTable.getColumn(CaseBMPBean.COLUMN_USER), exceptOwnersIds, true));
		}

		if (!StringUtil.isEmpty(caseCode)) {
			query.addCriteria(new MatchCriteria(casesTable.getColumn(COLUMN_CASE_CODE), MatchCriteria.EQUALS, caseCode));
		}

		query.addGroupByColumn(casesTable.getColumn(getIDColumnName()));

		java.util.logging.Logger.getLogger(getClass().getName()).log(Level.INFO, query.toString());
		return idoFindPKsByQuery(query);
	}

	@Override
	public boolean addVote(User voter) throws IDOAddRelationshipException {
		if (voter == null) {
			return false;
		}

		Collection<User> voters = getVoters();
		if (!ListUtil.isEmpty(voters) && voters.contains(voter)) {
			return false;
		}

		this.idoAddTo(voter, COLUMN_CASE_VOTERS);
		return true;
	}

	@Override
	public boolean removeVote(User voter) throws IDORemoveRelationshipException {
		if (voter == null) {
			return false;
		}

		super.idoRemoveFrom(voter, COLUMN_CASE_VOTERS);
		return true;
	}

	@Override
	public Collection<User> getVoters() {
		try {
			String userId = UserBMPBean.SQL_TABLE_NAME + "_ID";
			String caseId = TABLE_NAME + "_ID";
			String query = "select u." + userId + " from " + UserBMPBean.SQL_TABLE_NAME + " u, " + COLUMN_CASE_VOTERS + " voters, " + TABLE_NAME +
					" c where c." + caseId + " = " + getId() + " and u." + userId + " = voters." + userId + " and c." + caseId + " = voters." + caseId;
			return super.idoGetRelatedEntitiesBySQL(User.class, query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addSubscriber(User subscriber) throws IDOAddRelationshipException {
		if (subscriber == null) {
			getLogger().warning("Subscriber is not provided");
			return false;
		}

		Collection<User> currentSubscribers = getSubscribers();
		if (!ListUtil.isEmpty(currentSubscribers) && currentSubscribers.contains(subscriber)) {
			return false;
		}

		this.idoAddTo(subscriber, COLUMN_CASE_SUBSCRIBERS);
		return true;
	}

	@Override
	public boolean addSubscribers(Collection<User> subscribers) {
		if (ListUtil.isEmpty(subscribers)) {
			return false;
		}

		Collection<User> currentSubscribers = getSubscribers();
		if (!ListUtil.isEmpty(currentSubscribers)) {
			subscribers.removeAll(currentSubscribers);
		}

		return idoAddTo(subscribers, COLUMN_CASE_SUBSCRIBERS);
	}

	@Override
	public Collection<User> getSubscribers() {
		try {
			String userId = UserBMPBean.SQL_TABLE_NAME + "_ID";
			String caseId = TABLE_NAME + "_ID";
			String query = "select u." + userId + " from " + UserBMPBean.SQL_TABLE_NAME + " u, " + COLUMN_CASE_SUBSCRIBERS + " s, " + TABLE_NAME +
					" c where c." + caseId + " = " + getId() + " and u." + userId + " = s." + userId + " and c." + caseId + " = s." + caseId;
			return super.idoGetRelatedEntitiesBySQL(User.class, query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean removeSubscribers() {
		return super.idoRemoveFrom(COLUMN_CASE_SUBSCRIBERS);
	}

	@Override
	public boolean removeSubscriber(User subscriber) throws IDORemoveRelationshipException {
		if (subscriber == null) {
			getLogger().warning("User is not provided");
			return false;
		}

		super.idoRemoveFrom(subscriber, COLUMN_CASE_SUBSCRIBERS);
		return true;
	}

	public Collection<?> ejbFindAllByCaseCode(CaseCode code) throws FinderException {
		IDOQuery query = this.idoQueryGetSelect();
		query.appendWhereEquals(COLUMN_CASE_CODE, code);
		return super.idoFindPKsByQuery(query);
	}

	public int ejbGetCountedCasesByCasesIdsAndByCaseCode(Collection<Integer> casesIds, String caseCode) throws FinderException, IDOException {
		Table casesTable = new Table(this);
		SelectQuery query = new SelectQuery(casesTable);
		query.addColumn(new CountColumn(casesTable, getIDColumnName()));
		query.addCriteria(new MatchCriteria(casesTable.getColumn(COLUMN_CASE_CODE), MatchCriteria.EQUALS, caseCode));
		query.addCriteria(new InCriteria(casesTable.getColumn(getIDColumnName()), casesIds));
		return super.idoGetNumberOfRecords(query);
	}

	public Integer ejbHomeGetNumberOfCasesByCaseCode(String code) throws FinderException, IDOException {
		if (StringUtil.isEmpty(code)) {
			return 0;
		}

		Table casesTable = new Table(this);
		SelectQuery query = new SelectQuery(casesTable);
		query.addColumn(new CountColumn(casesTable, getIDColumnName()));
		query.addCriteria(new MatchCriteria(casesTable.getColumn(COLUMN_CASE_CODE), MatchCriteria.EQUALS, code));
		return super.idoGetNumberOfRecords(query);
	}

	@Override
	public void store() throws IDOStoreException {
		super.store();
		publishEvent(new CaseModifiedEvent(this));
	}

	private void publishEvent(final ApplicationEvent event) {
		ELUtil.getInstance().getApplicationContext().publishEvent(event);
	}

	@Override
	public void remove() throws RemoveException {
		super.remove();
		publishEvent(new CaseDeletedEvent(this));
	}

	@Override
	public Boolean isRead() {
		return getBooleanColumnValue(COLUMN_READ);
	}

	@Override
	public void setRead(Boolean read) {
		setColumn(COLUMN_READ, read);
	}

	public Collection<Case> ejbFindCases(User user, String status,String caseCode, Boolean read)  throws FinderException{
		return ejbFindCases(user.getId(), status, caseCode, read);
	}

	public Collection<Case> ejbFindCases(String userId, String status,String caseCode, Boolean read)  throws FinderException{
		try {

			SelectQuery query = idoQueryGetAllCasesByUser(userId, status, caseCode, read);
			Order order = new Order(idoQueryTable().getColumn(COLUMN_CREATED), false);
			query.addOrder(order);
			return idoFindPKsByQuery(query);
		}
		catch(FinderException e){
			throw e;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	protected SelectQuery idoSelectQueryGetAllCasesByUser(String userId) {
		SelectQuery query = idoSelectQuery();
		query.addCriteria(idoCriteriaForUser(userId));
		return query;
	}

	protected Criteria idoCriteriaForUser(String userId) {
		return new MatchCriteria(idoQueryTable(), COLUMN_USER, MatchCriteria.EQUALS, userId);
	}

	public Criteria idoCriteriaForStatus(String caseStatus) {
		return new MatchCriteria(idoQueryTable(), COLUMN_CASE_STATUS, MatchCriteria.EQUALS, caseStatus, true);
	}

	public Criteria idoCriteriaForCaseCode(String code) {
		return new MatchCriteria(idoQueryTable(), COLUMN_CASE_CODE, MatchCriteria.EQUALS, code, true);
	}

	public Criteria idoCriteriaForCaseRead(boolean read) {
		if(read){
			return new MatchCriteria(idoQueryTable(), COLUMN_READ, MatchCriteria.EQUALS, CoreConstants.Y, true);
		}
		Criteria notRead = new MatchCriteria(idoQueryTable(), COLUMN_READ, MatchCriteria.NOTEQUALS, CoreConstants.Y, true);
		Criteria isNull = new MatchCriteria(idoQueryTable().getColumn(COLUMN_READ), false);
		OR orCriteria = new OR(notRead, isNull);
		return orCriteria;

	}

	protected SelectQuery idoQueryGetAllCasesByUser(String userId, String status, String caseCode,Boolean read) {
		try {
			SelectQuery query = idoSelectQueryGetAllCasesByUser(userId);

			if(status != null){
				query.addCriteria(idoCriteriaForStatus(status));
			}
			if(caseCode != null){
				query.addCriteria(idoCriteriaForCaseCode(caseCode));
			}
			if(read != null){
					query.addCriteria(idoCriteriaForCaseRead(read));
			}
			return query;
		}
		catch (Exception e) {
			throw new IDORuntimeException(e, this);
		}
	}

	public Collection<Integer> ejbFindAllCasesBySubscriber(User subscriber) throws FinderException {
		String query = "select " + PK_COLUMN + " from " + COLUMN_CASE_SUBSCRIBERS + " where " + UserBMPBean.getColumnNameUserID() +
				" = " + subscriber.getId();
		return idoFindPKsBySQL(query);
	}

	@Override
	public boolean isClosed() {
		CaseStatus status = getCaseStatus();
		if (status == null) {
			return false;
		}

		String statusKey = status.getStatus();
		if (StringUtil.isEmpty(statusKey)) {
			return false;
		}

		try {
			CaseBusiness caseBusiness = IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CaseBusiness.class);
			List<String> closedCaseStatuses = Arrays.asList(caseBusiness.getStatusesForClosedCases());
			return closedCaseStatuses.contains(statusKey);
		} catch (Exception e) {}
		return false;
	}

}