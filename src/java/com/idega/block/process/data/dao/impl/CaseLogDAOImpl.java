package com.idega.block.process.data.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idega.block.process.data.bean.CaseLog;
import com.idega.block.process.data.dao.CaseLogDAO;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;
import com.idega.util.ArrayUtil;
import com.idega.util.IWTimestamp;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

@Repository("caseLogDAO")
@Transactional(readOnly = true)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CaseLogDAOImpl extends GenericDaoImpl implements CaseLogDAO {

	@Override
	public List<CaseLog> getCaseLogsWithType() {
		return getResultList(CaseLog.QUERY_FIND_WITH_TYPE, CaseLog.class);
	}

	@Override
	public List<CaseLog> getCaseLogsByType(String type) {
		return getResultList(CaseLog.QUERY_FIND_WITH_TYPE, CaseLog.class, new Param(CaseLog.PARAM_TYPE, type));
	}

	@Override
	public List<String> getCaseUUIDSByCaseUUIDSAndStatusBeforeAndStatusAfter(List<String> caseUUIDs, String statusBefore, String statusAfter) {
		if (ListUtil.isEmpty(caseUUIDs) || StringUtil.isEmpty(statusBefore) || StringUtil.isEmpty(statusAfter)) {
			return null;
		}

		try {
			return getResultList(
					CaseLog.QUERY_FIND_CASE_UUIDS_BY_CASE_UUIDS_AND_STATUS_BEFORE_STATUS_AFTER,
					String.class,
					new Param(CaseLog.PARAM_CASE_UUIDS, caseUUIDs),
					new Param(CaseLog.PARAM_STATUS_BEFORE, statusBefore),
					new Param(CaseLog.PARAM_STATUS_AFTER, statusAfter)
			);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting case UUIDs by case UUIDs list: " + caseUUIDs + ", status before: " + statusBefore + ", status after: " + statusAfter, e);
		}

		return null;
	}

	@Override
	public Map<String, Map<String, Timestamp>> getCasesIdsWithAllStatuses(String caseCode, Timestamp from, Timestamp to) {
		if (StringUtil.isEmpty(caseCode) || from == null || to == null) {
			return null;
		}

		try {
			List<Object[]> allData = getResultList(
					CaseLog.QUERY_GET_LOGS_FOR_CASES_BY_CASE_CODE_AND_DATE_RANGE,
					Object[].class,
					new Param("caseCode", caseCode),
					new Param("from", from),
					new Param("to", to)
			);
			if (ListUtil.isEmpty(allData)) {
				return null;
			}

			Map<String, Map<String, Timestamp>> results = new HashMap<>();
			for (Object[] data: allData) {
				if (ArrayUtil.isEmpty(data) || data.length < 2) {
					continue;
				}

				String caseUniqueId = (String) data[0];
				String statusAfter = (String) data[1];
				Timestamp timestamp = (Timestamp) data[2];
				if (StringUtil.isEmpty(caseUniqueId) || StringUtil.isEmpty(statusAfter)) {
					continue;
				}

				Map<String, Timestamp> caseData = results.get(caseUniqueId);
				if (caseData == null) {
					caseData = new HashMap<>();
					results.put(caseUniqueId, caseData);
				}

				caseData.put(statusAfter, timestamp == null ? IWTimestamp.RightNow().getTimestamp() : timestamp);
			}
			return results;
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting cases IDs with all statuses by case code " + caseCode + " and date range from " + from + " to " + to, e);
		}

		return null;
	}

}