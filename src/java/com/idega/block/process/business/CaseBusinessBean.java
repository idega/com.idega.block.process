package com.idega.block.process.business;
import com.idega.business.IBOServiceBean;
import com.idega.block.process.data.*;
import com.idega.data.*;
import com.idega.idegaweb.IWBundle;
import com.idega.core.data.*;
import com.idega.user.data.*;
import com.idega.util.IWTimestamp;
import java.rmi.RemoteException;
import javax.ejb.*;
import java.util.Collection;
import java.util.Locale;
/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega software
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public class CaseBusinessBean extends IBOServiceBean implements CaseBusiness
{
	/*private  final String CASE_STATUS_OPEN_KEY = "UBEH";
	private  final String CASE_STATUS_INACTIVE_KEY = "TYST";
	private  final String CASE_STATUS_GRANTED_KEY = "BVJD";
	private  final String CASE_STATUS_DENIED_KEY = "AVSL";
	private  final String CASE_STATUS_REVIEW_KEY = "OMPR";
	private  final String CASE_STATUS_CANCELLED_KEY = "UPPS";*/
	private String CASE_STATUS_OPEN_KEY;
	private String CASE_STATUS_INACTIVE_KEY;
	private String CASE_STATUS_GRANTED_KEY;
	private String CASE_STATUS_DENIED_KEY;
	private String CASE_STATUS_REVIEW_KEY;
	private String CASE_STATUS_CANCELLED_KEY;
	private String CASE_STATUS_PRELIMINARY_KEY;
	private String CASE_STATUS_CONTRACT_KEY;
	private String CASE_STATUS_READY_KEY;
	private String CASE_STATUS_REDEEM_KEY;
	public CaseBusinessBean()
	{
		try
		{
			CASE_STATUS_OPEN_KEY = this.getCaseHome().getCaseStatusOpen();
			CASE_STATUS_INACTIVE_KEY = this.getCaseHome().getCaseStatusInactive();
			CASE_STATUS_GRANTED_KEY = this.getCaseHome().getCaseStatusGranted();
			CASE_STATUS_DENIED_KEY = this.getCaseHome().getCaseStatusDenied();
			CASE_STATUS_REVIEW_KEY = this.getCaseHome().getCaseStatusReview();
			CASE_STATUS_CANCELLED_KEY = this.getCaseHome().getCaseStatusCancelled();
			CASE_STATUS_PRELIMINARY_KEY = this.getCaseHome().getCaseStatusPreliminary();
			CASE_STATUS_CONTRACT_KEY = this.getCaseHome().getCaseStatusContract();
			CASE_STATUS_READY_KEY = this.getCaseHome().getCaseStatusReady();
			CASE_STATUS_REDEEM_KEY = this.getCaseHome().getCaseStatusRedeem();
		}
		catch (RemoteException e)
		{
			System.err.println("CaseBusinessBean : Error initializing case codes : Error : " + e.getMessage());
		}
	}
	/**
	 * Returns the correct CaseBusiness implementation instance for the specified case code.<br>
	 * If there is no specified the default (this) is returned;
	 **/
	public CaseBusiness getCaseBusiness(String caseCode)
	{
		try
		{
			CaseCode code = this.getCaseCodeHome().findByPrimaryKey(caseCode);
			return this.getCaseBusiness(code);
		}
		catch (Exception e)
		{
			throw new EJBException(e.getMessage());
		}
	}
	/**
	 * Returns the correct CaseBusiness implementation instance for the specified case code.<br>
	 * If there is no specified the default (this) is returned;
	 **/
	public CaseBusiness getCaseBusiness(CaseCode code)
	{
		/**
		 * @todo: implement
		 */
		try
		{
			ICObject handler = code.getBusinessHandler();
			if (handler != null)
			{
				Class objClass = handler.getObjectClass();
				return (CaseBusiness) this.getServiceInstance(objClass);
			}
			/**
			 *TODO: Remove hardcoding of CaseBusinessBeans
			 */
			try{
				Class c = Class.forName("se.idega.idegaweb.commune.school.business.SchoolChoiceBusiness");
				if(code.getCode().equals("MBSKOLV")){
					return (CaseBusiness) this.getServiceInstance(c);
				}
			}
			catch(ClassNotFoundException e){}
			try{
				Class c = Class.forName("se.idega.idegaweb.commune.childcare.business.ChildCareBusiness");
				if(code.getCode().equals("MBANBOP")){
					return (CaseBusiness) this.getServiceInstance(c);
				}
			}
			catch(ClassNotFoundException e){}
			return this;
		}
		catch (ClassNotFoundException cnfe)
		{
			throw new EJBException(cnfe.getMessage());
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public Case createCase(int userID, String caseCode) throws CreateException, RemoteException
	{
		try
		{
			User user = this.getUserHome().findByPrimaryKey(new Integer(userID));
			CaseCode code = this.getCaseCode(caseCode);
			return createCase(user, code);
		}
		catch (FinderException fe)
		{
			throw new CreateException(fe.getMessage());
		}
	}
	public Case createCase(User user, CaseCode code) throws CreateException, RemoteException
	{
		try
		{
			Case newCase = this.getCaseHome().create();
			newCase.setOwner(user);
			newCase.setCaseCode(code);
			newCase.setCreated(new IWTimestamp().getTimestamp());
			newCase.store();
			return newCase;
		}
		catch (IDOStoreException se)
		{
			throw new CreateException(se.getMessage());
		}
	}
	/**
	 * Creates a new case that is a result of the previous case with the same case code.
	 */
	public Case createSubCase(Case oldCase) throws CreateException, RemoteException
	{
		return createSubCase(oldCase, oldCase.getCaseCode());
	}
	/**
	 * Creates a new case with a specified case code that is a result of the previous case .
	 */
	public Case createSubCase(Case oldCase, CaseCode newCaseCode) throws CreateException, RemoteException
	{
		try
		{
			Case newCase = this.getCaseHome().create();
			newCase.setOwner(oldCase.getOwner());
			newCase.setCaseCode(newCaseCode);
			newCase.setCreated(new IWTimestamp().getTimestamp());
			newCase.store();
			return newCase;
		}
		catch (IDOStoreException se)
		{
			throw new CreateException(se.getMessage());
		}
	}
	/**
	 * Gets all the active Cases for the User
	 */
	public Collection getAllActiveCasesForUser(User user) throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user);
	}
	/**
	 * Gets all the active Cases for the User with a specificed code
	 */
	public Collection getAllActiveCasesForUser(User user, CaseCode code) throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user, code);
	}
	/**
	 * Gets all the active Cases for the User with a specificed code
	 */
	public Collection getAllActiveCasesForUser(User user, String caseCode) throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user, caseCode);
	}
	/**
	 * Gets all the active Cases for the User with a specificed code and status
	 */
	public Collection getAllActiveCasesForUser(User user, CaseCode code, CaseStatus status)
		throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user, code, status);
	}
	/**
	 * Gets all the active Cases for the User with a specificed code and status
	 */
	public Collection getAllActiveCasesForUser(User user, String caseCode, String caseStatus)
		throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user, caseCode, caseStatus);
	}
	/**
	 * Gets all the Cases for the User
	 */
	public Collection getAllCasesForUser(User user) throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user);
	}
	
	/**
	 * Gets all the Cases for the User except the ones with one of the CaseCode in the codes[] array.
	 */
	public Collection getAllCasesForUserExceptCodes(User user,CaseCode[] codes) throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesForUserExceptCodes(user,codes);
	}
	/**
	 * Gets all the Cases for the User with a specificed code
	 */
	public Collection getAllCasesForUser(User user, CaseCode code) throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user, code);
	}
	/**
	 * Gets all the Cases for the User with a specificed code
	 */
	public Collection getAllCasesForUser(User user, String caseCode) throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user, caseCode);
	}
	/**
	 * Gets all the Cases for the User with a specificed code and status
	 */
	public Collection getAllCasesForUser(User user, String caseCode, String caseStatus)
		throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user, caseCode, caseStatus);
	}
	/**
	 * Gets all the Cases for the User with a specificed code and status
	 */
	public Collection getAllCasesForUser(User user, CaseCode code, CaseStatus status)
		throws FinderException, RemoteException
	{
		return this.getCaseHome().findAllCasesByUser(user, code, status);
	}
	public Case getCase(int caseID) throws RemoteException, FinderException
	{
		return getCaseHome().findByPrimaryKey(new Integer(caseID));
	}
	public CaseCode getCaseCode(String caseCode) throws RemoteException, FinderException
	{
		return getCaseCodeHome().findByPrimaryKey(caseCode);
	}
	protected UserHome getUserHome() throws RemoteException
	{
		return (UserHome) com.idega.data.IDOLookup.getHome(User.class);
	}
	protected User getUser(int userID) throws RemoteException, FinderException
	{
		return this.getUserHome().findByPrimaryKey(new Integer(userID));
	}
	protected CaseHome getCaseHome() throws RemoteException
	{
		return (CaseHome) com.idega.data.IDOLookup.getHome(Case.class);
	}
	protected CaseCodeHome getCaseCodeHome() throws RemoteException
	{
		return (CaseCodeHome) com.idega.data.IDOLookup.getHome(CaseCode.class);
	}
	protected CaseLogHome getCaseLogHome() throws RemoteException
	{
		return (CaseLogHome) com.idega.data.IDOLookup.getHome(CaseLog.class);
	}
	protected CaseStatusHome getCaseStatusHome() throws RemoteException
	{
		return (CaseStatusHome) com.idega.data.IDOLookup.getHome(CaseStatus.class);
	}
	public CaseStatus getCaseStatus(String StatusCode) throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(StatusCode);
		}
		catch (FinderException e)
		{
			throw new EJBException("CaseStatus " + StatusCode + " is not installed or does not exist");
		}
	}
	public CaseStatus getCaseStatusOpen() throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(CASE_STATUS_OPEN_KEY);
		}
		catch (FinderException e)
		{
			throw new EJBException("CaseStatus " + CASE_STATUS_OPEN_KEY + " is not installed or does not exist");
		}
	}
	public CaseStatus getCaseStatusGranted() throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_GRANTED_KEY);
		}
		catch (FinderException e)
		{
			throw new EJBException(
				"CaseStatus " + this.CASE_STATUS_GRANTED_KEY + " is not installed or does not exist");
		}
	}
	public CaseStatus getCaseStatusDenied() throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(CASE_STATUS_GRANTED_KEY);
		}
		catch (FinderException e)
		{
			throw new EJBException("CaseStatus " + this.CASE_STATUS_DENIED_KEY + " is not installed or does not exist");
		}
	}
	public CaseStatus getCaseStatusReview() throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_REVIEW_KEY);
		}
		catch (FinderException e)
		{
			throw new EJBException("CaseStatus " + this.CASE_STATUS_REVIEW_KEY + " is not installed or does not exist");
		}
	}
	public CaseStatus getCaseStatusCancelled() throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_CANCELLED_KEY);
		}
		catch (FinderException e)
		{
			throw new EJBException(
				"CaseStatus " + this.CASE_STATUS_CANCELLED_KEY + " is not installed or does not exist");
		}
	}
	public CaseStatus getCaseStatusInactive() throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_INACTIVE_KEY);
		}
		catch (FinderException e)
		{
			throw new EJBException(
				"CaseStatus " + this.CASE_STATUS_INACTIVE_KEY + " is not installed or does not exist");
		}
	}
	public CaseStatus getCaseStatusPreliminary() throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_PRELIMINARY_KEY);
		}
		catch (FinderException e)
		{
			throw new EJBException(
				"CaseStatus " + this.CASE_STATUS_PRELIMINARY_KEY + " is not installed or does not exist");
		}
	}
	public CaseStatus getCaseStatusContract() throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(this.CASE_STATUS_CONTRACT_KEY);
		}
		catch (FinderException e)
		{
			throw new EJBException(
				"CaseStatus " + this.CASE_STATUS_CONTRACT_KEY + " is not installed or does not exist");
		}
	}
	public CaseStatus getCaseStatusReady() throws RemoteException
	{
		return getCaseStatusAndInstallIfNotExists(CASE_STATUS_READY_KEY);
	}

	public CaseStatus getCaseStatusRedeem() throws RemoteException
	{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(CASE_STATUS_REDEEM_KEY);
		}
		catch (FinderException e)
		{
			throw new EJBException(
				"CaseStatus " + CASE_STATUS_REDEEM_KEY + " is not installed or does not exist");
		}
	}
	
	protected CaseStatus getCaseStatusAndInstallIfNotExists(String caseStatusString)throws EJBException,RemoteException{
		try
		{
			return this.getCaseStatusHome().findByPrimaryKey(caseStatusString);
		}
		catch (FinderException fe)
		{
			try{
				CaseStatus status = getCaseStatusHome().create();
				status.setStatus(caseStatusString);
				status.store();
				return status;
			}
			catch(Exception e){
				throw new EJBException(
					"Error creating CaseStatus " + caseStatusString + " is not installed or does not exist. Message: "+e.getMessage());
			}
		}
	}
	
	protected Locale getDefaultLocale()
	{
		//return com.idega.util.LocaleUtil.getLocale("en");
		return getIWApplicationContext().getApplication().getSettings().getDefaultLocale();
	}
	protected String getLocalizedString(String key, String defaultValue)
	{
		return getLocalizedString(key, defaultValue, this.getDefaultLocale());
	}
	protected String getLocalizedString(String key, String defaultValue, Locale locale)
	{
		return getBundle().getResourceBundle(locale).getLocalizedString(key, defaultValue);
	}
	public void changeCaseStatus(int theCaseID, String newCaseStatus, User performer)
		throws FinderException, RemoteException
	{
		Case theCase = this.getCase(theCaseID);
		changeCaseStatus(theCase, newCaseStatus, performer);
	}
	public void changeCaseStatus(Case theCase, String newCaseStatus, User performer) throws RemoteException
	{
		try{
			String oldCaseStatus = theCase.getStatus();
			
			theCase.setStatus(newCaseStatus);
			theCase.setHandler(performer);
			theCase.store();
		
			if ( oldCaseStatus != newCaseStatus ) {
				CaseLog log = getCaseLogHome().create();
				log.setCase(theCase);
				log.setCaseStatusBefore(oldCaseStatus);
				log.setCaseStatusAfter(newCaseStatus);
				log.setPerformer(performer);
				log.store();
			}
		}
		catch(Exception e){
			throw new RemoteException("Error changing case status: "+e.getMessage());	
		}
	}
	public String getLocalizedCaseDescription(Case theCase, Locale locale)throws RemoteException
	{
		return getLocalizedCaseDescription(theCase.getCaseCode(),locale);
	}
	public String getLocalizedCaseDescription(CaseCode theCaseCode, Locale locale)throws RemoteException
	{
		return getLocalizedString("case_code_key." + theCaseCode.toString(), theCaseCode.toString());
	}
	public String getLocalizedCaseStatusDescription(CaseStatus status, Locale locale)
	{
		return getLocalizedString("case_status_key." + status.toString(), status.toString());
	}
	private static final String PROC_CASE_BUNDLE_IDENTIFIER = "com.idega.block.process";
	/**
	 * Can be overrided in subclasses
	 */
	public String getBundleIdentifier()
	{
		return PROC_CASE_BUNDLE_IDENTIFIER;
	}
	protected IWBundle getBundle()
	{
		return getIWApplicationContext().getApplication().getBundle(getBundleIdentifier());
	}
	
	/**
	 * Gets the last modifier of the Case. Returns null if not modification found.
	 **/
	public User getLastModifier(Case aCase){
		try{
			CaseLog log = this.getCaseLogHome().findLastCaseLogForCase(aCase);
			return log.getPerformer();
		}
		catch(Exception e){
			
		}
		return null;
	}
	
}