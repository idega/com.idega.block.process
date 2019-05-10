package com.idega.block.process.data.bean;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = CaseMileageReimbursement.TABLE_NAME)
@Cacheable
@NamedQueries({
	@NamedQuery(name = CaseMileageReimbursement.FIND_BY_IDS, query = "select r from CaseMileageReimbursement r where r.id in (:" + CaseMileageReimbursement.PARAM_IDS + ")"),
	@NamedQuery(
			name = CaseMileageReimbursement.GET_ALL,
			query = "from CaseMileageReimbursement"
	)
})
public class CaseMileageReimbursement implements Serializable {
	private static final long serialVersionUID = 3467615456388370633L;

	public static final String	TABLE_NAME = "proc_case_mileage",
								COLUMN_ID = TABLE_NAME + "_id",
								COLUMN_NAME = "name",
								COLUMN_PRICE = "price",
								COLUMN_RATE_TYPE = "type",
								COLUMN_SPECIFIED_QUANTITY = "specified_quantity",
								COLUMN_SPECIFIED_VAT = "specified_vat",

								FIND_BY_IDS = "CaseMileageReimbursement.findByIds",
								GET_ALL = "CaseMileageReimbursement.getAll",
								PARAM_IDS = "mileageReimbursementIds";

	public static final String MILEAGE_RATE_KM = "KM";
	public static final String MILEAGE_RATE_MIL = "Mil";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_ID)
	private Integer id;

	@Column(name = COLUMN_NAME, nullable = false)
	private String name;

	@Column(name = COLUMN_PRICE)
	private Double price;

	@Column(name = COLUMN_RATE_TYPE, nullable = false)
	private String rateType;

	@Column(name = COLUMN_SPECIFIED_QUANTITY)
	private Integer specifiedQuantity;

	@Column(name = COLUMN_SPECIFIED_VAT)
	private Double specifiedVAT; //Moms


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public Integer getSpecifiedQuantity() {
		return specifiedQuantity;
	}

	public void setSpecifiedQuantity(Integer specifiedQuantity) {
		this.specifiedQuantity = specifiedQuantity;
	}

	public Double getSpecifiedVAT() {
		return specifiedVAT;
	}

	public void setSpecifiedVAT(Double specifiedVAT) {
		this.specifiedVAT = specifiedVAT;
	}



}