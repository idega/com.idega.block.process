package com.idega.block.process.data.dao.impl;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idega.block.process.data.bean.CaseLog;
import com.idega.block.process.data.dao.CaseLogDAO;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;

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

}