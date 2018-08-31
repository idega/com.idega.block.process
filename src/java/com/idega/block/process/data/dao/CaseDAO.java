package com.idega.block.process.data.dao;

import java.util.List;

import com.idega.block.process.data.bean.Case;
import com.idega.block.process.data.bean.CaseSettings;
import com.idega.block.process.data.model.ReminderModel;
import com.idega.block.process.data.model.SettingsModel;
import com.idega.core.persistence.GenericDao;

public interface CaseDAO extends GenericDao {

	public List<Integer> getCasesIdsByCaseSubject(String subject);

	public Case getCaseById(Integer id);

	public Case getCaseByUniqueId(String uuid);

	public Integer getCaseIdByUniqueId(String uuid);

	public CaseSettings getCaseSettings(Integer settingsId);

	public ReminderModel updateReminder(Integer reminderId, List<String> receiversUUIDs, Long timestamp, String message);

	public SettingsModel updateCaseSettings(
			String caseUUID,
			Integer settingsId,
			Integer numberOfMonthsOfInnactivity,
			List<String> thirdPartiesUUIDs,
			List<Integer> remindersIds,
			List<String> rolesKeys
	);

}