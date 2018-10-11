package com.idega.block.process.data.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idega.block.process.data.bean.Case;
import com.idega.block.process.data.bean.CaseReminder;
import com.idega.block.process.data.bean.CaseSettings;
import com.idega.block.process.data.dao.CaseDAO;
import com.idega.block.process.data.model.ReminderModel;
import com.idega.core.accesscontrol.data.bean.ICRole;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;
import com.idega.user.dao.UserDAO;
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
		if (ListUtil.isEmpty(statuses)) {
			getLogger().warning("Statuses are not provided!");
			return null;
		}

		try {
			return getResultList(Case.FIND_ALL_BY_STATUSES, Case.class, new Param(Case.PARAM_STATUSES, statuses));
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting cases by statuses: " + statuses, e);
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
	public ReminderModel updateReminder(Integer reminderId, List<String> receiversUUIDs, Long timestamp, String message) {
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

		reminder.setReceivers(getUsers(receiversUUIDs));
		reminder.setTimestamp(timestamp == null ? null : new Timestamp(timestamp));
		reminder.setMessage(message);

		if (reminder.getId() == null) {
			persist(reminder);
		} else {
			merge(reminder);
		}

		return reminder.getId() == null ? null : reminder;
	}

	@Override
	@Transactional(readOnly = false)
	public CaseSettings updateCaseSettings(
			String caseUUID,
			Integer settingsId,
			Integer numberOfMonthsOfInnactivity,
			Set<String> thirdPartiesUUIDs,
			List<Integer> remindersIds,
			List<String> rolesKeys
	) {
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

			List<User> thirdParties = getUsers(thirdPartiesUUIDs);
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



}