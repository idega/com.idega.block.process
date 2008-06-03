package com.idega.block.process.presentation.beans;

import java.rmi.RemoteException;

import javax.faces.component.UIComponent;

import com.idega.presentation.IWContext;

public interface GeneralCaseManagerViewBuilder {

	public static final String SPRING_BEAN_IDENTIFIER = "GeneralCaseManagerViewBuilder";
	
	public UIComponent getCaseManagerView(IWContext iwc) throws RemoteException;
	
}
