package com.idega.block.process.data;

import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.IDOException;
import com.idega.data.IDOFinderException;
import com.idega.data.IDOQuery;
import com.idega.user.data.User;
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

	private static final String COLUMN_TIMESTAMP = "PROC_TIMESTAMP";

	private static final String COLUMN_COMMENT = "PROC_COMMENT";

	private static final String LOC_KEY_PREFIX = "case.code";

	public void initializeAttributes() {
		addAttribute(COLUMN_CASE_LOG_ID);
		this.addManyToOneRelationship(COLUMN_CASE_ID, "The case", Case.class);
		this.addManyToOneRelationship(COLUMN_CASE_STATUS_BEFORE, "The CaseStatus before change", CaseStatus.class);
		this.addManyToOneRelationship(COLUMN_CASE_STATUS_AFTER, "The CaseStatus after change", CaseStatus.class);
		this.addManyToOneRelationship(COLUMN_PERFORMER, "The User who makes the change", User.class);
		this.addAttribute(COLUMN_TIMESTAMP, "Timestamp of the change", Timestamp.class);
		this.addAttribute(COLUMN_COMMENT, "Comment for change", String.class);
	}

	public void setDefaultValues() {
		setTimeStamp(IWTimestamp.getTimestampRightNow());
	}

	public String getIDColumnName() {
		return COLUMN_CASE_LOG_ID;
	}

	public String getEntityName() {
		return TABLE_NAME;
	}

	public Case getCase() {
		return (Case) (this.getColumnValue(COLUMN_CASE_ID));
	}

	public int getCaseId() {
		return (this.getIntColumnValue(COLUMN_CASE_ID));
	}

	public void setCase(Case aCase) {
		setColumn(COLUMN_CASE_ID, aCase);
	}

	public void setCase(int aCaseID) {
		setColumn(COLUMN_CASE_ID, aCaseID);
	}

	public CaseStatus getCaseStatusBefore() {
		return (CaseStatus) (this.getColumnValue(COLUMN_CASE_STATUS_BEFORE));
	}

	public String getStatusBefore() {
		return (this.getStringColumnValue(COLUMN_CASE_STATUS_BEFORE));
	}

	public void setCaseStatusBefore(CaseStatus aCaseStatus) {
		setColumn(COLUMN_CASE_STATUS_BEFORE, aCaseStatus);
	}

	public void setCaseStatusBefore(String caseStatus) {
		setColumn(COLUMN_CASE_STATUS_BEFORE, caseStatus);
	}

	public CaseStatus getCaseStatusAfter() {
		return (CaseStatus) (this.getColumnValue(COLUMN_CASE_STATUS_AFTER));
	}

	public String getStatusAfter() {
		return (this.getStringColumnValue(COLUMN_CASE_STATUS_AFTER));
	}

	public void setCaseStatusAfter(CaseStatus aCaseStatus) {
		setColumn(COLUMN_CASE_STATUS_AFTER, aCaseStatus);
	}

	public void setCaseStatusAfter(String caseStatus) {
		setColumn(COLUMN_CASE_STATUS_AFTER, caseStatus);
	}

	public User getPerformer() {
		return (User) (this.getColumnValue(COLUMN_PERFORMER));
	}

	public int getPerformerId() {
		return (this.getIntColumnValue(COLUMN_PERFORMER));
	}

	public void setPerformer(User performer) {
		setColumn(COLUMN_PERFORMER, performer);
	}

	public void setPerformer(int performerUserId) {
		setColumn(COLUMN_PERFORMER, performerUserId);
	}

	public Timestamp getTimeStamp() {
		return (Timestamp) getColumnValue(COLUMN_TIMESTAMP);
	}

	public void setTimeStamp(Timestamp stamp) {
		setColumn(COLUMN_TIMESTAMP, stamp);
	}

	public String getComment() {
		return getStringColumnValue(COLUMN_COMMENT);
	}

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
	 * Finds the last CaseLog recorded for the specified aCase
	 */
	public Integer ejbFindLastCaseLogForCase(Case aCase) throws FinderException {
		Integer theReturn = (Integer) super.idoFindOnePKBySQL("select * from " + TABLE_NAME + " where " + COLUMN_CASE_ID + "=" + aCase.getPrimaryKey().toString() + " order by " + COLUMN_TIMESTAMP);
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
}