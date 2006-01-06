/*
 * $Id: Case.java,v 1.16 2006/01/06 01:06:42 gimmi Exp $
 * Created on Jan 6, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import com.idega.core.data.ICTreeNode;
import com.idega.data.IDOEntity;
import com.idega.data.UniqueIDCapable;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;


/**
 * <p>
 * TODO gimmi Describe Type Case
 * </p>
 *  Last modified: $Date: 2006/01/06 01:06:42 $ by $Author: gimmi $
 * 
 * @author <a href="mailto:gimmi@idega.com">gimmi</a>
 * @version $Revision: 1.16 $
 */
public interface Case extends IDOEntity, ICTreeNode, UniqueIDCapable {

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setCode
	 */
	public void setCode(String caseCode);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getCode
	 */
	public String getCode();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setCaseCode
	 */
	public void setCaseCode(CaseCode caseCode);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getCaseCode
	 */
	public CaseCode getCaseCode();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setCaseStatus
	 */
	public void setCaseStatus(CaseStatus status);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getCaseStatus
	 */
	public CaseStatus getCaseStatus();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setStatus
	 */
	public void setStatus(String status);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getStatus
	 */
	public String getStatus();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setCreated
	 */
	public void setCreated(Timestamp statusChanged);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getCreated
	 */
	public Timestamp getCreated();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setParentCase
	 */
	public void setParentCase(Case theCase);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getParentCase
	 */
	public Case getParentCase();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setOwner
	 */
	public void setOwner(User owner);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getHandler
	 */
	public Group getHandler();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getHandlerId
	 */
	public int getHandlerId();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setHandler
	 */
	public void setHandler(Group handler);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setHandler
	 */
	public void setHandler(int handlerGroupID);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getOwner
	 */
	public User getOwner();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setExternalId
	 */
	public void setExternalId(String externalId);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getExternalId
	 */
	public String getExternalId();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setCaseNumber
	 */
	public void setCaseNumber(String caseNumber);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getCaseNumber
	 */
	public String getCaseNumber();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setExternalHandler
	 */
	public void setExternalHandler(User user);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getExternalHandler
	 */
	public User getExternalHandler();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getSubject
	 */
	public String getSubject();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setSubject
	 */
	public void setSubject(String subject);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getBody
	 */
	public String getBody();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#setBody
	 */
	public void setBody(String body);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getParentNode
	 */
	public ICTreeNode getParentNode();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getChildAtIndex
	 */
	public ICTreeNode getChildAtIndex(int childIndex);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getChildCount
	 */
	public int getChildCount();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getChildrenIterator
	 */
	public Iterator getChildrenIterator();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getChildren
	 */
	public Collection getChildren();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getSiblingCount
	 */
	public int getSiblingCount();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getNodeID
	 */
	public int getNodeID();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getNodeName
	 */
	public String getNodeName();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getNodeName
	 */
	public String getNodeName(Locale locale);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getNodeName
	 */
	public String getNodeName(Locale locale, IWApplicationContext iwac);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#isLeaf
	 */
	public boolean isLeaf();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getIndex
	 */
	public int getIndex(ICTreeNode node);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getAllowsChildren
	 */
	public boolean getAllowsChildren();
}
