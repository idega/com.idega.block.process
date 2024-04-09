package com.idega.block.process.data.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.idega.block.process.data.CaseLogBMPBean;
import com.idega.user.data.bean.User;

@Entity
@Table(name = CaseLogBMPBean.TABLE_NAME, indexes = {
		@Index(name = "CASE_LOG_CASE_INDEX", columnList = CaseLogBMPBean.COLUMN_CASE_ID),
		@Index(name = "CASE_LOG_TYPE_INDEX", columnList = CaseLogBMPBean.COLUMN_TYPE),
		@Index(name = "CASE_LOG_STATUS_INDEX", columnList = CaseLogBMPBean.COLUMN_STATUS)
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
	@NamedQuery(name = CaseLog.QUERY_FIND_WITH_TYPE, query = "select l from CaseLog l where l.type is not null order by l.timestamp desc"),
	@NamedQuery(name = CaseLog.QUERY_COUNT_WITH_TYPE, query = "select count(l.id) from CaseLog l where l.type is not null"),

	@NamedQuery(name = CaseLog.QUERY_FIND_BY_TYPE, query = "select l from CaseLog l where l.type = :" + CaseLog.PARAM_TYPE + " order by l.timestamp desc"),
	@NamedQuery(name = CaseLog.QUERY_COUNT_BY_TYPE, query = "select count(l.id) from CaseLog l where l.type = :" + CaseLog.PARAM_TYPE),

	@NamedQuery(name = CaseLog.QUERY_FIND_CASE_UUIDS_BY_CASE_UUIDS_AND_STATUS_BEFORE_STATUS_AFTER, query = "SELECT DISTINCT(l.theCase.uniqueId) FROM CaseLog l "
			+ " WHERE l.theCase.uniqueId IN (:" + CaseLog.PARAM_CASE_UUIDS + ") "
			+ " AND l.statusBefore = :" + CaseLog.PARAM_STATUS_BEFORE
			+ " AND l.statusAfter = :" + CaseLog.PARAM_STATUS_AFTER),

	@NamedQuery(
			name = CaseLog.QUERY_GET_LOGS_FOR_CASES_BY_CASE_CODE_AND_DATE_RANGE,
			query = "select pc.uniqueId, l.statusAfter, l.timestamp from CaseLog l inner join l.theCase pc where pc.caseCode = :caseCode and pc.created >= :from and pc.created <= :to"
	)
})
public class CaseLog implements Serializable {

	private static final long serialVersionUID = -1197828866148406406L;

	public static final String	QUERY_FIND_WITH_TYPE = "CaseLog.findWithType",
								QUERY_COUNT_WITH_TYPE = "CaseLog.countWithType",
								QUERY_FIND_BY_TYPE = "CaseLog.findByType",
								QUERY_COUNT_BY_TYPE = "CaseLog.countByType",
								QUERY_FIND_CASE_UUIDS_BY_CASE_UUIDS_AND_STATUS_BEFORE_STATUS_AFTER = "CaseLog.caseUUIDSByCaseUUIDSAndStatusBeforeAndStatusAfter",
								QUERY_GET_LOGS_FOR_CASES_BY_CASE_CODE_AND_DATE_RANGE = "CaseLog.getLogsForCasesByCaseCodeAndDateRange",

								PARAM_TYPE = "type",
								PARAM_STATUS = "status",
								PARAM_CASE_UUIDS = "caseUUIDs",
								PARAM_STATUS_BEFORE = "statusBefore",
								PARAM_STATUS_AFTER = "statusAfter";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = CaseLogBMPBean.COLUMN_CASE_LOG_ID)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = CaseLogBMPBean.COLUMN_CASE_ID)
	private Case theCase;

	@Column(name = CaseLogBMPBean.COLUMN_CASE_STATUS_BEFORE)
	private String statusBefore;

	@Column(name = CaseLogBMPBean.COLUMN_CASE_STATUS_AFTER)
	private String statusAfter;

	@ManyToOne
	@JoinColumn(name = CaseLogBMPBean.COLUMN_PERFORMER)
	private User performer;

	@Column(name = CaseLogBMPBean.COLUMN_TIMESTAMP)
	private Timestamp timestamp;

	@Column(name = CaseLogBMPBean.COLUMN_COMMENT)
	private String comment;

	@Column(name = CaseLogBMPBean.COLUMN_TYPE)
	private String type;

	@Column(name = CaseLogBMPBean.COLUMN_STATUS)
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Case getCase() {
		return theCase;
	}

	public void setCase(Case theCase) {
		this.theCase = theCase;
	}

	public String getStatusBefore() {
		return statusBefore;
	}

	public void setStatusBefore(String statusBefore) {
		this.statusBefore = statusBefore;
	}

	public String getStatusAfter() {
		return statusAfter;
	}

	public void setStatusAfter(String statusAfter) {
		this.statusAfter = statusAfter;
	}

	public User getPerformer() {
		return performer;
	}

	public void setPerformer(User performer) {
		this.performer = performer;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Case log ID " + getId();
	}

}