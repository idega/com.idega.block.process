package com.idega.block.process.data.dao;

import com.idega.block.process.data.bean.CaseInvoiceInfo;
import com.idega.core.persistence.GenericDao;

public interface CaseInvoiceInfoDAO extends GenericDao {

	public CaseInvoiceInfo getByCaseId(Integer caseId);

	public CaseInvoiceInfo createUpdateCaseInvoiceInfo(CaseInvoiceInfo info);

}