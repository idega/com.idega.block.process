package com.idega.block.process.data;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import javax.ejb.FinderException;
import com.idega.data.GenericEntity;
import com.idega.data.IDOFinderException;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      idega software
 * @author <a href="tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class CaseLogBMPBean extends GenericEntity implements CaseLog
{
	public static final String TABLE_NAME = "PROC_CASE_LOG";
	private static final String COLUMN_CASE_LOG_ID = "CASE_LOG_ID";
	private static final String COLUMN_CASE_ID = "CASE_ID";
	private static final String COLUMN_CASE_STATUS_BEFORE = "CASE_STATUS_BEFORE";
	private static final String COLUMN_CASE_STATUS_AFTER = "CASE_STATUS_AFTER";
	private static final String COLUMN_PERFORMER = "PERFORMER_USER_ID";
	private static final String COLUMN_TIMESTAMP = "PROC_TIMESTAMP";
	private static final String LOC_KEY_PREFIX = "case.code";
	public void initializeAttributes() {
		addAttribute(COLUMN_CASE_LOG_ID);
		this.addManyToOneRelationship(COLUMN_CASE_ID, "The case", Case.class);
		this.addManyToOneRelationship(COLUMN_CASE_STATUS_BEFORE, "The CaseStatus before change", CaseStatus.class);
		this.addManyToOneRelationship(COLUMN_CASE_STATUS_AFTER, "The CaseStatus after change", CaseStatus.class);
		this.addManyToOneRelationship(COLUMN_PERFORMER, "The User who makes the change", User.class);
		this.addAttribute(COLUMN_TIMESTAMP, "Timestamp of the change", Timestamp.class);
	}
	public void setDefaultValues() {
		setTimeStamp(IWTimestamp.getTimestampRightNow());
	}
	public String getIDColumnName(){
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

	/**
	 * Finds all CaseLogs recorded for the specified aCase
	 **/
	public Collection ejbFindAllCaseLogsByCase(Case aCase) throws FinderException, RemoteException {
		return super.idoFindAllIDsByColumnBySQL(COLUMN_CASE_ID, aCase.getPrimaryKey().toString());
	}
	
	/**
	 * Finds the last CaseLog recorded for the specified aCase
	 **/
	public Integer ejbFindLastCaseLogForCase(Case aCase) throws FinderException {
		Integer theReturn = null;
		//try{
			theReturn = (Integer)super.idoFindOnePKBySQL("select * from "+TABLE_NAME+" where "+COLUMN_CASE_ID+"="+aCase.getPrimaryKey().toString()+" order by "+COLUMN_TIMESTAMP);
		//}
		//catch(RemoteException e){
		//	throw new IDOFinderException(e);	
		//}
		if(theReturn==null){
			throw new IDOFinderException("No records found for case");	
		}
		return theReturn;
	}
}