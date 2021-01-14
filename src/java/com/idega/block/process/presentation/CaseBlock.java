/*
 * $Id: CaseBlock.java,v 1.7 2009/06/30 09:35:57 valdas Exp $
 * Created on Sep 24, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.presentation;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.business.ProcessConstants;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.user.business.UserBusiness;
import com.idega.util.CoreConstants;
import com.idega.util.PresentationUtil;
import com.idega.util.StringUtil;


/**
 * Last modified: $Date: 2009/06/30 09:35:57 $ by $Author: valdas $
 *
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.7 $
 */
public abstract class CaseBlock extends Block {

	private CaseBusiness business;
	private UserBusiness userBusiness;

	private IWBundle iwb;
	private IWResourceBundle iwrb;

	private int pageSize = 20,
				page = 1;

	private Long totalNumberOfCases = null;

	private boolean addCredentialsToExernalUrls,
					showCaseNumberColumn = true,
					showCreationTimeInDateColumn = true,
					showCreatorColumn = true,
					usePDFDownloadColumn = true,
					allowPDFSigning = true,
					showStatistics,
					hideEmptySection,
					showAttachmentStatistics,
					showOnlyCreatorInContacts,
					onlySubscribedCases,
					showLegend,
					showAllCases,
					showLoadingMessage = Boolean.TRUE,
					waitForAllCasePartsLoaded = Boolean.TRUE,
					userNameShown = Boolean.TRUE,
					showLogExportButton,
					showUserProfilePicture = Boolean.TRUE,
					addExportContacts = false,
					showUserCompany = false,
					showLastLoginDate = false,
					showCaseStatus = true,
					showExportAllCasesButton,
					showComments = true,
					showContacts = true,
					descriptionEditable = true,
					useJavascriptForPageSwitching = true,
					showCasesOnlyByProvidedProcesses = false,
					changerCheckboxVisible = Boolean.FALSE,
					useXMLDataProvider = true,
					showTimeSpentOnCase = false;
	protected boolean showCaseSubstatus = false;

	private String	caseStatusesToHide,
					caseStatusesToShow,
					caseCodes,
					commentsManagerIdentifier,
					dateCustomValueVariable,
					dateCustomLabelLocalizationKey,
					searchResultsId,
					specialBackPage,

					customColumns,
					customColumnsForExport,
					casesListCustomizer,

					externalStyleSheet,
					commaSeparatedExcludedHandlersIds,
					caseNavigationBlockPosition,
					inactiveTasksToShow,
					customView;

	private List<Integer> casesIds;
	private List<Long> procInstIds;
	private Set<String> roles;

	public boolean isUseXMLDataProvider() {
		return useXMLDataProvider;
	}

	public void setUseXMLDataProvider(boolean useXMLDataProvider) {
		this.useXMLDataProvider = useXMLDataProvider;
	}

	public boolean isChangerCheckboxVisible() {
		return changerCheckboxVisible;
	}

	public void setChangerCheckboxVisible(boolean changerCheckboxVisible) {
		this.changerCheckboxVisible = changerCheckboxVisible;
	}

	public String getCommaSeparatedExcludedHandlersIds() {
		return commaSeparatedExcludedHandlersIds;
	}

	public void setCommaSeparatedExcludedHandlersIds(
			String commaSeparatedExcludedHandlersIds) {
		this.commaSeparatedExcludedHandlersIds = commaSeparatedExcludedHandlersIds;
	}

	public List<Long> getProcInstIds() {
		return procInstIds;
	}

	public void setProcInstIds(List<Long> procInstIds) {
		this.procInstIds = procInstIds;
	}

	public List<Integer> getCasesIds() {
		return casesIds;
	}

	public void setCasesIds(List<Integer> casesIds) {
		this.casesIds = casesIds;
	}

	@Override
	public void main(IWContext iwc) throws Exception {
		initialize(iwc);

		int page = getPage();
		doResolveIds(iwc, getPageSize(), page);
		totalNumberOfCases = getTotalCountOfCases(iwc, page);

		present(iwc);
	}

	protected Long getTotalCountOfCases(IWContext iwc, int page) throws Exception {
		return null;
	}
	protected void doResolveIds(IWContext iwc, int pageSize, int page) throws Exception {}

	protected abstract void present(IWContext iwc) throws Exception;

	@Override
	public String getBundleIdentifier() {
		return ProcessConstants.IW_BUNDLE_IDENTIFIER;
	}

	private void initialize(IWContext iwc) {
		setResourceBundle(getResourceBundle(iwc));
		setBundle(getBundle(iwc));
		this.business = getCaseBusiness(iwc);
		this.userBusiness = getUserBusiness(iwc);
		PresentationUtil.addStyleSheetToHeader(iwc, iwc.getIWMainApplication().getBundle(ProcessConstants.IW_BUNDLE_IDENTIFIER).getVirtualPathWithFileNameString("style/process.css"));
		if (externalStyleSheet != null) {
			PresentationUtil.addStyleSheetToHeader(iwc, externalStyleSheet);
		}
	}

	protected IWBundle getBundle() {
		return this.iwb;
	}

	protected IWResourceBundle getResourceBundle() {
		return this.iwrb;
	}

	protected CaseBusiness getBusiness() {
		return this.business;
	}

	protected CaseBusiness getCaseBusiness(IWApplicationContext iwac) {
		try {
			return IBOLookup.getServiceInstance(iwac, CaseBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected UserBusiness getUserBusiness() {
		return this.userBusiness;
	}

	private UserBusiness getUserBusiness(IWApplicationContext iwac) {
		try {
			return IBOLookup.getServiceInstance(iwac, UserBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
	private void setBundle(IWBundle iwb) {
		this.iwb = iwb;
	}

	private void setResourceBundle(IWResourceBundle iwrb) {
		this.iwrb = iwrb;
	}

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

	public boolean isShowCreationTimeInDateColumn() {
		return showCreationTimeInDateColumn;
	}

	public void setShowCreationTimeInDateColumn(boolean showCreationTimeInDateColumn) {
		this.showCreationTimeInDateColumn = showCreationTimeInDateColumn;
	}

	public boolean isShowCreatorColumn() {
		return showCreatorColumn;
	}

	public void setShowCreatorColumn(boolean showCreatorColumn) {
		this.showCreatorColumn = showCreatorColumn;
	}

	public String getCaseStatusesToHide() {
		return caseStatusesToHide;
	}

	public void setCaseStatusesToHide(String caseStatusesToHide) {
		this.caseStatusesToHide = caseStatusesToHide;
	}

	public String getCaseStatusesToShow() {
		return caseStatusesToShow;
	}

	public void setCaseStatusesToShow(String caseStatusesToShow) {
		this.caseStatusesToShow = caseStatusesToShow;
	}

	public String getCaseCodes() {
		return caseCodes;
	}

	public void setCaseCodes(String caseCodes) {
		this.caseCodes = caseCodes;
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

	public String getCommentsManagerIdentifier() {
		return commentsManagerIdentifier;
	}

	public void setCommentsManagerIdentifier(String commentsManagerIdentifier) {
		this.commentsManagerIdentifier = commentsManagerIdentifier;
	}

	public boolean isAddCredentialsToExernalUrls() {
		return addCredentialsToExernalUrls;
	}

	public void setAddCredentialsToExernalUrls(boolean addCredentialsToExernalUrls) {
		this.addCredentialsToExernalUrls = addCredentialsToExernalUrls;
	}

	public abstract boolean showCheckBox();
	public abstract boolean showCheckBoxes();

	public UICasesList getCasesList(IWContext iwc, String id) throws RemoteException {
		UICasesList list = (UICasesList) iwc.getApplication().createComponent(UICasesList.COMPONENT_TYPE);

		list.setType(getCasesProcessorType());
		list.setUserCasesPageMap(getUserCasesPageMap());
		list.setAddCredentialsToExernalUrls(isAddCredentialsToExernalUrls());
		list.setShowCheckBoxes(showCheckBoxes());
		list.setUsePDFDownloadColumn(isUsePDFDownloadColumn());
		list.setAllowPDFSigning(isAllowPDFSigning());
		list.setShowStatistics(isShowStatistics());
		list.setHideEmptySection(isHideEmptySection());
		list.setPageSize(getPageSize());
		list.setPage(getPage());
		list.setComponentId(id);
		list.setInstanceId(getBuilderService(iwc).getInstanceId(this));
		list.setShowCaseNumberColumn(isShowCaseNumberColumn());
		list.setShowCreationTimeInDateColumn(isShowCreationTimeInDateColumn());
		list.setCaseCodes(getCaseCodes());
		list.setCaseStatusesToHide(getCaseStatusesToHide());
		list.setCaseStatusesToShow(getCaseStatusesToShow());
		list.setCommentsManagerIdentifier(getCommentsManagerIdentifier());
		list.setShowCreatorColumn(isShowCreatorColumn());
		list.setDateCustomValueVariable(getDateCustomValueVariable());
		list.setDateCustomLabelLocalizationKey(getDateCustomLabelLocalizationKey());
		list.setShowAttachmentStatistics(isShowAttachmentStatistics());
		list.setShowOnlyCreatorInContacts(isShowOnlyCreatorInContacts());
		list.setOnlySubscribedCases(isOnlySubscribedCases());
		list.setSearchResultsId(getSearchResultsId());
		list.setUseJavascriptForPageSwitching(getUseJavascriptForPageSwitching());
		list.setShowLegend(isShowLegend());
		list.setShowLogExportButton(isShowLogExportButton());
		list.setShowComments(isShowComments());
		list.setShowContacts(isShowContacts());
		list.setShowAllCases(isShowAllCases());
		list.setSpecialBackPage(getSpecialBackPage());
		list.setShowCaseStatus(isShowCaseStatus());
		list.setCasesListCustomizer(getCasesListCustomizer());
		list.setCustomColumns(StringUtil.isEmpty(getCustomColumns()) ? null : Arrays.asList(getCustomColumns().split(CoreConstants.COMMA)));
		list.setShowExportAllCasesButton(isShowExportAllCasesButton());
		list.setShowLoadingMessage(isShowLoadingMessage());
		list.setWaitForAllCasePartsLoaded(isWaitForAllCasePartsLoaded());
		list.setDescriptionEditable(isDescriptionEditable());
		list.setProcInstIds(getProcInstIds());
		list.setCasesIds(getCasesIds());
		list.setRoles(getRoles());
		list.setShowCasesOnlyByProvidedProcesses(isShowCasesOnlyByProvidedProcesses());
		list.setNameFromExternalEntity(!isUserNameShown());
		list.setShowUserProfilePicture(isShowUserProfilePicture());
		list.setAddExportContacts(isAddExportContacts());
		list.setShowUserCompany(isShowUserCompany());
		list.setShowLastLoginDate(isShowLastLoginDate());
		list.setTotalNumberOfCases(getTotalNumberOfCases());
		list.setCustomColumnsForExport(StringUtil.isEmpty(getCustomColumnsForExport()) ? null : Arrays.asList(getCustomColumnsForExport().split(CoreConstants.COMMA)));
		list.setUseXMLDataProvider(isUseXMLDataProvider());
		list.setCaseNavigationBlockPosition(getCaseNavigationBlockPosition());
		list.setInactiveTasksToShow(getInactiveTasksToShow());
		list.setShowTimeSpentOnCase(isShowTimeSpentOnCase());
		list.setCustomView(getCustomView());
		list.setShowCaseSubstatus(isShowCaseSubstatus());
		return list;
	}

	public abstract String getCasesProcessorType();

	public abstract Map<Object, Object> getUserCasesPageMap();

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

	public String getSearchResultsId() {
		return searchResultsId;
	}

	public void setSearchResultsId(String searchResultsId) {
		this.searchResultsId = searchResultsId;
	}

	public boolean getUseJavascriptForPageSwitching() {
		return this.useJavascriptForPageSwitching;
	}

	public void setUseJavascriptForPageSwitching(boolean useJavascriptForPageSwitching) {
		this.useJavascriptForPageSwitching = useJavascriptForPageSwitching;
	}

	public boolean isShowLegend() {
		return showLegend;
	}

	public void setShowLegend(boolean showLegend) {
		this.showLegend = showLegend;
	}

	public boolean isShowLogExportButton() {
		return showLogExportButton;
	}

	public void setShowLogExportButton(boolean showLogExportButton) {
		this.showLogExportButton = showLogExportButton;
	}

	public boolean isShowAllCases() {
		return showAllCases;
	}

	public void setShowAllCases(boolean showAllCases) {
		this.showAllCases = showAllCases;
	}

	public String getSpecialBackPage() {
		return specialBackPage;
	}

	public void setSpecialBackPage(String specialBackPage) {
		this.specialBackPage = specialBackPage;
	}

	public void setExternalStyleSheet(String externalStyleSheet) {
		this.externalStyleSheet = externalStyleSheet;
	}

	public boolean isShowCaseStatus() {
		return showCaseStatus;
	}

	public void setShowCaseStatus(boolean showCaseStatus) {
		this.showCaseStatus = showCaseStatus;
	}

	public String getCustomColumns() {
		return customColumns;
	}

	public void setCustomColumns(String customColumns) {
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

	public boolean isDescriptionEditable() {
		return descriptionEditable;
	}

	public void setDescriptionEditable(boolean descriptionEditable) {
		this.descriptionEditable = descriptionEditable;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public boolean isShowCasesOnlyByProvidedProcesses() {
		return showCasesOnlyByProvidedProcesses;
	}

	public void setShowCasesOnlyByProvidedProcesses(boolean showCasesOnlyByProvidedProcesses) {
		this.showCasesOnlyByProvidedProcesses = showCasesOnlyByProvidedProcesses;
	}

	public boolean isUserNameShown() {
		return userNameShown;
	}

	public void setUserNameShown(boolean userNameShown) {
		this.userNameShown = userNameShown;
	}

	public boolean isShowUserProfilePicture() {
		return showUserProfilePicture;
	}

	public void setShowUserProfilePicture(boolean showUserProfilePicture) {
		this.showUserProfilePicture = showUserProfilePicture;
	}

	public boolean isAddExportContacts() {
		return addExportContacts;
	}

	public void setAddExportContacts(boolean addExportContacts) {
		this.addExportContacts = addExportContacts;
	}

	public boolean isShowUserCompany() {
		return showUserCompany;
	}

	public void setShowUserCompany(boolean showUserCompany) {
		this.showUserCompany = showUserCompany;
	}

	public boolean isShowLastLoginDate() {
		return showLastLoginDate;
	}

	public void setShowLastLoginDate(boolean showLastLoginDate) {
		this.showLastLoginDate = showLastLoginDate;
	}

	public Long getTotalNumberOfCases() {
		return totalNumberOfCases;
	}

	public void setTotalNumberOfCases(Long totalNumberOfCases) {
		this.totalNumberOfCases = totalNumberOfCases;
	}

	public String getCustomColumnsForExport() {
		return customColumnsForExport;
	}

	public void setCustomColumnsForExport(String customColumnsForExport) {
		this.customColumnsForExport = customColumnsForExport;
	}

	public String getCaseNavigationBlockPosition() {
		return caseNavigationBlockPosition;
	}

	public void setCaseNavigationBlockPosition(
			String caseNavigationBlockPosition) {
		this.caseNavigationBlockPosition = caseNavigationBlockPosition;
	}

	public String getInactiveTasksToShow() {
		return inactiveTasksToShow;
	}

	public void setInactiveTasksToShow(String inactiveTasksToShow) {
		this.inactiveTasksToShow = inactiveTasksToShow;
	}

	public boolean isShowTimeSpentOnCase() {
		return showTimeSpentOnCase;
	}

	public void setShowTimeSpentOnCase(boolean showTimeSpentOnCase) {
		this.showTimeSpentOnCase = showTimeSpentOnCase;
	}

	public String getCustomView() {
		return customView;
	}

	public void setCustomView(String customView) {
		this.customView = customView;
	}

	public boolean isShowCaseSubstatus() {
		return showCaseSubstatus;
	}

	public void setShowCaseSubstatus(boolean showCaseSubstatus) {
		this.showCaseSubstatus = showCaseSubstatus;
	}

}