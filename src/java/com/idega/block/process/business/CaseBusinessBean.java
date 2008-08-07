/*
 * $Id: CaseBusinessBean.java,v 1.75 2008/08/07 13:39:30 valdas Exp $
 * Created in 2002 by Tryggvi Larusson
 *
 * Copyright (C) 2002-2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseCodeHome;
import com.idega.block.process.data.CaseHome;
import com.idega.block.process.data.CaseLog;
import com.idega.block.process.data.CaseLogHome;
import com.idega.block.process.data.CaseStatus;
import com.idega.block.process.data.CaseStatusHome;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
import com.idega.core.accesscontrol.business.NotLoggedOnException;
import com.idega.data.IDOException;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.data.IDOStoreException;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.idegaweb.UnavailableIWContext;
import com.idega.presentation.IWContext;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.user.data.UserHome;
import com.idega.util.IWTimestamp;

/**
 * <p>
 * This is the main logic class for the case/process module.
 * </p>
 *  Last modified: $Date: 2008/08/07 13:39:30 $ by $Author: valdas $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.75 $
 */
public class CaseBusinessBean extends IBOServiceBean implements CaseBusiness {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
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
	private String CASE_STATUS_ERROR;
	private String CASE_STATUS_MOVED;
	private String CASE_STATUS_PLACED;
	private String CASE_STATUS_PENDING_KEY;
	private String CASE_STATUS_WAITING;
	
	private Map _statusMap;

	private static Map listenerCaseCodeMap;
	private static Map listenerCaseCodeStatusMap;
	
	protected final static String PARAMETER_SELECTED_CASE = "sel_case_nr";
	
	public CaseBusinessBean() {
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
		this.CASE_STATUS_ERROR = this.getCaseHome().getCaseStatusError();
		this.CASE_STATUS_MOVED = this.getCaseHome().getCaseStatusMoved();
		this.CASE_STATUS_PLACED = this.getCaseHome().getCaseStatusPlaced();
		this.CASE_STATUS_PENDING_KEY = this.getCaseHome().getCaseStatusPending();
		this.CASE_STATUS_WAITING = this.getCaseHome().getCaseStatusWaiting();
	}
	
	private CaseStatus getCaseStatusFromMap(String caseStatus) {
		if (this._statusMap != null) {
			return (CaseStatus) this._statusMap.get(caseStatus);
		}
		return null;
	}
	
	private void putCaseStatusInMap(CaseStatus status) {
		if (this._statusMap == null) {
			this._statusMap = new HashMap();
		}
		
		this._statusMap.put(status.getStatus(), status);
	}

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
	public Case createSubCase(Case oldCase) throws CreateException {
		return createSubCase(oldCase, oldCase.getCaseCode());
	}

	/**
	 * Creates a new case with a specified case code that is a result of the
	 * previous case .
	 */
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
	public Collection getAllActiveCasesForUser(User user) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user);
	}

	/**
	 * Gets all the active Cases for the User with a specificed code
	 */
	public Collection getAllActiveCasesForUser(User user, CaseCode code) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, code);
	}

	/**
	 * Gets all the active Cases for the User with a specificed code
	 */
	public Collection getAllActiveCasesForUser(User user, String caseCode) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, caseCode);
	}

	/**
	 * Gets all the active Cases for the User with a specificed code and status
	 */
	public Collection getAllActiveCasesForUser(User user, CaseCode code, CaseStatus status) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, code, status);
	}

	/**
	 * Gets all the active Cases for the User with a specificed code and status
	 */
	public Collection getAllActiveCasesForUser(User user, String caseCode, String caseStatus) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, caseCode, caseStatus);
	}

	/**
	 * Gets all the Cases for the User
	 */
	public Collection getAllCasesForUser(User user) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user);
	}

	/**
	 * Gets all the Cases for the User
	 */
	public Collection getAllCasesForGroup(Group group) throws FinderException {
		return this.getCaseHome().findAllCasesByGroup(group);
	}

	/**
	 * Gets all the Cases for the User except the ones with one of the CaseCode
	 * in the codes[] array.
	 */
	public Collection getAllCasesForUserExceptCodes(User user, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		return this.getCaseHome().findAllCasesForUserExceptCodes(user, codes, startingCase, numberOfCases);
	}

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
	public Collection getAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) throws FinderException {
		return this.getCaseHome().findAllCasesForGroupExceptCodes(group, codes);
	}

	/**
	 * Gets all the Cases for the Group except the ones with one of the CaseCode
	 * in the codes[] array.
	 */
	public Collection getAllCasesForUserAndGroupsExceptCodes(User user, Collection groups, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException {
		return this.getCaseHome().findAllCasesForGroupsAndUserExceptCodes(user, groups, codes, startingCase, numberOfCases);
	}

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
	public Collection getAllCasesForUser(User user, CaseCode code) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, code);
	}

	/**
	 * Gets all the Cases for the User with a specificed code
	 */
	public Collection getAllCasesForUser(User user, String caseCode) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, caseCode);
	}

	/**
	 * Gets all the Cases for the User with a specificed code and status
	 */
	public Collection getAllCasesForUser(User user, String caseCode, String caseStatus) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, caseCode, caseStatus);
	}

	/**
	 * Gets all the Cases for the User with a specificed code and status
	 */
	public Collection getAllCasesForUser(User user, CaseCode code, CaseStatus status) throws FinderException {
		return this.getCaseHome().findAllCasesByUser(user, code, status);
	}

	public Collection getCaseLogsByDates(Timestamp fromDate, Timestamp toDate) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByDate(fromDate, toDate);
	}

	public Collection getCaseLogsByCaseCodeAndDates(CaseCode caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException {
		return getCaseLogsByCaseCodeAndDates(caseCode.getCode(), fromDate, toDate);
	}

	public Collection getCaseLogsByCaseCodeAndDates(String caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByCaseAndDate(caseCode, fromDate, toDate);
	}

	public Collection getCaseLogsByDatesAndStatusChange(Timestamp fromDate, Timestamp toDate, CaseStatus statusBefore, CaseStatus statusAfter) throws FinderException {
		return getCaseLogsByDatesAndStatusChange(fromDate, toDate, statusBefore.getStatus(), statusAfter.getStatus());
	}

	public Collection getCaseLogsByDatesAndStatusChange(Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByDateAndStatusChange(fromDate, toDate, statusBefore, statusAfter);
	}

	public Collection getCaseLogsByCaseAndDatesAndStatusChange(CaseCode caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		return getCaseLogsByCaseAndDatesAndStatusChange(caseCode.getCode(), fromDate, toDate, statusBefore, statusAfter);
	}

	public Collection getCaseLogsByCaseAndDatesAndStatusChange(String caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByCaseAndDateAndStatusChange(caseCode, fromDate, toDate, statusBefore, statusAfter);
	}
	
	public Collection getCaseLogsByCase(Case theCase) throws FinderException {
		return getCaseLogHome().findAllCaseLogsByCaseOrderedByDate(theCase);
	}

	public Case getCase(int caseID) throws FinderException {
		return getCaseHome().findByPrimaryKey(new Integer(caseID));
	}

	public Case getCase(Object casePK) throws FinderException {
		return getCaseHome().findByPrimaryKey(new Integer(casePK.toString()));
	}

	public CaseCode getCaseCode(String caseCode) throws FinderException {
		return getCaseCodeHome().findByPrimaryKey(caseCode);
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
	
	public Collection getCaseCodes() {
		try {
			return getCaseCodeHome().findAllCaseCodes();
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			return new ArrayList();
		}
	}
	
	public Collection getCaseStatuses() {
		try {
			return getCaseStatusHome().findAllStatuses();
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			return new ArrayList();
		}
	}

	public CaseStatus getCaseStatus(String StatusCode) {
		return getCaseStatusAndInstallIfNotExists(StatusCode);
	}

	public CaseStatus getCaseStatusOpen() {
		return getCaseStatus(this.CASE_STATUS_OPEN_KEY);
	}

	public CaseStatus getCaseStatusGranted() {
		return getCaseStatus(this.CASE_STATUS_GRANTED_KEY);
	}

	public CaseStatus getCaseStatusDeleted() {
		return getCaseStatus(this.CASE_STATUS_DELETED_KEY);
	}

	public CaseStatus getCaseStatusDenied() {
		return getCaseStatus(this.CASE_STATUS_DENIED_KEY);
	}

	public CaseStatus getCaseStatusReview() {
		return getCaseStatus(this.CASE_STATUS_REVIEW_KEY);
	}

	public CaseStatus getCaseStatusWaiting() {
		return getCaseStatus(this.CASE_STATUS_WAITING);
	}

	public CaseStatus getCaseStatusMoved() {
		return getCaseStatus(this.CASE_STATUS_MOVED);
	}

	public CaseStatus getCaseStatusPlaced() {
		return getCaseStatus(this.CASE_STATUS_PLACED);
	}

	public CaseStatus getCaseStatusPending() {
		return getCaseStatus(this.CASE_STATUS_PENDING_KEY);
	}

	public CaseStatus getCaseStatusCancelled() {
		return this.getCaseStatus(this.CASE_STATUS_CANCELLED_KEY);
	}

	public CaseStatus getCaseStatusInactive() {
		return getCaseStatus(this.CASE_STATUS_INACTIVE_KEY);
	}

	public CaseStatus getCaseStatusPreliminary() {
		return getCaseStatus(this.CASE_STATUS_PRELIMINARY_KEY);
	}

	public CaseStatus getCaseStatusContract() {
		return getCaseStatus(this.CASE_STATUS_CONTRACT_KEY);
	}

	public CaseStatus getCaseStatusReady() {
		return getCaseStatusAndInstallIfNotExists(this.CASE_STATUS_READY_KEY);
	}

	public CaseStatus getCaseStatusRedeem() {
		return getCaseStatus(this.CASE_STATUS_REDEEM_KEY);
	}

	public CaseStatus getCaseStatusError() {
		return getCaseStatus(this.CASE_STATUS_ERROR);
	}

	protected CaseStatus getCaseStatusAndInstallIfNotExists(String caseStatusString) {
		
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

	
	protected String getLocalizedString(String key, String defaultValue, Locale locale) {
		return getBundle().getResourceBundle(locale).getLocalizedString(key, defaultValue);
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

	public void changeCaseStatus(int theCaseID, String newCaseStatus, User performer) throws FinderException {
		Case theCase = this.getCase(theCaseID);
		changeCaseStatus(theCase, newCaseStatus, performer);
	}
	
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer) {
		changeCaseStatus(theCase, newCaseStatus, performer, performer);
	}
	
	public void changeCaseStatusDoNotSendUpdates(Case theCase, String newCaseStatus, User performer) {
		changeCaseStatus(theCase, newCaseStatus, null, performer, null, false, null, false);
	}
	
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer,Map attributes) {
		changeCaseStatus(theCase, newCaseStatus, null, performer,null,false,attributes);
	}

	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer, Group handler) {
		changeCaseStatus(theCase, newCaseStatus, null, performer, handler);
	}
	
	public void changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler) {
		changeCaseStatus(theCase, newCaseStatus, comment, performer, handler, false);
	}
	
	public void changeCaseStatus(Case theCase, CaseStatus newCaseStatus, User performer) {
		changeCaseStatus(theCase, newCaseStatus.getStatus(), performer);	
	}
	
	public void changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler, boolean canBeSameStatus) {
		changeCaseStatus(theCase,newCaseStatus,comment,performer,handler,canBeSameStatus,null);
	}
	
	public void changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler, boolean canBeSameStatus,Map attributes) {
		changeCaseStatus(theCase, newCaseStatus, comment, performer, handler, canBeSameStatus, attributes, true);
	}

	public void changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler, boolean canBeSameStatus,Map attributes, boolean sendUpdates) {
		String oldCaseStatus = "";
		try {
			oldCaseStatus = theCase.getStatus();
			Collection listeners = null;
			if (sendUpdates) {
				listeners = getCaseChangeListeners(theCase,newCaseStatus);
			
			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				CaseChangeListener listener = (CaseChangeListener) iter.next();
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

			if (oldCaseStatus != newCaseStatus || canBeSameStatus) {
				CaseLog log = getCaseLogHome().create();
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
			}
			
			if (sendUpdates) {
			for (Iterator iter = listeners.iterator(); iter.hasNext();) {
				CaseChangeListener listener = (CaseChangeListener) iter.next();
				CaseChangeEvent event = new CaseChangeEvent(theCase);
				event.setPerformer(performer);
				event.setStatusFrom(oldCaseStatus);
				event.setStatusTo(newCaseStatus);
				event.setAttributes(attributes);
				listener.afterCaseChange(event);
			}
			}
			
		}
		catch (CreateException e) {
			throw new EJBException("Error changing case status: " + oldCaseStatus + " to " + newCaseStatus + ":" + e.getMessage());
		}
	}

	
	public String getLocalizedCaseDescription(Case theCase, Locale locale) {
		return getLocalizedCaseDescription(theCase.getCaseCode(), locale);
	}

	public String getLocalizedCaseDescription(CaseCode theCaseCode, Locale locale) {
		return getLocalizedString("case_code_key." + theCaseCode.toString(), theCaseCode.toString(),locale);
	}

	public String getLocalizedCaseStatusDescription(Case theCase, CaseStatus status, Locale locale) {
		return getLocalizedString("case_status_key." + status.toString(), status.toString(),locale);
	}

	private static final String PROC_CASE_BUNDLE_IDENTIFIER = "com.idega.block.process";

	/**
	 * Can be overrided in subclasses
	 */
	protected String getBundleIdentifier() {
		return PROC_CASE_BUNDLE_IDENTIFIER;
	}

	protected IWBundle getBundle() {
		return getIWApplicationContext().getIWMainApplication().getBundle(getBundleIdentifier());
	}

	/**
	 * Gets the last modifier of the Case. Returns null if not modification
	 * found.
	 */
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

	public String getCaseStatusOpenString() {
		return this.CASE_STATUS_OPEN_KEY;
	}

	public String getCaseStatusCancelledString() {
		return this.CASE_STATUS_CANCELLED_KEY;
	}

	public String getCaseStatusInactiveString() {
		return this.CASE_STATUS_INACTIVE_KEY;
	}

	public String getCaseStatusReadyString() {
		return this.CASE_STATUS_READY_KEY;
	}

	public String getCaseStatusDeletedString() {
		return this.CASE_STATUS_DELETED_KEY;
	}
	
	/**
	 * The parameters are added to the case link to the page set on the UserCases block when the casee and it's status are correct.
	 */
	public Map getCaseParameters(Case theCase) {
		return null;
	}
	
	public Class getEventListener() {
		return null;
	}
	
	public boolean canDeleteCase(Case theCase) {
		return false;
	}
	
	public void deleteCase(Case theCase, User performer) {
		changeCaseStatus(theCase, getCaseStatusDeletedString(), performer);
	}
	
	/**
	 * @return The parameter name of the current selected/clicked case number (case primary key). The parameter is always added to a case link
	 */
	public String getSelectedCaseParameter(){
		return PARAMETER_SELECTED_CASE;
	}
	
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
	public String getUrl(Case userCase) {
		String url = userCase.getUrl();
		return url;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.process.business.CaseBusiness#getCaseSubject(com.idega.block.process.data.Case, java.util.Locale)
	 */
	public String getCaseSubject(Case userCase, Locale currentLocale) {
		String subject = userCase.getSubject();
		if(subject!=null){
			return subject;
		}
		return getLocalizedCaseDescription(userCase,currentLocale);
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


	/**
	 * <p>
	 * TODO tryggvil describe method getCaseChangeListeners
	 * </p>
	 * @param theCase
	 * @param newCaseStatus
	 * @return
	 */
	protected List getCaseChangeListeners(Case theCase, String newCaseStatus) {
		ArrayList list = new ArrayList();
		String caseCode = theCase.getCode();
		List codeList = getListenerListForCaseCode(caseCode);
		list.addAll(codeList);
		List statusList = getListenerListForCaseCodeAndStatus(caseCode,newCaseStatus);
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
	public void addCaseChangeListener(CaseChangeListener myListener,String caseCode){
		List list = getListenerListForCaseCode(caseCode);
		list.add(myListener);
	}
	
	/**
	 * <p>
	 * TODO tryggvil describe method getListenerListForCaseCode
	 * </p>
	 * @param caseCode
	 * @return
	 */
	protected List getListenerListForCaseCode(String caseCode) {
		if(listenerCaseCodeMap==null){
			listenerCaseCodeMap = new HashMap();
		}
		List listenerList = (List) listenerCaseCodeMap.get(caseCode);
		if(listenerList==null){
			listenerList = new ArrayList();
			listenerCaseCodeMap.put(caseCode,listenerList);
		}
		return listenerList;
	}

	/**
	 * <p>
	 * TODO tryggvil describe method getListenerListForCaseCode
	 * </p>
	 * @param caseCode
	 * @return
	 */
	protected List getListenerListForCaseCodeAndStatus(String caseCode,String caseStatus) {
		if(listenerCaseCodeStatusMap==null){
			listenerCaseCodeStatusMap = new HashMap();
		}
		Map statusMap = (Map) listenerCaseCodeStatusMap.get(caseCode);
		if(statusMap==null){
			statusMap = new HashMap();
			listenerCaseCodeStatusMap.put(caseCode,statusMap);
		}
		List listenerList = (List) statusMap.get(caseStatus);
		if(listenerList==null){
			listenerList = new ArrayList();
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
	public void addCaseChangeListener(CaseChangeListener myListener,String caseCode,String caseStatusTo){
		List list = getListenerListForCaseCodeAndStatus(caseCode,caseStatusTo);
		list.add(myListener);
	}
	
	/**
	 * 
	 * @return The iwrb in the locale that is preferred by the user or current locale if the user does not prefer any.
	 */
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
	public IWResourceBundle getIWResourceBundleForUser(User user, IWContext iwc){
		 return getIWResourceBundleForUser(user,iwc,this.getBundle());
	}
	
	
	/**
	 * @return Warning use as a last resort if you have a way of getting IWContext (this will try to get it). Returns the iwrb in the locale that is preferred by the user or servers DEFAULT locale if the user does not prefer any. 
	 */
	public IWResourceBundle getIWResourceBundleForUser(User user){
		 return getIWResourceBundleForUser(user,null,this.getBundle());
	}
	
	private UserBusiness getUserBusiness() throws IBOLookupException {
		return (UserBusiness) this.getServiceInstance(UserBusiness.class);
	}
	
	public String[] getStatusesForOpenCases() {
		return new String[] {getCaseStatusOpen().getStatus(), getCaseStatusReview().getStatus()};
	}
	
	public String[] getStatusesForClosedCases() {
		return new String[] {getCaseStatusInactive().getStatus(), getCaseStatusReady().getStatus()};
	}
	
	public String[] getStatusesForMyCases() {
		return new String[] {getCaseStatusPending().getStatus(), getCaseStatusWaiting().getStatus()};
	}
	
	public String[] getStatusesForApprovedCases() {
		return new String[] { getCaseStatusGranted().getStatus(), getCaseStatusCancelled().getStatus() };	//	TODO:	Are these correct?
	}
	
	public String[] getStatusesForRejectedCases() {
		return new String[] { getCaseStatusDenied().getStatus() };
	}
}