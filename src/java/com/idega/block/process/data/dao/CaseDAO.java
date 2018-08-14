package com.idega.block.process.data.dao;

import java.util.List;

import com.idega.block.process.data.bean.Case;
import com.idega.core.persistence.GenericDao;

public interface CaseDAO extends GenericDao {

	public List<Integer> getCasesIdsByCaseSubject(String subject);

	public Case getCaseById(Integer id);

	public Case getCaseByUniqueId(String uuid);

	public Integer getCaseIdByUniqueId(String uuid);

}