package com.idega.block.process.data.dao;

import java.util.List;

import com.idega.core.persistence.GenericDao;

public interface CaseDAO extends GenericDao {

	public List<Integer> getCasesIdsByCaseSubject(String subject);

}