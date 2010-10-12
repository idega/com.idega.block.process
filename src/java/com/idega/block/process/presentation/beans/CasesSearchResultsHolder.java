package com.idega.block.process.presentation.beans;

import java.util.Collection;
import java.util.List;

import com.idega.io.MemoryFileBuffer;

public interface CasesSearchResultsHolder {

	public static final String SPRING_BEAN_IDENTIFIER = "casesSearchResultsHolder";
	
	public void setSearchResults(String id, CasesSearchResults results);
	
	public Integer getNextCaseId(String id, Integer currentId, String processDefinitionName);
	
	public Integer getNextCaseId(String id, Integer currentId);
	
	public boolean isSearchResultStored(String id);
	
	public boolean isAllDataLoaded(String id);
	
	public boolean doExport(String id);
	
	public MemoryFileBuffer getExportedSearchResults(String id);
	
	public boolean clearSearchResults(String id);
	
	public Collection<CasePresentation> getSearchResults(String id);
	
	public CasesSearchCriteriaBean getSearchCriteria(String id);
	
	public void concatExternalData(String id, List<CasePresentation> externalData);
}