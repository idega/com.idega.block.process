package com.idega.block.process.data.dao.impl;

import java.util.logging.Level;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idega.block.process.data.bean.CaseInvoiceInfo;
import com.idega.block.process.data.dao.CaseInvoiceInfoDAO;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;

@Repository("caseInvoiceInfoDAO")
@Transactional(readOnly = true)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CaseInvoiceInfoDAOImpl extends GenericDaoImpl implements CaseInvoiceInfoDAO {

	@Override
	public CaseInvoiceInfo getByCaseId(Integer caseId) {
		if (caseId == null) {
			getLogger().warning("Case id is not provided!");
			return null;
		}

		try {
			return getSingleResult(CaseInvoiceInfo.FIND_BY_CASE_ID, CaseInvoiceInfo.class, new Param(CaseInvoiceInfo.PARAM_CASE_ID, caseId));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting case invoice info by case id: " + caseId, e);
		}

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public CaseInvoiceInfo createUpdateCaseInvoiceInfo(CaseInvoiceInfo info) {
		if (info != null) {
			try {
				if (info.getId() == null) {
					persist(info);
				} else {
					merge(info);
				}
			} catch (Exception e) {
				getLogger().log(Level.WARNING, "Could not create/update case invoice info. Error message was: " + e.getLocalizedMessage(), e);
			}
		}
		return info;
	}


}