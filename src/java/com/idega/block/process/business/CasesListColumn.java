package com.idega.block.process.business;

public class CasesListColumn {
	
	private String name = null;
	private String type = null;
	private String id = null;
	
	public CasesListColumn(String name, String type, String id) {
		this.name = name;
		this.type = type;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
