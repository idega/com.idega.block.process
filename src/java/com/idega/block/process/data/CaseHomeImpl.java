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

import java.util.Collection;

import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.data.IDOEntity;
import com.idega.data.IDOException;
import com.idega.data.IDOFactory;
import com.idega.data.IDOLookup;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;


/**
 * <p>
 * TODO laddi Describe Type CaseHomeImpl
 * </p>
 *  Last modified: $Date: 2009/06/23 09:33:27 $ by $Author: valdas $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.29 $
 */
public class CaseHomeImpl extends IDOFactory implements CaseHome {

	@Override
	protected Class getEntityInterfaceClass() {
		return Case.class;
	}

	public Case create() throws javax.ejb.CreateException {
		return (Case) super.createIDO();
	}

	public Case findByPrimaryKey(Object pk) throws javax.ejb.FinderException {
		return (Case) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllCasesByUser(User user) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllCasesByGroup(Group group) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesByGroup(group);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllCasesByUser(User user, CaseCode caseCode) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user, caseCode);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllCasesByUser(User user, String caseCode) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user, caseCode);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllCasesByUser(User user, CaseCode caseCode, CaseStatus caseStatus) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user, caseCode, caseStatus);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllCasesByUser(User user, String caseCode, String caseStatus) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesByUser(user, caseCode, caseStatus);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findSubCasesUnder(Case theCase) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindSubCasesUnder(theCase);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int countSubCasesUnder(Case theCase) {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeCountSubCasesUnder(theCase);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusCancelled() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusCancelled();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusDeleted() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusDeleted();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusDenied() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusDenied();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusGranted() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusGranted();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusInactive() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusInactive();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusOpen() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusOpen();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusReview() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusReview();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusWaiting() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusWaiting();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
	
	public String getCaseStatusCreated() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusCreated();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
	
	public String getCaseStatusFinished() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusFinished();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusPreliminary() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusPreliminary();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusPending() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusPending();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusContract() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusContract();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusReady() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusReady();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusRedeem() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusRedeem();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusError() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusError();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusMoved() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusMoved();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusPlaced() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusPlaced();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusInProcess() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusInProcess();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusClosed() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusClosed();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusArchived() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusArchived();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public String getCaseStatusLocked() {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		String theReturn = ((CaseBMPBean) entity).ejbHomeGetCaseStatusLocked();
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public Collection findAllCasesForUserExceptCodes(User user, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesForUserExceptCodes(user, codes, startingCase, numberOfCases);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllCasesForUserByStatuses(User user, String[] statuses, int startingCase, int numberOfCases) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesForUserByStatuses(user, statuses, startingCase, numberOfCases);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int getCountOfAllCasesForUserByStatuses(User user, String[] statuses) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetCountOfAllCasesForUserByStatuses(user, statuses);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public Collection findAllCasesForGroupsByStatuses(Collection groups, String[] statuses, int startingCase, int numberOfCases) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesForGroupsByStatuses(groups, statuses, startingCase, numberOfCases);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int getCountOfAllCasesForGroupsByStatuses(Collection groups, String[] statuses) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetCountOfAllCasesForGroupsByStatuses(groups, statuses);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public Collection findAllCasesForGroupsAndUserExceptCodes(User user, Collection groups, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesForGroupsAndUserExceptCodes(user, groups, codes, startingCase, numberOfCases);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesForGroupExceptCodes(group, codes);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int getNumberOfCasesForUserExceptCodes(User user, CaseCode[] codes) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetNumberOfCasesForUserExceptCodes(user, codes);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getNumberOfCasesByGroupsOrUserExceptCodes(User user, Collection groups, CaseCode[] codes) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetNumberOfCasesByGroupsOrUserExceptCodes(user, groups, codes);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public Case findCaseByExternalId(String externalId) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CaseBMPBean) entity).ejbFindCaseByExternalId(externalId);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public Case findCaseByUniqueId(String uniqueId) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CaseBMPBean) entity).ejbFindCaseByUniqueId(uniqueId);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Integer> findIDsByCriteria(String caseNumber, String description, Collection<String> owners, String[] statuses, IWTimestamp dateFrom,
			IWTimestamp dateTo, User owner, Collection<Group> groups, boolean simpleCases) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Integer> ids = ((CaseBMPBean) entity).ejbFindByCriteria(caseNumber, description, owners, statuses, dateFrom, dateTo, owner, groups, simpleCases);
		this.idoCheckInPooledEntity(entity);
		return ids;
	}

	@SuppressWarnings("unchecked")
	public Collection<Case> findByCriteria(String caseNumber, String description, Collection<String> owners, String[] statuses, IWTimestamp dateFrom,
			IWTimestamp dateTo, User owner, Collection<Group> groups, boolean simpleCases) throws FinderException {
		
		Collection<Integer> ids = findIDsByCriteria(caseNumber, description, owners, statuses, dateFrom, dateTo, owner, groups, simpleCases);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Case> findAllByIds(Collection<Integer> ids) throws FinderException {
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
	
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
					caseBMPBean.ejbHomeGetCaseStatusClosed()
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

	public Collection<Case> findAllByCaseCode(CaseCode code) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CaseBMPBean) entity).ejbFindAllByCaseCode(code);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection<Case> getCasesByIds(Collection<Integer> ids) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
	
	public Collection<Case> getCases(User user, String status, String caseCode, Boolean read) throws FinderException{
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CaseBMPBean) entity).ejbFindCases(user, status,caseCode, read);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}
