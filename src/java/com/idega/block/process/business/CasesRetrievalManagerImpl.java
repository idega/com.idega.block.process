package com.idega.block.process.business;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.FinderException;
import javax.faces.component.UIComponent;

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
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
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
public class CasesRetrievalManagerImpl implements CasesRetrievalManager {

	public static final String beanIdentifier = "defaultCaseHandler";
	public static final String caseHandlerType = "CasesDefault";

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
			CasePresentation bean = convertToPresentation(caze, null, locale);
			beans.add(bean);
		}
		
		return beans;
	}

	protected CasePresentation convertToPresentation(Case theCase, CasePresentation bean, Locale locale) {
		if (bean == null) {
			bean = new CasePresentation();
		}
		
		bean.setPrimaryKey(theCase.getPrimaryKey() instanceof Integer ? (Integer) theCase.getPrimaryKey() : Integer.valueOf(theCase.getPrimaryKey().toString()));
		bean.setId(theCase.getId());
		bean.setUrl(theCase.getUrl());
		bean.setCaseManagerType(theCase.getCaseManagerType());
		bean.setOwner(theCase.getOwner());
		bean.setExternalId(theCase.getExternalId());
		bean.setCaseIdentifier(theCase.getCaseIdentifier());
		try {
			bean.setSubject(getCaseBusiness().getCaseSubject(theCase, locale));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			bean.setCaseStatus(getCaseBusiness().getCaseStatus(theCase.getStatus()));
		} catch (Exception e) {
			bean.setCaseStatus(theCase.getCaseStatus());
		}
		
		if (bean.getCaseStatus() != null) {
			bean.setLocalizedStatus(getLocalizedStatus(theCase, bean.getCaseStatus(), locale));
		}
		
		bean.setCreated(theCase.getCreated());
		bean.setCode(theCase.getCode());
		return bean;
	}
	
	protected String getLocalizedStatus(Case theCase, CaseStatus status, Locale locale) {
		String statusKey = status.getStatus();
		
		String localization = null;
		
		try {
			localization = getCaseBusiness().getLocalizedCaseStatusDescription(theCase, status, locale, "is.idega.idegaweb.egov.cases");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		if (StringUtil.isEmpty(localization) || localization.equals(statusKey)) {
			try {
				localization = getCaseBusiness().getLocalizedCaseStatusDescription(theCase, status, locale, IWBundleStarter.IW_BUNDLE_IDENTIFIER);
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
}