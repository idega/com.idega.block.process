package com.idega.block.process.presentation.beans;

import java.util.Collection;

import com.idega.io.MemoryFileBuffer;

public interface CasesSearchResultsHolder {

	public static final String SPRING_BEAN_IDENTIFIER = "casesSearchResultsHolder";
	
	public void setSearchResults(Collection<CasePresentation> cases);
	
	public Integer getNextCaseId(Integer currentId, String processDefinitionName);
	
	public Integer getNextCaseId(Integer currentId);
	
	public boolean isSearchResultStored();
	
	public boolean doExport();
	
	public MemoryFileBuffer getExportedSearchResults();
	
	public boolean clearSearchResults();
	
	public Collection<CasePresentation> getSearchResults();
	
}
