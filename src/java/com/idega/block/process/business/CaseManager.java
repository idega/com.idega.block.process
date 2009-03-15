package com.idega.block.process.business;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.idega.block.process.data.Case;
import com.idega.block.process.presentation.beans.CasePresentation;
import com.idega.presentation.IWContext;
import com.idega.presentation.paging.PagedDataCollection;
import com.idega.user.data.User;

/**
 * 
 * @author <a href="civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.16 $
 *
 * Last modified: $Date: 2009/03/15 16:07:12 $ by $Author: valdas $
 *
 */
public interface CaseManager {

	public static final String CASE_LIST_TYPE_MY = "MyCases";
	public static final String CASE_LIST_TYPE_USER = "UserCases";
	public static final String CASE_LIST_TYPE_OPEN = "OpenCases";
	public static final String CASE_LIST_TYPE_CLOSED = "ClosedCases";
	
	public abstract String getBeanIdentifier();
	
	public abstract String getType();
	
	public abstract Long getProcessInstanceId(Case theCase);
	
	public abstract Long getProcessInstanceIdByCaseId(Object id);
	
	public abstract Long getProcessDefinitionId(Case theCase);
	
	public abstract String getProcessDefinitionName(Case theCase);
	
	public abstract String getProcessIdentifier(Case theCase);
	
	public abstract UIComponent getView(IWContext iwc, Integer caseId, String type, String caseManagerType);
	
	public abstract PagedDataCollection<CasePresentation> getCases(User user, String type, Locale locale, List<String> statusesToHide, List<String> statusesToShow,
			int startIndex, int count);
	
	public abstract List<Integer> getCaseIds(User user, String type, List<String> statusesToHide, List<String> statusesToShow);
	
	public abstract PagedDataCollection<CasePresentation> getCasesByIds(List<Integer> ids, Locale locale);
		
	public abstract Map<Long, String> getAllCaseProcessDefinitionsWithName();
	
	public abstract List<Long> getAllCaseProcessDefinitions();
	
	public abstract String getProcessName(String processName, Locale locale);
		
	public abstract Long getLatestProcessDefinitionIdByProcessName(String name);

	@SuppressWarnings("unchecked")
	public abstract PagedDataCollection<CasePresentation> getClosedCases(Collection groups);

	public abstract PagedDataCollection<CasePresentation> getMyCases(User user);
	
	public abstract List<String> getCaseStringVariablesValuesByVariables(Case theCase, List<String> variablesNames);
	
	public abstract Long getTaskInstanceIdForTask(Case theCase, String taskName);
	
	public abstract boolean setCaseVariable(Case thCase, String variableName, String variableValue);
	
	public abstract List<Long> getCasesIdsByProcessDefinitionName(String processDefinitionName);
}