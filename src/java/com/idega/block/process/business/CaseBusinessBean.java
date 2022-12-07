/*
 * $Id: CaseBusinessBean.java,v 1.85 2009/06/23 09:33:27 valdas Exp $
 * Created in 2002 by Tryggvi Larusson
 *
 * Copyright (C) 2002-2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.block.process.IWBundleStarter;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseBMPBean;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseCodeHome;
import com.idega.block.process.data.CaseHome;
import com.idega.block.process.data.CaseLog;
import com.idega.block.process.data.CaseLogHome;
import com.idega.block.process.data.CaseStatus;
import com.idega.block.process.data.CaseStatusHome;
import com.idega.block.process.message.business.MessageTypeManager;
import com.idega.builder.bean.AdvancedProperty;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
import com.idega.core.accesscontrol.business.NotLoggedOnException;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOException;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.data.IDOStoreException;
import com.idega.data.SimpleQuerier;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.idegaweb.UnavailableIWContext;
import com.idega.presentation.IWContext;
import com.idega.user.business.GroupBusiness;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.user.data.UserHome;
import com.idega.util.ArrayUtil;
import com.idega.util.CoreConstants;
import com.idega.util.CoreUtil;
import com.idega.util.IWTimestamp;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

/**
 * <p>
 * This is the main logic class for the case/process module.
 * </p>
 *  Last modified: $Date: 2009/06/23 09:33:27 $ by $Author: valdas $
 *
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.85 $
 */
public class CaseBusinessBean extends IBOServiceBean implements CaseBusiness {

	private static final long serialVersionUID = 5676084152460108081L;

	private String CASE_STATUS_OPEN_KEY;
	private String CASE_STATUS_INACTIVE_KEY;
	private String CASE_STATUS_GRANTED_KEY;
	private String CASE_STATUS_DELETED_KEY;
	private String CASE_STATUS_DENIED_KEY;
	private String CASE_STATUS_REVIEW_KEY;
	private String CASE_STATUS_CANCELLED_KEY;
	private String CASE_STATUS_PRELIMINARY_KEY;
	private String CASE_STATUS_CONTRACT_KEY;
	private String CASE_STATUS_READY_KEY;
	private String CASE_STATUS_REDEEM_KEY;
	private String CASE_STATUS_ERROR_KEY;
	private String CASE_STATUS_MOVED_KEY;
	private String CASE_STATUS_PLACED_KEY;
	private String CASE_STATUS_PENDING_KEY;
	private String CASE_STATUS_WAITING_KEY;
	private String CASE_STATUS_REPORT;
	private String CASE_STATUS_CREATED_KEY;
	private String CASE_STATUS_FINISHED_KEY;
	private String CASE_STATUS_CLOSED_KEY;

	@Override
	public List<String> getAllCasesStatuses() {
		return Arrays.asList(
				CASE_STATUS_OPEN_KEY,
				CASE_STATUS_INACTIVE_KEY,
				CASE_STATUS_GRANTED_KEY,
				CASE_STATUS_DELETED_KEY,
				CASE_STATUS_DENIED_KEY,
				CASE_STATUS_REVIEW_KEY,
				CASE_STATUS_CANCELLED_KEY,
				CASE_STATUS_PRELIMINARY_KEY,
				CASE_STATUS_CONTRACT_KEY,
				CASE_STATUS_READY_KEY,
				CASE_STATUS_REDEEM_KEY,
				CASE_STATUS_ERROR_KEY,
				CASE_STATUS_MOVED_KEY,
				CASE_STATUS_PLACED_KEY,
				CASE_STATUS_PENDING_KEY,
				CASE_STATUS_WAITING_KEY,
				CASE_STATUS_CREATED_KEY,
				CASE_STATUS_FINISHED_KEY,
				CASE_STATUS_CLOSED_KEY
		);
	}

	private Map<String, CaseStatus> _statusMap;

	private static Map<String, List<CaseChangeListener>> listenerCaseCodeMap;
	private static Map<String, Map<String, List<CaseChangeListener>>> listenerCaseCodeStatusMap;

	protected final static String PARAMETER_SELECTED_CASE = "sel_case_nr";

	public CaseBusinessBean() {

		this.getCaseHome().createDefaultCaseStatuses();

		this.CASE_STATUS_OPEN_KEY = this.getCaseHome().getCaseStatusOpen();
		this.CASE_STATUS_INACTIVE_KEY = this.getCaseHome().getCaseStatusInactive();
		this.CASE_STATUS_GRANTED_KEY = this.getCaseHome().getCaseStatusGranted();
		this.CASE_STATUS_DELETED_KEY = this.getCaseHome().getCaseStatusDeleted();
		this.CASE_STATUS_DENIED_KEY = this.getCaseHome().getCaseStatusDenied();
		this.CASE_STATUS_REVIEW_KEY = this.getCaseHome().getCaseStatusReview();
		this.CASE_STATUS_CANCELLED_KEY = this.getCaseHome().getCaseStatusCancelled();
		this.CASE_STATUS_PRELIMINARY_KEY = this.getCaseHome().getCaseStatusPreliminary();
		this.CASE_STATUS_CONTRACT_KEY = this.getCaseHome().getCaseStatusContract();
		this.CASE_STATUS_READY_KEY = this.getCaseHome().getCaseStatusReady();
		this.CASE_STATUS_REDEEM_KEY = this.getCaseHome().getCaseStatusRedeem();
		this.CASE_STATUS_ERROR_KEY = this.getCaseHome().getCaseStatusError();
		this.CASE_STATUS_MOVED_KEY = this.getCaseHome().getCaseStatusMoved();
		this.CASE_STATUS_PLACED_KEY = this.getCaseHome().getCaseStatusPlaced();
		this.CASE_STATUS_PENDING_KEY = this.getCaseHome().getCaseStatusPending();
		this.CASE_STATUS_WAITING_KEY = this.getCaseHome().getCaseStatusWaiting();
		this.CASE_STATUS_CREATED_KEY = this.getCaseHome().getCaseStatusCreated();
		this.CASE_STATUS_FINISHED_KEY = this.getCaseHome().getCaseStatusFinished();
		this.CASE_STATUS_CLOSED_KEY = this.getCaseHome().getCaseStatusClosed();
		this.CASE_STATUS_REPORT = this.getCaseHome().getCaseStatusReport();
	}

	private CaseStatus getCaseStatusFromMap(String caseStatus) {
		if (this._statusMap != null) {
			return this._statusMap.get(caseStatus);
		}
		return null;
	}

	private void putCaseStatusInMap(CaseStatus status) {
		if (this._statusMap == null) {
			this._statusMap = new HashMap<>();
		}

		this._statusMap.put(status.getStatus(), status);
	}

	@Override
	public Case createCase(int userID, String caseCode) throws CreateException {
		try {
			User user = this.getUserHome().findByPrimaryKey(new Integer(userID));
			CaseCode code = this.getCaseCode(caseCode);
			return createCase(user, code);
		}
		catch (FinderException fe) {
			throw new CreateException(fe.getMessage());
		}
	}

	@Override
	public Case createCase(User user, CaseCode code) throws CreateException {
		try {
			Case newCase = this.getCaseHome().create();
			newCase.setOwner(user);
			newCase.setCaseCode(code);
			newCase.setCreated(new IWTimestamp().getTimestamp());
			newCase.store();
			return newCase;
		}
		catch (IDOStoreException se) {
			throw new CreateException(se.getMessage());
		}
	}

	/**
	 * Creates a new case that is a result of the previous case with the same
	 * case code.
	 */
	@Override
	public Case createSubCase(Case oldCase) throws CreateException {
		return createSubCase(oldCase, oldCase.getCaseCode());
	}

	/**
	 * Creates a new case with a specified case code that is a result of the
	 * previous case .
	 */
	@Override
	public Case createSubCase(Case oldCase, CaseCode newCaseCode) throws CreateException {
		try {
			Case newCase = this.getCaseHome().create();
			newCase.setOwner(oldCase.getOwner());
			newCase.setCaseCode(newCaseCode);
			newCase.setCreated(new IWTimestamp().getTimestamp());
			newCase.store();
			return newCase;
		}
		catch (IDOStoreException se) {
			throw new CreateException(se.getMessage());
		}
	}

	/**
	 * Gets all the active Cases for the User
	 */
	@Override
	public Collection<Case> getAllActiveCasesForUser(User user) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user);
	}

	/**
	 * Gets all the active Cases for the User with a specificed code
	 */
	@Override
	public Collection<Case> getAllActiveCasesForUser(User user, CaseCode code) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, code);
	}

	/**
	 * Gets all the active Cases for the User with a specificed code
	 */
	@Override
	public Collection<Case> getAllActiveCasesForUser(User user, String caseCode) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, caseCode);
	}

	/**
	 * Gets all the active Cases for the User with a specificed code and status
	 */
	@Override
	public Collection<Case> getAllActiveCasesForUser(User user, CaseCode code, CaseStatus status) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, code, status);
	}

	/**
	 * Gets all the active Cases for the User with a specificed code and status
	 */
	@Override
	public Collection<Case> getAllActiveCasesForUser(User user, String caseCode, String caseStatus) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, caseCode, caseStatus);
	}

	/**
	 * Gets all the Cases for the User
	 */
	@Override
	public Collection<Case> getAllCasesForUser(User user) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user);
	}

	/**
	 * Gets all the Cases for the User
	 */
	@Override
	public Collection<Case> getAllCasesForGroup(Group group) throws FinderException {
		return this.getCaseHome().findAllCasesByGroup(group);
	}

	/**
	 * Gets all the Cases for the User except the ones with one of the CaseCode
	 * in the codes[] array.
	 */
	@Override
	public Collection<Case> getAllCasesForUserExceptCodes(User user, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		return this.getCaseHome().findAllCasesForUserExceptCodes(user, codes, startingCase, numberOfCases);
	}

	@Override
	public int getNumberOfCasesForUserExceptCodes(User user, CaseCode[] codes) {
		try {
			return this.getCaseHome().getNumberOfCasesForUserExceptCodes(user, codes);
		}
		catch (IDOException e) {
			return 0;
		}
	}

	/**
	 * Gets all the Cases for the Group except the ones with one of the CaseCode
	 * in the codes[] array.
	 */
	@Override
	public Collection<Case> getAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) throws FinderException {
		return this.getCaseHome().findAllCasesForGroupExceptCodes(group, codes);
	}

	/**
	 * Gets all the Cases for the Group except the ones with one of the CaseCode
	 * in the codes[] array.
	 */
	@Override
	public Collection<Case> getAllCasesForUserAndGroupsExceptCodes(User user, Collection groups, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		return this.getCaseHome().findAllCasesForGroupsAndUserExceptCodes(user, groups, codes, startingCase, numberOfCases);
	}

	@Override
	public int getNumberOfCasesForUserAndGroupsExceptCodes(User user, Collection groups, CaseCode[] codes) {
		try {
			return this.getCaseHome().getNumberOfCasesByGroupsOrUserExceptCodes(user, groups, codes);
		}
		catch (IDOException e) {
			return 0;
		}
	}

	/**
	 * Gets all the Cases for the User with a specificed code
	 */
	@Override
	public Collection<Case> getAllCasesForUser(User user, CaseCode code) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, code);
	}

	/**
	 * Gets all the Cases for the User with a specificed code
	 */
	@Override
	public Collection<Case> getAllCasesForUser(User user, String caseCode) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, caseCode);
	}

	/**
	 * Gets all the Cases for the User with a specificed code and status
	 */
	@Override
	public Collection<Case> getAllCasesForUser(User user, String caseCode, String caseStatus) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, caseCode, caseStatus);
	}

	/**
	 * Gets all the Cases for the User with a specificed code and status
	 */
	@Override
	public Collection<Case> getAllCasesForUser(User user, CaseCode code, CaseStatus status) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, code, status);
	}

	@Override
	public Collection<CaseLog> getCaseLogsByDates(Timestamp fromDate, Timestamp toDate) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByDate(fromDate, toDate);
	}

	@Override
	public Collection<CaseLog> getCaseLogsByCaseCodeAndDates(CaseCode caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException {
		return getCaseLogsByCaseCodeAndDates(caseCode.getCode(), fromDate, toDate);
	}

	@Override
	public Collection<CaseLog> getCaseLogsByCaseCodeAndDates(String caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByCaseAndDate(caseCode, fromDate, toDate);
	}

	@Override
	public Collection<CaseLog> getCaseLogsByDatesAndStatusChange(Timestamp fromDate, Timestamp toDate, CaseStatus statusBefore, CaseStatus statusAfter) throws FinderException {
		return getCaseLogsByDatesAndStatusChange(fromDate, toDate, statusBefore.getStatus(), statusAfter.getStatus());
	}

	@Override
	public Collection<CaseLog> getCaseLogsByDatesAndStatusChange(Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByDateAndStatusChange(fromDate, toDate, statusBefore, statusAfter);
	}

	@Override
	public Collection getCaseLogsByCaseAndDatesAndStatusChange(CaseCode caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		return getCaseLogsByCaseAndDatesAndStatusChange(caseCode.getCode(), fromDate, toDate, statusBefore, statusAfter);
	}

	@Override
	public Collection<CaseLog> getCaseLogsByCaseAndDatesAndStatusChange(String caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByCaseAndDateAndStatusChange(caseCode, fromDate, toDate, statusBefore, statusAfter);
	}

	@Override
	public Collection<CaseLog> getCaseLogsByCase(Case theCase) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByCaseOrderedByDate(theCase);
	}

	@Override
	public CaseLog getLatestLogForCase(Case theCase) throws FinderException {
		return getCaseLogHome().findLastCaseLogForCase(theCase);
	}

	@Override
	public Case getCase(int caseID) throws FinderException {
		return getCaseHome().findByPrimaryKey(new Integer(caseID));
	}

	@Override
	public Case getCase(Object casePK) throws FinderException {
		return getCaseHome().findByPrimaryKey(new Integer(casePK.toString()));
	}

	@Override
	public CaseCode getCaseCode(String caseCode) throws FinderException {
		return getCaseCodeHome().findByPrimaryKey(caseCode);
	}

	private GroupBusiness groupBusiness;

	protected GroupBusiness getGroupBusiness() {
		if (this.groupBusiness == null) {
			try {
				this.groupBusiness = IBOLookup.getServiceInstance(
						getIWApplicationContext(),
						GroupBusiness.class);
			} catch (IBOLookupException e) {
				getLogger().log(Level.WARNING,
						"Failed to get " + GroupBusiness.class, e);
			}
		}

		return this.groupBusiness;
	}

	protected UserHome getUserHome() {
		try {
			return (UserHome) IDOLookup.getHome(User.class);
		}
		catch (IDOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected User getUser(int userID) throws FinderException {
		return this.getUserHome().findByPrimaryKey(new Integer(userID));
	}

	protected CaseHome getCaseHome() {
		try {
			return (CaseHome) IDOLookup.getHome(Case.class);
		}
		catch (IDOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected CaseCodeHome getCaseCodeHome() {
		try {
			return (CaseCodeHome) IDOLookup.getHome(CaseCode.class);
		}
		catch (IDOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected CaseLogHome getCaseLogHome() {
		try {
			return (CaseLogHome) IDOLookup.getHome(CaseLog.class);
		}
		catch (IDOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected CaseStatusHome getCaseStatusHome() {
		try {
			return (CaseStatusHome) IDOLookup.getHome(CaseStatus.class);
		}
		catch (IDOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	@Override
	public Collection<CaseCode> getCaseCodes() {
		try {
			return getCaseCodeHome().findAllCaseCodes();
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public Collection<CaseStatus> getCaseStatuses() {
		try {
			return getCaseStatusHome().findAllStatuses();
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public CaseStatus getCaseStatus(String StatusCode) {
		try {
			return getCaseStatusAndInstallIfNotExists(StatusCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CaseStatus getCaseStatusOpen() {
		return getCaseStatus(this.CASE_STATUS_OPEN_KEY);
	}

	@Override
	public CaseStatus getCaseStatusGranted() {
		return getCaseStatus(this.CASE_STATUS_GRANTED_KEY);
	}

	@Override
	public CaseStatus getCaseStatusDeleted() {
		return getCaseStatus(this.CASE_STATUS_DELETED_KEY);
	}

	@Override
	public CaseStatus getCaseStatusDenied() {
		return getCaseStatus(this.CASE_STATUS_DENIED_KEY);
	}

	@Override
	public CaseStatus getCaseStatusReview() {
		return getCaseStatus(this.CASE_STATUS_REVIEW_KEY);
	}

	@Override
	public CaseStatus getCaseStatusWaiting() {
		return getCaseStatus(this.CASE_STATUS_WAITING_KEY);
	}

	@Override
	public CaseStatus getCaseStatusReport() {
		return getCaseStatus(this.CASE_STATUS_REPORT);
	}

	@Override
	public CaseStatus getCaseStatusMoved() {
		return getCaseStatus(this.CASE_STATUS_MOVED_KEY);
	}

	@Override
	public CaseStatus getCaseStatusPlaced() {
		return getCaseStatus(this.CASE_STATUS_PLACED_KEY);
	}

	@Override
	public CaseStatus getCaseStatusPending() {
		return getCaseStatus(this.CASE_STATUS_PENDING_KEY);
	}

	@Override
	public CaseStatus getCaseStatusCancelled() {
		return this.getCaseStatus(this.CASE_STATUS_CANCELLED_KEY);
	}

	@Override
	public CaseStatus getCaseStatusInactive() {
		return getCaseStatus(this.CASE_STATUS_INACTIVE_KEY);
	}

	@Override
	public CaseStatus getCaseStatusPreliminary() {
		return getCaseStatus(this.CASE_STATUS_PRELIMINARY_KEY);
	}

	@Override
	public CaseStatus getCaseStatusContract() {
		return getCaseStatus(this.CASE_STATUS_CONTRACT_KEY);
	}

	@Override
	public CaseStatus getCaseStatusReady() {
		try {
			return getCaseStatusAndInstallIfNotExists(this.CASE_STATUS_READY_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CaseStatus getCaseStatusCreated() {
		try {
			return getCaseStatusAndInstallIfNotExists(this.CASE_STATUS_CREATED_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CaseStatus getCaseStatusFinished() {
		try {
			return getCaseStatusAndInstallIfNotExists(this.CASE_STATUS_FINISHED_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CaseStatus getCaseStatusRedeem() {
		return getCaseStatus(this.CASE_STATUS_REDEEM_KEY);
	}

	@Override
	public CaseStatus getCaseStatusError() {
		return getCaseStatus(this.CASE_STATUS_ERROR_KEY);
	}

	protected CaseStatus getCaseStatusAndInstallIfNotExists(String caseStatusString) throws Exception {
		if (StringUtil.isEmpty(caseStatusString)) {
			Logger.getLogger(getClass().getName()).warning("Status code is unknown! Can not create status.");
			return null;
		}

		if(caseStatusString.length()>4){
			caseStatusString=caseStatusString.substring(0,4);
		}

		CaseStatus status = getCaseStatusFromMap(caseStatusString);
		if (status != null) {
			return status;
		}

		try {
			status = getCaseStatusHome().findByPrimaryKey(caseStatusString);
		}
		catch (FinderException fe) {
			try {
				status = getCaseStatusHome().create();
				status.setStatus(caseStatusString);
				status.store();
			}
			catch (Exception e) {
				throw new EJBException("Error creating CaseStatus " + caseStatusString + " is not installed or does not exist. Message: " + e.getMessage());
			}
		}

		putCaseStatusInMap(status);
		return status;
	}

	protected boolean hasStatusChange(Case theCase, String statusBefore, String statusAfter) {
		try {
			return getCaseLogHome().getCountByStatusChange(theCase, statusBefore, statusAfter) > 0;
		}
		catch (IDOException ie) {
			return false;
		}
	}

	protected Locale getDefaultLocale() {
		return getIWApplicationContext().getIWMainApplication().getSettings().getDefaultLocale();
	}

	/**
	 * @deprecated Use getIWResourceBundleForUser. This method was deprecated because it is most often used to localize messages but ignores that the user might have a preferred language! Please use getIWResourceBundleForUser(...) to get the iwrb (locale) the user prefers.
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	@Deprecated
	protected String getLocalizedString(String key, String defaultValue) {
		//at least a little better although it should still be deprecated, try to get the current locale, user etc. fallback on server default value
		try {
			IWContext iwc = IWContext.getInstance();
			User user = null;
			try {
				user = iwc.getCurrentUser();
			} catch (NotLoggedOnException e1) {
			}

			if(user!=null){
				IWResourceBundle iwrb = this.getIWResourceBundleForUser(user, iwc);
				return iwrb.getLocalizedString(key, defaultValue);
			}else{
				return getLocalizedString(key, defaultValue, iwc.getCurrentLocale());
			}
		} catch (UnavailableIWContext e) {
			return getLocalizedString(key, defaultValue, this.getDefaultLocale());
		}
	}

	protected String getLocalizedString(String key, String defaultValue, Locale locale, String bundleIdentifier) {
		return getIWMainApplication().getBundle(bundleIdentifier).getResourceBundle(locale).getLocalizedString(locale, key, defaultValue);
	}

	protected String getLocalizedString(String key, String defaultValue, Locale locale) {
		return getLocalizedString(key, defaultValue, locale, getBundleIdentifier());
	}

	/**
	 *
	 * @param key
	 * @param defaultValue
	 * @param user
	 * @param iwc
	 * @return Gets a localized string in the users preferred language
	 */
	protected String getLocalizedStringForUser(String key, String defaultValue, User user, IWContext iwc) {
		return this.getIWResourceBundleForUser(user, iwc).getLocalizedString(key, defaultValue);
	}

	@Override
	public void changeCaseStatus(int theCaseID, String newCaseStatus, User performer) throws FinderException {
		Case theCase = this.getCase(theCaseID);
		changeCaseStatus(theCase, newCaseStatus, performer);
	}

	@Override
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer) {
		changeCaseStatus(theCase, newCaseStatus, performer, performer);
	}

	@Override
	public void changeCaseStatusDoNotSendUpdates(Case theCase, String newCaseStatus, User performer) {
		changeCaseStatus(theCase, newCaseStatus, null, performer, null, false, null, false);
	}

	@Override
	public void changeCaseStatusDoNotSendUpdates(Case theCase, String newCaseStatus, User performer, String comment) {
		changeCaseStatusDoNotSendUpdates(theCase, newCaseStatus, performer, comment, false);
	}

	@Override
	public CaseLog changeCaseStatusDoNotSendUpdates(Case theCase, String newCaseStatus, User performer, String comment, boolean canBeSameStatus) {
		return changeCaseStatus(theCase, newCaseStatus, comment, performer, null, canBeSameStatus, null, false);
	}

	@Override
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer,Map attributes) {
		changeCaseStatus(theCase, newCaseStatus, null, performer,null,false,attributes);
	}

	@Override
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer, Group handler) {
		changeCaseStatus(theCase, newCaseStatus, null, performer, handler);
	}

	@Override
	public CaseLog changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler) {
		return changeCaseStatus(theCase, newCaseStatus, comment, performer, handler, false);
	}

	@Override
	public void changeCaseStatus(Case theCase, CaseStatus newCaseStatus, User performer) {
		changeCaseStatus(theCase, newCaseStatus.getStatus(), performer);
	}

	@Override
	public CaseLog changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler, boolean canBeSameStatus) {
		return changeCaseStatus(theCase,newCaseStatus,comment,performer,handler,canBeSameStatus,null);
	}

	@Override
	public CaseLog changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler, boolean canBeSameStatus,Map attributes) {
		return changeCaseStatus(theCase, newCaseStatus, comment, performer, handler, canBeSameStatus, attributes, true);
	}

	@Override
	public CaseLog changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler, boolean canBeSameStatus, Map attributes, boolean sendUpdates) {
		CaseLog log = null;
		String oldCaseStatus = CoreConstants.EMPTY;
		try {
			if (StringUtil.isEmpty(newCaseStatus)) {
				getLogger().info("Case's status can not be null/empty: not creating log for changing status from '" + oldCaseStatus + "' to '" +  newCaseStatus + "' by " + performer +
						" for case " + theCase + ", comment: " + comment);
			}

			oldCaseStatus = theCase.getStatus();
			Collection<CaseChangeListener> listeners = null;
			if (sendUpdates) {
				listeners = getCaseChangeListeners(theCase, newCaseStatus);

				for (Iterator<CaseChangeListener> iter = listeners.iterator(); iter.hasNext();) {
					CaseChangeListener listener = iter.next();
					CaseChangeEvent event = new CaseChangeEvent(theCase);
					event.setPerformer(performer);
					event.setStatusFrom(oldCaseStatus);
					event.setStatusTo(newCaseStatus);
					event.setAttributes(attributes);
					listener.beforeCaseChange(event);
				}
			}

			theCase.setStatus(newCaseStatus);
			if (handler != null) {
				theCase.setHandler(handler);
			}
			theCase.store();

			CaseLogHome caseLogHome = getCaseLogHome();

			boolean createLog = !oldCaseStatus.equals(newCaseStatus) || canBeSameStatus;
			if (!createLog && !StringUtil.isEmpty(comment)) {
				Collection<CaseLog> allLogs = null;
				try {
					allLogs = caseLogHome.findAllCaseLogsByCaseOrderedByDate(theCase);
				} catch (Exception e) {}
				if (ListUtil.isEmpty(allLogs)) {
					createLog = true;
				} else {
					boolean foundSameComment = false;
					for (Iterator<CaseLog> iter = allLogs.iterator(); (!foundSameComment && iter.hasNext());) {
						CaseLog tmpLog = iter.next();
						String existingComment = tmpLog.getComment();
						if (!StringUtil.isEmpty(existingComment) && comment.equals(existingComment)) {
							if (performer != null) {
								foundSameComment = Integer.valueOf(performer.getId()) == tmpLog.getPerformerId();
								log = tmpLog;
								break;
							} else {
								foundSameComment = true;
								log = tmpLog;
								break;
							}
						}
					}
					createLog = !foundSameComment;
				}
			}
			if (createLog) {
				log = caseLogHome.create();
				log.setCase(Integer.parseInt(theCase.getPrimaryKey().toString()));
				log.setCaseStatusBefore(oldCaseStatus);
				log.setCaseStatusAfter(newCaseStatus);
				if (performer != null) {
					log.setPerformer(performer);
				}
				if (comment != null) {
					log.setComment(comment);
				}
				log.store();
				getLogger().info("Created log (" + log.getPrimaryKey() + ") for changing status from '" + oldCaseStatus + "' to '" +  newCaseStatus +
						"' by " + performer + " for case " + theCase + ", comment: " + comment);
			} else {
				getLogger().info("Not creating log for changing status from '" + oldCaseStatus + "' to '" +  newCaseStatus + "' by " + performer +
						" for case " + theCase + ", comment: " + comment);
			}

			if (sendUpdates) {
				for (Iterator<CaseChangeListener> iter = listeners.iterator(); iter.hasNext();) {
					CaseChangeListener listener = iter.next();
					CaseChangeEvent event = new CaseChangeEvent(theCase);
					event.setPerformer(performer);
					event.setStatusFrom(oldCaseStatus);
					event.setStatusTo(newCaseStatus);
					event.setAttributes(attributes);
					listener.afterCaseChange(event);
				}
			}

		} catch (CreateException e) {
			throw new EJBException("Error changing case status: " + oldCaseStatus + " to " + newCaseStatus + ":" + e.getMessage());
		}
		return log;
	}

	@Override
	public String getLocalizedCaseDescription(Case theCase, Locale locale) {
		return getLocalizedCaseDescription(theCase.getCaseCode(), locale);
	}

	@Override
	public String getLocalizedCaseDescription(CaseCode theCaseCode, Locale locale) {
		return getLocalizedString("case_code_key." + theCaseCode.toString(), theCaseCode.toString(),locale);
	}

	@Override
	public String getLocalizedCaseStatusDescription(Case theCase, CaseStatus status, Locale locale) {
		return getLocalizedCaseStatusDescription(theCase, status, locale, getBundleIdentifier());
	}

	@Override
	public String getLocalizedCaseStatusDescription(Case theCase, CaseStatus status, Locale locale, String bundleIdentifier) {
		String statusKey = status.toString();
		String localizationKey = ProcessConstants.CASE_STATUS_KEY + CoreConstants.DOT + statusKey;
		String localization = getLocalizedString(localizationKey, statusKey, locale, bundleIdentifier);
		if ((StringUtil.isEmpty(localization) || statusKey.equals(localization)) && !ProcessConstants.IW_BUNDLE_IDENTIFIER.equals(bundleIdentifier)) {
			IWBundle bundle = IWMainApplication.getDefaultIWMainApplication().getBundle(ProcessConstants.IW_BUNDLE_IDENTIFIER);
			IWResourceBundle iwrb = bundle.getResourceBundle(locale);
			localization = iwrb.getLocalizedString(localizationKey, statusKey);
		}

		return StringUtil.isEmpty(localization) ? statusKey : localization;
	}

	/**
	 * Can be overrided in subclasses
	 */
	@Override
	protected String getBundleIdentifier() {
		return IWBundleStarter.IW_BUNDLE_IDENTIFIER;
	}

	@Override
	protected IWBundle getBundle() {
		return getIWApplicationContext().getIWMainApplication().getBundle(getBundleIdentifier());
	}

	/**
	 * Gets the last modifier of the Case. Returns null if not modification
	 * found.
	 */
	@Override
	public User getLastModifier(Case aCase) {
		try {
			CaseLog log = this.getCaseLogHome().findLastCaseLogForCase(aCase);
			return log.getPerformer();
		}
		catch (Exception e) {
			// empty
		}
		return null;
	}

	@Override
	public String getCaseStatusOpenString() {
		return this.CASE_STATUS_OPEN_KEY;
	}

	@Override
	public String getCaseStatusCancelledString() {
		return this.CASE_STATUS_CANCELLED_KEY;
	}

	@Override
	public String getCaseStatusInactiveString() {
		return this.CASE_STATUS_INACTIVE_KEY;
	}

	@Override
	public String getCaseStatusReadyString() {
		return this.CASE_STATUS_READY_KEY;
	}

	@Override
	public String getCaseStatusDeletedString() {
		return this.CASE_STATUS_DELETED_KEY;
	}

	/**
	 * The parameters are added to the case link to the page set on the UserCases block when the casee and it's status are correct.
	 */
	@Override
	public Map getCaseParameters(Case theCase) {
		return null;
	}

	@Override
	public Class getEventListener() {
		return null;
	}

	@Override
	public boolean canDeleteCase(Case theCase) {
		return false;
	}

	@Override
	public void deleteCase(Case theCase, User performer) {
		changeCaseStatus(theCase, getCaseStatusDeletedString(), performer);
	}

	/**
	 * @return The parameter name of the current selected/clicked case number (case primary key). The parameter is always added to a case link
	 */
	@Override
	public String getSelectedCaseParameter(){
		return PARAMETER_SELECTED_CASE;
	}

	@Override
	public CaseBusiness getCaseBusiness(String caseCode) throws FinderException {
		CaseCode code = getCaseCodeHome().findByPrimaryKey(caseCode);
		try {
			return CaseCodeManager.getInstance().getCaseBusinessOrDefault(code, getIWApplicationContext());
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.idega.block.process.business.CaseBusiness#getUrl(com.idega.block.process.data.Case)
	 */
	@Override
	public String getUrl(Case userCase) {
		String url = userCase.getUrl();
		return url;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.process.business.CaseBusiness#getCaseSubject(com.idega.block.process.data.Case, java.util.Locale)
	 */
	@Override
	public String getCaseSubject(Case userCase, Locale currentLocale) {
		String subject = userCase.getSubject();
		if (!StringUtil.isEmpty(subject) && !subject.equals(userCase.getCode())) {
			return subject;
		}
		return getLocalizedCaseDescription(userCase, currentLocale);
	}

	/**
	 * <p>
	 * Gets the CaseCode instance with primary key caseCode if it exitsts
	 * in the databse. Else it is created inserted and returned.
	 * </p>
	 * @param caseCode
	 * @return
	 */
	protected CaseCode getCaseCodeAndInstallIfNotExists(String caseCode) {
		CaseCode code=null;
		try {
			code = getCaseCode(caseCode);
		}
		catch (FinderException e) {
			try {
				code = getCaseCodeHome().create();
				if(caseCode.length()>7){
					caseCode=caseCode.substring(0,7);
				}
				code.setCode(caseCode);
				code.store();
			}
			catch (CreateException e1) {
				throw new RuntimeException(e);
			}
		}
		return code;
	}

	protected List<CaseChangeListener> getCaseChangeListeners(Case theCase, String newCaseStatus) {
		List<CaseChangeListener> list = new ArrayList<>();
		String caseCode = theCase.getCode();
		List<CaseChangeListener> codeList = getListenerListForCaseCode(caseCode);
		list.addAll(codeList);
		List<CaseChangeListener> statusList = getListenerListForCaseCodeAndStatus(caseCode,newCaseStatus);
		list.addAll(statusList);
		return list;
	}
	/**
	 * <p>
	 * Registers a listener on all status changes for all cases with given caseCode
	 * </p>
	 * @param myListener
	 * @param caseCode
	 * @param caseStatusTo
	 */
	@Override
	public void addCaseChangeListener(CaseChangeListener myListener,String caseCode){
		List<CaseChangeListener> list = getListenerListForCaseCode(caseCode);
		list.add(myListener);
	}

	/**
	 * <p>
	 * TODO tryggvil describe method getListenerListForCaseCode
	 * </p>
	 * @param caseCode
	 * @return
	 */
	protected List<CaseChangeListener> getListenerListForCaseCode(String caseCode) {
		if (listenerCaseCodeMap == null) {
			listenerCaseCodeMap = new HashMap<>();
		}

		List<CaseChangeListener> listenerList = listenerCaseCodeMap.get(caseCode);
		if (listenerList == null) {
			listenerList = new ArrayList<>();
			listenerCaseCodeMap.put(caseCode, listenerList);
		}
		return listenerList;
	}

	protected List<CaseChangeListener> getListenerListForCaseCodeAndStatus(String caseCode, String caseStatus) {
		if (listenerCaseCodeStatusMap == null) {
			listenerCaseCodeStatusMap = new HashMap<>();
		}

		Map<String, List<CaseChangeListener>> statusMap = listenerCaseCodeStatusMap.get(caseCode);
		if (statusMap==null) {
			statusMap = new HashMap<>();
			listenerCaseCodeStatusMap.put(caseCode, statusMap);
		}

		List<CaseChangeListener> listenerList = statusMap.get(caseStatus);
		if (listenerList == null) {
			listenerList = new ArrayList<>();
			statusMap.put(caseStatus,listenerList);
		}
		return listenerList;
	}
	/**
	 * <p>
	 * Registers a listener on a status change for all cases with given caseCode and when the status
	 * is changed to caseStatusTo
	 * </p>
	 * @param myListener
	 * @param caseCode
	 * @param caseStatusTo
	 */
	@Override
	public void addCaseChangeListener(CaseChangeListener myListener,String caseCode,String caseStatusTo){
		List<CaseChangeListener> list = getListenerListForCaseCodeAndStatus(caseCode,caseStatusTo);
		list.add(myListener);
	}

	/**
	 *
	 * @return The iwrb in the locale that is preferred by the user or current locale if the user does not prefer any.
	 */
	@Override
	public IWResourceBundle getIWResourceBundleForUser(User user, IWContext iwc, IWBundle bundle){
		Locale locale = null;

		if(iwc!=null && user==null){
			try{
				user = iwc.getCurrentUser();
			}
			catch(NotLoggedOnException ex){
			}
		}

		try {
			if(user!=null){
				locale = getUserBusiness().getUsersPreferredLocale(user);
			}

		} catch (IBOLookupException e) {
			e.printStackTrace();
		}

		if(locale!=null){
			return bundle.getResourceBundle(locale);
		}
		else{
			if(iwc!=null){
				return bundle.getResourceBundle(iwc);
			}
			else{
				try {
					iwc = IWContext.getInstance();
					return bundle.getResourceBundle(iwc);
				} catch (UnavailableIWContext e) {
					return bundle.getResourceBundle(this.getDefaultLocale());
				}
			}
		}

	}

	/**
	 * @return The iwrb in the locale that is preferred by the user or current locale if the user does not prefer any.
	 */
	@Override
	public IWResourceBundle getIWResourceBundleForUser(User user, IWContext iwc){
		 return getIWResourceBundleForUser(user,iwc,this.getBundle());
	}


	/**
	 * @return Warning use as a last resort if you have a way of getting IWContext (this will try to get it). Returns the iwrb in the locale that is preferred by the user or servers DEFAULT locale if the user does not prefer any.
	 */
	@Override
	public IWResourceBundle getIWResourceBundleForUser(User user){
		 return getIWResourceBundleForUser(user,null,this.getBundle());
	}

	private UserBusiness getUserBusiness() throws IBOLookupException {
		return this.getServiceInstance(UserBusiness.class);
	}

	private boolean isStatusForOpenCase(String status) {
		if (StringUtil.isEmpty(status)) {
			return false;
		}
		return getSettings().getBoolean("case.status_open_".concat(status), Boolean.TRUE);
	}

	@Override
	public String[] getStatusesForOpenCases() {
		String customStatusesForClosedCases = getApplicationProperty("statuses_for_open_cases");
		if (!StringUtil.isEmpty(customStatusesForClosedCases)) {
			String[] statuses = customStatusesForClosedCases.split(CoreConstants.COMMA);
			if (!ArrayUtil.isEmpty(statuses)) {
				return statuses;
			}
		}

		String[] statuses = new String[] {
				getCaseStatusOpen().getStatus(),
				getCaseStatusReview().getStatus(),
				getCaseStatusCreated().getStatus(),
				getCaseStatusPending().getStatus(),
				CaseBMPBean.CASE_STATUS_IN_PROCESS_KEY,
				CaseBMPBean.CASE_STATUS_ASSIGNED,
				CaseBMPBean.CASE_STATUS_ON_HOLD,
				CaseBMPBean.CASE_STATUS_RECEIVED_METRICS,
				CaseBMPBean.CASE_STATUS_RECEIVED_ORDER,
				CaseBMPBean.CASE_STATUS_REMOVED_KEY
		};
		if (isStatusForOpenCase(CaseBMPBean.CASE_STATUS_RECEIPT)) {
			List<String> tmp = new ArrayList<>(Arrays.asList(statuses));
			tmp.add(CaseBMPBean.CASE_STATUS_RECEIPT);
			statuses = ArrayUtil.convertListToArray(tmp);
		}
		return statuses;
	}

	@Override
	public String[] getStatusesForClosedCases() {
		String customStatusesForClosedCases = getApplicationProperty("statuses_for_closed_cases");
		if (!StringUtil.isEmpty(customStatusesForClosedCases)) {
			String[] statuses = customStatusesForClosedCases.split(CoreConstants.COMMA);
			if (!ArrayUtil.isEmpty(statuses)) {
				return statuses;
			}
		}

		String[] statuses = new String[] {
				getCaseStatusInactive().getStatus(),
				getCaseStatusReady().getStatus(),
				getCaseStatusFinished().getStatus(),
				CaseBMPBean.CASE_STATUS_CLOSED,
				CaseBMPBean.CASE_STATUS_DELETED_KEY
		};
		if (!isStatusForOpenCase(CaseBMPBean.CASE_STATUS_RECEIPT)) {
			List<String> tmp = new ArrayList<>(Arrays.asList(statuses));
			tmp.add(CaseBMPBean.CASE_STATUS_RECEIPT);
			statuses = ArrayUtil.convertListToArray(tmp);
		}
		return statuses;
	}

	@Override
	public String[] getStatusesForMyCases() {
		return new String[] {getCaseStatusPending().getStatus(), getCaseStatusWaiting().getStatus()};
	}

	@Override
	public String[] getStatusesForApprovedCases() {
		return new String[] { getCaseStatusGranted().getStatus(), getCaseStatusCancelled().getStatus() };	//	TODO:	Are these correct?
	}

	@Override
	public String[] getStatusesForRejectedCases() {
		return new String[] {
				getCaseStatusDenied().getStatus(),
				CaseBMPBean.CASE_STATUS_SPAM
		};
	}

	@Override
	public CaseCode[] getCaseCodesForUserCasesList() {
		@SuppressWarnings("unchecked")
		Collection<String> msgCodes = MessageTypeManager.getInstance().getMessageCodes();
		if (msgCodes.isEmpty()) {
			return null;
		}

		List<CaseCode> codes = new ArrayList<>();
		for (String code : msgCodes) {
			try {
				codes.add(getCaseCode(code));
			} catch (FinderException e) {
				Logger.getLogger(getClass().getName()).log(Level.WARNING, "Exception while resolving hidden case code by message cod = " + code);
			}
		}

		return codes.toArray(new CaseCode[codes.size()]);
	}

	@Override
	public Collection<Case> getCasesByIds(Collection<Integer> ids) {
		try {
			return getCaseHome().findAllByIds(ids);
		} catch (FinderException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Could not get cases by ids", e);
		}
		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.process.business.CaseBusiness#addSubscriber(java.lang.Object, com.idega.user.data.User)
	 */
	@Override
	public boolean addSubscriber(Object casePK, User subscriber) {
		Case theCase = null;
		try {
			theCase = getCase(casePK);
		} catch(Exception e) {
			getLogger().log(Level.WARNING, "Failed to get case by id: " + casePK);
		}

		return addSubscriber(theCase, subscriber);
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.process.business.CaseBusiness#addSubscriber(com.idega.block.process.data.Case, com.idega.user.data.User)
	 */
	@Override
	public boolean addSubscriber(Case theCase, User subscriber) {
		if (subscriber == null || theCase == null) {
			return false;
		}

		try {
			theCase.addSubscriber(subscriber);
		} catch (IDOAddRelationshipException e) {
			getLogger().log(Level.WARNING, "Failed to add subscriber, cause of: ", e);
			return false;
		}

		theCase.store();
		CoreUtil.clearIDOCaches();

		return true;
	}

	@Override
	public boolean isSubscribed(Object casePK, User user) {
		if (user == null) {
			return false;
		}

		Case theCase = null;
		try {
			theCase = getCase(casePK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (theCase == null) {
			return false;
		}

		Collection<User> subscribers = theCase.getSubscribers();
		return ListUtil.isEmpty(subscribers) ? false : subscribers.contains(user);
	}

	@Override
	public Boolean isCaseClosed(String caseIdentifier) {
		if (StringUtil.isEmpty(caseIdentifier)) {
			return null;
		}

		Case theCase = null;
		try {
			theCase = getCaseByIdentifier(caseIdentifier);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Unable to find case by identifier: " + caseIdentifier, e);
		}

		if (theCase == null) {
			return Boolean.FALSE;
		}

		return theCase.isClosed();
	}

	@Override
	public Case getCaseByIdentifier(String caseIdentifier) throws FinderException, RemoteException {
		if (StringUtil.isEmpty(caseIdentifier)) {
			getLogger().warning("Case identifier not provided");
			return null;
		}

		Collection<Case> cases = getCaseHome().findCasesByCaseIdentifier(caseIdentifier);
		if (ListUtil.isEmpty(cases)) {
			getLogger().warning("Case was not found by identifier: " + caseIdentifier);
			return null;
		}

		for (Case caseInstance: cases) {
			return caseInstance;
		}

		return null;
	}

	@Override
	public List<Integer> findSubscribedCasesIds(Collection<User> handlers) {
		if (ListUtil.isEmpty(handlers)) {
			return Collections.emptyList();
		}

		StringBuilder sb = new StringBuilder("SELECT distinct pcs.PROC_CASE_ID ");
		sb.append("FROM proc_case_subscribers pcs ");
		sb.append("WHERE pcs.IC_USER_ID IN (");
		for (Iterator<User> iterator = handlers.iterator(); iterator.hasNext();) {
			sb.append(iterator.next().getPrimaryKey().toString());
			if (iterator.hasNext()) {
				sb.append(CoreConstants.COMMA);
				sb.append(CoreConstants.SPACE);
			}
		}

		sb.append(")");

		String[] caseIds = null;
		try {
			caseIds = SimpleQuerier.executeStringQuery(sb.toString());
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed to execute query: " +
					sb.toString() + " cause of: ", e);
		}

		if (ArrayUtil.isEmpty(caseIds)) {
			return Collections.emptyList();
		}

		List<Integer> ids = new ArrayList<>(caseIds.length);
		for (String caseId : caseIds) {
			ids.add(Integer.valueOf(caseId));
		}

		return ids;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.process.business.CaseBusiness#findSubscribedCases(java.util.Collection)
	 */
	@Override
	public List<Case> findSubscribedCasesByHandlers(Collection<User> handlers) {
		List<Integer> caseIds = findSubscribedCasesIds(handlers);
		if (ListUtil.isEmpty(caseIds)) {
			return Collections.emptyList();
		}

		Collection<Case> cases = getCasesByIds(caseIds);
		if (ListUtil.isEmpty(cases)) {
			return Collections.emptyList();
		}

		return new ArrayList<>(cases);
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.process.business.CaseBusiness#findSubscribedCases(java.util.Set)
	 */
	@Override
	public List<Case> findSubscribedCases(Collection<Group> groups) {
		if (ListUtil.isEmpty(groups)) {
			return Collections.emptyList();
		}

		/*
		 * FIXME need method in group business, which would return all users
		 * from multiple groups. For now, this method usually takes only one
		 * group
		 */
		Collection<User> handlers = new ArrayList<>();
		Collection<User> users = null;
		for (Group group: groups) {
			try {
				users = getGroupBusiness().getUsers(group);
			} catch (Exception e) {
				getLogger().log(
						Level.WARNING,
						"Failed to get users from groups: ", e);
			}

			if (!ListUtil.isEmpty(users)) {
				handlers.addAll(users);
			}
		}

		return findSubscribedCasesByHandlers(handlers);
	}

	/* (non-Javadoc)
	 * @see is.idega.idegaweb.egov.reykjavik.business.HandlerGroupManagerBusiness#findAllCases(java.lang.String)
	 */
	@Override
	public List<Case> findSubscribedCases(String groupName) {
		if (StringUtil.isEmpty(groupName)) {
			return Collections.emptyList();
		}

		Collection<Group> groups = null;
		try {
			groups = getGroupBusiness().getGroupsByGroupName(groupName);
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to find groups: ", e);
		}

		if (ListUtil.isEmpty(groups)) {
			return Collections.emptyList();
		}

		return findSubscribedCases(new HashSet<>(groups));
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.process.business.CaseBusiness#findSubscribedCases(java.lang.Object)
	 */
	@Override
	public List<Case> findSubscribedCasesByPrimaryKey(String groupPrimaryKey) {
		if (StringUtil.isEmpty(groupPrimaryKey)) {
			return Collections.emptyList();
		}

		Group group = null;
		try {
			group = getGroupBusiness().getGroupByGroupID(Integer.valueOf(groupPrimaryKey));
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to find groups: ", e);
		} catch (NumberFormatException e) {
			getLogger().log(Level.WARNING,
					"Unable to convert: " + groupPrimaryKey +
					" to " + Integer.class, e);
		} catch (FinderException e) {
			getLogger().log(Level.WARNING,
					"Group by id " + groupPrimaryKey + " not found!");
		}

		if (group == null) {
			return Collections.emptyList();
		}

		return findSubscribedCases(group);
	}

	/*
	 * (non-Javadoc)
	 * @see is.idega.idegaweb.egov.reykjavik.business.HandlerGroupManagerBusiness#findAllCases(com.idega.user.data.Group)
	 */
	@Override
	public List<Case> findSubscribedCases(Group group) {
		return findSubscribedCases(Arrays.asList(group));
	}

	@Override
	public String getCaseStatusLocalized(String statusKey) {
		if (StringUtil.isEmpty(statusKey)) {
			return null;
		}

		try {
			CaseStatusHome caseStatusHome = getCaseStatusHome();
			CaseStatus caseStatus = caseStatusHome.findByPrimaryKey(statusKey);
			return getCaseStatusLocalized(caseStatus);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting localized status for: " + statusKey, e);
		}

		return null;
	}

	@Override
	public String getCaseStatusLocalized(CaseStatus caseStatus) {
		if (caseStatus == null) {
			return null;
		}

		String statusKey = caseStatus.getStatus();
		try {
			Locale locale = CoreUtil.getCurrentLocale();

			String localization = null;
			try {
				@SuppressWarnings("unchecked")
				Class<? extends CaseBusiness> caseBusinessClass = (Class<? extends CaseBusiness>) Class.forName("is.idega.idegaweb.egov.cases.business.CasesBusiness");
				CaseBusiness casesBusiness = IBOLookup.getServiceInstance(getIWApplicationContext(), caseBusinessClass);
				localization = casesBusiness.getLocalizedCaseStatusDescription(null, caseStatus, locale);
			} catch (Exception e) {}
			if (!StringUtil.isEmpty(localization) && !localization.equals(statusKey)) {
				return localization;
			}

			localization = getLocalizedCaseStatusDescription(null, caseStatus, locale);
			if (StringUtil.isEmpty(localization) || localization.equals(statusKey)) {
				localization = getLocalizedCaseStatusDescription(null, caseStatus, locale, "is.idega.idegaweb.egov.cases");
			}
			if (StringUtil.isEmpty(localization)) {
				return statusKey;
			}
			return localization;
		} catch(Exception e) {
			getLogger().log(Level.WARNING, "Error getting localized status for: " + caseStatus, e);
		}

		return statusKey;
	}

	@Override
	public Map<String, AdvancedProperty> getCaseStatuses(boolean showAllStatuses, String caseStatusesToShow, String caseStatusesToHide) {
		Collection<CaseStatus> allStatuses = null;
		try {
			allStatuses = getCaseStatuses();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ListUtil.isEmpty(allStatuses)) {
			getLogger().warning("There are no statuses available");
			return null;
		}

		Locale l = CoreUtil.getCurrentLocale();
		if (l == null) {
			l = Locale.ENGLISH;
		}

		boolean addStatus = true;
		String localizedStatus = null;
		Map<String, AdvancedProperty> statuses = new HashMap<>();
		for (CaseStatus status: allStatuses) {
			addStatus = true;

			try {
				localizedStatus = getLocalizedCaseStatusDescription(null, status, l);
				if (!showAllStatuses && localizedStatus.equals(status.getStatus())) {
					addStatus = false;
				}

				if (caseStatusesToShow != null) {
					if (caseStatusesToShow.indexOf(status.getStatus()) != -1) {
						addStatus = true;
					} else if (!showAllStatuses) {
						addStatus = false;
					}
				}

				if (caseStatusesToHide != null) {
					if (caseStatusesToHide.indexOf(status.getStatus()) != -1) {
						addStatus = false;
					} else if (showAllStatuses) {
						addStatus = true;
					}
				}

				if (addStatus) {
					String statusKey = status.getStatus();
					if (statuses.containsKey(localizedStatus)) {
						AdvancedProperty statusItem = statuses.get(localizedStatus);
						statusItem.setId(statusItem.getId().concat(CoreConstants.COMMA).concat(statusKey));
					} else {
						statuses.put(localizedStatus, new AdvancedProperty(statusKey, localizedStatus));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statuses;
	}

	@Override
	public Collection<Case> getCasesByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			String caseStatus
	) throws FinderException, RemoteException {
		return getCasesByCriteria(caseNumber, caseSubject, caseCode, caseStatus, null, null);
	}

	@Override
	public Collection<Case> getCasesByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			String caseStatus,
			Integer from,
			Integer amount
	) throws FinderException, RemoteException {
		return getCasesByCriteria(caseNumber, caseSubject, caseCode, StringUtil.isEmpty(caseStatus) ? Collections.emptyList() : Arrays.asList(caseStatus), from, amount);
	}

	@Override
	public Collection<Case> getCasesByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			List<String> caseStatuses,
			Integer from,
			Integer amount
	) throws FinderException, RemoteException {
		if (StringUtil.isEmpty(caseCode)) {
			getLogger().warning("Case code is not provided");
			return null;
		}

		Collection<Case> cases = getCaseHome().findByCriteria(caseNumber, caseSubject, caseCode, caseStatuses, from, amount);
		return cases;
	}

	@Override
	public Long getCountedCasesByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			String caseStatus
	) {
		return getCountedCasesByCriteria(caseNumber, caseSubject, caseCode, StringUtil.isEmpty(caseStatus) ? Collections.emptyList() : Arrays.asList(caseStatus));
	}

	@Override
	public Long getCountedCasesByCriteria(
			String caseNumber,
			String caseSubject,
			String caseCode,
			List<String> caseStatuses
	) {
		try {
			return getCaseHome().getCountedCasesByCriteria(caseNumber, caseSubject, caseCode, caseStatuses);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error counting cases by criteria. Number: " + caseNumber + ", subject: " + caseSubject + ", code: " + caseCode + ", status: " + caseStatuses, e);
		}
		return null;
	}

}