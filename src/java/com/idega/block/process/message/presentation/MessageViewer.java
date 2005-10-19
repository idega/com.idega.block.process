/*
 * $Id$
 * Created on Oct 18, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.message.presentation;

import com.idega.block.process.message.data.Message;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Text;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
import com.idega.util.text.Name;
import com.idega.util.text.TextSoap;


public class MessageViewer extends MessageBlock {

	protected void present(IWContext iwc) throws Exception {
		if (iwc.isParameterSet(PARAMETER_MESSAGE_PK)) {
			Layer layer = new Layer(Layer.DIV);
			layer.setID("messageViewer");
			layer.setStyleClass("caseElement");
			
			Layer headerLayer = new Layer(Layer.DIV);
			headerLayer.setStyleClass("caseHeader");
			layer.add(headerLayer);
			
			Layer headingLayer = new Layer(Layer.DIV);
			headingLayer.setStyleClass("caseHeading");
			headingLayer.add(new Text(getResourceBundle().getLocalizedString("message.viewer", "Message")));
			headerLayer.add(headingLayer);
			
			Layer messageLayer = new Layer(Layer.DIV);
			messageLayer.setStyleClass("messageDiv");
			layer.add(messageLayer);
			
			Message message = getMessageBusiness().getMessage(iwc.getParameter(PARAMETER_MESSAGE_PK));
			User sender = message.getSender();
			IWTimestamp created = new IWTimestamp(message.getCreated());
			
			getMessageBusiness().markMessageAsRead(message);

			Layer messageItem = new Layer(Layer.DIV);
			messageItem.setID("messageSubject");
			messageItem.setStyleClass("messageItem");
			messageItem.add(new Heading1(getResourceBundle().getLocalizedString("subject", "Subject")));
			messageItem.add(new Text(message.getSubject() != null ? message.getSubject(): getResourceBundle().getLocalizedString("message.no_subject", "No subject")));
			messageLayer.add(messageItem);
			
			messageItem = new Layer(Layer.DIV);
			messageItem.setID("messageSender");
			messageItem.setStyleClass("messageItem");
			messageItem.add(new Heading1(getResourceBundle().getLocalizedString("sender", "Sender")));
			if (sender != null) {
				Name name = new Name(sender.getFirstName(), sender.getMiddleName(), sender.getLastName());
				messageItem.add(new Text(name.getName(iwc.getCurrentLocale())));
			}
			else {
				messageItem.add(new Text("-"));
			}
			messageLayer.add(messageItem);
			
			messageItem = new Layer(Layer.DIV);
			messageItem.setID("messageDate");
			messageItem.setStyleClass("messageItem");
			messageItem.add(new Heading1(getResourceBundle().getLocalizedString("date", "Date")));
			messageItem.add(new Text(created.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.MEDIUM, IWTimestamp.MEDIUM)));
			messageLayer.add(messageItem);
			
			messageItem = new Layer(Layer.DIV);
			messageItem.setID("messageBody");
			messageItem.setStyleClass("messageItem");
			messageItem.add(new Heading1(getResourceBundle().getLocalizedString("body", "Body")));
			messageItem.add(new Text(message.getBody() != null ? TextSoap.formatText(message.getBody()): ""));
			messageLayer.add(messageItem);
			
			add(layer);
		}
		else {
			add(new Text(getResourceBundle().getLocalizedString("message.no_message_found", "No message found.")));
		}
	}
}