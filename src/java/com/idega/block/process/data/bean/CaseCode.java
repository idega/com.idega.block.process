package com.idega.block.process.data.bean;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.idega.block.process.data.CaseCodeBMPBean;
import com.idega.block.process.data.model.CaseCodeModel;

@Entity
@Table(name = CaseCodeBMPBean.TABLE_NAME)
@Cacheable
public class CaseCode implements Serializable, CaseCodeModel {

	private static final long serialVersionUID = 4194300231248081158L;

	@Id
	@Column(name = CaseCodeBMPBean.CASE_CODE, length = 7)
	private String code;

	@Override
	public Object getPrimaryKey() {
		return getCode();
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}