package com.idega.block.process.business;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.ejb.FinderException;
import javax.faces.component.UIComponent;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.process.IWBundleStarter;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseCode;
import com.idega.block.process.data.CaseHome;
import com.idega.block.process.data.CaseStatus;
import com.idega.block.process.event.CaseModifiedEvent;
import com.idega.block.process.presentation.UserCases;
import com.idega.block.process.presentation.beans.CasePresentation;
import com.idega.block.process.presentation.beans.CasesSearchCriteriaBean;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.core.business.DefaultSpringBean;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.IWContext;
import com.idega.presentation.paging.PagedDataCollection;
import com.idega.presentation.text.Link;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

/**
 * Default implementation of CaseManager. 
 * 
 * @author donatas
 *
 */
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service(CasesRetrievalManagerImpl.beanIdentifier)
public class CasesRetrievalManagerImpl extends DefaultSpringBean implements CasesRetrievalManager, ApplicationListener {

	public static final String beanIdentifier = "defaultCaseHandler";
	public static final String caseHandlerType = "CasesDefault";

	protected static final String CASES_LIST_IDS_CACHE = "casesListIdsCache";
	
	public String getBeanIdentifier() {
		return beanIdentifier;
	}

	public String getType() {
		return caseHandlerType;
	}
	
	public List<Long> getAllCaseProcessDefinitions() {
		return new ArrayList<Long>();
	}

	public Map<Long, String> getAllCaseProcessDefinitionsWithName() {
		throw new UnsupportedOperationException("Not implemented");
	}

	public List<Link> getCaseLinks(Case theCase, String componentType) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@SuppressWarnings("unchecked")
	public PagedDataCollection<CasePresentation> getCases(User user, String type, Locale locale, List<String> caseCodes, List<String> caseStatusesToHide,
			List<String> caseStatusesToShow, int startIndex, int count, boolean onlySubscribedCases) {
		
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
			return new PagedDataCollection<CasePresentation>(convertToPresentationBeans(casesToShow, locale), caseCount);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		}
		return new PagedDataCollection<CasePresentation>(new ArrayList<CasePresentation>());
	}
	
	public List<Integer> getCaseIds(User user, String type, List<String> caseCodes, List<String> statusesToHide, List<String> statusesToShow,
			boolean onlySubscribedCases) throws Exception {
		throw new UnsupportedOperationException("Not implemented");
	}

	public Long getLatestProcessDefinitionIdByProcessName(String name) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public Long getProcessDefinitionId(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public String getProcessDefinitionName(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public String getProcessIdentifier(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public Long getProcessInstanceId(Case theCase) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public String getProcessName(String processName, Locale locale) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public UIComponent getView(IWContext iwc, Integer caseId, String caseProcessorType, String caseManagerType) {
		return null;
	}

	public PagedDataCollection<CasePresentation> getCasesByIds(List<Integer> ids, Locale locale) {
		Collection<Case> cases = getCaseBusiness().getCasesByIds(ids);
		return getCasesByEntities(cases, locale);
	}
	
	public PagedDataCollection<CasePresentation> getCasesByEntities(Collection<Case> cases, Locale locale) {
		return new PagedDataCollection<CasePresentation>(convertToPresentationBeans(cases, locale), cases.size());
	}

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
		}
		catch (IBOLookupException ile) {
			business = getCaseBusiness();
		}
		
		bean.setPrimaryKey(theCase.getPrimaryKey() instanceof Integer ? (Integer) theCase.getPrimaryKey() : Integer.valueOf(theCase.getPrimaryKey().toString()));
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
	
	public PagedDataCollection<CasePresentation> getClosedCases(Collection<Group> groups) {
		return new PagedDataCollection<CasePresentation>(new ArrayList<CasePresentation>());
	}
	
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

	public Long getTaskInstanceIdForTask(Case theCase, String taskName) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public List<Long> getCasesIdsByProcessDefinitionName(String processDefinitionName) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public String resolveCaseId(IWContext iwc) {
		if (iwc == null) {
			return null;
		}
		
		return iwc.getParameter(UserCases.PARAMETER_CASE_PK);
	}

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

	public Collection<CasePresentation> getReLoadedCases(CasesSearchCriteriaBean criterias) {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	private Map<String, List<Integer>> getCache() {
		return getCache(CASES_LIST_IDS_CACHE, 60 * 30);
	}
	
	private String getCacheKey(User user, String type, List<String> caseCodes, List<String> statusesToHide, List<String> statusesToShow,
			boolean onlySubscribedCases, Set<String> roles,	List<Integer> groups, List<String> codes) {
		
		return new StringBuffer().append(user == null ? "-1" : user.getId()).append(type == null ? "-" : type).append(caseCodes).append(statusesToHide)
				.append(statusesToShow).append(onlySubscribedCases).append(roles).append(groups).append(codes).toString();
	}
	
	protected List<Integer> getCachedIds(User user, String type, List<String> caseCodes, List<String> caseStatusesToHide, List<String> caseStatusesToShow,
			boolean onlySubscribedCases, Set<String> roles,	List<Integer> groups, List<String> codes) {
		
		Map<String, List<Integer>> cache = getCache();
		if (cache == null) {
			return null;
		}
		
		String key = getCacheKey(user, type, caseCodes, caseStatusesToHide, caseStatusesToShow, onlySubscribedCases, roles,	groups, codes);
		return cache.get(key);
	}
	
	protected void putIdsToCache(List<Integer> ids, User user, String type, List<String> caseCodes, List<String> caseStatusesToHide, List<String> caseStatusesToShow,
			boolean onlySubscribedCases, Set<String> roles,	List<Integer> groups, List<String> codes) {
		
		Map<String, List<Integer>> cache = getCache();
		if (cache == null) {
			return;
		}
		
		String key = getCacheKey(user, type, caseCodes, caseStatusesToHide, caseStatusesToShow, onlySubscribedCases, roles,	groups, codes);
		cache.put(key, ids);
	}

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof CaseModifiedEvent) {
			Thread cacheClearer = new Thread(new Runnable() {
				public void run() {
					Map<String, List<Integer>> cache = getCache();
					if (cache != null) {
						cache.clear();
					}
				}
			});
			cacheClearer.start();
		}
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