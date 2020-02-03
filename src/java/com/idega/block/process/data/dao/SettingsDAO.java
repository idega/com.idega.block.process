package com.idega.block.process.data.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.idega.block.process.data.model.ReminderModel;
import com.idega.block.process.data.model.SettingsModel;
import com.idega.core.file.data.bean.ICFile;
import com.idega.user.data.bean.Group;

public interface SettingsDAO {

	public ReminderModel updateReminder(Integer reminderId, List<String> receiversUUIDs, Long timestamp, String message, List<Integer> dashboardRoleIds);

	public void updateReminder(ReminderModel reminder);

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
			Integer fixedInvoicedHours,
			List<Integer> rateIds,
			List<Integer> mileageReimbursementIds,
			List<Integer> materialIds,
			List<Integer> consultantIds,
			Group referenceUnit,
			String invoiceReferenceCode,
			Integer priceRateId,
			List<ICFile> settingsFiles,
			ICFile icon
	);

}