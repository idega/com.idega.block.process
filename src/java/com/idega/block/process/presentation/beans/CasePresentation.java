/**
 *
 */
package com.idega.block.process.presentation.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseStatus;
import com.idega.builder.bean.AdvancedProperty;
import com.idega.business.IBOLookup;
import com.idega.idegaweb.IWMainApplication;
import com.idega.user.data.User;
import com.idega.util.StringUtil;

/**
 * Case data needed in presentation layer.
 *
 * @author donatas
 *
 */
public class CasePresentation implements Serializable {

	private static final long serialVersionUID = 2381335679460968723L;

	private Integer primaryKey;

	private User owner, handledBy;

	private String caseIdentifier, subject, localizedStatus, caseManagerType, code, url, id, externalId, categoryId, caseTypeName, processName, body, uniqueId;

	private CaseStatus caseStatus;

	private Timestamp created;

	private boolean isPrivate, bpm = false;

	private List<AdvancedProperty> externalData;

	private Map<String, String> caseVariables;

	public CasePresentation() {
		super();
	}

	public CasePresentation(Case theCase) {
		this();

		primaryKey = Integer.valueOf(theCase.getId());
		owner = theCase.getOwner();
		caseIdentifier = theCase.getCaseIdentifier();
		subject = theCase.getSubject();
		caseStatus = theCase.getCaseStatus();
		uniqueId = theCase.getUniqueId();
	}

	public Integer getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Integer primaryKey) {
		this.primaryKey = primaryKey;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getCaseIdentifier() {
		return caseIdentifier;
	}

	public void setCaseIdentifier(String caseIdentifier) {
		this.caseIdentifier = caseIdentifier;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public CaseStatus getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(CaseStatus caseStatus) {
		this.caseStatus = caseStatus;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getLocalizedStatus() {
		return localizedStatus;
	}

	public void setLocalizedStatus(String localizedStatus) {
		this.localizedStatus = localizedStatus;
	}

	public String getCaseManagerType() {
		return caseManagerType;
	}

	public void setCaseManagerType(String caseManagerType) {
		this.caseManagerType = caseManagerType;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getCaseTypeName() {
		return caseTypeName;
	}

	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public User getHandledBy() {
		return handledBy;
	}

	public void setHandledBy(User handledBy) {
		this.handledBy = handledBy;
	}

	public void setBpm(boolean bpm) {
		this.bpm = bpm;
	}

	public boolean isBpm() {
		return bpm;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getOwnerName() {
		return owner == null ? null : owner.getName();
	}

	public String getCaseStatusLocalized() {
		if (caseStatus == null) {
			return null;
		}

		if (!StringUtil.isEmpty(localizedStatus) && !localizedStatus.equals(caseStatus.getStatus())) {
			return localizedStatus;
		}

		try {
			CaseBusiness caseBusiness = IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CaseBusiness.class);
			return caseBusiness.getCaseStatusLocalized(caseStatus);
		} catch(Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Error getting localized status for: " + caseStatus, e);
		}

		return caseStatus.getStatus();
	}

	public List<AdvancedProperty> getExternalData() {
		return externalData;
	}

	public void setExternalData(List<AdvancedProperty> externalData) {
		this.externalData = externalData;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "\n\t" + getId() + " case identifier: '" + getCaseIdentifier() + "'";
	}

	public Map<String, String> getCaseVariables() {
		return caseVariables;
	}

	public void setCaseVariables(Map<String, String> caseVariables) {
		this.caseVariables = caseVariables;
	}

	public final String getUniqueId() {
		return uniqueId;
	}

	public final void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}