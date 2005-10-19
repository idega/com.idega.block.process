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

import com.idega.block.process.business.CaseConstants;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.ui.CloseButton;
import com.idega.presentation.ui.Window;


public class MessageWindow extends Window {

	public MessageWindow() {
		this.setWidth(400);
		this.setHeight(400);
		this.setScrollbar(true);
		this.setResizable(false);
	}

	/**
	 * @see com.idega.presentation.PresentationObject#main(IWContext)
	 */
	public void main(IWContext iwc) throws Exception {
		getParentPage().setStyleClass("messageViewerWindow");
		IWResourceBundle iwrb = getResourceBundle(iwc);

		add(new MessageViewer());
		
		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		
		CloseButton button = new CloseButton(iwrb.getLocalizedString("close", "Close"));
		button.setStyleClass("button");
		buttonLayer.add(button);
		
		add(buttonLayer);
		setParentToReload();
	}
	
	public String getBundleIdentifier() {
		return CaseConstants.IW_BUNDLE_IDENTIFIER;
	}
}