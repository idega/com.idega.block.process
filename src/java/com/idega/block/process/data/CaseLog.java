/*
 * $Id: CaseLog.java 1.1 5.12.2004 laddi Exp $
 * Created on 5.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.data;

import java.sql.Timestamp;


import com.idega.data.IDOEntity;
import com.idega.user.data.User;


/**
 * Last modified: $Date: 2004/06/28 09:09:50 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.1 $
 */
public interface CaseLog extends IDOEntity {

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getCase
	 */
	public Case getCase();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getCaseId
	 */
	public int getCaseId();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setCase
	 */
	public void setCase(Case aCase);

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setCase
	 */
	public void setCase(int aCaseID);

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getCaseStatusBefore
	 */
	public CaseStatus getCaseStatusBefore();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getStatusBefore
	 */
	public String getStatusBefore();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setCaseStatusBefore
	 */
	public void setCaseStatusBefore(CaseStatus aCaseStatus);

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setCaseStatusBefore
	 */
	public void setCaseStatusBefore(String caseStatus);

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getCaseStatusAfter
	 */
	public CaseStatus getCaseStatusAfter();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getStatusAfter
	 */
	public String getStatusAfter();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setCaseStatusAfter
	 */
	public void setCaseStatusAfter(CaseStatus aCaseStatus);

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setCaseStatusAfter
	 */
	public void setCaseStatusAfter(String caseStatus);

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getPerformer
	 */
	public User getPerformer();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getPerformerId
	 */
	public int getPerformerId();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setPerformer
	 */
	public void setPerformer(User performer);

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setPerformer
	 */
	public void setPerformer(int performerUserId);

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getTimeStamp
	 */
	public Timestamp getTimeStamp();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setTimeStamp
	 */
	public void setTimeStamp(Timestamp stamp);

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#getComment
	 */
	public String getComment();

	/**
	 * @see com.idega.block.process.data.CaseLogBMPBean#setComment
	 */
	public void setComment(String comment);

}
