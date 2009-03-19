/**
 * 
 */
package com.idega.block.process.presentation.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import com.idega.block.process.data.CaseStatus;
import com.idega.user.data.User;

/**
 * Case data needed in presentation layer.
 * 
 * @author donatas
 *
 */
public class CasePresentation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer primaryKey;
	
	private User owner;
	
	private String caseIdentifier;
	
	private String subject;
	
	private CaseStatus caseStatus;
	
	private Timestamp created;
	
	private String localizedStatus;
	
	private String caseManagerType;

	private String code;
	
	private String url;
	
	private String id;
	
	private String externalId;
	
	private Integer categoryId;
	
	private String caseTypeName;

	private boolean isPrivate = false;
	
	private User handledBy;
	
	private boolean bpm = false;
	
	private String processName;
	
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	@Override
	public String toString() {
		return getId();
	}
}
