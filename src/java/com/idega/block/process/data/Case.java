/*
 * $Id: Case.java,v 1.23 2009/05/25 13:36:31 valdas Exp $
 * Created on Apr 11, 2006
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
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOEntity;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.data.MetaDataCapable;
import com.idega.data.UniqueIDCapable;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;


/**
 * <p>
 * TODO laddi Describe Type Case
 * </p>
 *  Last modified: $Date: 2009/05/25 13:36:31 $ by $Author: valdas $
 *
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.23 $
 */
public interface Case extends IDOEntity, ICTreeNode<Case>, UniqueIDCapable, MetaDataCapable {

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
	 * @see com.idega.block.process.data.CaseBMPBean#setCreator
	 */
	public void setCreator(User creator);

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
	 * @see com.idega.block.process.data.CaseBMPBean#getCreator
	 */
	public User getCreator();

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
	@Override
	public Case getParentNode();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getChildAtIndex
	 */
	@Override
	public Case getChildAtIndex(int childIndex);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getChildCount
	 */
	@Override
	public int getChildCount();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getChildrenIterator
	 */
	@Override
	public Iterator<Case> getChildrenIterator();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getChildren
	 */
	@Override
	public Collection<Case> getChildren();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getSiblingCount
	 */
	@Override
	public int getSiblingCount();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getNodeID
	 */
	@Override
	public int getNodeID();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getNodeName
	 */
	@Override
	public String getNodeName();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getNodeName
	 */
	@Override
	public String getNodeName(Locale locale);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getNodeName
	 */
	@Override
	public String getNodeName(Locale locale, IWApplicationContext iwac);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#isLeaf
	 */
	@Override
	public boolean isLeaf();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getIndex
	 */
	@Override
	public int getIndex(Case node);

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getAllowsChildren
	 */
	@Override
	public boolean getAllowsChildren();

	/**
	 * @see com.idega.block.process.data.CaseBMPBean#getUrl
	 */
	public String getUrl();

	public String getCaseManagerType();

	public void setCaseManagerType(String type);

	public String getCaseIdentifier();

	public void setCaseIdentifier(String caseIdentifier);

	/**
	 *
	 * @param subscriber to add, not <code>null</code>
	 * @return <code>true</code> if successfully appended, <code>false</code>
	 * otherwise;
	 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
	 */
	public boolean addSubscriber(User subscriber) throws IDOAddRelationshipException;

	/**
	 *
	 * @param subscribers to add, not <code>null</code>;
	 * @return <code>true</code> if successfully appended, <code>false</code>
	 * otherwise;
	 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
	 */
	public boolean addSubscribers(Collection<User> subscribers);

	public Collection<User> getSubscribers();
	public boolean removeSubscriber(User subscriber) throws IDORemoveRelationshipException;

	/**
	 *
	 * <p>Removes all subscribers from this {@link Case}.</p>
	 * @return <code>true</code> on success, <code>false</code> otherwise;
	 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
	 */
	public boolean removeSubscribers();

	public Boolean isRead();

	public void setRead(Boolean read);

	public boolean addVote(User voter) throws IDOAddRelationshipException;
	public boolean removeVote(User voter) throws IDORemoveRelationshipException;
	public Collection<User> getVoters();

	public boolean isClosed();

	public default String getOwnerName() {
		return getOwner() == null ? null : getOwner().getName();
	}
}