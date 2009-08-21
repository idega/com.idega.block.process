package com.idega.block.process.business;


import com.idega.presentation.IWContext;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.block.process.data.Case;
import javax.ejb.CreateException;
import com.idega.block.process.data.CaseStatus;
import java.util.Map;
import com.idega.block.process.data.CaseCode;
import com.idega.user.data.User;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import com.idega.idegaweb.IWBundle;
import com.idega.user.data.Group;
import java.util.Locale;
import java.util.Collection;
import javax.ejb.FinderException;
import com.idega.business.IBOService;

public interface CaseBusiness extends IBOService {
	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#createCase
	 */
	public Case createCase(int userID, String caseCode) throws CreateException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#createCase
	 */
	public Case createCase(User user, CaseCode code) throws CreateException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#createSubCase
	 */
	public Case createSubCase(Case oldCase) throws CreateException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#createSubCase
	 */
	public Case createSubCase(Case oldCase, CaseCode newCaseCode) throws CreateException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user, CaseCode code) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user, String caseCode) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user, CaseCode code, CaseStatus status) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllActiveCasesForUser
	 */
	public Collection getAllActiveCasesForUser(User user, String caseCode, String caseStatus) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForGroup
	 */
	public Collection getAllCasesForGroup(Group group) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUserExceptCodes
	 */
	public Collection getAllCasesForUserExceptCodes(User user, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getNumberOfCasesForUserExceptCodes
	 */
	public int getNumberOfCasesForUserExceptCodes(User user, CaseCode[] codes) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForGroupExceptCodes
	 */
	public Collection getAllCasesForGroupExceptCodes(Group group, CaseCode[] codes) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUserAndGroupsExceptCodes
	 */
	public Collection getAllCasesForUserAndGroupsExceptCodes(User user, Collection groups, CaseCode[] codes, int startingCase, int numberOfCases) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getNumberOfCasesForUserAndGroupsExceptCodes
	 */
	public int getNumberOfCasesForUserAndGroupsExceptCodes(User user, Collection groups, CaseCode[] codes) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user, CaseCode code) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user, String caseCode) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user, String caseCode, String caseStatus) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getAllCasesForUser
	 */
	public Collection getAllCasesForUser(User user, CaseCode code, CaseStatus status) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByDates
	 */
	public Collection getCaseLogsByDates(Timestamp fromDate, Timestamp toDate) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByCaseCodeAndDates
	 */
	public Collection getCaseLogsByCaseCodeAndDates(CaseCode caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByCaseCodeAndDates
	 */
	public Collection getCaseLogsByCaseCodeAndDates(String caseCode, Timestamp fromDate, Timestamp toDate) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByDatesAndStatusChange
	 */
	public Collection getCaseLogsByDatesAndStatusChange(Timestamp fromDate, Timestamp toDate, CaseStatus statusBefore, CaseStatus statusAfter) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByDatesAndStatusChange
	 */
	public Collection getCaseLogsByDatesAndStatusChange(Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByCaseAndDatesAndStatusChange
	 */
	public Collection getCaseLogsByCaseAndDatesAndStatusChange(CaseCode caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByCaseAndDatesAndStatusChange
	 */
	public Collection getCaseLogsByCaseAndDatesAndStatusChange(String caseCode, Timestamp fromDate, Timestamp toDate, String statusBefore, String statusAfter) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseLogsByCase
	 */
	public Collection getCaseLogsByCase(Case theCase) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCase
	 */
	public Case getCase(int caseID) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCase
	 */
	public Case getCase(Object casePK) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseCode
	 */
	public CaseCode getCaseCode(String caseCode) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseCodes
	 */
	public Collection getCaseCodes() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatuses
	 */
	public Collection getCaseStatuses() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatus
	 */
	public CaseStatus getCaseStatus(String StatusCode) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusOpen
	 */
	public CaseStatus getCaseStatusOpen() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusGranted
	 */
	public CaseStatus getCaseStatusGranted() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusDeleted
	 */
	public CaseStatus getCaseStatusDeleted() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusDenied
	 */
	public CaseStatus getCaseStatusDenied() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusReview
	 */
	public CaseStatus getCaseStatusReview() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusWaiting
	 */
	public CaseStatus getCaseStatusWaiting() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusMoved
	 */
	public CaseStatus getCaseStatusMoved() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusPlaced
	 */
	public CaseStatus getCaseStatusPlaced() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusPending
	 */
	public CaseStatus getCaseStatusPending() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusCancelled
	 */
	public CaseStatus getCaseStatusCancelled() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusInactive
	 */
	public CaseStatus getCaseStatusInactive() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusPreliminary
	 */
	public CaseStatus getCaseStatusPreliminary() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusContract
	 */
	public CaseStatus getCaseStatusContract() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusReady
	 */
	public CaseStatus getCaseStatusReady() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusRedeem
	 */
	public CaseStatus getCaseStatusRedeem() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusError
	 */
	public CaseStatus getCaseStatusError() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(int theCaseID, String newCaseStatus, User performer) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatusDoNotSendUpdates
	 */
	public void changeCaseStatusDoNotSendUpdates(Case theCase, String newCaseStatus, User performer) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer, Map attributes) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer, Group handler) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, CaseStatus newCaseStatus, User performer) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler, boolean canBeSameStatus) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler, boolean canBeSameStatus, Map attributes) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#changeCaseStatus
	 */
	public void changeCaseStatus(Case theCase, String newCaseStatus, String comment, User performer, Group handler, boolean canBeSameStatus, Map attributes, boolean sendUpdates) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getLocalizedCaseDescription
	 */
	public String getLocalizedCaseDescription(Case theCase, Locale locale) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getLocalizedCaseDescription
	 */
	public String getLocalizedCaseDescription(CaseCode theCaseCode, Locale locale) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getLocalizedCaseStatusDescription
	 */
	public String getLocalizedCaseStatusDescription(Case theCase, CaseStatus status, Locale locale) throws RemoteException;
	
	public String getLocalizedCaseStatusDescription(Case theCase, CaseStatus status, Locale locale, String bundleIdentifier) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getLastModifier
	 */
	public User getLastModifier(Case aCase) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusOpenString
	 */
	public String getCaseStatusOpenString() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusCancelledString
	 */
	public String getCaseStatusCancelledString() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusInactiveString
	 */
	public String getCaseStatusInactiveString() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusReadyString
	 */
	public String getCaseStatusReadyString() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseStatusDeletedString
	 */
	public String getCaseStatusDeletedString() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseParameters
	 */
	public Map getCaseParameters(Case theCase) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getEventListener
	 */
	public Class getEventListener() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#canDeleteCase
	 */
	public boolean canDeleteCase(Case theCase) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#deleteCase
	 */
	public void deleteCase(Case theCase, User performer) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getSelectedCaseParameter
	 */
	public String getSelectedCaseParameter() throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseBusiness
	 */
	public CaseBusiness getCaseBusiness(String caseCode) throws FinderException, RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getUrl
	 */
	public String getUrl(Case userCase) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getCaseSubject
	 */
	public String getCaseSubject(Case userCase, Locale currentLocale) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#addCaseChangeListener
	 */
	public void addCaseChangeListener(CaseChangeListener myListener, String caseCode) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#addCaseChangeListener
	 */
	public void addCaseChangeListener(CaseChangeListener myListener, String caseCode, String caseStatusTo) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getIWResourceBundleForUser
	 */
	public IWResourceBundle getIWResourceBundleForUser(User user, IWContext iwc, IWBundle bundle) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getIWResourceBundleForUser
	 */
	public IWResourceBundle getIWResourceBundleForUser(User user, IWContext iwc) throws RemoteException;

	/**
	 * @see com.idega.block.process.business.CaseBusinessBean#getIWResourceBundleForUser
	 */
	public IWResourceBundle getIWResourceBundleForUser(User user) throws RemoteException;
	
	public String[] getStatusesForOpenCases();
	
	public String[] getStatusesForClosedCases();
	
	public String[] getStatusesForMyCases();
	
	public String[] getStatusesForApprovedCases();
	
	public String[] getStatusesForRejectedCases();
	
	public CaseCode[] getCaseCodesForUserCasesList();
	
	public Collection<Case> getCasesByIds(Collection<Integer> ids);
	
	public boolean addSubscriber(Object casePK, User subscriber);
		
	public boolean isSubscribed(Object casePK, User user);
	
	public CaseStatus getCaseStatusCreated();
	
	public CaseStatus getCaseStatusFinished();
}