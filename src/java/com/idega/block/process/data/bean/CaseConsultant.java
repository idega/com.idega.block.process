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
@Table(name = CaseConsultant.TABLE_NAME)
@Cacheable
@NamedQueries({
	@NamedQuery(name = CaseConsultant.FIND_BY_IDS, query = "select r from CaseConsultant r where r.id in (:" + CaseConsultant.PARAM_IDS + ")"),
	@NamedQuery(
			name = CaseConsultant.GET_ALL,
			query = "from CaseConsultant"
	)
})
public class CaseConsultant implements Serializable {
	private static final long serialVersionUID = 6742979473379006208L;

	public static final String	TABLE_NAME = "proc_case_consultant",
								COLUMN_ID = TABLE_NAME + "_id",
								COLUMN_NAME = "name",
								COLUMN_QUANTITY = "quantity",
								COLUMN_PRICE = "price",
								COLUMN_SPECIFIED_QUANTITY = "specified_quantity",
								COLUMN_SPECIFIED_VAT = "specified_vat",

								FIND_BY_IDS = "CaseConsultant.findByIds",
								GET_ALL = "CaseConsultant.getAll",
								PARAM_IDS = "caseConsultantIds";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_ID)
	private Integer id;

	@Column(name = COLUMN_NAME, nullable = false)
	private String name;

	@Column(name = COLUMN_QUANTITY)
	private Integer quantity;

	@Column(name = COLUMN_PRICE)
	private Double price;

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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