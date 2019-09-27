package com.idega.block.process.business;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

	public static final String	CASE_LIST_TYPE_ALL = "AllCases",
								CASE_LIST_TYPE_MY = "MyCases",
								CASE_LIST_TYPE_USER = "UserCases",
								CASE_LIST_TYPE_OPEN = "OpenCases",
								CASE_LIST_TYPE_CLOSED = "ClosedCases",
								CASE_LIST_TYPE_PUBLIC = "PublicCases",
								CASE_LIST_TYPE_HANDLER = "HandlerCases",
								CASE_LIST_TYPE_HANDLER_BPM2 = "HandlerCasesBPM2",
								CASE_LIST_TYPE_SUBSCRIBER = "SubscriberCases",
								CASE_LIST_TYPE_SELECTED_USERS = "SelectedUsers",

								COMMENTS_PERSISTENCE_MANAGER_IDENTIFIER = "commentsPersistenceManagerIdentifier",
								BEAN_IDENTIFIER = "defaultCaseHandler",
								QUALIFIER = BEAN_IDENTIFIER + "Qualifier";

	public abstract String getBeanIdentifier();

	public abstract String getType();

	public abstract Long getProcessInstanceId(Case theCase);

	public abstract Long getProcessInstanceIdByCaseId(Object id);

	public abstract Long getProcessDefinitionId(Case theCase);

	public abstract String getProcessDefinitionName(Case theCase);

	public abstract String getProcessIdentifier(Case theCase);

	public abstract UIComponent getView(
			IWContext iwc,
			Integer caseId,
			String type,
			String caseManagerType);

	public abstract PagedDataCollection<CasePresentation> getCases(
			User user,
			String type,
			Locale locale,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			int startIndex,
			int count,
			boolean onlySubscribedCases,
			boolean showAllCases);

	public abstract PagedDataCollection<CasePresentation> getCases(
			User user,
			String type,
			Locale locale,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			int startIndex, int count,
			boolean onlySubscribedCases,
			boolean showAllCases,
			List<Long> procInstIds,
			Set<String> roles,
			boolean searchQuery);

	public abstract List<Integer> getCaseIds(
			IWContext iwc,
			User user,
			String type,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			boolean onlySubscribedCases,
			boolean showAllCases,
			Integer page,
			Integer pageSize
	) throws Exception;

	public abstract List<Integer> getCaseIds(
			IWContext iwc,
			User user,
			String type,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			boolean onlySubscribedCases,
			boolean showAllCases,
			Integer page,
			Integer pageSize,
			Boolean handlerAssignedCases,
			List<Integer> exceptOwnersIds,
			List<String> exceptCaseCodes,
			List<Integer> userIdsToUseInSearch
	) throws Exception;

	public abstract Long getCountedCaseIds(
			IWContext iwc,
			User user,
			String type,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			boolean onlySubscribedCases,
			boolean showAllCases,
			List<String> exceptCaseCodes,
			List<Integer> userIdsToUseInSearch
	) throws Exception;

	public abstract List<Integer> getCaseIds(
			User user,
			String type,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			boolean onlySubscribedCases,
			boolean showAllCases,
			List<Long> procInstIds,
			Set<String> roles) throws Exception;

	/**
	 *
	 * @param user to get {@link Case}s for, not <code>null</code>;
	 * @param type is one of:
	 * <li>{@link CasesRetrievalManager#CASE_LIST_TYPE_CLOSED}</li>
	 * <li>{@link CasesRetrievalManager#CASE_LIST_TYPE_HANDLER}</li>
	 * <li>{@link CasesRetrievalManager#CASE_LIST_TYPE_MY}</li>
	 * <li>{@link CasesRetrievalManager#CASE_LIST_TYPE_OPEN}</li>
	 * <li>{@link CasesRetrievalManager#CASE_LIST_TYPE_PUBLIC}</li>
	 * <li>{@link CasesRetrievalManager#CASE_LIST_TYPE_USER}</li>
	 * <li>{@link CasesRetrievalManager#CASE_LIST_TYPE_SUBSCRIBER}</li>
	 * @param caseCodes is {@link Collection} of {@link Case#getCaseCode()} or
	 * process definition names;
	 * @param statusesToHide is {@link Collection} of {@link Case#getStatus()}
	 * telling about which {@link Case}s should be hidden;
	 * @param statusesToShow is {@link Collection} of {@link Case#getStatus()}
	 * telling about which {@link Case}s should be shown;
	 * @param onlySubscribedCases show cases where given {@link User} is
	 * {@link Case#getSubscribers()} list;
	 * @param showAllCases if <code>true</code>, then statuses are ignored;
	 * @param procInstIds is {@link Collection} of process instance id's for
	 * {@link Case};
	 * @param handlerCategoryIDs is {@link Group#getPrimaryKey()}s of
	 * {@link Group}s where subscribers are;
	 * @return filtered {@link List} of {@link Case#getPrimaryKey()} or
	 * {@link Collections#emptyList()} on failure;
	 * @throws Exception
	 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
	 */
	public List<Integer> getCasePrimaryKeys(
			IWContext iwc,
			User user,
			String type,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			boolean onlySubscribedCases,
			boolean showAllCases,
			List<Long> procInstIds,
			Set<String> roles,
			Collection<Long> handlerCategoryIDs,
			List<Integer> exceptOwnersIds,
			boolean searchQuery
	) throws Exception;

	public abstract PagedDataCollection<CasePresentation> getCasesByIds(List<Integer> ids, Locale locale);

	public CasePresentation getCaseByIdLazily(Integer caseId);

	public abstract PagedDataCollection<CasePresentation> getCasesByEntities(Collection<Case> cases, Locale locale);

	public abstract Map<Long, String> getAllCaseProcessDefinitionsWithName();

	public abstract List<Long> getAllCaseProcessDefinitions();

	public abstract String getProcessName(String processName, Locale locale);

	public abstract <T extends Serializable> T getLatestProcessDefinitionIdByProcessName(String name);

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

	public abstract List<String> getSuggestedCasesSubjects(
			IWContext iwc,
			User user,
			String type,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			boolean onlySubscribedCases,
			boolean showAllCases,
			Boolean handlerAssignedCases,
			List<Integer> exceptOwnersIds
	) throws Exception;

	public abstract List<String> getSuggestedOwnerNames(
			IWContext iwc,
			User user,
			String type,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			boolean onlySubscribedCases,
			boolean showAllCases,
			Boolean handlerAssignedCases,
			List<Integer> exceptOwnersIds
	) throws Exception;

}