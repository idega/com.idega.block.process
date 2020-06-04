package com.idega.block.process.message.data;

import java.util.Collection;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOException;
import com.idega.data.IDOHome;
import com.idega.idegaweb.IWUserContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;

public interface MessageHome extends IDOHome {

	public Message create() throws CreateException;

	public Message findByPrimaryKey(Object pk) throws FinderException;

	public Collection<? extends Message> findMessages(User user) throws FinderException;

	public Collection<? extends Message> findMessages(User user, String[] status) throws FinderException;

	public Collection<? extends Message> findMessages(Group group, String[] status) throws FinderException;

	public Collection<? extends Message> findMessages(User user, String[] status, int numberOfEntries, int startingEntry) throws FinderException;

	public Collection<? extends Message> findMessages(
			IWUserContext iwuc,
			com.idega.user.data.bean.User user,
			String[] status,
			Boolean onlyForParentCaseCreator,
			Set<String> parentCasesNotHavingCaseCode,
			int numberOfEntries,
			int startingEntry
	) throws FinderException;

	public int getNumberOfMessages(
			IWUserContext iwuc,
			com.idega.user.data.bean.User user,
			String[] status,
			Boolean onlyForParentCaseCreator,
			Set<String> parentCasesNotHavingCaseCode
	) throws FinderException, IDOException;

	public Collection<? extends Message> findMessages(Group group, String[] status, int numberOfEntries, int startingEntry) throws FinderException;

	public Collection<? extends Message> findMessages(User user, Collection<Group> groups, String[] status, int numberOfEntries, int startingEntry)
		throws FinderException;

	public int getNumberOfMessages(User user, String[] status) throws IDOException;

	public int getNumberOfMessages(User user, Collection<Group> groups, String[] status) throws IDOException;

	public Collection<Message> findMessages(User user, String caseId) throws FinderException;

	public Collection<Message> findMessagesForUser(User user, String status,Boolean read) throws FinderException;

}