package com.idega.block.process.data.bean;

import java.io.Serializable;
import java.sql.Timestamp;
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
import com.idega.user.data.bean.User;
import com.idega.util.DBUtil;

@Entity
@Table(name = CaseReminder.TABLE_NAME)
@Cacheable
public class CaseReminder implements Serializable, ReminderModel {

	private static final long serialVersionUID = 8770937327449914917L;

	public static final String	TABLE_NAME = Case.ENTITY_NAME + "_reminder",
								COLUMN_ID = TABLE_NAME + "_id";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_ID)
	private Integer id;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = User.class)
	@JoinTable(name = TABLE_NAME + "_rec", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = User.COLUMN_USER_ID) })
	private List<User> receivers;

	@Column(name = "timestamp")
	private Timestamp timestamp;

	@Column(name = "message", length = 65000)
	private String message;

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

}