package com.idega.block.process.presentation.beans;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.process.presentation.UserCases;

/**
 * 
 * @author <a href="civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.3 $
 *
 * Last modified: $Date: 2008/04/03 13:43:07 $ by $Author: civilis $
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
		
		if(caseId == null) {
			
			String caseIdPar = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(UserCases.PARAMETER_CASE_PK);
			
			if(caseIdPar != null)
				caseId = new Integer(caseIdPar);
		}
		
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