package com.idega.block.process.presentation.beans;

public class CaseListPropertiesBean {

	private String type;
	private String instanceId;
	private String componentId;
	
	private boolean showCheckBoxes;
	private boolean usePDFDownloadColumn;
	private boolean allowPDFSigning;
	private boolean showStatistics;
	private boolean hideEmptySection;
	private boolean showCaseNumberColumn = true;
	private boolean showCreationTimeInDateColumn = true;
	private boolean addCredentialsToExernalUrls;
	
	private int pageSize;
	private int page;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public boolean isShowCheckBoxes() {
		return showCheckBoxes;
	}
	public void setShowCheckBoxes(boolean showCheckBoxes) {
		this.showCheckBoxes = showCheckBoxes;
	}
	public boolean isUsePDFDownloadColumn() {
		return usePDFDownloadColumn;
	}
	public void setUsePDFDownloadColumn(boolean usePDFDownloadColumn) {
		this.usePDFDownloadColumn = usePDFDownloadColumn;
	}
	public boolean isAllowPDFSigning() {
		return allowPDFSigning;
	}
	public void setAllowPDFSigning(boolean allowPDFSigning) {
		this.allowPDFSigning = allowPDFSigning;
	}
	public boolean isShowStatistics() {
		return showStatistics;
	}
	public void setShowStatistics(boolean showStatistics) {
		this.showStatistics = showStatistics;
	}
	public boolean isHideEmptySection() {
		return hideEmptySection;
	}
	public void setHideEmptySection(boolean hideEmptySection) {
		this.hideEmptySection = hideEmptySection;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public boolean isShowCaseNumberColumn() {
		return showCaseNumberColumn;
	}
	public void setShowCaseNumberColumn(boolean showCaseNumberColumn) {
		this.showCaseNumberColumn = showCaseNumberColumn;
	}
	public boolean isAddCredentialsToExernalUrls() {
		return addCredentialsToExernalUrls;
	}
	public void setAddCredentialsToExernalUrls(boolean addCredentialsToExernalUrls) {
		this.addCredentialsToExernalUrls = addCredentialsToExernalUrls;
	}
	public boolean isShowCreationTimeInDateColumn() {
		return showCreationTimeInDateColumn;
	}
	public void setShowCreationTimeInDateColumn(boolean showCreationTimeInDateColumn) {
		this.showCreationTimeInDateColumn = showCreationTimeInDateColumn;
	}
	
}
