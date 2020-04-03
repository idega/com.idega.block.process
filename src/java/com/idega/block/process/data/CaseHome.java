/*
 * $Id: CaseHome.java,v 1.27 2009/06/23 09:33:27 valdas Exp $
 * Created on Apr 11, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.data;

import java.util.Collection;
import java.util.List;

import javax.ejb.FinderException;

import com.idega.data.IDOException;
import com.idega.data.IDOHome;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;


/**
 *  Last modified: $Date: 2009/06/23 09:33:27 $ by $Author: valdas $
 *
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.27 $
 */
public interface CaseHome extends IDOHome {

	public Case create() throws javax.ejb.CreateException;

	public Case findByPrimaryKey(Object pk) throws javax.ejb.FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection<Case> findAllCasesByUser(User user) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByGroup
	 */
	public Collection<Case> findAllCasesByGroup(Group group) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection<Case> findAllCasesByUser(User user, CaseCode caseCode) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection<Case> findAllCasesByUser(User user, String caseCode) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection<Case> findAllCasesByUser(User user, CaseCode caseCode, CaseStatus caseStatus) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection<Case> findAllCasesByUser(User user, String caseCode, String caseStatus) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindSubCasesUnder
	 */
	public Collection<Case> findSubCasesUnder(Case theCase) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeCountSubCasesUnder
	 */
	public int countSubCasesUnder(Case theCase);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusCancelled
	 */
	public String getCaseStatusCancelled();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusDeleted
	 */
	public String getCaseStatusDeleted();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusDenied
	 */
	public String getCaseStatusDenied();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusGranted
	 */
	public String getCaseStatusGranted();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusInactive
	 */
	public String getCaseStatusInactive();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusOpen
	 */
	public String getCaseStatusOpen();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusReview
	 */
	public String getCaseStatusReview();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusWaiting
	 */
	public String getCaseStatusWaiting();

	public String getCaseStatusCreated();

	public String getCaseStatusFinished();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusPreliminary
	 */
	public String getCaseStatusPreliminary();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusPending
	 */
	public String getCaseStatusPending();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusContract
	 */
	public String getCaseStatusContract();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusReady
	 */
	public String getCaseStatusReady();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusRedeem
	 */
	public String getCaseStatusRedeem();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusError
	 */
	public String getCaseStatusError();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusMoved
	 */
	public String getCaseStatusMoved();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusPlaced
	 */
	public String getCaseStatusPlaced();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusInProcess
	 */
	public String getCaseStatusInProcess();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusClosed
	 */
	public String getCaseStatusClosed();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusArchived
	 */
	public String getCaseStatusArchived();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCaseStatusLocked
	 */
	public String getCaseStatusLocked();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForUserExceptCodes
	 */
	public Collection<Case> findAllCasesForUserExceptCodes(User user, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForUserByStatuses
	 */
	public Collection<Case> findAllCasesForUserByStatuses(User user, String[] statuses, int startingCase, int numberOfCases) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCountOfAllCasesForUserByStatuses
	 */
	public int getCountOfAllCasesForUserByStatuses(User user, String[] statuses) throws IDOException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForGroupsByStatuses
	 */
	public Collection<Case> findAllCasesForGroupsByStatuses(Collection groups, String[] statuses, int startingCase, int numberOfCases) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCountOfAllCasesForGroupsByStatuses
	 */
	public int getCountOfAllCasesForGroupsByStatuses(Collection groups, String[] statuses) throws IDOException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForGroupsAndUserExceptCodes
	 */
	public Collection<Case> findAllCasesForGroupsAndUserExceptCodes(User user, Collection groups, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForGroupExceptCodes
	 */
	public Collection<Case> findAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetNumberOfCasesForUserExceptCodes
	 */
	public int getNumberOfCasesForUserExceptCodes(User user, CaseCode[] codes) throws IDOException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetNumberOfCasesByGroupsOrUserExceptCodes
	 */
	public int getNumberOfCasesByGroupsOrUserExceptCodes(User user, Collection groups, CaseCode[] codes) throws IDOException;

	public Integer getNumberOfCasesByCaseCode(String code) throws FinderException, IDOException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindCaseByExternalId
	 */
	public Case findCaseByExternalId(String externalId) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindCaseByUniqueId
	 */
	public Case findCaseByUniqueId(String uniqueId) throws FinderException;

	public Collection<Case> findByCriteria(
			String caseNumber,
			String description,
			Collection<String> owners,
			String[] statuses,
			IWTimestamp dateFrom,
			IWTimestamp dateTo,
			User owner,
			Collection<Group> groups,
			boolean simpleCases
	) throws FinderException;

	public Collection<Integer> findIDsByCriteria(
			String caseNumber,
			String description,
			Collection<String> owners,
			String[] statuses,
			IWTimestamp dateFrom,
			IWTimestamp dateTo,
			User owner,
			Collection<Group> groups,
			boolean simpleCases
	) throws FinderException;

	public Collection<Integer> findIDsByCriteria(
			String caseNumber,
			String description,
			Collection<String> owners,
			String[] statuses,
			IWTimestamp dateFrom,
			IWTimestamp dateTo,
			User owner,
			Collection<Group> groups,
			boolean simpleCases,
			Boolean withHandler,
			List<Integer> exceptOwnersIds
	) throws FinderException;

	public Collection<Integer> findIDsByCriteria(
			String caseNumber,
			String description,
			Collection<String> owners,
			String[] statuses,
			IWTimestamp dateFrom,
			IWTimestamp dateTo,
			User owner,
			Collection<Group> groups,
			boolean simpleCases,
			Boolean withHandler,
			List<Integer> exceptOwnersIds,
			String caseCode
	) throws FinderException;

	public Collection<Integer> findIDsByCriteria(
			String caseNumber,
			String description,
			Collection<String> owners,
			String[] statuses,
			IWTimestamp dateFrom,
			IWTimestamp dateTo,
			User owner,
			Collection<Group> groups,
			boolean simpleCases,
			Boolean withHandler,
			List<Integer> exceptOwnersIds,
			String caseCode,
			List<String> caseManagerTypes
	) throws FinderException;

	public abstract Collection<Case> findAllByIds(Collection<Integer> ids) throws FinderException;

	public void createDefaultCaseStatuses();

	public Collection<Case> findAllByCaseCode(CaseCode code) throws FinderException;

	public Collection<Case> getCasesByIds(Collection<Integer> ids) throws FinderException;

	public int getCountedCasesByCasesIdsAndByCaseCode(Collection<Integer> casesIds, String caseCode) throws FinderException, IDOException;

	/**
	 *
	 * @param user	owner of the case
	 * @param status if null than omitted
	 * @param caseCode if null than omitted
	 * @param read is case read by any user, if null than omitted
	 * @return
	 * @throws FinderException
	 */
	public Collection<Case> getCases(User user, String status, String caseCode, Boolean read) throws FinderException;

	public Collection<Case> findCasesForSubscriber(User subscriber) throws FinderException;

	/**
	 *
	 * @param caseIdentifier - {@link Case#getCaseIdentifier()}, not
	 * <code>null</code>;
	 * @return {@link Case} when found, <code>null</code> otherwise;
	 * @throws FinderException usually means that nothing found;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Collection<Case> findCasesByCaseIdentifier(String caseIdentifier) throws FinderException;

	public String getCaseStatusReport();

	public Collection<Integer> getCasesIds(Collection<Integer> ids, Integer from, Integer amount) throws FinderException;

	public Collection<Integer> findIdsByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			String caseStatus
	) throws FinderException;

	public Collection<Case> findByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			String caseStatus
	) throws FinderException;

}