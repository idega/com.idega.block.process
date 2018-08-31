package com.idega.block.process.data.model;

import java.util.List;

import com.idega.core.accesscontrol.data.bean.ICRole;
import com.idega.user.data.bean.User;

public interface SettingsModel {

	public Integer getId();

	public void setAutoCloseAfterInnactiveForMonths(Integer numberOfMonthsOfInnactivity);

	public Integer getAutoCloseAfterInnactiveForMonths();

	public void setReminders(List<ReminderModel> reminders);

	public List<ReminderModel> getReminders();

	public void setPredefinedListOfThirdPartiesToInvite(List<User> thirdPartiesToInvite);

	public List<User> getPredefinedListOfThirdPartiesToInvite();

	public void setRolesToHandle(List<ICRole> roles);

	public List<ICRole> getRolesToHandle();

}