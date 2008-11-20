package com.idega.block.process.business;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.idega.block.process.data.Case;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Link;
import com.idega.user.data.User;

/**
 * 
 * @author <a href="civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.9 $
 *
 * Last modified: $Date: 2008/11/20 07:30:46 $ by $Author: valdas $
 *
 */
public interface CaseManager {

	public abstract String getBeanIdentifier();
	
	public abstract String getType();
	
	public abstract String getProcessIdentifier(Case theCase);
	
	public abstract List<Link> getCaseLinks(Case theCase, String componentType);
	
	public abstract UIComponent getView(IWContext iwc, Case theCase, String caseProcessorType);
	
	public abstract Collection<? extends Case> getCases(User user, String casesComponentType);
	
	public abstract Map<Long, String> getAllCaseProcessDefinitionsWithName();
	
	public abstract List<Long> getAllCaseProcessDefinitions();
	
	public String getProcessName(Long processDefinitionId, Locale locale);
}