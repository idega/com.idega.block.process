package com.idega.block.process.presentation.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.idega.builder.bean.AdvancedProperty;
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
					casesListCustomizer;

	private Long subscribersGroupId;
	private String[] statuses;

	private IWTimestamp	dateFrom,
						dateTo;

	private int page = 0,
				pageSize = 0,
				foundResults = 0;

	private boolean allDataLoaded = Boolean.TRUE, showAllCases, showStatistics, showLoadingMessage = Boolean.TRUE, noOrdering;

	private List<String> customColumns, exportColumns;
	
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

		String dateRange = getDateRange();
		if (dateRange != null) {
			String splitter = " - ";
			if (dateRange.indexOf(splitter) == -1) {
				Date date = IWDatePickerHandler.getParsedDate(dateRange, locale);
				dateFrom = date == null ? null : new IWTimestamp(date);
			}
			else {
				String[] dateRangeParts = dateRange.split(splitter);

				Date date = IWDatePickerHandler.getParsedDate(dateRangeParts[0], locale);
				dateFrom = date == null ? null : new IWTimestamp(date);
				date = IWDatePickerHandler.getParsedDate(dateRangeParts[1], locale);
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

}