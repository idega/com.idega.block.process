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

import com.idega.util.CoreConstants;

/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.3 $
 *
 * Last modified: $Date: 2008/04/21 05:03:02 $ by $Author: civilis $
 */
@Scope("singleton")
@Service(CaseManagersProvider.beanIdentifier)
public class CaseManagersProvider implements ApplicationContextAware {
	
	public static final String beanIdentifier = "casesHandlersProvider";
	
	private ApplicationContext applicationContext;
	private final Map<String, String> caseHandlersTypesBeanIdentifiers;
	
	public CaseManagersProvider() {
		caseHandlersTypesBeanIdentifiers = new HashMap<String, String>();
	}
	
	public CaseManager getCaseHandler(String handlerType) {
		
		if(handlerType == null || CoreConstants.EMPTY.equals(handlerType))
			throw new IllegalArgumentException("No or empty handlerType provided");
		
		if(!getCaseHandlersTypesBeanIdentifiers().containsKey(handlerType))
			throw new IllegalArgumentException("No case handler bound to handler type provided: "+handlerType);
		
		String beanIdentifier = getCaseHandlersTypesBeanIdentifiers().get(handlerType);
		return (CaseManager)getApplicationContext().getBean(beanIdentifier);
	}
	
	public List<CaseManager> getCaseHandlers() {
		
		List<CaseManager> handlers = new ArrayList<CaseManager>(caseHandlersTypesBeanIdentifiers.size());
		
		for (String handlerIdentifier : caseHandlersTypesBeanIdentifiers.values()) {
			
			CaseManager handler = (CaseManager)getApplicationContext().getBean(handlerIdentifier);
			
			if(handler != null)
				handlers.add(handler);
		}
		
		return handlers;
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
		return caseHandlersTypesBeanIdentifiers;
	}
}