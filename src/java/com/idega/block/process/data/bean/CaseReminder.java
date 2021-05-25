package com.idega.block.process.data.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hsqldb.lib.StringUtil;

import com.idega.block.process.data.model.ReminderModel;
import com.idega.user.data.bean.User;
import com.idega.util.CoreConstants;
import com.idega.util.DBUtil;
import com.idega.util.ListUtil;

@Entity
@Table(name = CaseReminder.TABLE_NAME)
@Cacheable
@NamedQueries({
	@NamedQuery(name = CaseReminder.FIND_BY_IDS, query = "select r from CaseReminder r where r.id in (:" + CaseReminder.PARAM_IDS + ")"),
})
public class CaseReminder implements Serializable, ReminderModel {

	private static final long serialVersionUID = 8770937327449914917L;

	public static final String	TABLE_NAME = Case.ENTITY_NAME + "_reminder",
								COLUMN_ID = TABLE_NAME + "_id",
								COLUMN_DASHBOARD_ROLE_ID = "dashboard_role_id",
								JOIN_COLUMN_REMINDER_ID = "reminder_id",

								FIND_BY_IDS = "CaseReminder.findByIds",
								PARAM_IDS = "caseReminderIds";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_ID)
	private Integer id;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = TABLE_NAME + "_rec", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = User.COLUMN_USER_ID) })
	private List<User> receivers;

	@Column(name = "timestamp")
	private Timestamp timestamp;

	@Column(name = "message", length = 16000)
	private String message;

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(
			name = TABLE_NAME + "_dr",
			joinColumns=@JoinColumn(name=JOIN_COLUMN_REMINDER_ID))
	@Column(name = COLUMN_DASHBOARD_ROLE_ID)
	private List<Integer> dashboardRoles;

	@Column(name = "reminder_sent")
	private Boolean reminderSent;

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void setReceivers(List<User> receivers) {
		this.receivers = receivers;
	}

	@Override
	public List<User> getReceivers() {
		receivers = DBUtil.getInstance().lazyLoad(receivers);
		return receivers;
	}

	@Override
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public Timestamp getTimestamp() {
		return timestamp;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public List<Integer> getDashboardRoles() {
		return dashboardRoles;
	}

	@Override
	public void setDashboardRoles(List<Integer> dashboardRoles) {
		this.dashboardRoles = dashboardRoles;
	}

	public Boolean getReminderSent() {
		return reminderSent;
	}

	@Override
	public boolean isReminderSent() {
		return reminderSent == null ? Boolean.FALSE : reminderSent;
	}

	@Override
	public void setReminderSent(Boolean reminderSent) {
		this.reminderSent = reminderSent;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder info = new StringBuilder();

		if (this.timestamp != null) {
			info.append(timestamp).append(CoreConstants.COLON);
		}

		if (!StringUtil.isEmpty(this.message)) {
			info.append(message).append(CoreConstants.SPACE);
		}

		List<User> users = getReceivers();
		if (!ListUtil.isEmpty(users)) {
			for (User user: users) {
				info.append(user.getName()).append(CoreConstants.SPACE);
			}
		}

		return info.toString();
	}
}