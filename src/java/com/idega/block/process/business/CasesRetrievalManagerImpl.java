package com.idega.block.process.business;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import javax.ejb.FinderException;
import javax.faces.component.UIComponent;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.process.IWBundleStarter;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseHome;
import com.idega.block.process.data.CaseStatus;
import com.idega.block.process.presentation.UserCases;
import com.idega.block.process.presentation.beans.CasePresentation;
import com.idega.block.process.presentation.beans.CasesSearchCriteriaBean;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.core.business.DefaultSpringBean;
import com.idega.core.cache.IWCacheManager2;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.IWContext;
import com.idega.presentation.paging.PagedDataCollection;
import com.idega.presentation.text.Link;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.datastructures.map.MapUtil;

/**
 * Default implementation of CaseManager.
 *
 * @author donatas
 *
 */
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Qualifier(CasesRetrievalManager.QUALIFIER)
@Service(CasesRetrievalManager.BEAN_IDENTIFIER)
public class CasesRetrievalManagerImpl extends DefaultSpringBean implements CasesRetrievalManager {

	private ReentrantLock lock = new ReentrantLock();

	public static final String caseHandlerType = "CasesDefault";

	private static final String CASES_LIST_IDS_CACHE = "casesListIdsCache";

	@Override
	public String getBeanIdentifier() {
		return CasesRetrievalManager.BEAN_IDENTIFIER;
	}

	@Override
	public String getType() {
		return caseHandlerType;
	}

	@Override
	public List<Long> getAllCaseProcessDefinitions() {
		return new ArrayList<Long>();
	}

	@Override
	public Map<Long, String> getAllCaseProcessDefinitionsWithName() {
		throw new UnsupportedOperationException("Not implemented");
	}

	public List<Link> getCaseLinks(Case theCase, String componentType) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public PagedDataCollection<CasePresentation> getCases(User user, String type, Locale locale, List<String> caseCodes, List<String> caseStatusesToHide,
			List<String> caseStatusesToShow, int startIndex, int count, boolean onlySubscribedCases, boolean showAllCases) {

		CaseBusiness caseBusiness = getCaseBusiness();
		try {
			CaseCode[] codes = caseBusiness.getCaseCodesForUserCasesList();
			Collection<Case> cases = caseBusiness.getAllCasesForUserExceptCodes(user, codes, startIndex, count);
			Collection<Case> casesToShow = null;
			if (onlySubscribedCases) {
				casesToShow = new ArrayList<Case>();
				for (Case theCase : cases) {
					Collection<User> subscribers = theCase.getSubscribers();
					if (!ListUtil.isEmpty(subscribers) && subscribers.contains(user)) {
						casesToShow.add(theCase);
					}
				}
			} else {
				casesToShow = cases;
			}

			int caseCount = caseBusiness.getNumberOfCasesForUserExceptCodes(user, codes);
			return new PagedDataCollection<CasePresentation>(convertToPresentationBeans(casesToShow, locale), Long.valueOf(caseCount));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		}
		return new PagedDataCollection<CasePresentation>(new ArrayList<CasePresentation>());
	}

	@Override
	public PagedDataCollection<CasePresentation> getCases(User user, String type, Locale locale, List<String> caseCodes, List<String> caseStatusesToHide,
			List<String> caseStatusesToShow, int startIndex, int count, boolean onlySubscribedCases, boolean showAllCases, List<Long> ids,
			Set<String> roles,
			boolean searchQuery) {
		return getCases(user, type, locale, caseCodes, caseStatusesToHide, caseStatusesToShow, startIndex, count, onlySubscribedCases, showAllCases);
	}

	@Override
	public List<Integer> getCaseIds(
			User user,
			String type,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			boolean onlySubscribedCases,
			boolean showAllCases,
			Integer page,
			Integer pageSize
	) throws Exception {
		return Collections.emptyList();
	}

	@Override
	public List<Integer> getCaseIds(User user, String type, List<String> caseCodes, List<String> statusesToHide, List<String> statusesToShow,
			boolean onlySubscribedCases, boolean showAllCases, List<Long> procInstIds, Set<String> roles) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public List<Integer> getCasePrimaryKeys(User user, String type, List<String> caseCodes, List<String> statusesToHide, List<String> statusesToShow,
			boolean onlySubscribedCases, boolean showAllCases, List<Long> procInstIds, Set<String> roles, Collection<Long> handlerCategoryIDs,
			boolean searchQuery) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Long getLatestProcessDefinitionIdByProcessName(String name) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Long getProcessDefinitionId(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String getProcessDefinitionName(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String getProcessIdentifier(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Long getProcessInstanceId(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String getProcessName(String processName, Locale locale) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public UIComponent getView(IWContext iwc, Integer caseId, String caseProcessorType, String caseManagerType) {
		return null;
	}

	@Override
	public PagedDataCollection<CasePresentation> getCasesByIds(List<Integer> ids, Locale locale) {
		Collection<Case> cases = getCaseBusiness().getCasesByIds(ids);
		return getCasesByEntities(cases, locale);
	}

	@Override
	public PagedDataCollection<CasePresentation> getCasesByEntities(Collection<Case> cases, Locale locale) {
		return new PagedDataCollection<CasePresentation>(convertToPresentationBeans(cases, locale), Long.valueOf(cases.size()));
	}

	@Override
	public Long getProcessInstanceIdByCaseId(Object id) {
		throw new UnsupportedOperationException("Not implemented");
	}

	/**
	 * Converts Case entities to presentation beans.
	 *
	 * @param cases Collection of Case entities.
	 * @param locale Current locale.
	 * @return List of CasePresentation beans.
	 */
	protected List<CasePresentation> convertToPresentationBeans(Collection<? extends Case> cases, Locale locale) {
		if (ListUtil.isEmpty(cases)) {
			return new ArrayList<CasePresentation>(0);
		}

		List<CasePresentation> beans = new ArrayList<CasePresentation>(cases.size());
		for (Iterator<? extends Case> iterator = cases.iterator(); iterator.hasNext();) {
			Case caze = iterator.next();

			CasePresentation bean = null;
			try {
				bean = convertToPresentation(caze, null, locale);
			} catch (Exception e) {
				getLogger().log(Level.WARNING, "Error while converting case " + caze + " to " + CasePresentation.class, e);
			}

			if (bean != null) {
				beans.add(bean);
			}
		}

		return beans;
	}

	protected CasePresentation convertToPresentation(Case theCase, CasePresentation bean, Locale locale) {
		if (bean == null) {
			bean = new CasePresentation();
		}

		CaseCode code = theCase.getCaseCode();

		CaseBusiness business;
		try {
			business = CaseCodeManager.getInstance().getCaseBusinessOrDefault(code, IWMainApplication.getDefaultIWApplicationContext());
		} catch (IBOLookupException ile) {
			business = getCaseBusiness();
		}

		bean.setPrimaryKey(theCase.getPrimaryKey() instanceof Integer ?
				(Integer) theCase.getPrimaryKey() :
				Integer.valueOf(theCase.getPrimaryKey().toString()));
		bean.setId(theCase.getId());
		bean.setUrl(theCase.getUrl());
		bean.setCaseManagerType(theCase.getCaseManagerType());
		bean.setOwner(theCase.getOwner());
		bean.setExternalId(theCase.getExternalId());
		bean.setCaseIdentifier(theCase.getCaseIdentifier());
		try {
			bean.setSubject(business.getCaseSubject(theCase, locale));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		bean.setBody(theCase.getBody());
		try {
			bean.setCaseStatus(getCaseBusiness().getCaseStatus(theCase.getStatus()));
		} catch (Exception e) {
			bean.setCaseStatus(theCase.getCaseStatus());
		}

		if (bean.getCaseStatus() != null) {
			bean.setLocalizedStatus(getLocalizedStatus(theCase, bean.getCaseStatus(), business, locale));
		}

		bean.setCreated(theCase.getCreated());
		bean.setCode(theCase.getCode());
		return bean;
	}

	protected String getLocalizedStatus(Case theCase, CaseStatus status, Locale locale) {
		return getLocalizedStatus(theCase, status, getCaseBusiness(), locale);
	}

	protected String getLocalizedStatus(Case theCase, CaseStatus status, CaseBusiness business, Locale locale) {
		String statusKey = status.getStatus();

		String localization = null;

		try {
			localization = business.getLocalizedCaseStatusDescription(theCase, status, locale);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (StringUtil.isEmpty(localization) || localization.equals(statusKey)) {
			try {
				localization = business.getLocalizedCaseStatusDescription(theCase, status, locale, "is.idega.idegaweb.egov.cases");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		if (StringUtil.isEmpty(localization) || localization.equals(statusKey)) {
			try {
				localization = business.getLocalizedCaseStatusDescription(theCase, status, locale, IWBundleStarter.IW_BUNDLE_IDENTIFIER);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		return StringUtil.isEmpty(localization) ? statusKey : localization;
	}

	@Override
	public PagedDataCollection<CasePresentation> getClosedCases(Collection<Group> groups) {
		return new PagedDataCollection<CasePresentation>(new ArrayList<CasePresentation>());
	}

	@Override
	public PagedDataCollection<CasePresentation> getMyCases(User user) {
		return new PagedDataCollection<CasePresentation>(new ArrayList<CasePresentation>());
	}

	protected CaseBusiness getCaseBusiness() {
		try {
			return IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CaseBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	@Override
	public Long getTaskInstanceIdForTask(Case theCase, String taskName) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public List<Long> getCasesIdsByProcessDefinitionName(String processDefinitionName) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String resolveCaseId(IWContext iwc) {
		if (iwc == null) {
			return null;
		}

		return iwc.getParameter(UserCases.PARAMETER_CASE_PK);
	}

	@Override
	public User getCaseOwner(Object entityId) {
		if (entityId == null || entityId instanceof Long) {
			return null;
		}

		try {
			CaseHome caseHome = (CaseHome) IDOLookup.getHome(Case.class);
			Case theCase = caseHome.findByPrimaryKey(entityId);
			return theCase == null ? null : theCase.getOwner();
		} catch(FinderException e) {
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Collection<CasePresentation> getReLoadedCases(CasesSearchCriteriaBean criterias) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private int lastUsedCacheSize = 75;
	private Map<CasesCacheCriteria, Map<Integer, Date>> getCache() {
		int cacheSize = Integer.valueOf(getApplication().getSettings().getProperty("cases_cache_size", String.valueOf(75)));
		if (cacheSize == lastUsedCacheSize) {
			return getCache(CASES_LIST_IDS_CACHE, 86400, cacheSize);
		}

		IWCacheManager2.getInstance(getApplication()).invalidate(CASES_LIST_IDS_CACHE);
		lastUsedCacheSize = cacheSize;
		return getCache();
	}

	/**
	 *
	 * <p>Creates new search criteria for cache by:</p>
	 * @param user
	 * @param type
	 * @param caseCodes
	 * @param statusesToHide is {@link Collection} of {@link Case#getStatus()},
	 * which should be hidden;
	 * @param statusesToShow is {@link Collection} of {@link Case#getStatus()},
	 * which should be shown;
	 * @param onlySubscribedCases shows only those {@link Case}s where given
	 * {@link User} is in {@link Case#getSubscribers()};
	 * @param roles of BPM processes, which {@link User} can access. More info
	 * in bpm_actors table;
	 * @param groups
	 * @param codes
	 * @param showAllCases
	 * @param procInstIds is id's of BPM process instances;
	 * @param handlerCategoryIDs is ID's of groups, which has {@link User}s, who
	 * is in {@link Case#getSubscribers()} list;
	 * @return criteria bean for querying cache;
	 */
	protected CasesCacheCriteria getCacheKey(
			User user,
			String type,
			Collection<String> caseCodes,
			Collection<String> statusesToHide,
			Collection<String> statusesToShow,
			boolean onlySubscribedCases,
			Collection<String> roles,
			Collection<Integer> groups,
			Collection<String> codes,
			boolean showAllCases,
			Collection<Long> procInstIds,
			Collection<Long> handlerCategoryIDs
	) {
		return new CasesCacheCriteria(
				user == null ? -1 : Integer.valueOf(user.getId()),
				type,
				caseCodes,
				statusesToHide,
				statusesToShow,
				roles,
				groups,
				codes,
				procInstIds,
				handlerCategoryIDs,
				onlySubscribedCases,
				showAllCases
		);
	}

	private boolean isLockRequired() {
		return getApplication().getSettings().getBoolean("cases.list_lock_required", Boolean.FALSE);
	}

	protected void clearCache() throws Exception {
		boolean lockRequired = isLockRequired();
		if (lockRequired) {
			assert !lock.isHeldByCurrentThread();
			lock.lock();
		}

		try {
			getCache().clear();
		} catch (Exception e) {
		} finally {
			if (lockRequired) {
				lock.unlock();
			}
		}
	}

	protected void removeFromCache(CasesCacheCriteria key) throws Exception {
		boolean lockRequired = isLockRequired();
		if (lockRequired) {
			assert !lock.isHeldByCurrentThread();
			lock.lock();
		}

		try {
			getCache().remove(key);
		} catch (Exception e) {
		} finally {
			if (lockRequired) {
				lock.unlock();
			}
		}
	}

	protected void removeElementFromCache(CasesCacheCriteria key, Integer id) throws Exception {
		boolean lockRequired = isLockRequired();
		if (lockRequired) {
			assert !lock.isHeldByCurrentThread();
			lock.lock();
		}

		try {
			Map<Integer, Date> ids = getCache().get(key);
			if (ids != null) {
				ids.remove(id);
			}
		} catch (Exception e) {
		} finally {
			if (lockRequired) {
				lock.unlock();
			}
		}
	}

	protected boolean containsElement(CasesCacheCriteria key, Integer id) throws Exception {
		boolean lockRequired = isLockRequired();
		if (lockRequired) {
			assert !lock.isHeldByCurrentThread();
			lock.lock();
		}

		try {
			Map<Integer, Date> ids = getCache().get(key);
			return !MapUtil.isEmpty(ids) && ids.containsKey(id);
		} finally {
			if (lockRequired) {
				lock.unlock();
			}
		}
	}

	protected void addElementToCache(CasesCacheCriteria key, Integer id, Date creationDate) throws Exception {
		if (key == null || id == null) {
			return;
		}
		Map<Integer, Date> data = new HashMap<Integer, Date>(1);
		data.put(id, creationDate);
		putIdsToCache(data, key);
	}

	protected Collection<CasesCacheCriteria> getCacheKeySet() throws Exception {
		boolean lockRequired = isLockRequired();
		if (lockRequired) {
			assert !lock.isHeldByCurrentThread();
			lock.lock();
		}

		try {
			return new ArrayList<CasesCacheCriteria>(getCache().keySet());
		} catch (Exception e) {
		} finally {
			if (lockRequired) {
				lock.unlock();
			}
		}

		return Collections.emptyList();
	}

	protected Map<Integer, Date> getCachedIds(CasesCacheCriteria key) {
		boolean lockRequired = isLockRequired();
		if (lockRequired) {
			assert !lock.isHeldByCurrentThread();
			lock.lock();
		}

		try {
			Map<Integer, Date> data = getCache().get(key);
			if (MapUtil.isEmpty(data)) {
				return Collections.emptyMap();
			}
			return new LinkedHashMap<Integer, Date>(data);
		} finally {
			if (lockRequired) {
				lock.unlock();
			}
		}
	}

	protected List<Integer> getSortedIds(Map<Integer, Date> data) {
		if (MapUtil.isEmpty(data)) {
			return Collections.emptyList();
		}

		/* Sorting by date - latest on the top */
		List<Map.Entry<Integer, Date>> entries = new ArrayList<Map.Entry<Integer, Date>>(data.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Integer, Date>>() {
			@Override
			public int compare(Map.Entry<Integer, Date> o1, Map.Entry<Integer, Date> o2) {
				return -1 * (o1.getValue().compareTo(o2.getValue()));
			}
		});
		List<Integer> cachedIds = new ArrayList<Integer>();
		for (Map.Entry<Integer, Date> entry: entries) {
			cachedIds.add(entry.getKey());
		}

		return cachedIds;
	}

	/**
	 *
	 * <p>Puts {@link Case#getPrimaryKey()}s to {@link Map}</p>
	 * @param ids is {@link Case#getPrimaryKey()}s to put, not <code>null</code>;
	 * @param user
	 * @param type
	 * @param caseCodes
	 * @param caseStatusesToHide is {@link Collection} of {@link Case#getStatus()},
	 * which should be hidden;
	 * @param caseStatusesToShow is {@link Collection} of {@link Case#getStatus()},
	 * which should be shown;
	 * @param onlySubscribedCases shows only those {@link Case}s where given
	 * {@link User} is in {@link Case#getSubscribers()};
	 * @param roles of BPM processes, which {@link User} can access. More info
	 * in bpm_actors table;
	 * @param groups
	 * @param codes
	 * @param showAllCases
	 * @param procInstIds is id's of BPM process instances;
	 * @param handlerCategoryIDs is ID's of groups, which has {@link User}s, who
	 * is in {@link Case#getSubscribers()} list;
	 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
	 */
	protected void putIdsToCache(
			Map<Integer, Date> data,
			User user,
			String type,
			Collection<String> caseCodes,
			Collection<String> caseStatusesToHide,
			Collection<String> caseStatusesToShow,
			boolean onlySubscribedCases,
			Collection<String> roles,
			Collection<Integer> groups,
			Collection<String> codes,
			boolean showAllCases,
			Collection<Long> procInstIds,
			Collection<Long> handlerCategoryIDs
	) {
		/* Creating key */
		CasesCacheCriteria key = getCacheKey(
				user,
				type,
				caseCodes,
				caseStatusesToHide,
				caseStatusesToShow,
				onlySubscribedCases,
				roles,
				groups,
				codes,
				showAllCases,
				procInstIds,
				handlerCategoryIDs
		);
		putIdsToCache(data, key);
	}

	protected void putIdsToCache(Map<Integer, Date> data, CasesCacheCriteria key) {
		if (MapUtil.isEmpty(data) || key == null) {
			return;
		}

		boolean lockRequired = isLockRequired();
		if (lockRequired) {
			assert !lock.isHeldByCurrentThread();
			lock.lock();
		}

		try {
			Map<CasesCacheCriteria, Map<Integer, Date>> cache = getCache();
			if (cache == null) {
				return;
			}

			/* Getting id's, that already cached by given criteria */
			Map<Integer, Date> cachedIds = cache.get(key);
			if (cachedIds == null) {
				cachedIds = new LinkedHashMap<Integer, Date>();
			}

			/* Putting to cache */
			for (Entry<Integer, Date> newData: data.entrySet()) {
				cachedIds.put(newData.getKey(), newData.getValue());
			}

			cache.put(key, cachedIds);
		} finally {
			if (lockRequired) {
				lock.unlock();
			}
		}
	}

	protected List<Integer> getCasesIds(
			User user,
			String type,
			List<String> caseCodes,
			List<String> caseStatusesToHide,
			List<String> caseStatusesToShow,
			boolean onlySubscribedCases,
			boolean showAllCases,
			Integer caseId,
			List<Long> procInstIds,
			Set<String> roles,
			Collection<Long> handlerCategoryIDs,
			boolean searchQuery
	) throws Exception {
		throw new UnsupportedOperationException("This method is not implemented");
	}

	@Override
	public CasePresentation getCaseByIdLazily(Integer caseId) {
		if (caseId == null) {
			return null;
		}

		Case theCase = null;
		try {
			theCase = getCaseBusiness().getCase(caseId);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FinderException e) {
		}
		if (theCase == null) {
			return null;
		}

		CasePresentation casePresentation = new CasePresentation(theCase);
		casePresentation.setLocalizedStatus(getLocalizedStatus(theCase, theCase.getCaseStatus(), getCurrentLocale()));
		return casePresentation;
	}

}