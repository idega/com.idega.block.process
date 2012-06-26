package com.idega.block.process.presentation.beans;

import java.util.List;
import java.util.Map;

public interface CasesListCustomizer {

	public List<String> getHeaders(List<String> headersKeys);
	
	public Map<String, Map<String, String>> getLabelsForHeaders(List<String> casesIds, List<String> headersKeys);
	
	/**
	 * @param headersKeys not <code>null</code>
	 * @return {@link Map} of (headerKey, header) or <code>null</code>.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public java.util.Map<String, String> getHeadersAndVariables(List<String> headersKeys);
}