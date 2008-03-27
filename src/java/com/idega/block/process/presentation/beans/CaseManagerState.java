package com.idega.block.process.presentation.beans;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author <a href="civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.2 $
 *
 * Last modified: $Date: 2008/03/27 08:46:36 $ by $Author: civilis $
 *
 */
@Scope("request")
@Service(CaseManagerState.beanIdentifier)
public class CaseManagerState implements Serializable {

	private static final long serialVersionUID = 7995176049178489211L;

	public static final String beanIdentifier = "caseManagerState";
		
	private Integer caseId;
	private Boolean showCaseHandler;
	private Boolean fullView;
	private Boolean inCasesComponent;
	
	public Integer getCaseId() {
		return caseId;
	}
	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
	public Boolean getShowCaseHandler() {
		return showCaseHandler == null ? false : showCaseHandler;
	}
	public void setShowCaseHandler(Boolean showCaseHandler) {
		this.showCaseHandler = showCaseHandler == null ? false : showCaseHandler;
	}
	public void reset() {
		
		showCaseHandler = false;
		caseId = null;
	}
	
	public Boolean getFullView() {
		return fullView == null ? false : fullView;
	}

	public void setFullView(Boolean fullView) {
		this.fullView = fullView;
	}

	public Boolean getInCasesComponent() {
		return inCasesComponent == null ? false : inCasesComponent;
	}

	public void setInCasesComponent(Boolean inCasesComponent) {
		this.inCasesComponent = inCasesComponent;
	}
}