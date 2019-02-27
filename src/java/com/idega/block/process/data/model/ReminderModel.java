package com.idega.block.process.data.model;

import java.sql.Timestamp;
import java.util.List;

import com.idega.user.data.bean.User;

public interface ReminderModel {

	public Integer getId();

	public void setReceivers(List<User> receivers);

	public List<User> getReceivers();

	public void setTimestamp(Timestamp timestamp);

	public Timestamp getTimestamp();

	public void setMessage(String message);

	public String getMessage();

	public void setDashboardRoles(List<Integer> dashboardRoleIds);

	public List<Integer> getDashboardRoles();

}