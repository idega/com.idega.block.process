package com.idega.block.process.data.dao.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idega.block.process.data.bean.Case;
import com.idega.block.process.data.bean.CaseConsultant;
import com.idega.block.process.data.bean.CaseInvoiceRecord;
import com.idega.block.process.data.bean.CaseMaterial;
import com.idega.block.process.data.bean.CaseMileageReimbursement;
import com.idega.block.process.data.bean.CaseReminder;
import com.idega.block.process.data.bean.CaseSettings;
import com.idega.block.process.data.dao.CaseDAO;
import com.idega.block.process.data.model.ReminderModel;
import com.idega.core.accesscontrol.data.bean.ICRole;
import com.idega.core.file.data.bean.ICFile;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;
import com.idega.user.dao.UserDAO;
import com.idega.user.data.bean.Group;
import com.idega.user.data.bean.User;
import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

@Repository("caseDAO")
@Transactional(readOnly = true)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CaseDAOImpl extends GenericDaoImpl implements CaseDAO {

	@Autowired
	private UserDAO userDAO;

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
			return getSingleResult(Case.FIND_CASE_BY_UUID, Case.class, new Param(Case.PARAM_UUID, uuid));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting case by UUID: " + uuid, e);
		}

		return null;
	}

	@Override
	public Integer getCaseIdByUniqueId(String uuid) {
		if (StringUtil.isEmpty(uuid)) {
			getLogger().warning("Case UUID is not provided!");
			return null;
		}

		try {
			return getSingleResult(Case.FIND_ID_BY_UUID, Integer.class, new Param(Case.PARAM_UUID, uuid));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting case ID by UUID: " + uuid, e);
		}

		return null;
	}

	@Override
	public List<Case> getAllCasesByStatuses(List<String> statuses) {
		return getByStatuses(statuses, Case.class);
	}

	@Override
	public List<Integer> getCasesIDsByStatuses(List<String> statuses) {
		return getByStatuses(statuses, Integer.class);
	}

	private <T> List<T> getByStatuses(List<String> statuses, Class<T> type) {
		if (ListUtil.isEmpty(statuses)) {
			getLogger().warning("Statuses are not provided!");
			return null;
		}

		boolean cases = false;
		try {
			cases = type.getName().equals(Case.class.getName());
			return getResultList(
					cases ? Case.FIND_ALL_BY_STATUSES : Case.FIND_IDS_BY_STATUSES,
					type,
					new Param(Case.PARAM_STATUSES, statuses)
			);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting cases " + (cases ? CoreConstants.EMPTY : "IDs") + " by statuses: " + statuses, e);
		}

		return null;
	}

	@Override
	public CaseSettings getCaseSettings(Integer settingsId) {
		if (settingsId == null) {
			return null;
		}

		try {
			return getSingleResult(CaseSettings.FIND_BY_ID, CaseSettings.class, new Param(CaseSettings.PARAM_ID, settingsId));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting case settings by ID: " + settingsId, e);
		}

		return null;
	}

	@Override
	public CaseSettings getSettings(Integer caseId) {
		if (caseId == null) {
			return null;
		}

		try {
			return getSingleResult(CaseSettings.FIND_BY_ID_CASE_ID, CaseSettings.class, new Param(CaseSettings.PARAM_CASE_ID, caseId));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting case settings by case ID: " + caseId, e);
		}

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public ReminderModel updateReminder(Integer reminderId, List<String> receiversUUIDs, Long timestamp, String message, List<Integer> dashboardRoleIds) {
		CaseReminder reminder = null;
		if (reminderId == null) {
			reminder = new CaseReminder();
		} else {
			List<CaseReminder> reminders = getResultList(CaseReminder.FIND_BY_IDS, CaseReminder.class, new Param(CaseReminder.PARAM_IDS, Arrays.asList(reminderId)));
			reminder = ListUtil.isEmpty(reminders) ? null : reminders.iterator().next();
		}
		if (reminder == null) {
			return null;
		}

		if (reminderId == null) {
			reminder.setReceivers(!ListUtil.isEmpty(receiversUUIDs) ? userDAO.findAll(null, receiversUUIDs, null) : null);
		}
		reminder.setTimestamp(timestamp == null ? null : new Timestamp(timestamp));
		reminder.setMessage(message);
		reminder.setDashboardRoles(dashboardRoleIds);


		if (reminder.getId() == null) {
			persist(reminder);
		} else {
			merge(reminder);
		}

		return reminder.getId() == null ? null : reminder;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateReminder(ReminderModel reminder) {
		if (reminder == null) {
			return;
		}

		if (reminder.getId() == null) {
			persist(reminder);
		} else {
			merge(reminder);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends Serializable> CaseSettings updateSettings(
			T uuid,
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
	) {
		if (uuid == null) {
			return null;
		}

		String caseUUID = uuid.toString();
		if (StringUtil.isEmpty(caseUUID)) {
			return null;
		}

		try {
			com.idega.block.process.data.bean.Case theCase = getCaseByUniqueId(caseUUID);
			if (theCase == null) {
				getLogger().warning("Case with UUID " + caseUUID + " does not exist");
				return null;
			}

			CaseSettings settings = null;
			if (settingsId == null) {
				settings = getSettings(theCase.getId());
			} else {
				settings = getCaseSettings(settingsId);
			}
			settings = settings == null ? new CaseSettings() : settings;

			settings.setAutoCloseAfterInnactiveForMonths(numberOfMonthsOfInnactivity);

			List<User> thirdParties = !ListUtil.isEmpty(thirdPartiesUUIDs) ? userDAO.findAll(null, thirdPartiesUUIDs, null) : new ArrayList<>();
			settings.setPredefinedListOfThirdPartiesToInvite(thirdParties);

			List<ReminderModel> reminders = null;
			if (!ListUtil.isEmpty(remindersIds)) {
				reminders = getResultList(CaseReminder.FIND_BY_IDS, ReminderModel.class, new Param(CaseReminder.PARAM_IDS, remindersIds));
			}
			settings.setReminders(reminders);

			List<ICRole> roles = null;
			if (!ListUtil.isEmpty(rolesKeys)) {
				roles = new ArrayList<>();
				for (String roleKey: rolesKeys) {
					if (StringUtil.isEmpty(roleKey)) {
						continue;
					}

					ICRole role = null;
					try {
						role = getSingleResult(ICRole.QUERY_FIND_ROLE_BY_KEY, ICRole.class, new Param("key", roleKey));
					} catch (Exception e) {
						getLogger().log(Level.WARNING, "Error getting role " + roleKey, e);
					}

					if (role != null) {
						roles.add(role);
					}
				}
			}
			settings.setRolesToHandle(roles);

			settings.setCaseId(theCase.getId());

			if (settings.getId() == null) {
				persist(settings);
			} else {
				merge(settings);
			}

			return settings == null || settings.getId() == null ? null : settings;
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error updating settings " + (settingsId == null ? CoreConstants.EMPTY : "(ID: " + settingsId + ") ") + "for case with UUID " + caseUUID, e);
		}

		return null;
	}

	@Override
	public Long getCountOfCasesCreatedAfterGivenTimestamp(Timestamp timestampAfter, List<String> caseManagerTypes) {
		if (timestampAfter == null) {
			getLogger().warning("Timestamp after is not provided!");
			return null;
		}

		try {
			if (ListUtil.isEmpty(caseManagerTypes)) {
				return getSingleResult(Case.COUNT_CASES_CREATED_AFTER_GIVEN_TIMESTAMP, Long.class, new Param(Case.PARAM_CREATED, timestampAfter));
			}

			return getSingleResult(Case.COUNT_CASES_CREATED_AFTER_GIVEN_TIMESTAMP_BY_CASE_MANAGERS, Long.class, new Param(Case.PARAM_CREATED, timestampAfter), new Param("caseManagerTypes", caseManagerTypes));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting count of cases created after the given timestamp: " + timestampAfter, e);
		}

		return null;
	}

	@Override
	public List<ReminderModel> getRemindersBySettingsId(Integer settingsId) {
		if (settingsId == null) {
			return null;
		}

		try {
			return getResultList(CaseSettings.FIND_REMINDERS_BY_CASE_ID, ReminderModel.class, new Param(CaseSettings.PARAM_ID, settingsId));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting reminders by settings ID: " + settingsId, e);
		}

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean removeReminderById(Integer reminderId) {
		if (reminderId == null) {
			return false;
		}

		try {
			//Remove reminder users
			Query qReminderUsers = getEntityManager().createNativeQuery("delete from " + CaseReminder.TABLE_NAME + "_rec" + " where " + CaseReminder.COLUMN_ID + " = ?");
			qReminderUsers.setParameter(1, reminderId);
			qReminderUsers.executeUpdate();

			//Remove reminder dashboard roles
			Query qReminderDashboardRoles = getEntityManager().createNativeQuery("delete from " + CaseReminder.TABLE_NAME + "_dr" + " where " + CaseReminder.JOIN_COLUMN_REMINDER_ID + " = ?");
			qReminderDashboardRoles.setParameter(1, reminderId);
			qReminderDashboardRoles.executeUpdate();

			//Remove reminder itself
			Query qReminder = getEntityManager().createNativeQuery("delete from " + CaseReminder.TABLE_NAME + " where " + CaseReminder.COLUMN_ID + " = ?");
			qReminder.setParameter(1, reminderId);
			qReminder.executeUpdate();

			return true;
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error removing the reminder and all it's children: " + reminderId, e);
		}
		return false;
	}


	@Override
	@Transactional(readOnly = false)
	public CaseMileageReimbursement updateMileageReimbursement(
			Integer id,
			String name,
			Double price,
			String type,
			Integer specifiedQuantity,
			Double specifiedVAT
	) {
		CaseMileageReimbursement mileageReimbursement = null;
		if (id == null) {
			mileageReimbursement = new CaseMileageReimbursement();
		} else {
			List<CaseMileageReimbursement> mileageReimbursements = getResultList(CaseMileageReimbursement.FIND_BY_IDS, CaseMileageReimbursement.class, new Param(CaseMileageReimbursement.PARAM_IDS, Arrays.asList(id)));
			mileageReimbursement = ListUtil.isEmpty(mileageReimbursements) ? null : mileageReimbursements.iterator().next();
		}
		if (mileageReimbursement == null) {
			return null;
		}

		mileageReimbursement.setName(name);
		mileageReimbursement.setPrice(price);
		mileageReimbursement.setRateType(type);
		mileageReimbursement.setSpecifiedQuantity(specifiedQuantity);
		mileageReimbursement.setSpecifiedVAT(specifiedVAT);

		if (mileageReimbursement.getId() == null) {
			persist(mileageReimbursement);
		} else {
			merge(mileageReimbursement);
		}

		return mileageReimbursement.getId() == null ? null : mileageReimbursement;
	}


	@Override
	@Transactional(readOnly = false)
	public CaseMaterial updateMaterial(
			Integer id,
			String name,
			Double price,
			Integer quantity,
			Integer specifiedQuantity,
			Double specifiedVAT
	) {
		CaseMaterial caseMaterial = null;
		if (id == null) {
			caseMaterial = new CaseMaterial();
		} else {
			List<CaseMaterial> caseMaterials = getResultList(CaseMaterial.FIND_BY_IDS, CaseMaterial.class, new Param(CaseMaterial.PARAM_IDS, Arrays.asList(id)));
			caseMaterial = ListUtil.isEmpty(caseMaterials) ? null : caseMaterials.iterator().next();
		}
		if (caseMaterial == null) {
			return null;
		}

		caseMaterial.setName(name);
		caseMaterial.setPrice(price);
		caseMaterial.setQuantity(quantity);
		caseMaterial.setSpecifiedQuantity(specifiedQuantity);
		caseMaterial.setSpecifiedVAT(specifiedVAT);

		if (caseMaterial.getId() == null) {
			persist(caseMaterial);
		} else {
			merge(caseMaterial);
		}

		return caseMaterial.getId() == null ? null : caseMaterial;
	}


	@Override
	@Transactional(readOnly = false)
	public CaseConsultant updateConsultant(
			Integer id,
			String name,
			Double price,
			Integer quantity,
			Integer specifiedQuantity,
			Double specifiedVAT
	) {
		CaseConsultant caseConsultant = null;
		if (id == null) {
			caseConsultant = new CaseConsultant();
		} else {
			List<CaseConsultant> caseConsultants = getResultList(CaseConsultant.FIND_BY_IDS, CaseConsultant.class, new Param(CaseConsultant.PARAM_IDS, Arrays.asList(id)));
			caseConsultant = ListUtil.isEmpty(caseConsultants) ? null : caseConsultants.iterator().next();
		}
		if (caseConsultant == null) {
			return null;
		}

		caseConsultant.setName(name);
		caseConsultant.setPrice(price);
		caseConsultant.setQuantity(quantity);
		caseConsultant.setSpecifiedQuantity(specifiedQuantity);
		caseConsultant.setSpecifiedVAT(specifiedVAT);

		if (caseConsultant.getId() == null) {
			persist(caseConsultant);
		} else {
			merge(caseConsultant);
		}

		return caseConsultant.getId() == null ? null : caseConsultant;
	}

	@Override
	public CaseInvoiceRecord getCaseInvoiceRecordById(Integer id) {
		if (id == null) {
			return null;
		}

		return getSingleResult(CaseInvoiceRecord.QUERY_FIND_BY_ID, CaseInvoiceRecord.class, new Param(CaseInvoiceRecord.PARAM_ID, id));
	}

	@Override
	public CaseInvoiceRecord getCaseInvoiceRecordByUniqeId(String uniqueId) {
		if (StringUtil.isEmpty(uniqueId)) {
			return null;
		}

		return getSingleResult(CaseInvoiceRecord.QUERY_FIND_BY_UUID, CaseInvoiceRecord.class, new Param(CaseInvoiceRecord.PARAM_UUID, uniqueId));
	}

	@Override
	public List<CaseInvoiceRecord> getAllByCaseId(Integer caseId) {
		if (caseId == null) {
			return null;
		}

		return getResultList(CaseInvoiceRecord.QUERY_FIND_ALL_BY_CASE_ID, CaseInvoiceRecord.class, new Param(CaseInvoiceRecord.PARAM_CASE_ID, caseId));
	}

	@Override
	@Transactional(readOnly = false)
	public CaseInvoiceRecord updateCaseInvoice(
			Integer caseId,
			Integer invoiceId,
			List<Integer> mileageReimbursementIds,
			List<Integer> materialIds,
			List<Integer> consultantIds
	) {
		if (caseId == null) {
			return null;
		}

		Case theCase = getCaseById(caseId);
		if (theCase == null) {
			return null;
		}

		CaseInvoiceRecord cir = null;
		if (invoiceId == null) {
			cir = new CaseInvoiceRecord();
		} else {
			cir = getCaseInvoiceRecordById(invoiceId);
		}
		if (cir == null) {
			return null;
		}

		cir.setCaseId(caseId);

		//Mileage reimbursements
		List<CaseMileageReimbursement> mileageReimbursements = null;
		if (!ListUtil.isEmpty(mileageReimbursementIds)) {
			mileageReimbursements = getResultList(CaseMileageReimbursement.FIND_BY_IDS, CaseMileageReimbursement.class, new Param(CaseMileageReimbursement.PARAM_IDS, mileageReimbursementIds));
		}
		cir.setMileageReimbursements(mileageReimbursements);

		//Materials
		List<CaseMaterial> caseMaterials = null;
		if (!ListUtil.isEmpty(materialIds)) {
			caseMaterials = getResultList(CaseMaterial.FIND_BY_IDS, CaseMaterial.class, new Param(CaseMaterial.PARAM_IDS, materialIds));
		}
		cir.setMaterials(caseMaterials);

		//Consultants
		List<CaseConsultant> caseConsultants = null;
		if (!ListUtil.isEmpty(consultantIds)) {
			caseConsultants = getResultList(CaseConsultant.FIND_BY_IDS, CaseConsultant.class, new Param(CaseConsultant.PARAM_IDS, consultantIds));
		}
		cir.setConsultants(caseConsultants);

		if (cir.getId() == null) {
			persist(cir);
		} else {
			merge(cir);
		}

		return cir;
	}

	@Override
	public List<Case> findByCaseSubjectAndCaseCodeAndDueDateLaterThanNow(String caseSubject, String caseCode) {
		if (StringUtil.isEmpty(caseSubject) || StringUtil.isEmpty(caseCode)) {
			return null;
		}

		try {
			return getResultList(
					Case.FIND_BY_CASE_SUBJECT_AND_CASE_CODE_AND_DUE_DATE_LATER_THAN_NOW,
					Case.class,
					new Param(Case.PARAM_SUBJECT, caseSubject),
					new Param(Case.PARAM_CASE_CODE, caseCode)
			);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting cases by case subject: " + caseSubject + " and case code: caseCode", e);
		}

		return null;

	}

}