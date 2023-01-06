package com.idega.block.process.business;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.idegaweb.IWMainApplication;
import com.idega.util.StringUtil;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.8 $
 *
 * Last modified: $Date: 2009/03/17 20:56:27 $ by $Author: civilis $
 */
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service(CaseManagersProvider.beanIdentifier)
public class CaseManagersProvider implements ApplicationContextAware {

	public static final String beanIdentifier = "casesHandlersProvider";

	private ApplicationContext applicationContext;
	private final Map<String, String> caseManagersTypesBeanIdentifiers;

	public CaseManagersProvider() {
		caseManagersTypesBeanIdentifiers = new HashMap<>();
	}

	public List<CasesRetrievalManager> getCaseManagers() {
		String mainAndOnlyManager = IWMainApplication.getDefaultIWMainApplication().getSettings().getProperty(ProcessConstants.MAIN_AND_ONLY_CASES_MANAGER);
		if (!StringUtil.isEmpty(mainAndOnlyManager)) {
			CasesRetrievalManager handler = (CasesRetrievalManager) getApplicationContext().getBean(mainAndOnlyManager);
			if (handler != null) {
				return Arrays.asList(handler);
			}
		}

		List<CasesRetrievalManager> managers = new ArrayList<>(caseManagersTypesBeanIdentifiers.size());

		for (String handlerIdentifier: caseManagersTypesBeanIdentifiers.values()) {
			CasesRetrievalManager handler = (CasesRetrievalManager)getApplicationContext().getBean(handlerIdentifier);
			if (handler != null)
				managers.add(handler);
		}

		return managers;
	}

	/**
	 * Returns case manager according to their priorities.
	 *
	 * @return CaseManager implementation.
	 */
	public CasesRetrievalManager getCaseManager() {
		String beanIdentifier = caseManagersTypesBeanIdentifiers.get(ProcessConstants.BPM_CASE);
		if (beanIdentifier == null) {
			beanIdentifier = caseManagersTypesBeanIdentifiers.get("CasesDefault");
		}
		if (beanIdentifier == null) {
			return null;
		}
		return (CasesRetrievalManager) getApplicationContext().getBean(beanIdentifier);
	}

	@Autowired(required=false)
	public void setCaseManagers(List<CasesRetrievalManager> caseManagers) {
		for (CasesRetrievalManager caseManager: caseManagers) {
			String beanIdentifier = caseManager.getBeanIdentifier();

			if (beanIdentifier == null)
				Logger.getLogger(getClass().getName()).log(Level.WARNING, "No bean identifier provided for case handler. Skipping. Class name: " +
						caseManager.getClass().getName());
			else
				getCaseHandlersTypesBeanIdentifiers().put(caseManager.getType(), beanIdentifier);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	protected ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	protected Map<String, String> getCaseHandlersTypesBeanIdentifiers() {
		return caseManagersTypesBeanIdentifiers;
	}
}