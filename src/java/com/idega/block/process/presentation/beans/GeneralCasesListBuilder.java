package com.idega.block.process.presentation.beans;

import java.util.Collection;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.idega.block.process.data.Case;
import com.idega.presentation.IWContext;

public interface GeneralCasesListBuilder {

	public static final String SPRING_BEAN_IDENTIFIER = "GeneralCasesListBuilder";
	
	@SuppressWarnings("unchecked")
	public UIComponent getCasesList(IWContext iwc, Collection cases, String casesType, boolean showCheckBoxes);
	
	@SuppressWarnings("unchecked")
	public UIComponent getUserCasesList(IWContext iwc, Collection<Case> cases, Map pages, String casesType, boolean addCredentialsToExernalUrls);
	
}
