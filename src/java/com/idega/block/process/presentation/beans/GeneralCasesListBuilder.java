package com.idega.block.process.presentation.beans;

import java.util.Collection;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.idega.block.process.data.Case;
import com.idega.presentation.IWContext;

public interface GeneralCasesListBuilder {

	public static final String SPRING_BEAN_IDENTIFIER = "GeneralCasesListBuilder";
	public static final String MAIN_CASES_LIST_CONTAINER_STYLE = "mainCasesListContainerStyleClass";
	public static final String USER_CASES_SEARCH_QUERY_BEAN_ATTRIBUTE = "userCasesSearchQueryBeanAttribute";
	
	@SuppressWarnings("unchecked")
	public abstract UIComponent getCasesList(IWContext iwc, Collection cases, String casesType, boolean showCheckBoxes, boolean usePDFDownloadColumn,
			boolean allowPDFSigning, boolean showStatistics, boolean hideEmptySection);
	
	@SuppressWarnings("unchecked")
	public abstract UIComponent getUserCasesList(IWContext iwc, Collection<Case> cases, Map pages, String casesType, boolean addCredentialsToExernalUrls,
			boolean usePDFDownloadColumn, boolean allowPDFSigning, boolean showStatistics, boolean hideEmptySection);

	public abstract UIComponent getCaseManagerView(IWContext iwc, Integer caseId, String caseProcessorType);
	
	public abstract UIComponent getCasesStatistics(IWContext iwc, Collection<Case> cases);
	
	public abstract String getEmailAddressMailtoFormattedWithSubject(String subject);
	
	public abstract String getSendEmailImage();
	
	public abstract String getTitleSendEmail();
}