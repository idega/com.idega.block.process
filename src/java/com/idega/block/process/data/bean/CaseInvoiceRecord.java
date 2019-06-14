package com.idega.block.process.data.bean;

import java.io.Serializable;
import java.sql.Date;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = CaseInvoiceRecord.ENTITY_NAME)
@Cacheable
@NamedQueries({
	@NamedQuery(name = CaseInvoiceRecord.QUERY_FIND_BY_ID, query = "select cir from CaseInvoiceRecord cir where cir.id = :" + CaseInvoiceRecord.PARAM_ID),
	@NamedQuery(name = CaseInvoiceRecord.QUERY_FIND_ALL_BY_CASE_ID, query = "select cir from CaseInvoiceRecord cir where cir.caseId = :" + CaseInvoiceRecord.PARAM_CASE_ID + " order by cir.created desc")
})
public class CaseInvoiceRecord implements Serializable {

	private static final long serialVersionUID = -2262656689050727593L;

	public static final String	ENTITY_NAME = Case.ENTITY_NAME + "_invoice_record",

								COLUMN_ID = ENTITY_NAME + "_id",

								QUERY_FIND_BY_ID = "CaseInvoiceRecord.findById",
								QUERY_FIND_ALL_BY_CASE_ID = "CaseInvoiceRecord.findAllByCaseId",

								PARAM_ID = "id",
								PARAM_CASE_ID = "caseId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_ID)
	private Integer id;

	@Column(name = Case.COLUMN_CASE_ID)
	private Integer caseId;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = CaseMileageReimbursement.class)
	@JoinTable(name = ENTITY_NAME + "_ml", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = CaseMileageReimbursement.COLUMN_ID, table = CaseMileageReimbursement.TABLE_NAME) })
	private List<CaseMileageReimbursement> mileageReimbursements;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = CaseMaterial.class)
	@JoinTable(name = ENTITY_NAME + "_mt", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = CaseMaterial.COLUMN_ID, table = CaseMaterial.TABLE_NAME) })
	private List<CaseMaterial> materials;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = CaseConsultant.class)
	@JoinTable(name = ENTITY_NAME + "_cl", joinColumns = { @JoinColumn(name = COLUMN_ID) }, inverseJoinColumns = { @JoinColumn(name = CaseConsultant.COLUMN_ID, table = CaseConsultant.TABLE_NAME) })
	private List<CaseConsultant> consultants;

	@Column(name = "created")
	private Date created;

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

	public List<CaseMileageReimbursement> getMileageReimbursements() {
		return mileageReimbursements;
	}

	public void setMileageReimbursements(List<CaseMileageReimbursement> mileageReimbursements) {
		this.mileageReimbursements = mileageReimbursements;
	}

	public List<CaseMaterial> getMaterials() {
		return materials;
	}

	public void setMaterials(List<CaseMaterial> materials) {
		this.materials = materials;
	}

	public List<CaseConsultant> getConsultants() {
		return consultants;
	}

	public void setConsultants(List<CaseConsultant> consultants) {
		this.consultants = consultants;
	}



	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "ID: " + getId();
	}

}