package com.idega.block.process.presentation.beans;

import java.io.Serializable;
import java.util.Collection;

public class CasesSearchResults implements Serializable {

	private static final long serialVersionUID = -5130051546048428868L;

	private String id;
	
	private Collection<CasePresentation> cases;
	
	private CasesSearchCriteriaBean criterias;
	
	public CasesSearchResults(String id, Collection<CasePresentation> cases, CasesSearchCriteriaBean criterias) {
		super();
		
		this.id = id;
		this.cases = cases;
		this.criterias = criterias;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<CasePresentation> getCases() {
		return cases;
	}

	public void setCases(Collection<CasePresentation> cases) {
		this.cases = cases;
	}

	public CasesSearchCriteriaBean getCriterias() {
		return criterias;
	}

	public void setCriterias(CasesSearchCriteriaBean criterias) {
		this.criterias = criterias;
	}
}