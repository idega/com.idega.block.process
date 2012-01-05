package com.idega.block.process.presentation.beans;

import java.util.List;
import java.util.Map;

public interface CasesListCustomizer {

	public List<String> getHeaders(List<String> headersKeys);
	
	public Map<String, Map<String, String>> getLabelsForHeaders(List<String> casesIds, List<String> headersKeys);
	
}