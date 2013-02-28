package com.idega.block.process.business.pdf;

import java.io.Serializable;

import com.idega.util.CoreConstants;
import com.idega.util.FileUtil;

public class CasePDF implements Serializable {

	private static final long serialVersionUID = 7711158647594524720L;

	private Integer caseId;

	private String name;

	private byte[] bytes;

	public CasePDF() {
		super();
	}

	public CasePDF(Integer caseId, String name, byte[] bytes) {
		this.caseId = caseId;
		this.name = name;
		this.bytes = bytes;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String toString() {
		return "PDF for case " + getCaseId() + ", name: " + getName() + (bytes == null ? CoreConstants.EMPTY : ", size: " +
				FileUtil.getHumanReadableSize(bytes.length));
	}
}