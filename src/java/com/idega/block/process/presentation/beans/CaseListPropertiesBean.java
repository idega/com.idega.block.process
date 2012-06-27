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
	private String	specialBackPage,
					casesListCustomizer;

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
	private boolean useJavascriptForPageSwitching = true;
	private boolean showLoadingMessage = true;
	private boolean waitForAllCasePartsLoaded = true;
	
	private boolean showLogExportButton,
					showCaseStatus = true,
					showExportAllCasesButton,
					showComments = true,
					showContacts = true;

	private int pageSize;
	private int page;
	private int foundResults;

	private List<String>	caseCodes,
							statusesToShow,
							statusesToHide,
							customColumns;

	/**
	 * @return the showLoadingMessage
	 */
	public boolean isShowLoadingMessage() {
		return showLoadingMessage;
	}
	/**
	 * @param showLoadingMessage the showLoadingMessage to set
	 */
	public void setShowLoadingMessage(boolean showLoadingMessage) {
		this.showLoadingMessage = showLoadingMessage;
	}
	
	/**
	 * @return the waitForAllCasePartsLoaded
	 */
	public boolean isWaitForAllCasePartsLoaded() {
		return waitForAllCasePartsLoaded;
	}
	/**
	 * @param waitForAllCasePartsLoaded the waitForAllCasePartsLoaded to set
	 */
	public void setWaitForAllCasePartsLoaded(boolean waitForAllCasePartsLoaded) {
		this.waitForAllCasePartsLoaded = waitForAllCasePartsLoaded;
	}
	
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

	public boolean getUseJavascriptForPageSwitching() {
		return this.useJavascriptForPageSwitching;
	}

	public void setUseJavascriptForPageSwitching(boolean useJavascriptForPageSwitching) {
		this.useJavascriptForPageSwitching = useJavascriptForPageSwitching;
	}

	public boolean isShowLogExportButton() {
		return showLogExportButton;
	}
	public void setShowLogExportButton(boolean showLogExportButton) {
		this.showLogExportButton = showLogExportButton;
	}

	public String getSpecialBackPage() {
		return specialBackPage;
	}
	public void setSpecialBackPage(String specialBackPage) {
		this.specialBackPage = specialBackPage;
	}

	public boolean isShowCaseStatus() {
		return showCaseStatus;
	}
	public void setShowCaseStatus(boolean showCaseStatus) {
		this.showCaseStatus = showCaseStatus;
	}

	public List<String> getCustomColumns() {
		return customColumns;
	}
	public void setCustomColumns(List<String> customColumns) {
		this.customColumns = customColumns;
	}

	public String getCasesListCustomizer() {
		return casesListCustomizer;
	}
	public void setCasesListCustomizer(String casesListCustomizer) {
		this.casesListCustomizer = casesListCustomizer;
	}
	public boolean isShowExportAllCasesButton() {
		return showExportAllCasesButton;
	}
	public void setShowExportAllCasesButton(boolean showExportAllCasesButton) {
		this.showExportAllCasesButton = showExportAllCasesButton;
	}
	public boolean isShowComments() {
		return showComments;
	}
	public void setShowComments(boolean showComments) {
		this.showComments = showComments;
	}
	public boolean isShowContacts() {
		return showContacts;
	}
	public void setShowContacts(boolean showContacts) {
		this.showContacts = showContacts;
	}

}