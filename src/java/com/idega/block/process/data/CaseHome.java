/*
 * $Id: CaseHome.java,v 1.19 2005/01/10 20:38:01 laddi Exp $
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
import com.idega.data.IDOHome;
import com.idega.user.data.Group;
import com.idega.user.data.User;


/**
 * Last modified: $Date: 2005/01/10 20:38:01 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.19 $
 */
public interface CaseHome extends IDOHome {

	public Case create() throws javax.ejb.CreateException;

	public Case findByPrimaryKey(Object pk) throws javax.ejb.FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection findAllCasesByUser(User user) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByGroup
	 */
	public Collection findAllCasesByGroup(Group group) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection findAllCasesByUser(User user, CaseCode caseCode) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection findAllCasesByUser(User user, String caseCode) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection findAllCasesByUser(User user, CaseCode caseCode, CaseStatus caseStatus) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesByUser
	 */
	public Collection findAllCasesByUser(User user, String caseCode, String caseStatus) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindSubCasesUnder
	 */
	public Collection findSubCasesUnder(Case theCase) throws FinderException;

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
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForUserExceptCodes
	 */
	public Collection findAllCasesForUserExceptCodes(User user, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForUserByStatuses
	 */
	public Collection findAllCasesForUserByStatuses(User user, String[] statuses, int startingCase, int numberOfCases) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCountOfAllCasesForUserByStatuses
	 */
	public int getCountOfAllCasesForUserByStatuses(User user, String[] statuses) throws IDOException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForGroupByStatuses
	 */
	public Collection findAllCasesForGroupByStatuses(Group group, String[] statuses, int startingCase, int numberOfCases) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetCountOfAllCasesForGroupByStatuses
	 */
	public int getCountOfAllCasesForGroupByStatuses(Group group, String[] statuses) throws IDOException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForGroupsAndUserExceptCodes
	 */
	public Collection findAllCasesForGroupsAndUserExceptCodes(User user, Collection groups, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbFindAllCasesForGroupExceptCodes
	 */
	public Collection findAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) throws FinderException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetNumberOfCasesForUserExceptCodes
	 */
	public int getNumberOfCasesForUserExceptCodes(User user, CaseCode[] codes) throws IDOException;

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#ejbHomeGetNumberOfCasesByGroupsOrUserExceptCodes
	 */
	public int getNumberOfCasesByGroupsOrUserExceptCodes(User user, Collection groups, CaseCode[] codes) throws IDOException;

}
