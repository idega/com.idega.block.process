package com.idega.block.process.presentation.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.idega.builder.bean.AdvancedProperty;
import com.idega.idegaweb.IWMainApplication;
import com.idega.presentation.IWContext;
import com.idega.presentation.ui.handlers.IWDatePickerHandler;
import com.idega.util.IWTimestamp;

public class CasesSearchCriteriaBean implements Serializable {

	private static final long serialVersionUID = -4214566172500799450L;

	private String	caseNumber,
					description,
					name,
					personalId,
					contact,
					statusId,
					dateRange,
					id,
					instanceId,
					casesListCustomizer,
					address;

	private Long subscribersGroupId;
	private String[] statuses;

	private IWTimestamp	dateFrom,
						dateTo;

	private int page = 0,
				pageSize = 0,
				foundResults = 0;

	private boolean allDataLoaded = Boolean.TRUE, showAllCases, showStatistics, showLoadingMessage = Boolean.TRUE, noOrdering, showContacts = Boolean.TRUE;

	private List<String> customColumns, exportColumns;

	private List<String> varCaseIdentifierList,
						 varOwnerKennitalaList,
						 varPropertyNumberList,
						 varPropertyOwnerPersonalIdList,
						 varOperatorPersonalIdList;

	private String ownerKennitala,
				   propertyNumber,
				   propertyOwnerPersonalId,
				   operatorPersonalId,
				   freeVariableText;


	public Long getSubscribersGroupId() {
		return subscribersGroupId;
	}

	public void setSubscribersGroupId(Long groupId) {
		this.subscribersGroupId = groupId;
	}

	public boolean isShowStatistics() {
		return showStatistics;
	}

	public void setShowStatistics(boolean showStatistics) {
		this.showStatistics = showStatistics;
	}

	private List<AdvancedProperty> sortingOptions;

	private List<Long> procInstIds;

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCaseNumber() {
		return caseNumber;
	}

	public String getDateRange() {
		return dateRange;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	public IWTimestamp getDateFrom() {
		if (dateFrom == null)
			parseDateString();

		return dateFrom;
	}

	public void setDateFrom(IWTimestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public IWTimestamp getDateTo() {
		if (dateTo == null)
			parseDateString();

		return dateTo;
	}

	public void setDateTo(IWTimestamp dateTo) {
		this.dateTo = dateTo;
	}

	private void parseDateString() {
		Locale locale = IWContext.getCurrentInstance().getCurrentLocale();
		String format = IWMainApplication.getDefaultIWMainApplication().getSettings().getProperty("datepicker_date_format");
		String dateRange = getDateRange();
		if (dateRange != null) {
			String splitter = " - ";
			if (dateRange.indexOf(splitter) == -1) {

				Date date = null;
				if (format == null) {
					date = IWDatePickerHandler.getParsedDate(dateRange, locale);
				} else {
					date = IWDatePickerHandler.getParsedDateByFormat(dateRange, format);
				}
				dateFrom = date == null ? null : new IWTimestamp(date);
			}
			else {
				String[] dateRangeParts = dateRange.split(splitter);

				Date date = null;

				if (format == null) {
					date = IWDatePickerHandler.getParsedDate(dateRangeParts[0], locale);
				} else {
					date = IWDatePickerHandler.getParsedDateByFormat(dateRangeParts[0], format);
				}
				dateFrom = date == null ? null : new IWTimestamp(date);
				if (format == null) {
					date = IWDatePickerHandler.getParsedDate(dateRangeParts[1], locale);
				} else {
					date = IWDatePickerHandler.getParsedDateByFormat(dateRangeParts[1], format);
				}
				dateTo = date == null ? null : new IWTimestamp(date);
				if (dateTo != null) {
					dateTo.setHour(23);
					dateTo.setMinute(59);
					dateTo.setSecond(59);
					dateTo.setMilliSecond(999);
				}
			}
		}
	}

	public String[] getStatuses() {
		return statuses;
	}

	public void setStatuses(String[] statuses) {
		this.statuses = statuses;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public List<AdvancedProperty> getSortingOptions() {
		return sortingOptions;
	}

	public void setSortingOptions(List<AdvancedProperty> sortingOptions) {
		this.sortingOptions = sortingOptions;
	}

	public boolean isAllDataLoaded() {
		return allDataLoaded;
	}

	public void setAllDataLoaded(boolean allDataLoaded) {
		this.allDataLoaded = allDataLoaded;
	}

	public List<Long> getProcInstIds() {
		return procInstIds;
	}

	public void setProcInstIds(List<Long> procInstIds) {
		this.procInstIds = procInstIds;
	}

	public boolean isShowAllCases() {
		return showAllCases;
	}
	public void setShowAllCases(boolean showAllCases) {
		this.showAllCases = showAllCases;
	}

	public int getFoundResults() {
		return foundResults;
	}

	public void setFoundResults(int foundResults) {
		this.foundResults = foundResults;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCasesListCustomizer() {
		return casesListCustomizer;
	}

	public void setCasesListCustomizer(String casesListCustomizer) {
		this.casesListCustomizer = casesListCustomizer;
	}

	public List<String> getCustomColumns() {
		return customColumns;
	}

	public void setCustomColumns(List<String> customColumns) {
		this.customColumns = customColumns;
	}

	public boolean isShowLoadingMessage() {
		return showLoadingMessage;
	}

	public void setShowLoadingMessage(boolean showLoadingMessage) {
		this.showLoadingMessage = showLoadingMessage;
	}

	public boolean isNoOrdering() {
		return noOrdering;
	}

	public void setNoOrdering(boolean noOrdering) {
		this.noOrdering = noOrdering;
	}

	public List<String> getExportColumns() {
		return exportColumns;
	}

	public void setExportColumns(List<String> exportColumns) {
		this.exportColumns = exportColumns;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isShowContacts() {
		return showContacts;
	}

	public void setShowContacts(boolean showContacts) {
		this.showContacts = showContacts;
	}

	public List<String> getVarCaseIdentifierList() {
		return varCaseIdentifierList;
	}

	public void setVarCaseIdentifierList(List<String> varCaseIdentifierList) {
		this.varCaseIdentifierList = varCaseIdentifierList;
	}

	public List<String> getVarOwnerKennitalaList() {
		return varOwnerKennitalaList;
	}

	public void setVarOwnerKennitalaList(List<String> varOwnerKennitalaList) {
		this.varOwnerKennitalaList = varOwnerKennitalaList;
	}

	public List<String> getVarPropertyNumberList() {
		return varPropertyNumberList;
	}

	public void setVarPropertyNumberList(List<String> varPropertyNumberList) {
		this.varPropertyNumberList = varPropertyNumberList;
	}

	public List<String> getVarPropertyOwnerPersonalIdList() {
		return varPropertyOwnerPersonalIdList;
	}

	public void setVarPropertyOwnerPersonalIdList(List<String> varPropertyOwnerPersonalIdList) {
		this.varPropertyOwnerPersonalIdList = varPropertyOwnerPersonalIdList;
	}

	public List<String> getVarOperatorPersonalIdList() {
		return varOperatorPersonalIdList;
	}

	public void setVarOperatorPersonalIdList(List<String> varOperatorPersonalIdList) {
		this.varOperatorPersonalIdList = varOperatorPersonalIdList;
	}


	public String getOwnerKennitala() {
		return ownerKennitala;
	}

	public void setOwnerKennitala(String ownerKennitala) {
		this.ownerKennitala = ownerKennitala;
	}

	public String getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public String getPropertyOwnerPersonalId() {
		return propertyOwnerPersonalId;
	}

	public void setPropertyOwnerPersonalId(String propertyOwnerPersonalId) {
		this.propertyOwnerPersonalId = propertyOwnerPersonalId;
	}

	public String getOperatorPersonalId() {
		return operatorPersonalId;
	}

	public void setOperatorPersonalId(String operatorPersonalId) {
		this.operatorPersonalId = operatorPersonalId;
	}

	public String getFreeVariableText() {
		return freeVariableText;
	}

	public void setFreeVariableText(String freeVariableText) {
		this.freeVariableText = freeVariableText;
	}



}