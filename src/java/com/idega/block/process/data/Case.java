/*
 * $Id: Case.java,v 1.15 2004/12/08 18:09:46 laddi Exp $
 * Created on 8.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
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
import com.idega.idegaweb.IWApplicationContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;


/**
 * Last modified: $Date: 2004/12/08 18:09:46 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.15 $
 */
public interface Case extends IDOEntity, ICTreeNode {

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
