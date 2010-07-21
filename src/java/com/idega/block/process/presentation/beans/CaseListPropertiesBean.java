package com.idega.block.process.presentation.beans;

import java.util.List;

public class CaseListPropertiesBean {

	private String type;
	private String instanceId;
	private String componentId;
	private String commentsManagerIdentifier;
	private String dateCustomValueVariable;
	private String dateCustomLabelLocalizationKey;
	private String criteriasId;
	
	private boolean showCheckBoxes;
	private boolean usePDFDownloadColumn;
	private boolean allowPDFSigning;
	private boolean showStatistics;
	private boolean hideEmptySection;
	private boolean showCaseNumberColumn = true;
	private boolean showCreationTimeInDateColumn = true;
	private boolean addCredentialsToExernalUrls;
	private boolean showCreatorColumn = true;
	private boolean showAttachmentStatistics;
	private boolean showOnlyCreatorInContacts;
	private boolean onlySubscribedCases;
	
	private int pageSize;
	private int page;
	private int foundResults;
	
	private List<String> caseCodes;
	private List<String> statusesToShow;
	private List<String> statusesToHide;
	
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
	public List<String> getCaseCodes() {
		return caseCodes;
	}
	public void setCaseCodes(List<String> caseCodes) {
		this.caseCodes = caseCodes;
	}
	public List<String> getStatusesToShow() {
		return statusesToShow;
	}
	public void setStatusesToShow(List<String> statusesToShow) {
		this.statusesToShow = statusesToShow;
	}
	public List<String> getStatusesToHide() {
		return statusesToHide;
	}
	public void setStatusesToHide(List<String> statusesToHide) {
		this.statusesToHide = statusesToHide;
	}
	public String getCommentsManagerIdentifier() {
		return commentsManagerIdentifier;
	}
	public void setCommentsManagerIdentifier(String commentsManagerIdentifier) {
		this.commentsManagerIdentifier = commentsManagerIdentifier;
	}
	public String getDateCustomValueVariable() {
		return dateCustomValueVariable;
	}
	public void setDateCustomValueVariable(String dateCustomValueVariable) {
		this.dateCustomValueVariable = dateCustomValueVariable;
	}
	public String getDateCustomLabelLocalizationKey() {
		return dateCustomLabelLocalizationKey;
	}
	public void setDateCustomLabelLocalizationKey(
			String dateCustomLabelLocalizationKey) {
		this.dateCustomLabelLocalizationKey = dateCustomLabelLocalizationKey;
	}
	public boolean isShowCreatorColumn() {
		return showCreatorColumn;
	}
	public void setShowCreatorColumn(boolean showCreatorColumn) {
		this.showCreatorColumn = showCreatorColumn;
	}
	public boolean isShowAttachmentStatistics() {
		return showAttachmentStatistics;
	}
	public void setShowAttachmentStatistics(boolean showAttachmentStatistics) {
		this.showAttachmentStatistics = showAttachmentStatistics;
	}
	public boolean isShowOnlyCreatorInContacts() {
		return showOnlyCreatorInContacts;
	}
	public void setShowOnlyCreatorInContacts(boolean showOnlyCreatorInContacts) {
		this.showOnlyCreatorInContacts = showOnlyCreatorInContacts;
	}
	public boolean isOnlySubscribedCases() {
		return onlySubscribedCases;
	}
	public void setOnlySubscribedCases(boolean onlySubscribedCases) {
		this.onlySubscribedCases = onlySubscribedCases;
	}
	public String getCriteriasId() {
		return criteriasId;
	}
	public void setCriteriasId(String criteriasId) {
		this.criteriasId = criteriasId;
	}
	public int getFoundResults() {
		return foundResults;
	}
	public void setFoundResults(int foundResults) {
		this.foundResults = foundResults;
	}
}