/*
 * $Id$
 * Created on Oct 24, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.presentation;

import java.util.Collection;
import java.util.Iterator;
import com.idega.block.process.message.data.Message;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.ListItem;
import com.idega.presentation.text.Lists;
import com.idega.presentation.text.Text;


public class UserMessagesList extends UserMessages {

	private int iMaxNumberOfLetters;

	protected void present(IWContext iwc) throws Exception {
		if (getMessageType() == null) {
			add(new Text("No code set..."));
			return;
		}
		if (!iwc.isLoggedOn()) {
			add(new Text("No user logged on..."));
			return;
		}
		if (getMaxNumberOfEntries() == -1) {
			setMaximumNumberOfEntries(5);
		}
		
		Lists list = new Lists();
		list.setStyleClass("userMessagesList");
		
		Collection cases = getMessages(iwc, 0, getMaxNumberOfEntries());
		Iterator iter = cases.iterator();
		while (iter.hasNext()) {
			Message message = (Message) iter.next();
			String subject = message.getSubject() != null ? message.getSubject() : getResourceBundle().getLocalizedString("message.no_subject", "No subject");
			if (iMaxNumberOfLetters > 0) {
				if (subject.length() > iMaxNumberOfLetters) {
					subject = subject.substring(0, iMaxNumberOfLetters) + "...";
				}
			}

			Link link = new Link(new Text(subject));
			link.addParameter(PARAMETER_MESSAGE_PK, message.getPrimaryKey().toString());
			if (getViewerPage() != null) {
				link.setPage(getViewerPage());
			}
			else {
				link.setWindowToOpen(MessageWindow.class);
			}
			
			ListItem item = new ListItem();
			item.add(link);
			if (!getMessageBusiness().isMessageRead(message)) {
				item.setStyleClass("newMessage");
			}
			list.add(item);
		}
		
		add(list);
	}
	
	public void setMaximumNumberOfLetters(int maxNumberOfLetters) {
		iMaxNumberOfLetters = maxNumberOfLetters;
	}
}