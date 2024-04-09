package com.idega.block.process.data.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.idega.block.process.data.bean.CaseLog;
import com.idega.core.persistence.GenericDao;

public interface CaseLogDAO extends GenericDao {

	public List<CaseLog> getCaseLogsWithType();

	public List<CaseLog> getCaseLogsByType(String type);

	public List<String> getCaseUUIDSByCaseUUIDSAndStatusBeforeAndStatusAfter(List<String> caseUUIDs, String statusBefore, String statusAfter);

	/**
	 * Case unique ID => status after => timestamp
	 * @param caseCode
	 * @param from
	 * @param to
	 * @return
	 */
	public Map<String, Map<String, Timestamp>> getCasesIdsWithAllStatuses(String caseCode, Timestamp from, Timestamp to);

}