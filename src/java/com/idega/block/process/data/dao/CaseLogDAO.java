package com.idega.block.process.data.dao;

import java.util.List;

import com.idega.block.process.data.bean.CaseLog;
import com.idega.core.persistence.GenericDao;

public interface CaseLogDAO extends GenericDao {

	public List<CaseLog> getCaseLogsWithType();

	public List<CaseLog> getCaseLogsByType(String type);

}