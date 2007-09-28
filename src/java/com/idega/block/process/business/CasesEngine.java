package com.idega.block.process.business;

import java.util.List;

import org.jdom.Document;

import com.idega.block.process.data.Case;
import com.idega.builder.bean.AdvancedProperty;
import com.idega.business.SpringBeanName;
import com.idega.presentation.IWContext;
import com.idega.user.data.User;

@SpringBeanName("casesEngine")
public interface CasesEngine {

	public List<Case> getCases(User user, String caseType);
	
	public List<String> getInfoForCases();
	
	public Case getCase(String caseId);
	
	public Document getCaseOverview(String caseId);
	
	public Document getCasesList(String caseType);
	
	public List<AdvancedProperty> getCasesTypes(IWContext iwc, boolean sort);

}
