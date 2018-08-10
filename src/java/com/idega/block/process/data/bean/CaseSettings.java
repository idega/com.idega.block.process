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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.idega.block.process.data.model.ReminderModel;
import com.idega.block.process.data.model.SettingsModel;
import com.idega.core.accesscontrol.data.bean.ICRole;
import com.idega.user.data.bean.User;
import com.idega.util.DBUtil;

@Entity
@Table(name = CaseSettings.TABLE_NAME)
@Cacheable
public class CaseSettings implements Serializable, SettingsModel {

	private static final long serialVersionUID = 1654153851491187223L;

	public static final String 	TABLE_NAME = Case.ENTITY_NAME + "_settings",
								COLUMN_ID = TABLE_NAME + "_id";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_ID)
	private Integer id;

	@Column(name = "months_of_innactivity")
	private Integer numberOfMonthsOfInnactivity;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = User.class)
	@JoinTable(name = TABLE_NAME + "_rem", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = CaseReminder.COLUMN_ID) })
	private List<ReminderModel> reminders;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = User.class)
	@JoinTable(name = TABLE_NAME + "_inv", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = User.COLUMN_USER_ID) })
	private List<User> thirdPartiesToInvite;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = User.class)
	@JoinTable(name = TABLE_NAME + "_rol", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = ICRole.COLUMN_ROLE_KEY) })
	private List<ICRole> roles;

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

}