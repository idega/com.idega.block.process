package com.idega.block.process.data;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.idega.core.data.ICTreeNode;
import com.idega.data.GenericEntity;
import com.idega.data.IDOException;
import com.idega.data.IDOLookup;
import com.idega.data.IDOQuery;
import com.idega.data.IDORuntimeException;
import com.idega.data.IDOStoreException;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
/**
 * Title:        idegaWeb
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      idega software
 * @author <a href="mailto:tryggvi@idega.is">Tryggvi Larusson</a>
 * @version 1.0
 */
public abstract class AbstractCaseBMPBean extends GenericEntity implements Case
{
	private Case _case;
	/**
	 * Returns a unique Key to identify this CaseCode
	 */
	public abstract String getCaseCodeKey();
	/**
	 * Returns a description for the CaseCode associated with this case type
	 */
	public abstract String getCaseCodeDescription();
	public void addGeneralCaseRelation()
	{
		this.addManyToOneRelationship(getIDColumnName(), "Case ID", Case.class);
		this.getAttribute(getIDColumnName()).setAsPrimaryKey(true);
	}
	public Object ejbCreate() throws CreateException
	{
		try
		{
			_case = this.getCaseHome().create();
			_case.setStatus(this.getCaseStatusOpen());
			this.setPrimaryKey(_case.getPrimaryKey());
			return super.ejbCreate();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public void setDefaultValues()
	{
		/*try{
		  System.out.println("AbstractCase : Calling setDefaultValues()");
		  setCode(getCaseCodeKey());
		}
		catch(RemoteException e){
		  throw new EJBException(e.getMessage());
		}*/
	}
	public void insertStartData()
	{
		try
		{
			//CaseHome chome = (CaseHome)IDOLookup.getHome(Case.class);
			CaseCodeHome cchome = (CaseCodeHome) IDOLookup.getHome(CaseCode.class);
			CaseStatusHome cshome = (CaseStatusHome) IDOLookup.getHome(CaseStatus.class);
			CaseCode code = cchome.create();
			code.setCode(getCaseCodeKey());
			code.setDescription(getCaseCodeDescription());
			code.store();
			String[] statusKeys = this.getCaseStatusKeys();
			String[] statusDescs = this.getCaseStatusDescriptions();
			if (statusKeys != null)
			{
				for (int i = 0; i < statusKeys.length; i++)
				{
					String statusKey = null;
					try
					{
						statusKey = statusKeys[i];
						String statusDesc = null;
						try
						{
							statusDesc = statusDescs[i];
						}
						catch (java.lang.NullPointerException ne)
						{}
						catch (java.lang.ArrayIndexOutOfBoundsException arre)
						{}
						CaseStatus status = cshome.create();
						status.setStatus(statusKey);
						if (statusDesc != null)
						{
							status.setDescription(statusDesc);
						}
						status.store();
						code.addAssociatedCaseStatus(status);
					}
					catch (Exception e)
					{
						//e.printStackTrace();
						System.err.println("Error inserting CaseStatus for key: " + statusKey);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Could be ovverrided for extra CaseStatus Keys associated with this CaseCode
	 * Returns an array of Strings.
	 */
	public String[] getCaseStatusKeys()
	{
		return null;
	}
	/**
	 * Could be ovverrided for extra CaseStatus Descriptions associated with this CaseCode
	 * Returns an array of String descriptions
	 * Does not need to return anything (but null), but if it returns a non-null value then the array must be as long as returned by getCaseStatusKeys()
	 */
	public String[] getCaseStatusDescriptions()
	{
		return null;
	}
	protected boolean doInsertInCreate()
	{
		return true;
	}
	public Object ejbFindByPrimaryKey(Object key) throws FinderException
	{
		try
		{
			_case = this.getCaseHome().findByPrimaryKey(key);
			return super.ejbFindByPrimaryKey(key);
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public void store() throws IDOStoreException
	{
		try
		{
			if (this.getCode() == null)
			{
				this.setCode(this.getCaseCodeKey());
			}
			getGeneralCase().store();
			super.store();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public void remove() throws RemoveException
	{
		try
		{
			super.remove();
			getGeneralCase().remove();
		}
		catch (RemoteException rme)
		{
			throw new RemoveException(rme.getMessage());
		}
	}
	protected CaseHome getCaseHome() throws RemoteException
	{
		return (CaseHome) com.idega.data.IDOLookup.getHome(Case.class);
	}
	protected Case getGeneralCase() throws RemoteException
	{
		if (_case == null)
		{
			try
			{
				_case = getCaseHome().findByPrimaryKey(this.getPrimaryKey());
			}
			catch (FinderException fe)
			{
				fe.printStackTrace();
				throw new EJBException(fe.getMessage());
			}
		}
		return _case;
	}
	public Timestamp getCreated() throws java.rmi.RemoteException
	{
		return getGeneralCase().getCreated();
	}
	public void setCaseCode(CaseCode p0) throws java.rmi.RemoteException
	{
		getGeneralCase().setCaseCode(p0);
	}
	public void setParentCase(Case p0) throws java.rmi.RemoteException
	{
		getGeneralCase().setParentCase(p0);
	}
	public void setStatus(String p0) throws java.rmi.RemoteException
	{
		getGeneralCase().setStatus(p0);
	}
	public String getCode() throws java.rmi.RemoteException
	{
		return this.getGeneralCase().getCode();
	}
	public void setCaseStatus(CaseStatus p0) throws java.rmi.RemoteException
	{
		this.getGeneralCase().setCaseStatus(p0);
	}
	public CaseCode getCaseCode() throws java.rmi.RemoteException
	{
		return this.getGeneralCase().getCaseCode();
	}
	public void setOwner(User p0) throws java.rmi.RemoteException
	{
		this.getGeneralCase().setOwner(p0);
	}
	public Case getParentCase() throws java.rmi.RemoteException
	{
		return this.getGeneralCase().getParentCase();
	}
	public void setCode(String p0) throws java.rmi.RemoteException
	{
		this.getGeneralCase().setCode(p0);
	}
	public User getOwner() throws java.rmi.RemoteException
	{
		return this.getGeneralCase().getOwner();
	}
	public CaseStatus getCaseStatus() throws java.rmi.RemoteException
	{
		return this.getGeneralCase().getCaseStatus();
	}
	public String getStatus() throws java.rmi.RemoteException
	{
		return this.getGeneralCase().getStatus();
	}
	public void setCreated(Timestamp p0) throws java.rmi.RemoteException
	{
		this.getGeneralCase().setCreated(p0);
	}
	public Iterator getChildren()
	{
		try
		{
			return this.getGeneralCase().getChildren();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public boolean getAllowsChildren()
	{
		try
		{
			return this.getGeneralCase().getAllowsChildren();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public ICTreeNode getChildAtIndex(int childIndex)
	{
		try
		{
			return this.getGeneralCase().getChildAtIndex(childIndex);
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public int getChildCount()
	{
		try
		{
			return this.getGeneralCase().getChildCount();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public int getIndex(ICTreeNode node)
	{
		try
		{
			return this.getGeneralCase().getIndex(node);
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public ICTreeNode getParentNode()
	{
		try
		{
			return this.getGeneralCase().getParentNode();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public boolean isLeaf()
	{
		try
		{
			return this.getGeneralCase().isLeaf();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public String getNodeName()
	{
		try
		{
			return this.getGeneralCase().getNodeName();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public String getNodeName(Locale locale)
	{
		try
		{
			return this.getGeneralCase().getNodeName(locale);
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public int getNodeID()
	{
		try
		{
			return this.getGeneralCase().getNodeID();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}
	public int getSiblingCount()
	{
		try
		{
			return this.getGeneralCase().getSiblingCount();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}



	public Group getHandler(){
		try
		{
			return this.getGeneralCase().getHandler();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}		
	}
	public int getHandlerId(){
		try
		{
			return this.getGeneralCase().getHandlerId();
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}		
	}
	public void setHandler(Group handler){
		try
		{
			this.getGeneralCase().setHandler(handler);
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}		
	}
	public void setHandler(int handlerGroupID){
		try
		{
			this.getGeneralCase().setHandler(handlerGroupID);
		}
		catch (RemoteException rme)
		{
			throw new EJBException(rme.getMessage());
		}
	}



	/**
	 * Returns the cASE_STATUS_CANCELLED_KEY.
	 * @return String
	 */
	protected String getCaseStatusCancelled() throws RemoteException
	{
		return this.getCaseHome().getCaseStatusCancelled();
	}
	/**
	 * Returns the cASE_STATUS_DENIED_KEY.
	 * @return String
	 */
	protected String getCaseStatusDenied() throws RemoteException
	{
		return this.getCaseHome().getCaseStatusDenied();
	}
	/**
	 * Returns the cASE_STATUS_GRANTED_KEY.
	 * @return String
	 */
	protected String getCaseStatusGranted() throws RemoteException
	{
		return this.getCaseHome().getCaseStatusGranted();
	}
	/**
	 * Returns the cASE_STATUS_INACTIVE_KEY.
	 * @return String
	 */
	public String getCaseStatusInactive()throws RemoteException
	{
		return this.getCaseHome().getCaseStatusInactive();
	}
	/**
	 * Returns the cASE_STATUS_OPEN_KEY.
	 * @return String
	 */
	public String getCaseStatusOpen()throws RemoteException
	{
		return this.getCaseHome().getCaseStatusOpen();
	}
	/**
	 * Returns the cASE_STATUS_REVIEW_KEY.
	 * @return String
	 */
	public String getCaseStatusReview()throws RemoteException
	{
		return getCaseHome().getCaseStatusReview();
	}
	/**
	 * Returns the CASE_STATUS_PRELIMINARY_KEY.
	 * @return String
	 */
	public String getCaseStatusPreliminary()throws RemoteException
	{
		return getCaseHome().getCaseStatusPreliminary();
	}
	/**
	 * Returns the CASE_STATUS_CONTRACT_KEY.
	 * @return String
	 */
	public String getCaseStatusContract()throws RemoteException
	{
		return getCaseHome().getCaseStatusContract();
	}
	/**
	 * Returns the CASE_STATUS_CONTRACT_KEY.
	 * @return String
	 */
	public String getCaseStatusReady()throws RemoteException
	{
		return getCaseHome().getCaseStatusReady();
	}
	
	protected String getSQLGeneralCaseTableName()
	{
		return CaseBMPBean.TABLE_NAME;
	}
	protected String getSQLGeneralCasePKColumnName()
	{
		return CaseBMPBean.PK_COLUMN;
	}
	protected String getSQLGeneralCaseUserColumnName()
	{
		return CaseBMPBean.COLUMN_USER;
	}
	protected String getSQLGeneralCaseCaseCodeColumnName()
	{
		return CaseBMPBean.COLUMN_CASE_CODE;
	}
	protected String getSQLGeneralCaseCaseStatusColumnName()
	{
		return CaseBMPBean.COLUMN_CASE_STATUS;
	}
	protected String getSQLGeneralCaseParentColumnName()
	{
		return CaseBMPBean.COLUMN_PARENT_CASE;
	}
		protected String getSQLGeneralCaseCreatedColumnName()
	{
		return CaseBMPBean.COLUMN_CREATED;
	}
	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode
	 */
	public Collection ejbFindAllCasesByStatus(CaseStatus caseStatus) throws FinderException,RemoteException
	{
		return ejbFindAllCasesByStatus(caseStatus.getStatus());
	}
	
	/**
	 * Finds all cases for the specified user and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByUser(User user) throws FinderException
	{
		IDOQuery sql = idoQueryGetAllCasesByUser(user);
		return (Collection) super.idoFindPKsByQuery(sql);
	}
	/**
	 * Finds all cases for the specified user and the associated caseCode and orders chronologically
	 */
	public IDOQuery idoQueryGetAllCasesByUser(User user)
	{
		String caseCode = this.getCaseCodeKey();
		//StringBuffer sql = new StringBuffer();
		IDOQuery sql = idoQuery();
		sql.append("select * from ");
		
		sql.append(getSQLGeneralCaseTableName());
		sql.append(" g,");
		sql.append(this.getTableName());
		sql.append(" a where g.");
		sql.append(this.getSQLGeneralCasePKColumnName());
		sql.append("=a.");
		sql.append(this.getIDColumnName());
		sql.append(" and g.");
		sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
		sql.append("='");
		sql.append(caseCode);
		sql.append("'");
		sql.append(" and g.");
		sql.append(this.getSQLGeneralCaseUserColumnName());
		sql.append("=");
		sql.append(user.getPrimaryKey().toString());
		sql.append(" order by g.");
		sql.append(this.getSQLGeneralCaseCreatedColumnName());
		
		return sql;
		//return (Collection) super.idoFindPKsBySQL(sql.toString());
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByStatus(String caseStatus) throws FinderException
	{
		return idoFindPKsByQuery(idoQueryGetAllCasesByStatusOrderedByCreation(caseStatus));
	}

	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public IDOQuery idoQueryGetAllCasesByStatus(String caseStatus)
	{
		String caseCode = this.getCaseCodeKey();
		//StringBuffer sql = new StringBuffer();
		IDOQuery sql = idoQuery();
		sql.append("select * from ");
		sql.append(getSQLGeneralCaseTableName());
		sql.append(" g,");
		sql.append(this.getTableName());
		sql.append(" a where g.");
		sql.append(this.getSQLGeneralCasePKColumnName());
		sql.append("=a.");
		sql.append(this.getIDColumnName());
		sql.append(" and g.");
		sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
		sql.append("='");
		sql.append(caseCode);
		sql.append("'");
		sql.append(" and g.");
		sql.append(this.getSQLGeneralCaseCaseStatusColumnName());
		sql.append("='");
		sql.append(caseStatus);
		sql.append("'");
		//sql.append(" order by ");
		//sql.append(this.getSQLGeneralCaseCreatedColumnName());
		
		return sql;
		//return (Collection) super.idoFindPKsBySQL(sql.toString());
	}
	
	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode ,created between given timestamps
	 */
	public IDOQuery idoQueryGetAllCasesByStatus(String caseStatus,IWTimestamp from, IWTimestamp to)
	{
		IDOQuery sql = idoQueryGetAllCasesByStatus(caseStatus);
		to.setHour(23);
		to.setMinute(59);
		to.setSecond(59);
		from.setHour(0);
		from.setMinute(0);
		from.setSecond(0);
		sql.appendAnd();
		sql.append(" g.");
		sql.append(getSQLGeneralCaseCreatedColumnName());
		sql.append(" >= '");
		sql.append(from.toSQLString());
		sql.append("'");
		sql.appendAnd();
		sql.append(" g.");
		sql.append(getSQLGeneralCaseCreatedColumnName());
		sql.append(" <= '");
		sql.append(to.toSQLString());
		sql.append("'");
		return sql;
	}
	
	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public IDOQuery idoQueryGetAllCasesByStatusOrderedByCreation(String caseStatus,IWTimestamp from, IWTimestamp to)
	{
		IDOQuery sql = idoQueryGetAllCasesByStatus(caseStatus,from,to);
		sql.append(" order by ");
		sql.append(this.getSQLGeneralCaseCreatedColumnName());
		return sql;
	}
	
	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public IDOQuery idoQueryGetAllCasesByStatusOrderedByCreation(String caseStatus)
	{
		IDOQuery sql = idoQueryGetAllCasesByStatus(caseStatus);
		sql.append(" order by g.");
		sql.append(this.getSQLGeneralCaseCreatedColumnName());
		return sql;
	}

	/**
	 * Finds all cases for the specified user with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByUserAndStatus(User user, String caseStatus)
		throws FinderException
	{
		return idoFindPKsByQuery(idoQueryGetAllCasesByUserAndStatus(user,caseStatus));
	}

	/**
	 * Finds all cases for the specified user with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public IDOQuery idoQueryGetAllCasesByUserAndStatus(User user, String caseStatus)
	{
		String caseCode = this.getCaseCodeKey();
		//StringBuffer sql = new StringBuffer();
		IDOQuery sql = idoQuery();
		sql.append("select * from ");
		sql.append(getSQLGeneralCaseTableName());
		sql.append(" g,");
		sql.append(this.getTableName());
		sql.append(" a where g.");
		sql.append(this.getSQLGeneralCasePKColumnName());
		sql.append("=a.");
		sql.append(this.getIDColumnName());
		sql.append(" and g.");
		sql.append(this.getSQLGeneralCaseUserColumnName());
		sql.append("=");
		sql.append(user.getPrimaryKey().toString());
		sql.append(" and g.");
		sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
		sql.append("='");
		sql.append(caseCode);
		sql.append("'");
		sql.append(" and g.");
		sql.append(this.getSQLGeneralCaseCaseStatusColumnName());
		sql.append("='");
		sql.append(caseStatus);
		sql.append("'");
		sql.append(" order by g.");
		sql.append(this.getSQLGeneralCaseCreatedColumnName());
		//return (Collection) super.idoFindPKsBySQL(sql.toString());
		return sql;
	}

	/**
	 *Returns all the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	public Collection ejbFindSubCasesUnder(Case theCase) throws FinderException
	{
		return idoFindPKsByQuery(idoQueryGetSubCasesUnder(theCase));
	}

	/**
	 *Returns all the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	public IDOQuery idoQueryGetSubCasesUnder(Case theCase) throws FinderException
	{
		String caseCode = this.getCaseCodeKey();
		//StringBuffer sql = new StringBuffer();
		IDOQuery sql = idoQuery();
		sql.append("select * from ");
		sql.append(getSQLGeneralCaseTableName());
		sql.append(" g,");
		sql.append(this.getTableName());
		sql.append(" a where g.");
		sql.append(this.getSQLGeneralCasePKColumnName());
		sql.append("=a.");
		sql.append(this.getIDColumnName());
		sql.append(" and g.");
		sql.append(this.getSQLGeneralCaseParentColumnName());
		sql.append("=");
		sql.append(theCase.getPrimaryKey().toString());
		sql.append(" and g.");
		sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
		sql.append("='");
		sql.append(caseCode);
		sql.append("'");
		sql.append(" order by g.");
		sql.append(this.getSQLGeneralCaseCreatedColumnName());
		return sql;
		//return (Collection) super.idoFindPKsBySQL(sql.toString());
	}
	
	/**
	 *Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	public int ejbHomeCountSubCasesUnder(Case theCase) throws RemoteException
	{
		try
		{
			IDOQuery sql = idoQueryGetCountSubCasesUnder(theCase);
			sql.append(this.getSQLGeneralCaseCreatedColumnName());
			return super.getNumberOfRecords(sql.toString());
		}
		catch (java.sql.SQLException sqle)
		{
			throw new EJBException(sqle.getMessage());
		}
	}
	
	/**
	 *Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	protected IDOQuery idoQueryGetCountSubCasesUnder(Case theCase) throws RemoteException
	{
			String caseCode = this.getCaseCodeKey();
			//StringBuffer sql = new StringBuffer();
			IDOQuery sql = idoQuery();
			sql.append("select count(*) from ");
			sql.append(getSQLGeneralCaseTableName());
			sql.append(" g,");
			sql.append(this.getTableName());
			sql.append(" a where g.");
			sql.append(this.getSQLGeneralCasePKColumnName());
			sql.append("=a.");
			sql.append(this.getIDColumnName());
			sql.append(" and g.");
			sql.append(this.getSQLGeneralCaseParentColumnName());
			sql.append("=");
			sql.append(theCase.getPrimaryKey().toString());
			sql.append(" and g.");
			sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
			sql.append("='");
			sql.append(caseCode);
			sql.append("'");
			sql.append(" order by g.");
			sql.append(this.getSQLGeneralCaseCreatedColumnName());
			return sql;
			//return super.getNumberOfRecords(sql.toString());
	}
        
        
	/**
	 *Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	protected IDOQuery idoQueryGetCountCasesWithStatus(String caseStatus)
	{
			String caseCode = this.getCaseCodeKey();
			//StringBuffer sql = new StringBuffer();
			IDOQuery sql = idoQuery();
			sql.append("select count(*) from ");
			sql.append(getSQLGeneralCaseTableName());
			sql.append(" g,");
			sql.append(this.getTableName());
			sql.append(" a where g.");
			sql.append(this.getSQLGeneralCasePKColumnName());
			sql.append("=a.");
			sql.append(this.getIDColumnName());
			sql.append(" and g.");
			sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
			sql.append("='");
			sql.append(caseCode);
			sql.append("'");
			sql.append(" and g.");
			sql.append(this.getSQLGeneralCaseCaseStatusColumnName());
			sql.append("='");
			sql.append(caseStatus);
			sql.append("'");
			return sql;
			//return super.getNumberOfRecords(sql.toString());
	}
        
        
	/**
	 *Counts the number of the subcases under the specified theCase and whith the associated CaseCode and orders chronologically
	 */
	public int ejbHomeCountCasesWithStatus(String caseStatus)
	{
		try
		{	
			IDOQuery sql = idoQueryGetCountCasesWithStatus(caseStatus);	
			return super.idoGetNumberOfRecords(sql);
		}
		catch (IDOException sqle)
		{
			throw new EJBException(sqle.getMessage());
		}
	}
	
	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByStatusArray(String caseStatus[]) throws FinderException {

		IDOQuery sql = idoQueryGetAllCasesByStatusArray(caseStatus);
		return (Collection) super.idoFindPKsByQuery(sql);
	}
	
	/**
	 * Finds all cases for all users with the specified caseStatus and the associated caseCode and orders chronologically
	 */
	public Collection ejbFindAllCasesByUserAndStatusArray(User user, String caseStatus[]) throws FinderException {
		IDOQuery sql = idQueryGetAllCasesByUserAndStatusArray(user, caseStatus);
		return (Collection) super.idoFindPKsByQuery(sql);
	}
	
	protected IDOQuery idQueryGetAllCasesByUserAndStatusArray(User user, String caseStatus[]) {
		try{
			String caseCode = this.getCaseCodeKey();
			//StringBuffer sql = new StringBuffer();
			IDOQuery sql = idoQuery();
			sql.append("select * from ");
			sql.append(getSQLGeneralCaseTableName());
			sql.append(" g,");
			sql.append(this.getTableName());
			sql.append(" a where g.");
			sql.append(this.getSQLGeneralCasePKColumnName());
			sql.append("=a.");
			sql.append(this.getIDColumnName());
			sql.append(" and g.");
			sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
			sql.append("='");
			sql.append(caseCode);
			sql.append("'");
			sql.append(" and g.");
			sql.append(this.getSQLGeneralCaseCaseStatusColumnName());
			sql.append(" in (");
			int length = caseStatus.length;
			for (int i = 0; i < length; i++) {
				sql.append("'");
				sql.append(caseStatus[i]);
				if (i != (length - 1))
					sql.append("', ");
				else
					sql.append("'");
			}
			sql.append(")");
			sql.append(" and g.");
			sql.append(this.getSQLGeneralCaseUserColumnName());
			sql.append("=");
			sql.append(user.getPrimaryKey().toString());
			sql.append(" order by g.");
			sql.append(this.getSQLGeneralCaseCreatedColumnName());

			debug("AbstractCase.idoQueryGetAllCasesByUserAndStatusArray(): sql = " + sql.toString());

			return sql;
		}
		catch(Exception e){
			throw new IDORuntimeException(e,this);
		}
	}
	
	protected IDOQuery idoQueryGetAllCasesByStatusArray(String caseStatus[])
	{
		try{
			String caseCode = this.getCaseCodeKey();
			//StringBuffer sql = new StringBuffer();
			IDOQuery sql = idoQuery();
			sql.append("select * from ");
			sql.append(getSQLGeneralCaseTableName());
			sql.append(" g,");
			sql.append(this.getTableName());
			sql.append(" a where g.");
			sql.append(this.getSQLGeneralCasePKColumnName());
			sql.append("=a.");
			sql.append(this.getIDColumnName());
			sql.append(" and g.");
			sql.append(this.getSQLGeneralCaseCaseCodeColumnName());
			sql.append("='");
			sql.append(caseCode);
			sql.append("'");
			sql.append(" and g.");
			sql.append(this.getSQLGeneralCaseCaseStatusColumnName());
			sql.append(" in (");
			int length = caseStatus.length;
			for (int i = 0; i < length; i++) {
				sql.append("'");
				sql.append(caseStatus[i]);
				if (i != (length - 1))
					sql.append("', ");
				else
					sql.append("'");
			}
			sql.append(")");
			sql.append(" order by g.");
			sql.append(this.getSQLGeneralCaseCreatedColumnName());

			debug("AbstractCase.idoQueryGetAllCasesByStatusArray(): sql = " + sql.toString());

			return sql;
		}
		catch(Exception e){
			throw new IDORuntimeException(e,this);
		}
	}
	
}