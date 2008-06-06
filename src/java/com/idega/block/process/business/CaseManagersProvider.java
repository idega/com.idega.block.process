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

import com.idega.builder.bean.AdvancedProperty;
import com.idega.util.CoreConstants;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.5 $
 *
 * Last modified: $Date: 2008/06/06 14:22:16 $ by $Author: civilis $
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
	
	public CaseManager getCaseManager(String managerType) {
		
		if(managerType == null || CoreConstants.EMPTY.equals(managerType))
			throw new IllegalArgumentException("No or empty handlerType provided");
		
		if(!getCaseHandlersTypesBeanIdentifiers().containsKey(managerType))
			throw new IllegalArgumentException("No case handler bound to handler type provided: "+managerType);
		
		String beanIdentifier = getCaseHandlersTypesBeanIdentifiers().get(managerType);
		return (CaseManager)getApplicationContext().getBean(beanIdentifier);
	}
	
	public List<AdvancedProperty> getExistingProcesses() {
		
		List<CaseManager> caseManagers = getCaseManagers();
		
		if(caseManagers != null) {
			
			for (CaseManager caseManager : caseManagers) {
				
				List<AdvancedProperty> allProcesses = caseManager.getAllCaseProcesses();
				
				if(allProcesses != null)
					return allProcesses;
			}
		}
		
		return null;
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