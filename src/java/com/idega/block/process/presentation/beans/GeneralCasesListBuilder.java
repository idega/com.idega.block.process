package com.idega.block.process.presentation.beans;

import java.util.Collection;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.idega.presentation.IWContext;
import com.idega.presentation.paging.PagedDataCollection;

public interface GeneralCasesListBuilder {

	public static final String SPRING_BEAN_IDENTIFIER = "GeneralCasesListBuilder";
	public static final String MAIN_CASES_LIST_CONTAINER_STYLE = "mainCasesListContainerStyleClass";
	public static final String USER_CASES_SEARCH_QUERY_BEAN_ATTRIBUTE = "userCasesSearchQueryBeanAttribute";
	
	public abstract UIComponent getCasesList(IWContext iwc, PagedDataCollection<CasePresentation> cases, String type, boolean showCheckBoxes, boolean usePDFDownloadColumn,
			boolean allowPDFSigning, boolean showStatistics, boolean hideEmptySection, int pageSize, int page, String instanceId, String componentId);
		
	@SuppressWarnings("unchecked")
	public abstract UIComponent getUserCasesList(IWContext iwc, PagedDataCollection<CasePresentation> cases, Map pages, String type, boolean addCredentialsToExernalUrls,
			boolean usePDFDownloadColumn, boolean allowPDFSigning, boolean showStatistics, boolean hideEmptySection, int pageSize, int page, String instanceId, String componentId);

	public abstract UIComponent getCaseManagerView(IWContext iwc, Integer caseId, String type);
	
	public abstract UIComponent getCasesStatistics(IWContext iwc, Collection<CasePresentation> cases);
	
	public abstract String getEmailAddressMailtoFormattedWithSubject(String subject);
	
	public abstract String getSendEmailImage();
	
	public abstract String getTitleSendEmail();
}