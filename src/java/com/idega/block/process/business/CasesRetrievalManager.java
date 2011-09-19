package com.idega.block.process.business;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.idega.block.process.data.Case;
import com.idega.block.process.presentation.beans.CasePresentation;
import com.idega.block.process.presentation.beans.CasesSearchCriteriaBean;
import com.idega.presentation.IWContext;
import com.idega.presentation.paging.PagedDataCollection;
import com.idega.user.data.Group;
import com.idega.user.data.User;

/**
 * This class represents various cases retrieval implementation, depending on the modules present in
 * the system. Don't use this for anything else
 * 
 * @author <a href="civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.3 $ Last modified: $Date: 2009/06/15 10:00:12 $ by $Author: valdas $
 */
public interface CasesRetrievalManager {
	
	public static final String CASE_LIST_TYPE_MY = "MyCases",
								CASE_LIST_TYPE_USER = "UserCases",
								CASE_LIST_TYPE_OPEN = "OpenCases",
								CASE_LIST_TYPE_CLOSED = "ClosedCases",
								CASE_LIST_TYPE_PUBLIC = "PublicCases";
	
	public static final String COMMENTS_PERSISTENCE_MANAGER_IDENTIFIER = "commentsPersistenceManagerIdentifier";
	
	public abstract String getBeanIdentifier();
	
	public abstract String getType();
	
	public abstract Long getProcessInstanceId(Case theCase);
	
	public abstract Long getProcessInstanceIdByCaseId(Object id);
	
	public abstract Long getProcessDefinitionId(Case theCase);
	
	public abstract String getProcessDefinitionName(Case theCase);
	
	public abstract String getProcessIdentifier(Case theCase);
	
	public abstract UIComponent getView(IWContext iwc, Integer caseId, String type, String caseManagerType);
	
	public abstract PagedDataCollection<CasePresentation> getCases(User user,
	        String type, Locale locale, List<String> caseCodes, List<String> statusesToHide,
	        List<String> statusesToShow, int startIndex, int count, boolean onlySubscribedCases, boolean showAllCases);
	
	public abstract List<Integer> getCaseIds(User user, String type, List<String> caseCodes,
	        List<String> statusesToHide, List<String> statusesToShow, boolean onlySubscribedCases, boolean showAllCases) throws Exception;
	
	public abstract PagedDataCollection<CasePresentation> getCasesByIds(List<Integer> ids, Locale locale);
	
	public CasePresentation getCaseByIdLazily(Integer caseId);
	
	public abstract PagedDataCollection<CasePresentation> getCasesByEntities(Collection<Case> cases, Locale locale);
	
	public abstract Map<Long, String> getAllCaseProcessDefinitionsWithName();
	
	public abstract List<Long> getAllCaseProcessDefinitions();
	
	public abstract String getProcessName(String processName, Locale locale);
	
	public abstract Long getLatestProcessDefinitionIdByProcessName(String name);
	
	public abstract PagedDataCollection<CasePresentation> getClosedCases(Collection<Group> groups);
	
	public abstract PagedDataCollection<CasePresentation> getMyCases(User user);
	
	public abstract Long getTaskInstanceIdForTask(Case theCase, String taskName);
	
	public abstract List<Long> getCasesIdsByProcessDefinitionName(String processDefinitionName);
	
	public abstract String resolveCaseId(IWContext iwc);
	
	/**
	 * Returns owner of a case
	 * @param entityId - case's or process instance's id
	 * @return {@link User}
	 */
	public abstract User getCaseOwner(Object entityId);
	
	public abstract Collection<CasePresentation> getReLoadedCases(CasesSearchCriteriaBean criterias) throws Exception;
}