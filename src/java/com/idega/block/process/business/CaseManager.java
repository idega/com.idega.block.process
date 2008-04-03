package com.idega.block.process.business;

import java.util.Collection;
import java.util.List;

import javax.faces.component.UIComponent;

import com.idega.block.process.data.Case;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Link;
import com.idega.user.data.User;

/**
 * 
 * @author <a href="civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.3 $
 *
 * Last modified: $Date: 2008/04/03 13:43:06 $ by $Author: civilis $
 *
 */
public interface CaseManager {

	public abstract String getBeanIdentifier();
	
	public abstract String getType();
	
	public abstract List<Link> getCaseLinks(Case theCase, String componentType);
	
	public abstract UIComponent getView(IWContext iwc, Case theCase);
	
	public abstract Collection<? extends Case> getCases(User user, String casesComponentType);
}