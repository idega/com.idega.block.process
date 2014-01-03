package com.idega.block.process.business.pdf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.idega.block.process.business.file.CaseAttachment;
import com.idega.util.CoreConstants;
import com.idega.util.FileUtil;

public class CasePDF implements Serializable {

	private static final long serialVersionUID = 7711158647594524720L;

	private Integer caseId;

	private String name, identifier;

	private byte[] bytes;

	private List<CaseAttachment> attachments;
	
	public CasePDF() {
		super();
	}
	
	public CasePDF(Integer caseId, String name, String identifier, byte[] bytes) {
		this();
		
		this.caseId = caseId;
		this.name = name;
		this.identifier = identifier;
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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public List<CaseAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<CaseAttachment> attachments) {
		this.attachments = attachments;
	}
	
	public void addAttachment(CaseAttachment attachment) {
		if (attachment == null) {
			return;
		}
		
		if (attachments == null) {
			attachments = new ArrayList<CaseAttachment>();
		}
		
		attachments.add(attachment);
	}

	@Override
	public String toString() {
		return "PDF for case " + getCaseId() + ", name: " + getName() + ", identifier: " + getIdentifier() +
				(bytes == null ? CoreConstants.EMPTY : ", size: " +	FileUtil.getHumanReadableSize(bytes.length));
	}
}