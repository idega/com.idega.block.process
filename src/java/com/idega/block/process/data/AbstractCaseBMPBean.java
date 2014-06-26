/*
 * $Id: AbstractCaseBMPBean.java,v 1.61 2009/01/09 16:10:07 donatas Exp $
 *
 * Copyright (C) 2002-2006 Idega hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 *
 */
package com.idega.block.process.data;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.idega.block.process.business.CaseBusiness;
import com.idega.business.IBOLookup;
import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOException;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.data.IDORuntimeException;
import com.idega.data.IDOStoreException;
import com.idega.data.MetaData;
import com.idega.data.MetaDataBMPBean;
import com.idega.data.MetaDataCapable;
import com.idega.data.UniqueIDCapable;
import com.idega.data.query.AND;
import com.idega.data.query.Column;
import com.idega.data.query.Criteria;
import com.idega.data.query.InCriteria;
import com.idega.data.query.JoinCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.OR;
import com.idega.data.query.Order;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWMainApplication;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.user.data.UserBMPBean;
import com.idega.util.CoreConstants;
import com.idega.util.IWTimestamp;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

/**
 * <p>
 * This entity class is a abstract class for extending the standard "Case" entity.<br/> This class is convenient to extend the Case entity by adding
 * a second table that is one-to-one related to the base Case entity table.
 * <p>
 * Last modified: $Date: 2009/01/09 16:10:07 $ by $Author: donatas $
 *
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.61 $
 */
public abstract class AbstractCaseBMPBean extends GenericEntity implements Case, MetaDataCapable, UniqueIDCapable {

	private static final long serialVersionUID = -5220291917851850708L;

	private Case _case;
	private Table caseTable;
	private Table genCaseTable;

	/**
	 * Returns a unique Key to identify this CaseCode
	 */
	public abstract String getCaseCodeKey();

	/**
	 * Returns a description for the CaseCode associated with this case type
	 */
	public abstract String getCaseCodeDescription();

	public void addGeneralCaseRelation() {
		this.addManyToOneRelationship(getIDColumnName(), "Case ID", Case.class);
		this.getAttribute(getIDColumnName()).setAsPrimaryKey(true);
	}

	@Override
	public Object ejbCreate() throws CreateException {
		this._case = this.getCaseHome().create();
		this._case.setStatus(this.getCaseStatusOpen());
		this.setPrimaryKey(this._case.getPrimaryKey());
		return super.ejbCreate();
	}

	@Override
	public void setDefaultValues() {
		/*
		 * try{ System.out.println("AbstractCase : Calling setDefaultValues()"); setCode(getCaseCodeKey()); } catch(RemoteException e){ throw new
		 * EJBException(e.getMessage()); }
		 */
	}

	private static final String SUBSCRIBERS = "_SUBSCRIBERS",
								VOTERS = "_VOTERS";

	@Override
	public void initializeAttributes() {
		addManyToManyRelationShip(User.class, getTableName() + SUBSCRIBERS);
		addManyToManyRelationShip(User.class, getTableName() + VOTERS);
	}

	@Override
	public void insertStartData() {
		try {
			// CaseHome chome = (CaseHome)IDOLookup.getHome(Case.class);
			CaseCodeHome cchome = (CaseCodeHome) IDOLookup.getHome(CaseCode.class);
			CaseStatusHome cshome = (CaseStatusHome) IDOLookup.getHome(CaseStatus.class);
			CaseCode code = cchome.create();
			code.setCode(getCaseCodeKey());
			code.setDescription(getCaseCodeDescription());
			code.store();
			String[] statusKeys = this.getCaseStatusKeys();
			String[] statusDescs = this.getCaseStatusDescriptions();
			if (statusKeys != null) {
				for (int i = 0; i < statusKeys.length; i++) {
					String statusKey = null;
					try {
						statusKey = statusKeys[i];
						String statusDesc = null;
						try {
							statusDesc = statusDescs[i];
						}
						catch (java.lang.NullPointerException ne) {
						}
						catch (java.lang.ArrayIndexOutOfBoundsException arre) {
						}
						CaseStatus status = cshome.create();
						status.setStatus(statusKey);
						if (statusDesc != null) {
							status.setDescription(statusDesc);
						}
						status.store();
						code.addAssociatedCaseStatus(status);
					}
					catch (Exception e) {
						// e.printStackTrace();
						System.err.println("Error inserting CaseStatus for key: " + statusKey);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Could be ovverrided for extra CaseStatus Keys associated with this CaseCode Returns an array of Strings.
	 */
	public String[] getCaseStatusKeys() {
		return null;
	}

	/**
	 * Could be ovverrided for extra CaseStatus Descriptions associated with this CaseCode Returns an array of String descriptions Does not need to
	 * return anything (but null), but if it returns a non-null value then the array must be as long as returned by getCaseStatusKeys()
	 */
	public String[] getCaseStatusDescriptions() {
		return null;
	}

	@Override
	protected boolean doInsertInCreate() {
		return true;
	}

	@Override
	public Object ejbFindByPrimaryKey(Object key) throws FinderException {
		this._case = this.getCaseHome().findByPrimaryKey(key);
		return super.ejbFindByPrimaryKey(key);
	}

	@Override
	public void store() throws IDOStoreException {
		initializeCaseCodeIfNull();
		getGeneralCase().store();
		super.store();
	}

	@Override
	public void remove() throws RemoveException {
		super.remove();
		getGeneralCase().remove();
	}

	protected CaseHome getCaseHome() {
		try {
			return (CaseHome) com.idega.data.IDOLookup.getHome(Case.class);
		}
		catch (IDOLookupException e) {
			throw new IDORuntimeException(e.getMessage());
		}
	}

	protected Case getGeneralCase() {
		if (this._case == null) {
			try {
				this._case = getCaseHome().findByPrimaryKey(this.getPrimaryKey());
			}
			catch (FinderException fe) {
				fe.printStackTrace();
				throw new EJBException(fe.getMessage());
			}
		}
		return this._case;
	}

	@Override
	public Timestamp getCreated() {
		return getGeneralCase().getCreated();
	}

	@Override
	public void setCaseCode(CaseCode p0) {
		getGeneralCase().setCaseCode(p0);
	}

	@Override
	public void setParentCase(Case p0) {
		getGeneralCase().setParentCase(p0);
	}

	@Override
	public void setStatus(String p0) {
		getGeneralCase().setStatus(p0);
	}

	@Override
	public String getCode() {
		initializeCaseCodeIfNull();
		return this.getGeneralCase().getCode();
	}

	@Override
	public void setCaseStatus(CaseStatus p0) {
		this.getGeneralCase().setCaseStatus(p0);
	}

	@Override
	public CaseCode getCaseCode() {
		initializeCaseCodeIfNull();
		return this.getGeneralCase().getCaseCode();
	}

	@Override
	public void setOwner(User p0) {
		this.getGeneralCase().setOwner(p0);
	}

	@Override
	public void setCreator(User p0) {
		this.getGeneralCase().setCreator(p0);
	}

	@Override
	public Case getParentCase() {
		return this.getGeneralCase().getParentCase();
	}

	@Override
	public void setCode(String p0) {
		this.getGeneralCase().setCode(p0);
	}

	@Override
	public User getOwner() {
		return this.getGeneralCase().getOwner();
	}

	@Override
	public User getCreator() {
		return this.getGeneralCase().getCreator();
	}

	@Override
	public CaseStatus getCaseStatus() {
		return this.getGeneralCase().getCaseStatus();
	}

	@Override
	public String getStatus() {
		return this.getGeneralCase().getStatus();
	}

	@Override
	public void setCreated(Timestamp p0) {
		this.getGeneralCase().setCreated(p0);
	}

	@Override
	public Collection<Case> getChildren() {
		return this.getGeneralCase().getChildren();
	}

	@Override
	public Iterator<Case> getChildrenIterator() {
		return this.getGeneralCase().getChildrenIterator();
	}

	@Override
	public boolean getAllowsChildren() {
		return this.getGeneralCase().getAllowsChildren();
	}

	@Override
	public Case getChildAtIndex(int childIndex) {
		return this.getGeneralCase().getChildAtIndex(childIndex);
	}

	@Override
	public int getChildCount() {
		return this.getGeneralCase().getChildCount();
	}

	@Override
	public int getIndex(Case node) {
		return this.getGeneralCase().getIndex(node);
	}

	@Override
	public Case getParentNode() {
		return this.getGeneralCase().getParentNode();
	}

	@Override
	public boolean isLeaf() {
		return this.getGeneralCase().isLeaf();
	}

	@Override
	public String getNodeName() {
		return this.getGeneralCase().getNodeName();
	}

	@Override
	public String getNodeName(Locale locale) {
		return this.getGeneralCase().getNodeName(locale);
	}

	@Override
	public String getNodeName(Locale locale, IWApplicationContext iwac) {
		return this.getGeneralCase().getNodeName(locale, iwac);
	}

	@Override
	public int getNodeID() {
		return this.getGeneralCase().getNodeID();
	}

	@Override
	public int getSiblingCount() {
		return this.getGeneralCase().getSiblingCount();
	}

	/**
	 * @see com.idega.core.Case#getNodeType()
	 */
	public int getNodeType() {
		return -1;
	}

	@Override
	public Group getHandler() {
		return this.getGeneralCase().getHandler();
	}

	@Override
	public int getHandlerId() {
		return this.getGeneralCase().getHandlerId();
	}

	@Override
	public void setHandler(Group handler) {
		this.getGeneralCase().setHandler(handler);
	}

	@Override
	public void setHandler(int handlerGroupID) {
		this.getGeneralCase().setHandler(handlerGroupID);
	}

	/**
	 * Returns the cASE_STATUS_CANCELLED_KEY.
	 *
	 * @return String
	 */
	protected String getCaseStatusCancelled() {
		return this.getCaseHome().getCaseStatusCancelled();
	}

	/**
	 * Returns the cASE_STATUS_DENIED_KEY.
	 *
	 * @return String
	 */
	protected String getCaseStatusDenied() {
		return this.getCaseHome().getCaseStatusDenied();
	}

	/**
	 * Returns the cASE_STATUS_GRANTED_KEY.
	 *
	 * @return String
	 */
	protected String getCaseStatusGranted() {
		return this.getCaseHome().getCaseStatusGranted();
	}

	/**
	 * Returns the cASE_STATUS_INACTIVE_KEY.
	 *
	 * @return String
	 */
	public String getCaseStatusInactive() {
		return this.getCaseHome().getCaseStatusInactive();
	}

	/**
	 * Returns the cASE_STATUS_OPEN_KEY.
	 *
	 * @return String
	 */
	public String getCaseStatusOpen() {
		return this.getCaseHome().getCaseStatusOpen();
	}

	/**
	 * Returns the cASE_STATUS_REVIEW_KEY.
	 *
	 * @return String
	 */
	public String getCaseStatusReview() {
		return getCaseHome().getCaseStatusReview();
	}

	/**
	 * Returns the CASE_STATUS_PRELIMINARY_KEY.
	 *
	 * @return String
	 */
	public String getCaseStatusPreliminary() {
		return getCaseHome().getCaseStatusPreliminary();
	}

	/**
	 * Returns the CASE_STATUS_CONTRACT_KEY.
	 *
	 * @return String
	 */
	public String getCaseStatusContract() {
		return getCaseHome().getCaseStatusContract();
	}

	/**
	 * Returns the CASE_STATUS_CONTRACT_KEY.
	 *
	 * @return String
	 */
	public String getCaseStatusReady() {
		return getCaseHome().getCaseStatusReady();
	}

	protected String getSQLGeneralCaseTableName() {
		return CaseBMPBean.TABLE_NAME;
	}

	protected String getSQLGeneralCasePKColumnName() {
		return CaseBMPBean.PK_COLUMN;
	}

	protected String getSQLGeneralCaseUserColumnName() {
		return CaseBMPBean.COLUMN_USER;
	}

	protected String getSQLGeneralCaseHandlerColumnName() {
		return CaseBMPBean.COLUMN_HANDLER;
	}

	protected String getSQLGeneralCaseCaseCodeColumnName() {
		return CaseBMPBean.COLUMN_CASE_CODE;
	}

	protected String getSQLGeneralCaseReadColumnName() {
		return CaseBMPBean.COLUMN_READ;
	}

	protected String getSQLGeneralCaseCaseStatusColumnName() {
		return CaseBMPBean.COLUMN_CASE_STATUS;
	}

	protected String getSQLGeneralCaseCaseManagerTypeColumnName() {
		return CaseBMPBean.COLUMN_CASE_MANAGER_TYPE;
	}

	protected String getSQLGeneralCaseParentColumnName() {
		return CaseBMPBean.COLUMN_PARENT_CASE;
	}

	protected String getSQLGeneralCaseCreatedColumnName() {
		return CaseBMPBean.COLUMN_CREATED;
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode
	 */
	public Collection ejbFindAllCasesByStatus(CaseStatus caseStatus) throws FinderException {
		return ejbFindAllCasesByStatus(caseStatus.getStatus());
	}

	/**
	 * Finds all cases for the specified user and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByUser(User user) throws FinderException {
		// IDOQuery sql = idoQueryGetAllCasesByUser(user);
		SelectQuery sql = idoSelectQueryGetAllCasesByUser(user);
		return super.idoFindPKsByQuery(sql);
	}

	/**
	 * Finds all cases for the specified user and the associated caseCode and orders chronologically
	 */
	/*
	 * public IDOQuery idoQueryGetAllCasesByUser(User user) { String caseCode = this.getCaseCodeKey(); //StringBuffer sql = new StringBuffer(); IDOQuery
	 * sql = idoQuery(); sql.append("select * from ");
	 *
	 * sql.append(getSQLGeneralCaseTableName()); sql.append(" g,"); sql.append(this.getTableName()); sql.append(" a where g.");
	 * sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a."); sql.append(this.getIDColumnName()); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='"); sql.append(caseCode); sql.append("'"); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseUserColumnName()); sql.append("="); sql.append(user.getPrimaryKey().toString()); sql.append(" order by g.");
	 * sql.append(this.getSQLGeneralCaseCreatedColumnName());
	 *
	 * return sql; //return (Collection) super.idoFindPKsBySQL(sql.toString()); }
	 */
	/**
	 * Finds all cases for the specified user and the associated caseCode and orders chronologically
	 */
	public SelectQuery idoSelectQueryGetAllCasesByUser(User user) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForUser(user));
		return query;
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByStatus(String caseStatus) throws FinderException {
		return idoFindPKsByQuery(idoSelectQueryGetAllCasesByStatus(caseStatus));
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	/*
	 * public IDOQuery idoQueryGetAllCasesByStatus(String caseStatus) { String caseCode = this.getCaseCodeKey(); //StringBuffer sql = new
	 * StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select * from "); sql.append(getSQLGeneralCaseTableName()); sql.append(" g,");
	 * sql.append(this.getTableName()); sql.append(" a where g."); sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a.");
	 * sql.append(this.getIDColumnName()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='");
	 * sql.append(caseCode); sql.append("'"); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseStatusColumnName()); sql.append("='");
	 * sql.append(caseStatus); sql.append("'"); //sql.append(" order by "); //sql.append(this.getSQLGeneralCaseCreatedColumnName());
	 *
	 * return sql; //return (Collection) super.idoFindPKsBySQL(sql.toString()); }
	 */

	public Table idoTableGeneralCase() {
		if (this.genCaseTable == null) {
			this.genCaseTable = new Table(getSQLGeneralCaseTableName(), "g");
		}
		return this.genCaseTable;
	}

	public Table idoTableSubCase() {
		if (this.caseTable == null) {
			this.caseTable = new Table(getTableName(), "a");
		}
		return this.caseTable;
	}

	/**
	 * Finds all cases for all users with the associated caseCode
	 */
	public SelectQuery idoSelectQueryGetAllCases() {
		String caseCode = this.getCaseCodeKey();
		Table caseTable = idoTableGeneralCase();
		Table subCasetable = idoTableSubCase();
		SelectQuery query = new SelectQuery(caseTable);
		query.addColumn(new WildCardColumn());
		query.addCriteria(new JoinCriteria(new Column(caseTable, getSQLGeneralCasePKColumnName()), new Column(subCasetable, getIDColumnName())));
		query.addCriteria(new MatchCriteria(caseTable, getSQLGeneralCaseCaseCodeColumnName(), MatchCriteria.EQUALS, caseCode, true));
		return query;
	}

	public Criteria idoCriteriaForStatus(CaseStatus caseStatus) {
		return idoCriteriaForStatus(caseStatus.getStatus());
	}

	public Criteria idoCriteriaForStatus(String caseStatus) {
		return new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseCaseStatusColumnName(), MatchCriteria.EQUALS, caseStatus, true);
	}
	public Criteria idoCriteriaForCaseCode(String code) {
		return new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseCaseCodeColumnName(), MatchCriteria.EQUALS, code, true);
	}

	public Criteria idoCriteriaForCaseRead(boolean read) {
		if(read){
			return new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseReadColumnName(), MatchCriteria.EQUALS, CoreConstants.Y, true);
		}
		Criteria notRead = new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseReadColumnName(), MatchCriteria.NOTEQUALS, CoreConstants.Y, true);
		Criteria isNull = new MatchCriteria(idoTableGeneralCase().getColumn(getSQLGeneralCaseReadColumnName()), false);
		OR orCriteria = new OR(notRead, isNull);
		return orCriteria;

	}

	public Criteria idoCriteriaForStatus(String[] caseStatus) {
		return new InCriteria(idoTableGeneralCase(), getSQLGeneralCaseCaseStatusColumnName(), caseStatus);
	}

	public Criteria idoCriteriaForUser(User user) {
		return new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseUserColumnName(), MatchCriteria.EQUALS, user.getPrimaryKey().toString());
	}

	public Criteria idoCriteriaForGroup(Group group) {
		return new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseHandlerColumnName(), MatchCriteria.EQUALS, group.getPrimaryKey().toString());
	}

	public Criteria idoCriteriaForGroup(Collection groups) {
		String[] groupIDs = new String[groups.size()];
		int row = 0;

		Iterator iter = groups.iterator();
		while (iter.hasNext()) {
			Group element = (Group) iter.next();
			groupIDs[row++] = element.getPrimaryKey().toString();
		}
		return new InCriteria(idoTableGeneralCase(), getSQLGeneralCaseHandlerColumnName(), groupIDs);
	}

	public Criteria idoCriteriaForParentCase(Case parentCase) {
		return idoCriteriaForParentCase(parentCase.getPrimaryKey().toString());
	}

	public Criteria idoCriteriaForParentCase(String parentCasePK) {
		return new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseParentColumnName(), MatchCriteria.EQUALS, parentCasePK);
	}

	public Criteria idoCriteriaForCreatedWithinDates(IWTimestamp theFrom, IWTimestamp theTo) {
		IWTimestamp from = new IWTimestamp(theFrom);
		IWTimestamp to = new IWTimestamp(theTo);
		to.setHour(23);
		to.setMinute(59);
		to.setSecond(59);
		from.setHour(0);
		from.setMinute(0);
		from.setSecond(0);
		return new AND(new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseCreatedColumnName(), MatchCriteria.GREATEREQUAL, from.getTimestamp()), new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseCreatedColumnName(), MatchCriteria.LESSEQUAL, to.getTimestamp()));
	}

	public Order idoOrderByCreationDate(boolean ascending) {
		return new Order(new Column(idoTableGeneralCase(), getSQLGeneralCaseCreatedColumnName()), ascending);
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public SelectQuery idoSelectQueryGetAllCasesByStatus(String caseStatus) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForStatus(caseStatus));
		query.addOrder(idoOrderByCreationDate(true));
		return query;
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode ,created between given timestamps
	 */
	/*
	 * public IDOQuery idoQueryGetAllCasesByStatus(String caseStatus, IWTimestamp from, IWTimestamp to) { IDOQuery sql =
	 * idoQueryGetAllCasesByStatus(caseStatus); to.setHour(23); to.setMinute(59); to.setSecond(59); from.setHour(0); from.setMinute(0);
	 * from.setSecond(0); sql.appendAnd(); sql.appendWithinStamps("g."+getSQLGeneralCaseCreatedColumnName(),from.getTimestamp(),to.getTimestamp());
	 * //sql.append(" g."); //sql.append(getSQLGeneralCaseCreatedColumnName()); //sql.appendGreaterThanOrEqualsSign();
	 * //sql.append((Timestamp)from.getTimestamp()); //sql.append(" >= '"); //sql.append(from.toSQLString()); //sql.append("'"); //sql.appendAnd();
	 * //sql.append(" g."); //sql.append(getSQLGeneralCaseCreatedColumnName()); //sql.appendLessThanOrEqualsSign();
	 * //sql.append((Timestamp)to.getTimestamp()); //sql.append(" <= '"); //sql.append(to.toSQLString()); //sql.append("'"); return sql; }
	 */

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode ,created between given timestamps
	 */
	public SelectQuery idoSelectQueryGetAllCasesByStatus(String caseStatus, IWTimestamp from, IWTimestamp to) {
		SelectQuery query = idoSelectQueryGetAllCasesByStatus(caseStatus);
		query.addCriteria(idoCriteriaForCreatedWithinDates(from, to));
		return query;
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	/*
	 * public IDOQuery idoQueryGetAllCasesByStatusOrderedByCreation(String caseStatus, IWTimestamp from, IWTimestamp to) { IDOQuery sql =
	 * idoQueryGetAllCasesByStatus(caseStatus, from, to); sql.append(" order by "); sql.append(this.getSQLGeneralCaseCreatedColumnName()); return sql; }
	 */

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public SelectQuery idoSelectQueryGetAllCasesByStatusOrderedByCreation(String caseStatus, IWTimestamp from, IWTimestamp to) {
		SelectQuery query = idoSelectQueryGetAllCasesByStatus(caseStatus, from, to);
		query.addOrder(idoOrderByCreationDate(true));
		return query;
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	/*
	 * public IDOQuery idoQueryGetAllCasesByStatusOrderedByCreation(String caseStatus) { IDOQuery sql = idoQueryGetAllCasesByStatus(caseStatus);
	 * sql.append(" order by g."); sql.append(this.getSQLGeneralCaseCreatedColumnName()); return sql; }
	 */

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public SelectQuery idoSelectQueryGetAllCasesByStatusOrderedByCreation(String caseStatus) {
		SelectQuery query = idoSelectQueryGetAllCasesByStatus(caseStatus);
		query.addOrder(idoOrderByCreationDate(true));
		return query;
	}

	/**
	 * Finds all cases for the specified user with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByUserAndStatus(User user, String caseStatus) throws FinderException {
		return idoFindPKsByQuery(idoSelectQueryGetAllCasesByUserAndStatus(user, caseStatus));
	}

	/**
	 * Finds all cases for the specified user with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	/*
	 * public IDOQuery idoQueryGetAllCasesByUserAndStatus(User user, String caseStatus) { String caseCode = this.getCaseCodeKey(); //StringBuffer sql =
	 * new StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select * from "); sql.append(getSQLGeneralCaseTableName()); sql.append(" g,");
	 * sql.append(this.getTableName()); sql.append(" a where g."); sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a.");
	 * sql.append(this.getIDColumnName()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseUserColumnName()); sql.append("=");
	 * sql.append(user.getPrimaryKey().toString()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='");
	 * sql.append(caseCode); sql.append("'"); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseStatusColumnName()); sql.append("='");
	 * sql.append(caseStatus); sql.append("'"); sql.append(" order by g."); sql.append(this.getSQLGeneralCaseCreatedColumnName()); //return (Collection)
	 * super.idoFindPKsBySQL(sql.toString()); return sql; }
	 */

	/**
	 * Finds all cases for the specified user with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public SelectQuery idoSelectQueryGetAllCasesByUserAndStatus(User user, String caseStatus) {
		SelectQuery query = idoSelectQueryGetAllCasesByUser(user);
		query.addCriteria(idoCriteriaForStatus(caseStatus));
		return query;
	}

	/**
	 * Returns all the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	public Collection ejbFindSubCasesUnder(Case theCase) throws FinderException {
		return idoFindPKsByQuery(idoSelectQueryGetSubCasesUnder(theCase));
	}

	/**
	 * Returns all the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	/*
	 * public IDOQuery idoQueryGetSubCasesUnder(Case theCase) throws FinderException { String caseCode = this.getCaseCodeKey(); //StringBuffer sql = new
	 * StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select * from "); sql.append(getSQLGeneralCaseTableName()); sql.append(" g,");
	 * sql.append(this.getTableName()); sql.append(" a where g."); sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a.");
	 * sql.append(this.getIDColumnName()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseParentColumnName()); sql.append("=");
	 * sql.append(theCase.getPrimaryKey().toString()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='");
	 * sql.append(caseCode); sql.append("'"); sql.append(" order by g."); sql.append(this.getSQLGeneralCaseCreatedColumnName()); return sql; //return
	 * (Collection) super.idoFindPKsBySQL(sql.toString()); }
	 */

	/**
	 * Returns all the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	public SelectQuery idoSelectQueryGetSubCasesUnder(Case theCase) throws FinderException {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForParentCase(theCase));
		query.addOrder(idoOrderByCreationDate(true));
		return query;
	}

	/**
	 * Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	public int ejbHomeCountSubCasesUnder(Case theCase) {
		try {
			SelectQuery sql = idoSelectQueryGetCountSubCasesUnder(theCase);
			// IDOQuery sql = idoQueryGetCountSubCasesUnder(theCase);
			// sql.append(this.getSQLGeneralCaseCreatedColumnName());
			return super.getNumberOfRecords(sql);
		}
		catch (java.sql.SQLException sqle) {
			throw new EJBException(sqle.getMessage());
		}
	}

	/**
	 * Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	/*
	 * protected IDOQuery idoQueryGetCountSubCasesUnder(Case theCase) { String caseCode = this.getCaseCodeKey(); //StringBuffer sql = new
	 * StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select count(*) from "); sql.append(getSQLGeneralCaseTableName()); sql.append(" g,");
	 * sql.append(this.getTableName()); sql.append(" a where g."); sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a.");
	 * sql.append(this.getIDColumnName()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseParentColumnName()); sql.append("=");
	 * sql.append(theCase.getPrimaryKey().toString()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='");
	 * sql.append(caseCode); sql.append("'"); sql.append(" order by g."); sql.append(this.getSQLGeneralCaseCreatedColumnName()); return sql; //return
	 * super.getNumberOfRecords(sql.toString()); }
	 */

	/**
	 * Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	protected SelectQuery idoSelectQueryGetCountSubCasesUnder(Case theCase) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForParentCase(theCase));
		query.setAsCountQuery(true);
		return query;
	}

	/**
	 * Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	/*
	 * protected IDOQuery idoQueryGetCountCasesWithStatus(String caseStatus) { String caseCode = this.getCaseCodeKey(); //StringBuffer sql = new
	 * StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select count(*) from "); sql.append(getSQLGeneralCaseTableName()); sql.append(" g,");
	 * sql.append(this.getTableName()); sql.append(" a where g."); sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a.");
	 * sql.append(this.getIDColumnName()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='");
	 * sql.append(caseCode); sql.append("'"); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseStatusColumnName()); sql.append("='");
	 * sql.append(caseStatus); sql.append("'"); return sql; //return super.getNumberOfRecords(sql.toString()); }
	 */

	/**
	 * Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	protected SelectQuery idoSelectQueryGetCountCasesWithStatus(String caseStatus) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForStatus(caseStatus));
		query.setAsCountQuery(true);
		return query;
	}

	/**
	 * Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	public int ejbHomeCountCasesWithStatus(String caseStatus) {
		try {
			SelectQuery sql = idoSelectQueryGetCountCasesWithStatus(caseStatus);
			// IDOQuery sql = idoQueryGetCountCasesWithStatus(caseStatus);
			return super.idoGetNumberOfRecords(sql);
		}
		catch (IDOException sqle) {
			throw new EJBException(sqle.getMessage());
		}
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByStatusArray(String caseStatus[]) throws FinderException {

		// IDOQuery sql = idoQueryGetAllCasesByStatusArray(caseStatus);
		SelectQuery sql = idoSelectQueryGetAllCasesByStatusArray(caseStatus);
		return super.idoFindPKsByQuery(sql);
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByUserAndStatusArray(User user, String caseStatus[]) throws FinderException {
		// IDOQuery sql = idQueryGetAllCasesByUserAndStatusArray(user, caseStatus);
		SelectQuery sql = idoSelectQueryGetAllCasesByUserAndStatusArray(user, caseStatus);
		return super.idoFindPKsByQuery(sql);
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByUserAndStatusArray(User user, String caseStatus[], int numberOfEntries, int startingEntry) throws FinderException {
		// IDOQuery sql = idQueryGetAllCasesByUserAndStatusArray(user, caseStatus);
		SelectQuery sql = idoSelectQueryGetAllCasesByUserAndStatusArray(user, caseStatus);
		return super.idoFindPKsByQuery(sql, numberOfEntries, startingEntry);
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public int ejbHomeGetCountCasesByUserAndStatusArray(User user, String caseStatus[]) throws IDOException {
		// IDOQuery sql = idQueryCountCasesByUserAndStatusArray(user, caseStatus);
		SelectQuery sql = idoSelectQueryCountCasesByUserAndStatusArray(user, caseStatus);
		return super.idoGetNumberOfRecords(sql);
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByUserAndGroupsAndStatusArray(User user, Collection groups, String caseStatus[], int numberOfEntries, int startingEntry) throws FinderException {
		// IDOQuery sql = idQueryGetAllCasesByUserAndGroupsAndStatusArray(user,
		// groups, caseStatus);
		SelectQuery sql = idoSelectQueryGetAllCasesByUserAndGroupsAndStatusArray(user, groups, caseStatus);
		return super.idoFindPKsByQuery(sql, numberOfEntries, startingEntry);
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public int ejbHomeGetCountCasesByUserAndGroupsAndStatusArray(User user, Collection groups, String caseStatus[]) throws IDOException {
		// IDOQuery sql = idQueryCountCasesByUserAndGroupsAndStatusArray(user,
		// groups, caseStatus);
		SelectQuery sql = idoSelectQueryCountCasesByUserAndGroupsAndStatusArray(user, groups, caseStatus);
		return super.idoGetNumberOfRecords(sql);
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByGroupAndStatusArray(Group group, String caseStatus[]) throws FinderException {
		// IDOQuery sql = idQueryGetAllCasesByGroupAndStatusArray(group,
		// caseStatus);
		SelectQuery sql = idoSelectQueryGetAllCasesByGroupAndStatusArray(group, caseStatus);
		return super.idoFindPKsByQuery(sql);
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByGroupAndStatusArray(Group group, String caseStatus[], int numberOfEntries, int startingEntry) throws FinderException {
		// IDOQuery sql = idQueryGetAllCasesByGroupAndStatusArray(group,
		// caseStatus);
		SelectQuery sql = idoSelectQueryGetAllCasesByGroupAndStatusArray(group, caseStatus);
		return super.idoFindPKsByQuery(sql, numberOfEntries, startingEntry);
	}

	/*
	 * protected IDOQuery idQueryGetAllCasesByUserAndStatusArray(User user, String caseStatus[]) { try { String caseCode = this.getCaseCodeKey();
	 * //StringBuffer sql = new StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select * from "); sql.append(getSQLGeneralCaseTableName());
	 * sql.append(" g,"); sql.append(this.getTableName()); sql.append(" a where g."); sql.append(this.getSQLGeneralCasePKColumnName());
	 * sql.append("=a."); sql.append(this.getIDColumnName()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
	 * sql.append("='"); sql.append(caseCode); sql.append("'"); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseStatusColumnName());
	 * sql.append(" in ("); int length = caseStatus.length; for (int i = 0; i < length; i++) { sql.append("'"); sql.append(caseStatus[i]); if (i !=
	 * (length - 1)) sql.append("', "); else sql.append("'"); } sql.append(")"); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseUserColumnName()); sql.append("="); sql.append(user.getPrimaryKey().toString()); sql.append(" order by g.");
	 * sql.append(this.getSQLGeneralCaseCreatedColumnName() + " desc");
	 *
	 * debug("AbstractCase.idoQueryGetAllCasesByUserAndStatusArray(): sql = " + sql.toString());
	 *
	 * return sql; } catch (Exception e) { throw new IDORuntimeException(e, this); } }
	 */

	protected SelectQuery idoSelectQueryGetAllCasesByUserAndStatusArray(User user, String caseStatus[]) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForUser(user));
		query.addCriteria(idoCriteriaForStatus(caseStatus));
		query.addOrder(idoOrderByCreationDate(true));
		return query;
	}

	/*
	 * protected IDOQuery idQueryCountCasesByUserAndStatusArray(User user, String caseStatus[]) { try { String caseCode = this.getCaseCodeKey();
	 * //StringBuffer sql = new StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select count(*) from ");
	 * sql.append(getSQLGeneralCaseTableName()); sql.append(" g,"); sql.append(this.getTableName()); sql.append(" a where g.");
	 * sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a."); sql.append(this.getIDColumnName()); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='"); sql.append(caseCode); sql.append("'"); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseCaseStatusColumnName()); sql.append(" in ("); int length = caseStatus.length; for (int i = 0; i < length; i++) {
	 * sql.append("'"); sql.append(caseStatus[i]); if (i != (length - 1)) sql.append("', "); else sql.append("'"); } sql.append(")"); sql.append(" and
	 * g."); sql.append(this.getSQLGeneralCaseUserColumnName()); sql.append("="); sql.append(user.getPrimaryKey().toString()); sql.append(" order by
	 * g."); sql.append(this.getSQLGeneralCaseCreatedColumnName());
	 *
	 * debug("AbstractCase.idoQueryGetAllCasesByUserAndStatusArray(): sql = " + sql.toString());
	 *
	 * return sql; } catch (Exception e) { throw new IDORuntimeException(e, this); } }
	 */

	protected SelectQuery idoSelectQueryCountCasesByUserAndStatusArray(User user, String caseStatus[]) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForUser(user));
		query.addCriteria(idoCriteriaForStatus(caseStatus));
		query.setAsCountQuery(true);
		return query;
	}

	/*
	 * protected IDOQuery idQueryCountCasesByUserAndGroupsAndStatusArray(User user, Collection groups, String caseStatus[]) { String[] groupIDs = new
	 * String[groups.size()]; int row = 0;
	 *
	 * Iterator iter = groups.iterator(); while (iter.hasNext()) { Group element = (Group) iter.next(); groupIDs[row++] =
	 * element.getPrimaryKey().toString(); }
	 *
	 * try { String caseCode = this.getCaseCodeKey(); //StringBuffer sql = new StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select count(*)
	 * from "); sql.append(getSQLGeneralCaseTableName()); sql.append(" g,"); sql.append(this.getTableName()); sql.append(" a where g.");
	 * sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a."); sql.append(this.getIDColumnName()); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='"); sql.append(caseCode); sql.append("'"); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseCaseStatusColumnName()); sql.append(" in ("); int length = caseStatus.length; for (int i = 0; i < length; i++) {
	 * sql.append("'"); sql.append(caseStatus[i]); if (i != (length - 1)) sql.append("', "); else sql.append("'"); } sql.append(")"); sql.append(" and
	 * (g."); sql.append(this.getSQLGeneralCaseUserColumnName()); sql.append("="); sql.append(user.getPrimaryKey().toString()); sql.appendOr();
	 * sql.append(this.getSQLGeneralCaseHandlerColumnName()); sql.appendInArray(groupIDs); sql.append(") order by g.");
	 * sql.append(this.getSQLGeneralCaseCreatedColumnName());
	 *
	 * debug("AbstractCase.idoQueryGetAllCasesByUserAndStatusArray(): sql = " + sql.toString());
	 *
	 * return sql; } catch (Exception e) { throw new IDORuntimeException(e, this); } }
	 */

	protected SelectQuery idoSelectQueryCountCasesByUserAndGroupsAndStatusArray(User user, Collection groups, String caseStatus[]) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForUser(user));
		query.addCriteria(idoCriteriaForStatus(caseStatus));
		query.addCriteria(idoCriteriaForGroup(groups));
		query.setAsCountQuery(true);
		return query;
	}

	/*
	 * protected IDOQuery idQueryGetAllCasesByUserAndGroupsAndStatusArray(User user, Collection groups, String caseStatus[]) { String[] groupIDs = new
	 * String[groups.size()]; int row = 0;
	 *
	 * Iterator iter = groups.iterator(); while (iter.hasNext()) { Group element = (Group) iter.next(); groupIDs[row++] =
	 * element.getPrimaryKey().toString(); }
	 *
	 * try { String caseCode = this.getCaseCodeKey(); //StringBuffer sql = new StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select * from ");
	 * sql.append(getSQLGeneralCaseTableName()); sql.append(" g,"); sql.append(this.getTableName()); sql.append(" a where g.");
	 * sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a."); sql.append(this.getIDColumnName()); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='"); sql.append(caseCode); sql.append("'"); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseCaseStatusColumnName()); sql.append(" in ("); int length = caseStatus.length; for (int i = 0; i < length; i++) {
	 * sql.append("'"); sql.append(caseStatus[i]); if (i != (length - 1)) sql.append("', "); else sql.append("'"); } sql.append(")"); sql.append(" and
	 * (g."); sql.append(this.getSQLGeneralCaseUserColumnName()); sql.append("="); sql.append(user.getPrimaryKey().toString()); sql.appendOr();
	 * sql.append(this.getSQLGeneralCaseHandlerColumnName()); sql.appendInArray(groupIDs); sql.append(") order by g.");
	 * sql.append(this.getSQLGeneralCaseCreatedColumnName() + " desc");
	 *
	 * debug("AbstractCase.idoQueryGetAllCasesByUserAndStatusArray(): sql = " + sql.toString());
	 *
	 * return sql; } catch (Exception e) { throw new IDORuntimeException(e, this); } }
	 */

	protected SelectQuery idoSelectQueryGetAllCasesByUserAndGroupsAndStatusArray(User user, Collection groups, String caseStatus[]) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForUser(user));
		query.addCriteria(idoCriteriaForStatus(caseStatus));
		query.addCriteria(idoCriteriaForGroup(groups));
		query.addOrder(idoOrderByCreationDate(true));
		return query;
	}

	/*
	 * protected IDOQuery idQueryGetAllCasesByGroupAndStatusArray(Group group, String caseStatus[]) { try { String caseCode = this.getCaseCodeKey();
	 * //StringBuffer sql = new StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select * from "); sql.append(getSQLGeneralCaseTableName());
	 * sql.append(" g,"); sql.append(this.getTableName()); sql.append(" a where g."); sql.append(this.getSQLGeneralCasePKColumnName());
	 * sql.append("=a."); sql.append(this.getIDColumnName()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
	 * sql.append("='"); sql.append(caseCode); sql.append("'"); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseStatusColumnName());
	 * sql.append(" in ("); int length = caseStatus.length; for (int i = 0; i < length; i++) { sql.append("'"); sql.append(caseStatus[i]); if (i !=
	 * (length - 1)) sql.append("', "); else sql.append("'"); } sql.append(")"); sql.append(" and g.");
	 * sql.append(this.getSQLGeneralCaseHandlerColumnName()); sql.append("="); sql.append(group.getPrimaryKey().toString()); sql.append(" order by g.");
	 * sql.append(this.getSQLGeneralCaseCreatedColumnName());
	 *
	 * debug("AbstractCase.idoQueryGetAllCasesByUserAndStatusArray(): sql = " + sql.toString());
	 *
	 * return sql; } catch (Exception e) { throw new IDORuntimeException(e, this); } }
	 */

	protected SelectQuery idoSelectQueryGetAllCasesByGroupAndStatusArray(Group group, String caseStatus[]) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForStatus(caseStatus));
		query.addCriteria(idoCriteriaForGroup(group));
		query.addOrder(idoOrderByCreationDate(true));
		return query;
	}

	/*
	 * protected IDOQuery idoQueryGetAllCasesByStatusArray(String caseStatus[]) { try { String caseCode = this.getCaseCodeKey(); //StringBuffer sql =
	 * new StringBuffer(); IDOQuery sql = idoQuery(); sql.append("select * from "); sql.append(getSQLGeneralCaseTableName()); sql.append(" g,");
	 * sql.append(this.getTableName()); sql.append(" a where g."); sql.append(this.getSQLGeneralCasePKColumnName()); sql.append("=a.");
	 * sql.append(this.getIDColumnName()); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseCodeColumnName()); sql.append("='");
	 * sql.append(caseCode); sql.append("'"); sql.append(" and g."); sql.append(this.getSQLGeneralCaseCaseStatusColumnName()); sql.append(" in ("); int
	 * length = caseStatus.length; for (int i = 0; i < length; i++) { sql.append("'"); sql.append(caseStatus[i]); if (i != (length - 1)) sql.append("',
	 * "); else sql.append("'"); } sql.append(")"); sql.append(" order by g."); sql.append(this.getSQLGeneralCaseCreatedColumnName());
	 *
	 * debug("AbstractCase.idoQueryGetAllCasesByStatusArray(): sql = " + sql.toString());
	 *
	 * return sql; } catch (Exception e) { throw new IDORuntimeException(e, this); } }
	 */
	protected SelectQuery idoSelectQueryGetAllCasesByStatusArray(String caseStatus[]) {
		SelectQuery query = idoSelectQueryGetAllCases();
		query.addCriteria(idoCriteriaForStatus(caseStatus));
		query.addOrder(idoOrderByCreationDate(true));
		return query;
	}

	@Override
	public String getExternalId() {
		return getGeneralCase().getExternalId();
	}

	@Override
	public void setExternalId(String externalId) {
		getGeneralCase().setExternalId(externalId);
	}

	@Override
	public String getCaseNumber() {
		return getGeneralCase().getCaseNumber();
	}

	@Override
	public void setCaseNumber(String caseNumber) {
		getGeneralCase().setCaseNumber(caseNumber);
	}

	@Override
	public void setExternalHandler(User user) {
		getGeneralCase().setExternalHandler(user);
	}

	@Override
	public User getExternalHandler() {
		return getGeneralCase().getExternalHandler();
	}

	@Override
	public String getSubject() {
		return getGeneralCase().getSubject();
	}

	@Override
	public void setSubject(String subject) {
		getGeneralCase().setSubject(subject);
	}

	@Override
	public String getBody() {
		return getGeneralCase().getBody();
	}

	@Override
	public void setBody(String body) {
		getGeneralCase().setBody(body);
	}

	@Override
	public String getUrl() {
		return getGeneralCase().getUrl();
	}

	/**
	 * Finds all cases with set metadata attributes with key metadataKey and value metadataValue
	 */
	public Collection ejbFindAllCasesByMetaData(String metadataKey, String metadataValue) throws FinderException {
		// IDOQuery sql = idQueryGetAllCasesByGroupAndStatusArray(group,
		// caseStatus);
		SelectQuery sql = idoSelectQueryGetAllCases();
		Table metadataTable = new Table(MetaData.class, "meta");
		Table caseTable = new Table(Case.class, "g");
		try {
			sql.addManyToManyJoin(caseTable, metadataTable);
			sql.addCriteria(new MatchCriteria(metadataTable, MetaDataBMPBean.COLUMN_META_KEY, MatchCriteria.EQUALS, metadataKey));
			sql.addCriteria(new MatchCriteria(metadataTable, MetaDataBMPBean.COLUMN_META_VALUE, MatchCriteria.EQUALS, metadataValue));
		}
		catch (IDORelationshipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.idoFindPKsByQuery(sql);
	}

	/**
	 * Finds all cases with set metadata attributes with key metadataKey and value, and not the value 'notValue' This is because there have been cases
	 * of duplicate keys for the same record with different set values. metadataValue
	 */
	public Collection ejbFindAllCasesByMetaDataNotDuplicateWithValue(String metadataKey, String metadataValue, String notValue) throws FinderException {
		// IDOQuery sql = idQueryGetAllCasesByGroupAndStatusArray(group,
		// caseStatus);
		SelectQuery sql = idoSelectQueryGetAllCases();
		Table metadataTable = new Table(MetaData.class, "meta");
		Table caseTable = new Table(Case.class, "g");
		try {
			sql.addManyToManyJoin(caseTable, metadataTable, "mp");
			sql.addCriteria(new MatchCriteria(metadataTable, MetaDataBMPBean.COLUMN_META_KEY, MatchCriteria.EQUALS, metadataKey));
			sql.addCriteria(new MatchCriteria(metadataTable, MetaDataBMPBean.COLUMN_META_VALUE, MatchCriteria.EQUALS, metadataValue));

			Table caseTable2 = new Table(Case.class, "cs");
			Table metadataTable2 = new Table(MetaData.class, "meta2");
			SelectQuery subSelect = new SelectQuery(caseTable2);

			String caseIdColumnName = CaseBMPBean.TABLE_NAME + "_ID";

			// String metaIdColumnName = MetaDataBMPBean.TABLE_NAME+"_ID";
			subSelect.addColumn(caseTable2, caseIdColumnName);

			subSelect.addManyToManyJoin(caseTable2, metadataTable2, "mp2");
			subSelect.addCriteria(new MatchCriteria(metadataTable2, MetaDataBMPBean.COLUMN_META_KEY, MatchCriteria.EQUALS, metadataKey));
			subSelect.addCriteria(new MatchCriteria(metadataTable2, MetaDataBMPBean.COLUMN_META_VALUE, MatchCriteria.EQUALS, notValue));

			sql.addCriteria(new InCriteria(caseTable, caseIdColumnName, subSelect, true));

		}
		catch (IDORelationshipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.idoFindPKsByQuery(sql);
	}

	/**
	 * Sets all the metadata key/values for this instance with the given map where the is keys and values of String type.
	 */
	@Override
	public void setMetaDataAttributes(Map<String, String> map) {
		getGeneralCase().setMetaDataAttributes(map);
	}

	/**
	 * Gets all the metadata key/values for this instance with the given map where the keys and values of String type.
	 */
	@Override
	public Map<String, String> getMetaDataAttributes() {
		return getGeneralCase().getMetaDataAttributes();
	}

	/**
	 * Sets all the metadata types this instance with the given map which is keys and values of String type.
	 */
	@Override
	public Map<String, String> getMetaDataTypes() {
		return getGeneralCase().getMetaDataTypes();
	}

	/**
	 * Set the metadata set for the key metaDataKey to value value
	 */
	@Override
	public void setMetaData(String metaDataKey, String value) {
		getGeneralCase().setMetaData(metaDataKey, value);
	}

	/**
	 * Set the metadata set for the key metaDataKey to value value
	 */
	@Override
	public void setMetaData(String metaDataKey, String value, String type) {
		getGeneralCase().setMetaData(metaDataKey, value, type);
	}

	/**
	 * Gets the metadata set for the key metaDataKey
	 */
	@Override
	public String getMetaData(String metaDataKey) {
		return getGeneralCase().getMetaData(metaDataKey);
	}

	/**
	 * Rename a metadata key
	 */
	@Override
	public void renameMetaData(String oldKeyName, String newKeyName) {
		getGeneralCase().renameMetaData(oldKeyName, newKeyName);
	}

	/**
	 * Rename a metadata key, and change the value
	 */
	@Override
	public void renameMetaData(String oldKeyName, String newKeyName, String value) {
		getGeneralCase().renameMetaData(oldKeyName, newKeyName, value);
	}

	/**
	 * Gets the metadata for the key metaDataKey
	 */
	@Override
	public boolean removeMetaData(String metaDataKey) {
		return getGeneralCase().removeMetaData(metaDataKey);
	}

	/**
	 * @return The unique id string of the entity if it has it, otherwise null
	 */
	@Override
	public String getUniqueId() {
		return getGeneralCase().getUniqueId();
	}

	/**
	 * Sets the Unique ID column. This method should generally never be called manually
	 *
	 * @param uniqueId
	 */
	@Override
	public void setUniqueId(String uniqueId) {
		getGeneralCase().setUniqueId(uniqueId);
	}

	@Override
	public String getId() {
		return getPrimaryKey().toString();
	}

	// set case code (of general case) to case code key (of this instance)
	// if the case code (of general case) is not set
	// this happens when the case was just created
	private void initializeCaseCodeIfNull() {
		if (getGeneralCase().getCode() == null) {
			setCode(getCaseCodeKey());
		}
	}

	@Override
	public String getCaseManagerType() {
		return getGeneralCase().getCaseManagerType();
	}

	@Override
	public void setCaseManagerType(String type) {
		getGeneralCase().setCaseManagerType(type);
	}

	@Override
	public String getCaseIdentifier() {
		return getGeneralCase().getCaseIdentifier();
	}

	@Override
	public void setCaseIdentifier(String caseIdentifier) {
		getGeneralCase().setCaseIdentifier(caseIdentifier);
	}

	protected String getSQLGeneralCaseExternalIdColumnName() {
		return CaseBMPBean.COLUMN_EXTERNAL_ID;
	}

	public Criteria idoCriteriaForExternalId(String externalId) {
		return new MatchCriteria(idoTableGeneralCase(), getSQLGeneralCaseExternalIdColumnName(), MatchCriteria.EQUALS, externalId);
	}

	public Criteria idoJoinCriteraWithBaseCaseTable(){
		Table subCasetable = idoTableSubCase();
		return new JoinCriteria(new Column(idoTableGeneralCase(), getSQLGeneralCasePKColumnName()), new Column(subCasetable, getIDColumnName()));

	}

	public Object ejbFindByExternalId(String externalId) throws FinderException {
		SelectQuery query = new SelectQuery(idoTableSubCase());
		query.addColumn(new Column(idoTableSubCase(),getIDColumnName()));
		query.addCriteria(idoJoinCriteraWithBaseCaseTable());
		query.addCriteria(idoCriteriaForExternalId(externalId));
		return idoFindOnePKByQuery(query);
	}

	@Override
	public Boolean isRead() {
		return getGeneralCase().isRead();
	}

	@Override
	public void setRead(Boolean read) {
		getGeneralCase().setRead(read);
	}

	public Collection<Integer> ejbFindCases(User user, String status,String caseCode, Boolean read)  throws FinderException{
		try {
			SelectQuery query = idoQueryGetAllCasesByUser(user, status, caseCode, read);
			Order order = new Order(idoTableGeneralCase().getColumn(getSQLGeneralCaseCreatedColumnName()), false);
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

	protected SelectQuery idoQueryGetAllCasesByUser(User user, String status, String caseCode,Boolean read) {
		try {
			SelectQuery query = idoSelectQueryGetAllCasesByUser(user);

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

	@Override
	public boolean addSubscriber(User subscriber) throws IDOAddRelationshipException {
		return addUserRelation(subscriber, SUBSCRIBERS, getSubscribers());
	}

	@Override
	public boolean addSubscribers(Collection<User> subscribers) {
		return addUserRelations(subscribers, SUBSCRIBERS);
	}

	@Override
	public boolean addVote(User voter) throws IDOAddRelationshipException {
		return addUserRelation(voter, VOTERS, getVoters());
	}

	private boolean addUserRelation(User user, String relation, Collection<User> currentRelations) throws IDOAddRelationshipException {
		if (user == null || StringUtil.isEmpty(relation)) {
			getLogger().warning("Either user (" + user + ") and/or relation (" + relation + ") are not provided");
			return false;
		}

		if (!ListUtil.isEmpty(currentRelations) && currentRelations.contains(user)) {
			getLogger().warning("User " + user + " already exists in relation " + relation + " with case " + getId() + " from " +
					getTableName());
			return false;
		}

		this.idoAddTo(user, getTableName() + relation);
		return true;
	}

	/**
	 *
	 * <p>Adds subscribers to relation table.</p>
	 * @param relations is {@link User}s, who should be realted with this
	 * entity instance, not <code>null</code>;
	 * @param ralationTableNamePostfix is postfix of table name related to this one, for
	 * example: "_SUBSCRIBERS", where full name would be: "PROC_CASE_SUBSCRIBERS";
	 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
	 */
	protected boolean addUserRelations(Collection<User> relations, String ralationTableNamePostfix) {
		if (ListUtil.isEmpty(relations) || StringUtil.isEmpty(ralationTableNamePostfix)) {
			return false;
		}

		Collection<User> currentSubscribers = getSubscribers();
		if (!ListUtil.isEmpty(currentSubscribers)) {
			relations.removeAll(currentSubscribers);
		}

		return idoAddTo(relations, getTableName() + ralationTableNamePostfix);
	}

	@Override
	public boolean removeSubscriber(User subscriber) throws IDORemoveRelationshipException {
		return removeUserRelation(subscriber, SUBSCRIBERS);
	}

	@Override
	public boolean removeSubscribers() {
		return removeUserRelations(SUBSCRIBERS);
	}

	@Override
	public boolean removeVote(User voter) throws IDORemoveRelationshipException {
		return removeUserRelation(voter, VOTERS);
	}

	/**
	 *
	 * <p>Removes a records from related SQL table.</p>
	 * @param relations is postfix of table name related to this one, for
	 * example: "_SUBSCRIBERS", where full name would be: "PROC_CASE_SUBSCRIBERS";
	 * @return <code>true</code> on success, <code>false</code> otherwise;
	 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
	 */
	protected boolean removeUserRelations(String relations) {
		return super.idoRemoveFrom(getTableName() + relations);
	}

	private boolean removeUserRelation(User user, String relation) throws IDORemoveRelationshipException {
		if (user == null) {
			getLogger().warning("User is not provided");
			return false;
		}

		super.idoRemoveFrom(user, getTableName() + relation);
		return true;
	}

	@Override
	public Collection<User> getSubscribers() {
		return getUserRelations(SUBSCRIBERS);
	}

	@Override
	public Collection<User> getVoters() {
		return getUserRelations(VOTERS);
	}

	private Collection<User> getUserRelations(String relation) {
		try {
			String userId = UserBMPBean.SQL_TABLE_NAME + "_ID";
			String caseId = getTableName() + "_ID";
			String query = "select u." + userId + " from " + UserBMPBean.SQL_TABLE_NAME + " u, " + getTableName() + relation + " r, " +
					getTableName() + " c where c." + caseId + " = " + getId() + " and u." + userId + " = r." + userId + " and c." + caseId +
					" = r." + caseId;
			return super.idoGetRelatedEntitiesBySQL(User.class, query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isClosed() {
		CaseStatus status = getCaseStatus();
		if (status == null)
			return false;

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