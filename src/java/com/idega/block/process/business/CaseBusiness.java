/*
 * $Id: CaseBusiness.java,v 1.25 2004/12/02 15:49:23 laddi Exp $
 * Created on 2.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Locale;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseStatus;
import com.idega.business.IBOService;
import com.idega.user.data.Group;
import com.idega.user.data.User;


/**
 * Last modified: $Date: 2004/12/02 15:49:23 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.25 $
 */
public interface CaseBusiness extends IBOService {

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#createCase
	 */
	public Case createCase(int userID, String caseCode) throws CreateException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#createCase
	 */
	public Case createCase(User user, CaseCode code) throws CreateException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#createSubCase
	 */
	public Case createSubCase(Case oldCase) throws CreateException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#createSubCase
	 */
	public Case createSubCase(Case oldCase, CaseCode newCaseCode) throws CreateException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user, CaseCode code) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user, String caseCode) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user, CaseCode code, CaseStatus status) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user, String caseCode, String caseStatus) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForGroup
	 */
	public Collection getAllCasesForGroup(Group group) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUserExceptCodes
	 */
	public Collection getAllCasesForUserExceptCodes(User user, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getNumberOfCasesForUserExceptCodes
	 */
	public int getNumberOfCasesForUserExceptCodes(User user, CaseCode[] codes) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForGroupExceptCodes
	 */
	public Collection getAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUserAndGroupsExceptCodes
	 */
	public Collection getAllCasesForUserAndGroupsExceptCodes(User user, Collection groups, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getNumberOfCasesForUserAndGroupsExceptCodes
	 */
	public int getNumberOfCasesForUserAndGroupsExceptCodes(User user, Collection groups, CaseCode[] codes) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user, CaseCode code) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user, String caseCode) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user, String caseCode, String caseStatus) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user, CaseCode code, CaseStatus status) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByDates
	 */
	public Collection getCaseLogsByDates(Timestamp fromDate, Timestamp toDate) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByCaseCodeAndDates
	 */
	public Collection getCaseLogsByCaseCodeAndDates(CaseCode caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByCaseCodeAndDates
	 */
	public Collection getCaseLogsByCaseCodeAndDates(String caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByDatesAndStatusChange
	 */
	public Collection getCaseLogsByDatesAndStatusChange(Timestamp fromDate, Timestamp toDate, CaseStatus statusBefore, CaseStatus statusAfter) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByDatesAndStatusChange
	 */
	public Collection getCaseLogsByDatesAndStatusChange(Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByCaseAndDatesAndStatusChange
	 */
	public Collection getCaseLogsByCaseAndDatesAndStatusChange(CaseCode caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByCaseAndDatesAndStatusChange
	 */
	public Collection getCaseLogsByCaseAndDatesAndStatusChange(String caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCase
	 */
	public Case getCase(int caseID) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseCode
	 */
	public CaseCode getCaseCode(String caseCode) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatus
	 */
	public CaseStatus getCaseStatus(String StatusCode) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusOpen
	 */
	public CaseStatus getCaseStatusOpen() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusGranted
	 */
	public CaseStatus getCaseStatusGranted() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusDeleted
	 */
	public CaseStatus getCaseStatusDeleted() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusDenied
	 */
	public CaseStatus getCaseStatusDenied() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusReview
	 */
	public CaseStatus getCaseStatusReview() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusMoved
	 */
	public CaseStatus getCaseStatusMoved() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusPlaced
	 */
	public CaseStatus getCaseStatusPlaced() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusPending
	 */
	public CaseStatus getCaseStatusPending() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusCancelled
	 */
	public CaseStatus getCaseStatusCancelled() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusInactive
	 */
	public CaseStatus getCaseStatusInactive() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusPreliminary
	 */
	public CaseStatus getCaseStatusPreliminary() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusContract
	 */
	public CaseStatus getCaseStatusContract() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusReady
	 */
	public CaseStatus getCaseStatusReady() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusRedeem
	 */
	public CaseStatus getCaseStatusRedeem() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusError
	 */
	public CaseStatus getCaseStatusError() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(int theCaseID, String newCaseStatus, User performer) throws FinderException, java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer, Group handler) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getLocalizedCaseDescription
	 */
	public String getLocalizedCaseDescription(Case theCase, Locale locale) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getLocalizedCaseDescription
	 */
	public String getLocalizedCaseDescription(CaseCode theCaseCode, Locale locale) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getLocalizedCaseStatusDescription
	 */
	public String getLocalizedCaseStatusDescription(CaseStatus status, Locale locale) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getLastModifier
	 */
	public User getLastModifier(Case aCase) throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusOpenString
	 */
	public String getCaseStatusOpenString() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusCancelledString
	 */
	public String getCaseStatusCancelledString() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusInactiveString
	 */
	public String getCaseStatusInactiveString() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusReadyString
	 */
	public String getCaseStatusReadyString() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusDeletedString
	 */
	public String getCaseStatusDeletedString() throws java.rmi.RemoteException;

}
