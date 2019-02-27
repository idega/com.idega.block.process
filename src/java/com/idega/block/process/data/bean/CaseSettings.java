package com.idega.block.process.data.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.idega.block.process.data.model.ReminderModel;
import com.idega.block.process.data.model.SettingsModel;
import com.idega.core.accesscontrol.data.bean.ICRole;
import com.idega.user.data.bean.User;
import com.idega.util.DBUtil;

@Entity
@Table(
		name = CaseSettings.TABLE_NAME,
		indexes = {
				@Index(columnList = Case.COLUMN_CASE_ID, name = "case_settings_idx_case")
		}
)
@Cacheable
@NamedQueries({
	@NamedQuery(name = CaseSettings.FIND_BY_ID, query = "select s from CaseSettings s where s.id = :" + CaseSettings.PARAM_ID),
	@NamedQuery(name = CaseSettings.FIND_BY_ID_CASE_ID, query = "select s from CaseSettings s where s.caseId = :" + CaseSettings.PARAM_CASE_ID),
	@NamedQuery(name = CaseSettings.FIND_REMINDERS_BY_CASE_ID, query = "select s.reminders from CaseSettings s where s.id = :" + CaseSettings.PARAM_ID)
})
public class CaseSettings implements Serializable, SettingsModel {

	private static final long serialVersionUID = 1654153851491187223L;

	public static final String 	TABLE_NAME = Case.ENTITY_NAME + "_settings",
								COLUMN_ID = TABLE_NAME + "_id",

								FIND_BY_ID = "CaseSettings.findById",
								FIND_BY_ID_CASE_ID = "CaseSettings.findByCaseId",
								FIND_REMINDERS_BY_CASE_ID = "CaseSettings.findRemindersByCaseId",

								PARAM_ID = "caseSettingId",
								PARAM_CASE_ID = "caseId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_ID)
	private Integer id;

	@Column(name = Case.COLUMN_CASE_ID, unique = true)
	private Integer caseId;

	@Column(name = "months_of_innactivity")
	private Integer numberOfMonthsOfInnactivity;

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = CaseReminder.class, cascade = { CascadeType.REMOVE })
	@JoinTable(name = TABLE_NAME + "_rem", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = CaseReminder.COLUMN_ID, table = CaseReminder.TABLE_NAME) })
	private List<ReminderModel> reminders;

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinTable(name = TABLE_NAME + "_inv", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = User.COLUMN_USER_ID) })
	private List<User> thirdPartiesToInvite;

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = ICRole.class)
	@JoinTable(name = TABLE_NAME + "_rol", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = ICRole.COLUMN_ROLE_KEY, table = ICRole.ENTITY_NAME) })
	private List<ICRole> roles;

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void setAutoCloseAfterInnactiveForMonths(Integer numberOfMonthsOfInnactivity) {
		this.numberOfMonthsOfInnactivity = numberOfMonthsOfInnactivity;
	}

	@Override
	public Integer getAutoCloseAfterInnactiveForMonths() {
		return numberOfMonthsOfInnactivity;
	}

	@Override
	public void setReminders(List<ReminderModel> reminders) {
		this.reminders = reminders;
	}

	@Override
	public List<ReminderModel> getReminders() {
		reminders = DBUtil.getInstance().lazyLoad(reminders);
		return reminders;
	}

	@Override
	public void setPredefinedListOfThirdPartiesToInvite(List<User> thirdPartiesToInvite) {
		this.thirdPartiesToInvite = thirdPartiesToInvite;
	}

	@Override
	public List<User> getPredefinedListOfThirdPartiesToInvite() {
		thirdPartiesToInvite = DBUtil.getInstance().lazyLoad(thirdPartiesToInvite);
		return thirdPartiesToInvite;
	}

	@Override
	public void setRolesToHandle(List<ICRole> roles) {
		this.roles = roles;
	}

	@Override
	public List<ICRole> getRolesToHandle() {
		roles = DBUtil.getInstance().lazyLoad(roles);
		return roles;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

}