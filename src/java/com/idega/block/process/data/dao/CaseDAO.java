package com.idega.block.process.data.dao;

import java.sql.Timestamp;
import java.util.List;

import com.idega.block.process.data.bean.Case;
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

	public Long getCountOfCasesCreatedAfterGivenTimestamp(Timestamp timestampAfter);
}