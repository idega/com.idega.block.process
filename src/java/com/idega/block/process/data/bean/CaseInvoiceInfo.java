package com.idega.block.process.data.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.idega.util.IWTimestamp;

@Entity
@Table(name = CaseInvoiceInfo.ENTITY_NAME)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
	@NamedQuery(name = CaseInvoiceInfo.FIND_BY_CASE_ID, query = "select c from CaseInvoiceInfo c where c.caseId = :" + CaseInvoiceInfo.PARAM_CASE_ID)
})
public class CaseInvoiceInfo implements Serializable {

	private static final long serialVersionUID = 1951716293635741958L;

	private static final String COLUMN_ID = "id",
								COLUMN_CASE_ID = "case_id",
								COLUMN_CASE_NUMBER = "CASE_NUMBER",
								COLUMN_PAYERS_PERSONAL_ID = "payers_personal_id",
								COLUMN_PAYERS_NAME = "payers_name",
								COLUMN_CREATED = "created",
								COLUMN_PAYMENT_DATE = "payment_date",
								COLUMN_AMOUNT = "amount",
								COLUMN_CURRENCY = "currency",
								COLUMN_IS_READ = "is_read",
								COLUMN_READ_DATE = "read_date",
								COLUMN_IS_PROCESSED = "is_processed",
								COLUMN_IS_APPROVED = "is_approved",
								COLUMN_IS_DENIED = "is_denied",
								COLUMN_CRATED_BY = "created_by";

	public static final String	ENTITY_NAME = "proc_case_invoice_info",

								PARAM_CASE_ID = "caseId",
								PARAM_CASE_NUMBER = "caseNumber", //identifier

								FIND_BY_CASE_ID = "CaseInvoiceInfo.findByCaseId",
								FIND_BY_ID = "CaseInvoiceInfo.findById";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_ID)
	private Integer id;

	@Column(name = COLUMN_CASE_ID, unique = true, nullable = false)
	private Integer caseId;

	@Column(name = COLUMN_CASE_NUMBER, length = 30, unique = true, nullable = false)
	private String caseNumber;

	@Column(name = COLUMN_PAYERS_PERSONAL_ID)
	private String payersPersonalId;

	@Column(name = COLUMN_PAYERS_NAME)
	private String payersName;

	@Column(name = COLUMN_CREATED)
	private Timestamp created;

	@Column(name = COLUMN_PAYMENT_DATE)
	private Timestamp paymentDate;

	@Column(name = COLUMN_AMOUNT)
	private Double amount;

	@Column(name = COLUMN_CURRENCY, length = 3)
	private String currency; //ISK or EUR or SEK

	@Column(name = COLUMN_IS_READ)
	private Boolean read = Boolean.FALSE;

	@Column(name = COLUMN_READ_DATE)
	private Timestamp readDate = null;

	@Column(name = COLUMN_IS_PROCESSED)
	private Boolean processed = Boolean.FALSE;

	@Column(name = COLUMN_IS_APPROVED)
	private Boolean approved = Boolean.FALSE;

	@Column(name = COLUMN_IS_DENIED)
	private Boolean denied = Boolean.FALSE;

	@Column(name = COLUMN_CRATED_BY)
	private Integer createdBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getPayersPersonalId() {
		return payersPersonalId;
	}

	public void setPayersPersonalId(String payersPersonalId) {
		this.payersPersonalId = payersPersonalId;
	}

	public String getPayersName() {
		return payersName;
	}

	public void setPayersName(String payersName) {
		this.payersName = payersName;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setDefaultPaymentDate() {
		if (this.paymentDate == null && getCreated() != null) {
			IWTimestamp paymentDateIWT = new IWTimestamp(getCreated());
			if (paymentDateIWT != null) {
				paymentDateIWT.addWeeks(2);
				this.paymentDate = paymentDateIWT.getTimestamp();
			}
		}
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Timestamp getReadDate() {
		return readDate;
	}

	public void setReadDate(Timestamp readDate) {
		this.readDate = readDate;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Boolean getDenied() {
		return denied;
	}

	public void setDenied(Boolean denied) {
		this.denied = denied;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		Integer id = getId();
		Class<?> theClass = getClass();
		return id == null ?
				"Unknown ID for " + theClass.getName() :
				theClass.getSimpleName() + ": " + id;
	}

}