/*
 * $Id: CaseHomeImpl.java,v 1.19 2005/01/10 20:38:01 laddi Exp $
 * Created on 8.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.IDOException;
import com.idega.data.IDOFactory;
import com.idega.user.data.Group;
import com.idega.user.data.User;


/**
 * Last modified: $Date: 2005/01/10 20:38:01 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.19 $
 */
public class CaseHomeImpl extends IDOFactory implements CaseHome {

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

	public Collection findAllCasesForGroupByStatuses(Group group, String[] statuses, int startingCase, int numberOfCases) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CaseBMPBean) entity).ejbFindAllCasesForGroupByStatuses(group, statuses, startingCase, numberOfCases);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int getCountOfAllCasesForGroupByStatuses(Group group, String[] statuses) throws IDOException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((CaseBMPBean) entity).ejbHomeGetCountOfAllCasesForGroupByStatuses(group, statuses);
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

}
