package com.idega.block.process.presentation.beans;

import java.util.Collection;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.idega.block.process.data.Case;
import com.idega.presentation.IWContext;

public interface GeneralCasesListBuilder {

	public static final String SPRING_BEAN_IDENTIFIER = "GeneralCasesListBuilder";
	public static final String MAIN_CASES_LIST_CONTAINER_STYLE = "mainCasesListContainerStyleClass";
	
	@SuppressWarnings("unchecked")
	public abstract UIComponent getCasesList(IWContext iwc, Collection cases, String casesType, boolean showCheckBoxes);
	
	@SuppressWarnings("unchecked")
	public abstract UIComponent getUserCasesList(IWContext iwc, Collection<Case> cases, Map pages, String casesType, boolean addCredentialsToExernalUrls);

	public abstract UIComponent getCaseManagerView(IWContext iwc, Integer caseId);
}