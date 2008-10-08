package com.idega.block.process.business;

import java.util.Collection;
import java.util.List;

import javax.faces.component.UIComponent;

import com.idega.block.process.data.Case;
import com.idega.builder.bean.AdvancedProperty;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Link;
import com.idega.user.data.User;

/**
 * 
 * @author <a href="civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.8 $
 *
 * Last modified: $Date: 2008/10/08 18:53:41 $ by $Author: civilis $
 *
 */
public interface CaseManager {

	public abstract String getBeanIdentifier();
	
	public abstract String getType();
	
	public abstract String getProcessIdentifier(Case theCase);
	
	public abstract List<Link> getCaseLinks(Case theCase, String componentType);
	
	public abstract UIComponent getView(IWContext iwc, Case theCase, String caseProcessorType);
	
	public abstract Collection<? extends Case> getCases(User user, String casesComponentType);
	
	/**
	 * TODO: this should be replaced with more wise one, in some different class. temporary solution
	 * @return returns all case bound processes
	 */
	public abstract List<AdvancedProperty> getAllCaseProcesses();
}