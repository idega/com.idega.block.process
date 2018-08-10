package com.idega.block.process.data.dao.impl;

import java.util.List;
import java.util.logging.Level;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idega.block.process.data.bean.Case;
import com.idega.block.process.data.dao.CaseDAO;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;
import com.idega.util.StringUtil;

@Repository("caseDAO")
@Transactional(readOnly = true)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CaseDAOImpl extends GenericDaoImpl implements CaseDAO {

	@Override
	public List<Integer> getCasesIdsByCaseSubject(String subject) {
		if (StringUtil.isEmpty(subject)) {
			getLogger().warning("Subject is not provided!");
			return null;
		}

		try {
			return getResultList(Case.FIND_ID_BY_SUBJECT, Integer.class, new Param(Case.PARAM_SUBJECT, subject));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting case ID with subject: " + subject, e);
		}

		return null;
	}

	@Override
	public Case getCaseById(Integer id) {
		if (id == null) {
			getLogger().warning("Case ID is not provided!");
			return null;
		}

		try {
			return getSingleResult(Case.FIND_ID_BY_ID, Case.class, new Param(Case.PARAM_ID, id));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting case by ID: " + id, e);
		}

		return null;
	}

	@Override
	public Case getCaseByUniqueId(String uuid) {
		if (StringUtil.isEmpty(uuid)) {
			getLogger().warning("Case UUID is not provided!");
			return null;
		}

		try {
			return getSingleResult(Case.FIND_ID_BY_UUID, Case.class, new Param(Case.PARAM_UUID, uuid));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting case by UUID: " + uuid, e);
		}

		return null;
	}

}