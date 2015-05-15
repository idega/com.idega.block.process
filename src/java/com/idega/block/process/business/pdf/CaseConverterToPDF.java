package com.idega.block.process.business.pdf;

import java.util.List;

import com.idega.block.process.data.Case;

public interface CaseConverterToPDF {

	public List<CasePDF> getPDFsForCase(Integer caseId) throws Exception;

	public List<CasePDF> getPDFsForCase(Case theCase) throws Exception;
	public List<CasePDF> getPDFsForCase(Case theCase, boolean resetContex) throws Exception;

	public List<CasePDF> getPDFsAndAttachmentsForCase(Integer caseId) throws Exception;

}