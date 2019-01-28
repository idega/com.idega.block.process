/*
 * $Id: CaseLogHome.java 1.1 5.12.2004 laddi Exp $
 * Created on 5.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

import javax.ejb.FinderException;

import com.idega.data.IDOException;
import com.idega.data.IDOHome;
import com.idega.user.data.User;


/**
 * Last modified: $Date: 2004/06/28 09:09:50 $ by $Author: laddi $
 *
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1 $
 */
public interface CaseLogHome extends IDOHome {

	public CaseLog create() throws javax.ejb.CreateException;

	public CaseLog findByPrimaryKey(Object pk) throws javax.ejb.FinderException;

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#ejbFindAllCaseLogsByCase
	 */
	public Collection findAllCaseLogsByCase(Case aCase) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#ejbFindAllCaseLogsByCaseOrderedByDate
	 */
	public Collection<CaseLog> findAllCaseLogsByCaseOrderedByDate(Case aCase) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#ejbFindLastCaseLogForCase
	 */
	public CaseLog findLastCaseLogForCase(Case aCase) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#ejbFindAllCaseLogsByDate
	 */
	public Collection findAllCaseLogsByDate(Timestamp fromDate, Timestamp toDate) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#ejbFindAllCaseLogsByCaseAndDate
	 */
	public Collection findAllCaseLogsByCaseAndDate(String caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException;

	/**
	 *
	 * @param theCase to find logs for;
	 * @param fromDate - starting date of submission;
	 * @param toDate - ending date of submission;
	 * @param performer - {@link User}, who performed operation;
	 * @return {@link CaseLog}s by criteria or {@link Collections#emptyList()}
	 * on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Collection<CaseLog> findAllCaseLogs(Case theCase, Timestamp fromDate, Timestamp toDate, User performer) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#ejbFindAllCaseLogsByDateAndStatusChange
	 */
	public Collection findAllCaseLogsByDateAndStatusChange(Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#ejbFindAllCaseLogsByCaseAndDateAndStatusChange
	 */
	public Collection findAllCaseLogsByCaseAndDateAndStatusChange(String caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#ejbHomeGetCountByStatusChange
	 */
	public int getCountByStatusChange(Case theCase, String statusBefore, String statusAfter) throws IDOException;

	public Collection<CaseLog> findAllCaseLogs(Case theCase, Timestamp fromDate, Timestamp toDate, User performer, Boolean useGeneralAppend) throws FinderException;

}
