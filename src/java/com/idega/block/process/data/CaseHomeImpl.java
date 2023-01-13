/*
 * $Id: CaseHomeImpl.java,v 1.29 2009/06/23 09:33:27 valdas Exp $
 * Created on Apr 11, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.data.IDOEntity;
import com.idega.data.IDOException;
import com.idega.data.IDOFactory;
import com.idega.data.IDOLookup;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
import com.idega.util.StringUtil;


/**
 *  Last modified: $Date: 2009/06/23 09:33:27 $ by $Author: valdas $
 *
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.29 $
 */
public class CaseHomeImpl extends IDOFactory implements CaseHome {

	private static final long serialVersionUID = 1130458737716958104L;

	@Override
	protected Class<Case> getEntityInterfaceClass() {
		return Case.class;
	}

	@Override
	public Case create() throws javax.ejb.CreateException {
		return (Case) super.createIDO();
	}

	@Override
	public Case findByPrimaryKey(Object pk) throws javax.ejb.FinderException {
		return super.findByPrimaryKeyIDO(pk);
	}

	@Override
	public Collection<Case> findAllCasesByUser(User user) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findAllCasesByGroup(Group group) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesByGroup(group);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findAllCasesByUser(User user, CaseCode caseCode) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user, caseCode);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findAllCasesByUser(User user, String caseCode) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user, caseCode);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findAllCasesByUser(User user, CaseCode caseCode, CaseStatus caseStatus) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user, caseCode, caseStatus);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findAllCasesByUser(User user, String caseCode, String caseStatus) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user, caseCode, caseStatus);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findSubCasesUnder(Case theCase) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindSubCasesUnder(theCase);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public int countSubCasesUnder(Case theCase) {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeCountSubCasesUnder(theCase);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusCancelled() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusCancelled();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusDeleted() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusDeleted();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusDenied() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusDenied();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusGranted() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusGranted();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusInactive() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusInactive();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusOpen() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusOpen();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusReview() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusReview();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusWaiting() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusWaiting();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusCreated() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusCreated();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusFinished() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusFinished();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusPreliminary() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusPreliminary();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusPending() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusPending();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusContract() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusContract();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusReady() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusReady();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusRedeem() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusRedeem();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusError() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusError();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusMoved() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusMoved();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusPlaced() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusPlaced();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusInProcess() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusInProcess();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusClosed() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusClosed();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusReport() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusReport();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusArchived() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusArchived();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public String getCaseStatusLocked() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusLocked();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public Collection<Case> findAllCasesForUserExceptCodes(User user, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesForUserExceptCodes(user, codes, startingCase, numberOfCases);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findAllCasesForUserByStatuses(User user, String[] statuses, int startingCase, int numberOfCases) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesForUserByStatuses(user, statuses, startingCase, numberOfCases);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public int getCountOfAllCasesForUserByStatuses(User user, String[] statuses) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetCountOfAllCasesForUserByStatuses(user, statuses);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public Collection<Case> findAllCasesForGroupsByStatuses(Collection groups, String[] statuses, int startingCase, int numberOfCases) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesForGroupsByStatuses(groups, statuses, startingCase, numberOfCases);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public int getCountOfAllCasesForGroupsByStatuses(Collection groups, String[] statuses) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetCountOfAllCasesForGroupsByStatuses(groups, statuses);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public Collection<Case> findAllCasesForGroupsAndUserExceptCodes(User user, Collection groups, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesForGroupsAndUserExceptCodes(user, groups, codes, startingCase, numberOfCases);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesForGroupExceptCodes(group, codes);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public int getNumberOfCasesForUserExceptCodes(User user, CaseCode[] codes) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetNumberOfCasesForUserExceptCodes(user, codes);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public int getNumberOfCasesByGroupsOrUserExceptCodes(User user, Collection groups, CaseCode[] codes) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetNumberOfCasesByGroupsOrUserExceptCodes(user, groups, codes);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public Integer getNumberOfCasesByCaseCode(String code) throws FinderException, IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetNumberOfCasesByCaseCode(code);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public Case findCaseByExternalId(String externalId) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CaseBMPBean) entity).ejbFindCaseByExternalId(externalId);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	@Override
	public Case findCaseByUniqueId(String uniqueId) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CaseBMPBean) entity).ejbFindCaseByUniqueId(uniqueId);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	@Override
	public Collection<Case> findCasesByUniqueIds(Collection<String> uuids) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindCasesByUniqueIds(uuids);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Integer> findIDsByCriteria(String caseNumber, String description, Collection<String> owners, String[] statuses, IWTimestamp dateFrom,
			IWTimestamp dateTo, User owner, Collection<Group> groups, boolean simpleCases) throws FinderException {
		return findIDsByCriteria(caseNumber, description, owners, statuses, dateFrom, dateTo, owner, groups, simpleCases, null, null);
	}

	@Override
	public Collection<Integer> findIDsByCriteria(String caseNumber, String description, Collection<String> owners, String[] statuses, IWTimestamp dateFrom,
			IWTimestamp dateTo, User owner, Collection<Group> groups, boolean simpleCases, Boolean withHandler, List<Integer> exceptOwnersIds) throws FinderException {
		return findIDsByCriteria(caseNumber, description, owners, statuses, dateFrom, dateTo, owner, groups, simpleCases, withHandler, exceptOwnersIds, null);
	}

	@Override
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
	) throws FinderException {
		return findIDsByCriteria(caseNumber, description, owners, statuses, dateFrom, dateTo, owner, groups, simpleCases, withHandler, exceptOwnersIds, caseCode, null);
	}

	@Override
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
	) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Integer> ids = ((CaseBMPBean) entity).ejbFindByCriteria(caseNumber, description, owners, statuses, dateFrom, dateTo, owner, groups, simpleCases, withHandler, exceptOwnersIds, caseCode, caseManagerTypes, null);
		this.idoCheckInPooledEntity(entity);
		return ids;
	}

	@Override
	public Collection<Case> findByCriteria(String caseNumber, String description, Collection<String> owners, String[] statuses, IWTimestamp dateFrom,
			IWTimestamp dateTo, User owner, Collection<Group> groups, boolean simpleCases) throws FinderException {

		Collection<Integer> ids = findIDsByCriteria(caseNumber, description, owners, statuses, dateFrom, dateTo, owner, groups, simpleCases);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findAllByIds(Collection<Integer> ids) throws FinderException {
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public void createDefaultCaseStatuses() {
		try {
			CaseBMPBean caseBMPBean = ((CaseBMPBean) this.idoCheckOutPooledEntity());

			String[] statusKeys = {
					caseBMPBean.ejbHomeGetCaseStatusOpen() ,
					caseBMPBean.ejbHomeGetCaseStatusInactive(),
					caseBMPBean.ejbHomeGetCaseStatusGranted(),
					caseBMPBean.ejbHomeGetCaseStatusDenied(),
					caseBMPBean.ejbHomeGetCaseStatusPreliminary(),
					caseBMPBean.ejbHomeGetCaseStatusReady(),
					caseBMPBean.ejbHomeGetCaseStatusMoved(),
					caseBMPBean.ejbHomeGetCaseStatusInProcess(),
					caseBMPBean.ejbHomeGetCaseStatusPlaced(),
					caseBMPBean.ejbHomeGetCaseStatusWaiting(),
					caseBMPBean.ejbHomeGetCaseStatusPending(),
					caseBMPBean.ejbHomeGetCaseStatusGrouped(),
					caseBMPBean.ejbHomeGetCaseStatusCreated(),
					caseBMPBean.ejbHomeGetCaseStatusFinished(),
					caseBMPBean.ejbHomeGetCaseStatusClosed(),
					caseBMPBean.ejbHomeGetCaseStatusOffered()
			};

			CaseStatusHome cshome = (CaseStatusHome) IDOLookup.getHome(CaseStatus.class);
			CaseStatus caseStatus;

			for (int i = 0; i < statusKeys.length; i++) {
				try {
					caseStatus = cshome.findByPrimaryKey(statusKeys[i]);
				}
				catch (FinderException fe) {
					try {
						caseStatus = cshome.create();
						caseStatus.setStatus(statusKeys[i]);
						caseStatus.store();
					}
					catch (Exception e) {
						throw new EJBException("Error creating CaseStatus " + statusKeys[i] + " is not installed or does not exist. Message: " + e.getMessage());
					}

				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<Case> findAllByCaseCode(CaseCode code) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllByCaseCode(code);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public int getCountedCasesByCasesIdsAndByCaseCode(Collection<Integer> casesIds, String caseCode) throws FinderException, IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbGetCountedCasesByCasesIdsAndByCaseCode(casesIds, caseCode);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	@Override
	public Collection<Case> getCasesByIds(Collection<Integer> ids) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> getCases(User user, String status, String caseCode, Boolean read) throws FinderException{
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindCases(user, status,caseCode, read);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findCasesForSubscriber(User subscriber) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindAllCasesBySubscriber(subscriber);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Case> findCasesByCaseIdentifier(String caseIdentifier) throws FinderException {
		if (StringUtil.isEmpty(caseIdentifier)) {
			return null;
		}

		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<?> ids = ((CaseBMPBean) entity).ejbFindByCaseIdentifier(caseIdentifier);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Integer> getCasesIds(Collection<Integer> ids, Integer from, Integer amount) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Integer> result = ((CaseBMPBean) entity).ejbFindCasesIds(ids, from, amount);
		this.idoCheckInPooledEntity(entity);
		return result;
	}

	@Override
	public Collection<Integer> findIdsByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			String caseStatus
	) throws FinderException {
		return findIdsByCriteria(caseNumber, caseSubject, caseCode, StringUtil.isEmpty(caseStatus) ? Collections.emptyList() : Arrays.asList(caseStatus), null, null);
	}

	public Collection<Integer> findIdsByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			List<String> caseStatuses,
			Integer from,
			Integer amount
	) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Integer> ids = ((CaseBMPBean) entity).ejbFindByCriteria(caseNumber, caseSubject, caseCode, caseStatuses, from, amount);
		this.idoCheckInPooledEntity(entity);
		return ids;
	}

	@Override
	public Collection<Case> findByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			String caseStatus
	) throws FinderException {
		return findByCriteria(caseNumber, caseSubject, caseCode, StringUtil.isEmpty(caseStatus) ? Collections.emptyList() : Arrays.asList(caseStatus), null, null);
	}

	@Override
	public Collection<Case> findByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			List<String> caseStatuses,
			Integer from,
			Integer amount
	) throws FinderException {
		Collection<Integer> ids = findIdsByCriteria(caseNumber, caseSubject, caseCode, caseStatuses, from, amount);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Long getCountedCasesByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			List<String> caseStatuses
	) {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Long count = ((CaseBMPBean) entity).getCountedCasesByCriteria(caseNumber, caseSubject, caseCode, caseStatuses);
		this.idoCheckInPooledEntity(entity);
		return count;
	}

	@Override
	public Collection<Integer> findByCaseIdsAndStatuses(Collection<Integer> casesIds, String[] statuses) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Integer> ids = ((CaseBMPBean) entity).ejbFindByCaseIdsAndStatuses(casesIds, statuses);
		this.idoCheckInPooledEntity(entity);
		return ids;
	}

}