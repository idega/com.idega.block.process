package com.idega.block.process.data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.IDOException;
import com.idega.data.IDOFinderException;
import com.idega.data.IDOQuery;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.data.query.WildCardColumn;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.IWTimestamp;

/**
 * Title: idegaWeb Description: Copyright: Copyright (c) 2002 Company: idega
 * software
 *
 * @author <a href="tryggvi@idega.is">Tryggvi Larusson </a>
 * @version 1.0
 */
public class CaseLogBMPBean extends GenericEntity implements CaseLog {

	public static final String TABLE_NAME = "PROC_CASE_LOG";

	private static final String COLUMN_CASE_LOG_ID = "CASE_LOG_ID";

	private static final String COLUMN_CASE_ID = "CASE_ID";

	private static final String COLUMN_CASE_STATUS_BEFORE = "CASE_STATUS_BEFORE";

	private static final String COLUMN_CASE_STATUS_AFTER = "CASE_STATUS_AFTER";

	private static final String COLUMN_PERFORMER = "PERFORMER_USER_ID";

	private static final String COLUMN_ASSIGNED_TO = "ASSIGNED_TO_ID";

	private static final String COLUMN_TIMESTAMP = "PROC_TIMESTAMP";

	private static final String COLUMN_COMMENT = "PROC_COMMENT";

	private static final String COLUMN_TYPE = "TYPE";

	@Override
	public void initializeAttributes() {
		addAttribute(COLUMN_CASE_LOG_ID);
		this.addManyToOneRelationship(COLUMN_CASE_ID, "The case", Case.class);
		this.addManyToOneRelationship(COLUMN_CASE_STATUS_BEFORE, "The CaseStatus before change", CaseStatus.class);
		this.addManyToOneRelationship(COLUMN_CASE_STATUS_AFTER, "The CaseStatus after change", CaseStatus.class);
		this.addManyToOneRelationship(COLUMN_PERFORMER, "The User who makes the change", User.class);
		this.addManyToOneRelationship(COLUMN_ASSIGNED_TO, "The group or user which is assigned to case", Group.class);
		this.addAttribute(COLUMN_TIMESTAMP, "Timestamp of the change", Timestamp.class);
		this.addAttribute(COLUMN_COMMENT, "Comment for change", String.class, 4000);
		this.addAttribute(COLUMN_TYPE, "Type", String.class);
		this.addAttribute(COLUMN_STATUS, "Status", String.class);
	}

	@Override
	public void setDefaultValues() {
		setTimeStamp(IWTimestamp.getTimestampRightNow());
	}

	@Override
	public String getIDColumnName() {
		return COLUMN_CASE_LOG_ID;
	}

	@Override
	public String getEntityName() {
		return TABLE_NAME;
	}

	@Override
	public Case getCase() {
		return (Case) (this.getColumnValue(COLUMN_CASE_ID));
	}

	@Override
	public int getCaseId() {
		return (this.getIntColumnValue(COLUMN_CASE_ID));
	}

	@Override
	public void setCase(Case aCase) {
		setColumn(COLUMN_CASE_ID, aCase);
	}

	@Override
	public void setCase(int aCaseID) {
		setColumn(COLUMN_CASE_ID, aCaseID);
	}

	@Override
	public CaseStatus getCaseStatusBefore() {
		return (CaseStatus) (this.getColumnValue(COLUMN_CASE_STATUS_BEFORE));
	}

	@Override
	public String getStatusBefore() {
		return (this.getStringColumnValue(COLUMN_CASE_STATUS_BEFORE));
	}

	@Override
	public void setCaseStatusBefore(CaseStatus aCaseStatus) {
		setColumn(COLUMN_CASE_STATUS_BEFORE, aCaseStatus);
	}

	@Override
	public void setCaseStatusBefore(String caseStatus) {
		setColumn(COLUMN_CASE_STATUS_BEFORE, caseStatus);
	}

	@Override
	public CaseStatus getCaseStatusAfter() {
		return (CaseStatus) (this.getColumnValue(COLUMN_CASE_STATUS_AFTER));
	}

	@Override
	public String getStatusAfter() {
		return (this.getStringColumnValue(COLUMN_CASE_STATUS_AFTER));
	}

	@Override
	public void setCaseStatusAfter(CaseStatus aCaseStatus) {
		setColumn(COLUMN_CASE_STATUS_AFTER, aCaseStatus);
	}

	@Override
	public void setCaseStatusAfter(String caseStatus) {
		setColumn(COLUMN_CASE_STATUS_AFTER, caseStatus);
	}

	@Override
	public User getPerformer() {
		return (User) (this.getColumnValue(COLUMN_PERFORMER));
	}

	@Override
	public int getPerformerId() {
		return (this.getIntColumnValue(COLUMN_PERFORMER));
	}

	@Override
	public void setPerformer(User performer) {
		setColumn(COLUMN_PERFORMER, performer);
	}

	@Override
	public void setPerformer(int performerUserId) {
		setColumn(COLUMN_PERFORMER, performerUserId);
	}

	@Override
	public Timestamp getTimeStamp() {
		return (Timestamp) getColumnValue(COLUMN_TIMESTAMP);
	}

	@Override
	public void setTimeStamp(Timestamp stamp) {
		setColumn(COLUMN_TIMESTAMP, stamp);
	}

	@Override
	public String getComment() {
		return getStringColumnValue(COLUMN_COMMENT);
	}

	@Override
	public void setComment(String comment) {
		setColumn(COLUMN_COMMENT, comment);
	}

	/**
	 * Finds all CaseLogs recorded for the specified aCase
	 */
	public Collection ejbFindAllCaseLogsByCase(Case aCase) throws FinderException {
		return super.idoFindAllIDsByColumnBySQL(COLUMN_CASE_ID, aCase.getPrimaryKey().toString());
	}

	/**
	 * Finds all CaseLogs recorded for the specified aCase
	 */
	public Collection ejbFindAllCaseLogsByCaseOrderedByDate(Case aCase) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new WildCardColumn());
		query.addCriteria(new MatchCriteria(table, COLUMN_CASE_ID, MatchCriteria.EQUALS, aCase));
		query.addOrder(table, COLUMN_TIMESTAMP, false);
		return super.idoFindPKsByQuery(query);
	}

	/**
	 * Finds the last CaseLog recorded for the specified aCase
	 */
	public Integer ejbFindLastCaseLogForCase(Case aCase) throws FinderException {
		Integer theReturn = (Integer) super.idoFindOnePKBySQL("select * from " + TABLE_NAME + " where " + COLUMN_CASE_ID + "=" + aCase.getPrimaryKey().toString() + " order by " + COLUMN_TIMESTAMP + " desc");
		if (theReturn == null) {
			throw new IDOFinderException("No records found for case");
		}
		return theReturn;
	}

	public Collection ejbFindAllCaseLogsByDate(Timestamp fromDate, Timestamp toDate) throws FinderException {
		IDOQuery query = idoQuery();
		query.appendSelectAllFrom(this).appendWhere();
		query.append(COLUMN_TIMESTAMP).appendLessThanOrEqualsSign().append(toDate);
		query.appendAnd().append(COLUMN_TIMESTAMP).appendGreaterThanOrEqualsSign().append(fromDate);
		return super.idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllCaseLogsByCaseAndDate(String caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException {
		IDOQuery query = idoQuery();
		query.appendSelect().append("pl.*").appendFrom().append(getEntityName()).append(" pl, proc_case p ").appendWhere();
		query.append(COLUMN_TIMESTAMP).appendLessThanOrEqualsSign().append(toDate);
		query.appendAnd().append(COLUMN_TIMESTAMP).appendGreaterThanOrEqualsSign().append(fromDate);
		query.appendAndEquals("pl." + COLUMN_CASE_ID, "p.proc_case_id");
		query.appendAndEqualsQuoted("p.case_code", caseCode);
		return super.idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllCaseLogsByDateAndStatusChange(Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		IDOQuery query = idoQuery();
		query.appendSelectAllFrom(this).appendWhere();
		query.append(COLUMN_TIMESTAMP).appendLessThanOrEqualsSign().append(toDate);
		query.appendAnd().append(COLUMN_TIMESTAMP).appendGreaterThanOrEqualsSign().append(fromDate);
		query.appendAndEqualsQuoted(COLUMN_CASE_STATUS_BEFORE, statusBefore);
		query.appendAndEqualsQuoted(COLUMN_CASE_STATUS_AFTER, statusAfter);
		return super.idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllCaseLogsByCaseAndDateAndStatusChange(String caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		IDOQuery query = idoQuery();
		query.appendSelect().append("pl.*").appendFrom().append(getEntityName()).append(" pl, proc_case p ").appendWhere();
		query.append(COLUMN_TIMESTAMP).appendLessThanOrEqualsSign().append(toDate);
		query.appendAnd().append(COLUMN_TIMESTAMP).appendGreaterThanOrEqualsSign().append(fromDate);
		query.appendAndEqualsQuoted(COLUMN_CASE_STATUS_BEFORE, statusBefore);
		query.appendAndEqualsQuoted(COLUMN_CASE_STATUS_AFTER, statusAfter);
		query.appendAndEquals("pl." + COLUMN_CASE_ID, "p.proc_case_id");
		query.appendAndEqualsQuoted("p.case_code", caseCode);
		return super.idoFindPKsByQuery(query);
	}

	public int ejbHomeGetCountByStatusChange(Case theCase, String statusBefore, String statusAfter) throws IDOException {
		IDOQuery query = idoQuery();
		query.appendSelectCountFrom(this);
		query.appendWhereEqualsQuoted(COLUMN_CASE_STATUS_BEFORE, statusBefore);
		query.appendAndEqualsQuoted(COLUMN_CASE_STATUS_AFTER, statusAfter);
		query.appendAndEquals(COLUMN_CASE_ID, theCase);
		return super.idoGetNumberOfRecords(query);
	}


	/**
	 *
	 * @param theCase to find logs for;
	 * @param fromDate - starting date of submission;
	 * @param toDate - ending date of submission;
	 * @param performer - {@link User}, who performed operation;
	 * @return ids of {@link CaseLog}s or {@link Collections#emptyList()}
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 * @throws FinderException
	 */
	public Collection<Long> ejbFindAllCaseLogsByCaseAndDate(Case theCase,
			Timestamp fromDate, Timestamp toDate, User performer) throws FinderException {
		return ejbFindAllCaseLogsByCaseAndDate(theCase, fromDate, toDate, performer, null);
	}


	/**
	 *
	 * @param theCase to find logs for;
	 * @param fromDate - starting date of submission;
	 * @param toDate - ending date of submission;
	 * @param performer - {@link User}, who performed operation;
	 * @param useGeneralAppend Should use general append or not (with general append, if app property adjust_to_local_time_zone is enabled, timestamp will be changed and select will not be correct)
	 * @return ids of {@link CaseLog}s or {@link Collections#emptyList()}
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 * @throws FinderException
	 */
	public Collection<Long> ejbFindAllCaseLogsByCaseAndDate(Case theCase,
			Timestamp fromDate, Timestamp toDate, User performer, Boolean useGeneralAppend) throws FinderException {
		IDOQuery query = idoQuery();
		query.appendSelectAllFrom(this);
		query.appendWhere();
		query.append(COLUMN_CASE_LOG_ID).appendIsNotNull();

		if (theCase != null) {
			query.appendAndEquals(COLUMN_CASE_ID, theCase);
		}

		if (toDate != null) {
			query.appendAnd();
			if (useGeneralAppend != null && !useGeneralAppend.booleanValue()) {
				String toDateString = toDate.toString();
				toDateString = toDateString.substring(0, toDateString.lastIndexOf(CoreConstants.DOT));
				toDateString = CoreConstants.QOUTE_SINGLE_MARK + toDateString + CoreConstants.QOUTE_SINGLE_MARK;
				query.append(COLUMN_TIMESTAMP).appendLessThanOrEqualsSign().append(toDateString);
			} else {
				query.append(COLUMN_TIMESTAMP).appendLessThanOrEqualsSign().append(toDate);
			}
		}

		if (fromDate != null) {
			query.appendAnd();
			if (useGeneralAppend != null && !useGeneralAppend.booleanValue()) {
				String fromDateString = fromDate.toString();
				fromDateString = fromDateString.substring(0, fromDateString.lastIndexOf(CoreConstants.DOT));
				fromDateString = CoreConstants.QOUTE_SINGLE_MARK + fromDateString + CoreConstants.QOUTE_SINGLE_MARK;
				query.append(COLUMN_TIMESTAMP).appendGreaterThanOrEqualsSign().append(fromDateString);
			} else {
				query.append(COLUMN_TIMESTAMP).appendGreaterThanOrEqualsSign().append(fromDate);
			}
		}

		if (performer != null) {
			query.appendAndEquals(COLUMN_PERFORMER, performer);
		}

		query.appendOrderBy(COLUMN_TIMESTAMP);
		return super.idoFindPKsByQuery(query);
	}

	@Override
	public void setAssigned(Group group) {
		setColumn(COLUMN_ASSIGNED_TO, group);
	}

	@Override
	public Group getAssigned() {
		return (Group) getColumnValue(COLUMN_ASSIGNED_TO);
	}

	@Override
	public Integer getAssignedId() {
		int id = (this.getIntColumnValue(COLUMN_ASSIGNED_TO));
		return id <= 0 ? null : id;
	}

	@Override
	public String getType() {
		return getStringColumnValue(COLUMN_TYPE);
	}

	@Override
	public void setType(String type) {
		setColumn(COLUMN_TYPE, type);
	}

	@Override
	public String getStatus() {
		return getStringColumnValue(COLUMN_STATUS);
	}

	@Override
	public void setStatus(String status) {
		setColumn(COLUMN_STATUS, status);
	}

}