package com.idega.block.process.data.dao;

import java.sql.Timestamp;
import java.util.List;

import com.idega.block.process.data.bean.Case;
import com.idega.block.process.data.bean.CaseConsultant;
import com.idega.block.process.data.bean.CaseInvoiceRecord;
import com.idega.block.process.data.bean.CaseMaterial;
import com.idega.block.process.data.bean.CaseMileageReimbursement;
import com.idega.block.process.data.bean.CaseSettings;
import com.idega.core.persistence.GenericDao;

public interface CaseDAO extends GenericDao, SettingsDAO {

	public List<Integer> getCasesIdsByCaseSubject(String subject);

	public Case getCaseById(Integer id);

	public Case getCaseByUniqueId(String uuid);

	public Integer getCaseIdByUniqueId(String uuid);

	public CaseSettings getCaseSettings(Integer settingsId);

	public CaseSettings getSettings(Integer caseId);

	public List<Case> getAllCasesByStatuses(List<String> statuses);
	public List<Integer> getCasesIDsByStatuses(List<String> statuses);

	public Long getCountOfCasesCreatedAfterGivenTimestamp(Timestamp timestampAfter, List<String> caseManagerTypes, List<String> caseCodes);

	public CaseMileageReimbursement updateMileageReimbursement(
			Integer id,
			String name,
			Double price,
			String type,
			Integer specifiedQuantity,
			Double specifiedVAT
	);

	public CaseMaterial updateMaterial(
			Integer id,
			String name,
			Double price,
			Integer quantity,
			Integer specifiedQuantity,
			Double specifiedVAT
	);

	public CaseConsultant updateConsultant(
			Integer id,
			String name,
			Double price,
			Integer quantity,
			Integer specifiedQuantity,
			Double specifiedVAT
	);

	public CaseInvoiceRecord getCaseInvoiceRecordById(Integer id);

	public CaseInvoiceRecord getCaseInvoiceRecordByUniqeId(String uniqueId);

	public List<CaseInvoiceRecord> getAllByCaseId(Integer caseId);

	public CaseInvoiceRecord updateCaseInvoice(
			Integer caseId,
			Integer invoiceId,
			List<Integer> mileageReimbursementIds,
			List<Integer> materialIds,
			List<Integer> consultantIds
	);

	public List<Case> findByCaseSubjectAndCaseCodeAndDueDateLaterThanNow(String caseSubject, String caseCode);
}