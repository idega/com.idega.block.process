package com.idega.block.process.business.file;

import java.io.Serializable;

public class CaseAttachment implements Serializable {

	private static final long serialVersionUID = 756355544561899314L;

	private String name;
	private byte[] bytes;
	
	public CaseAttachment() {
		super();
	}

	public CaseAttachment(String name, byte[] bytes) {
		this();
		
		this.name = name;
		this.bytes = bytes;
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

}