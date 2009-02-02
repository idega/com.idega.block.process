package com.idega.block.process.business;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.7 $
 *
 * Last modified: $Date: 2009/02/02 13:42:29 $ by $Author: donatas $
 */
@Scope("singleton")
@Service(CaseManagersProvider.beanIdentifier)
public class CaseManagersProvider implements ApplicationContextAware {
	
	public static final String beanIdentifier = "casesHandlersProvider";
	
	private ApplicationContext applicationContext;
	private final Map<String, String> caseManagersTypesBeanIdentifiers;
	
	public CaseManagersProvider() {
		caseManagersTypesBeanIdentifiers = new HashMap<String, String>();
	}
		
	public List<CaseManager> getCaseManagers() {
		
		List<CaseManager> managers = new ArrayList<CaseManager>(caseManagersTypesBeanIdentifiers.size());
		
		for (String handlerIdentifier : caseManagersTypesBeanIdentifiers.values()) {
			
			CaseManager handler = (CaseManager)getApplicationContext().getBean(handlerIdentifier);
			
			if(handler != null)
				managers.add(handler);
		}
		
		return managers;
	}
	
	/**
	 * Returns case manager according to their priorities.
	 * 
	 * @return CaseManager implementation.
	 */
	public CaseManager getCaseManager() {
		String beanIdentifier = caseManagersTypesBeanIdentifiers.get("CasesBPM");
		if (beanIdentifier == null) {
			beanIdentifier = caseManagersTypesBeanIdentifiers.get("CasesDefault");
		}
		if (beanIdentifier == null) {
			return null;
		}
		return (CaseManager) getApplicationContext().getBean(beanIdentifier);
	}
	
	@Autowired(required=false)
	public void setCaseManagers(List<CaseManager> caseManagers) {
		
		for (CaseManager caseManager : caseManagers) {
		
			String beanIdentifier = caseManager.getBeanIdentifier();
			
			if(beanIdentifier != null)
				getCaseHandlersTypesBeanIdentifiers().put(caseManager.getType(), beanIdentifier);
			else
				Logger.getLogger(getClass().getName()).log(Level.WARNING, "No bean identifier provided for case handler. Skipping. Class name: "+caseManager.getClass().getName());
		}
	}

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