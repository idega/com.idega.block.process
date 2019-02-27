package com.idega.block.process.data.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.idega.block.process.data.model.ReminderModel;
import com.idega.block.process.data.model.SettingsModel;

public interface SettingsDAO {

	public ReminderModel updateReminder(Integer reminderId, List<String> receiversUUIDs, Long timestamp, String message, List<Integer> dashboardRoleIds);

	public List<ReminderModel> getRemindersBySettingsId(Integer settingsId);

	public boolean removeReminderById(Integer reminderId);

	public <T extends Serializable> SettingsModel updateSettings(
			T id,
			Integer settingsId,
			Integer numberOfMonthsOfInnactivity,
			Set<String> thirdPartiesUUIDs,
			List<Integer> remindersIds,
			List<String> rolesKeys,
			List<Integer> signatureProfileIds,
			List<Integer> decisionTemplateIds,
			String invoicingType,
			Double price,
			Integer fixedInvoicedHours
	);

}