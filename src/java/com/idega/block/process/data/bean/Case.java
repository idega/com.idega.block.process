package com.idega.block.process.data.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.idega.block.process.data.CaseBMPBean;
import com.idega.core.idgenerator.business.IdGenerator;
import com.idega.core.idgenerator.business.IdGeneratorFactory;
import com.idega.data.MetaDataCapable;
import com.idega.data.UniqueIDCapable;
import com.idega.data.bean.Metadata;
import com.idega.user.data.bean.Group;
import com.idega.user.data.bean.User;
import com.idega.util.DBUtil;

@Entity
@Table(name = Case.ENTITY_NAME, indexes = {
		@Index(name = "UNIQUE_ID", columnList = Case.COLUMN_UNIQUE_ID),
		@Index(name = "CASE_SUBJECT_INDEX", columnList = Case.COLUMN_SUBJECT)
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
	@NamedQuery(name = Case.FIND_ID_BY_SUBJECT, query = "select c.id from Case c where c.subject = :" + Case.PARAM_SUBJECT),
	@NamedQuery(name = Case.FIND_ID_BY_ID, query = "select c from Case c where c.id = :" + Case.PARAM_ID),
	@NamedQuery(name = Case.FIND_CASE_BY_UUID, query = "select c from Case c where c.uniqueId = :" + Case.PARAM_UUID),
	@NamedQuery(name = Case.FIND_ID_BY_UUID, query = "select c.id from Case c where c.uniqueId = :" + Case.PARAM_UUID),
	@NamedQuery(name = Case.FIND_ALL_BY_STATUSES, query = "select c from Case c where c.caseStatus in (:" + Case.PARAM_STATUSES + ")"),
	@NamedQuery(name = Case.COUNT_CASES_CREATED_AFTER_GIVEN_TIMESTAMP, query = "select count(c.id) from Case c where c.created > :" + Case.PARAM_CREATED
				+ " and c.caseCode != 'GENCASE' and c.identifier is not null")
})
public class Case implements Serializable, UniqueIDCapable, MetaDataCapable {

	private static final long serialVersionUID = 2009559065592278210L;

	private static final String COLUMN_CASE_CODE = "case_code",
								COLUMN_CASE_STATUS = "case_status",
								COLUMN_CREATED = "CREATED",
								COLUMN_PARENT_CASE = "PARENT_CASE_ID",
								COLUMN_CREATOR = "creator_id",
								COLUMN_HANDLER_GROUP = "HANDLER_GROUP_ID",
								COLUMN_CASE_NUMBER = "CASE_NUMBER",
								COLUMN_EXTERNAL_ID = "EXTERNAL_ID",
								COLUMN_EXTERNAL_HANDLER = "EXTERNAL_HANDLER_ID",
								COLUMN_BODY = "CASE_BODY",
								COLUMN_MANAGER_TYPE = "CASE_MANAGER_TYPE",
								COLUMN_IDENTIFIER = "CASE_IDENTIFIER",
								COLUMN_IS_READ = "PROC_CASEREAD";

	public static final String	ENTITY_NAME = "proc_case",
								SQL_RELATION_METADATA = "ic_metadata_proc_case",

								COLUMN_CASE_ID = "proc_case_id",
								COLUMN_UNIQUE_ID = "unique_id",
								COLUMN_SUBJECT = "CASE_SUBJECT",
								COLUMN_USER = "user_id",

								PARAM_SUBJECT = "subject",
								PARAM_ID = "id",
								PARAM_UUID = "uuid",
								PARAM_STATUSES = "statuses",
								PARAM_CREATED = "created",

								FIND_ID_BY_SUBJECT = "Case.findIdBySubject",
								FIND_ID_BY_ID = "Case.findIdById",
								FIND_CASE_BY_UUID = "Case.findCaseByUUID",
								FIND_ID_BY_UUID = "Case.findIdByUUID",
								FIND_ALL_BY_STATUSES = "Case.findAllByStatuses",
								COUNT_CASES_CREATED_AFTER_GIVEN_TIMESTAMP = "Case.countOfCasesCreatedAfterGivenTimestamp";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = COLUMN_CASE_ID)
	private Integer id;

	@Column(name = COLUMN_CASE_CODE, length = 7)
	private String caseCode;

	@Column(name = COLUMN_CASE_STATUS, length = 4)
	private String caseStatus;

	@Column(name = COLUMN_CREATED)
	private Timestamp created;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = COLUMN_PARENT_CASE, referencedColumnName = COLUMN_CASE_ID)
	private Case parentCase;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = COLUMN_USER, referencedColumnName = COLUMN_USER)
	private User user;

	@Column(name = COLUMN_USER)
	private Integer userId;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = COLUMN_CREATOR, referencedColumnName = User.COLUMN_USER_ID)
	private User creator;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = COLUMN_HANDLER_GROUP, referencedColumnName = Group.COLUMN_GROUP_ID)
	private Group handlerGroup;

	@Column(name = COLUMN_CASE_NUMBER, length = 30)
	private String caseNumber;

	@Column(name = COLUMN_EXTERNAL_ID, length = 36)
	private String externalId;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn(name = COLUMN_EXTERNAL_HANDLER, referencedColumnName = User.COLUMN_USER_ID)
	private User externalHandler;

	public static final String PROP_UNIQUE_ID = ENTITY_NAME + "_" + COLUMN_UNIQUE_ID;
	@Column(name = COLUMN_UNIQUE_ID, length = 36, nullable = false, unique = true)
	private String uniqueId;

	@Column(name = COLUMN_SUBJECT)
	private String subject;

	@Column(name = COLUMN_BODY, length = 4000)
	private String body;

	@Column(name = COLUMN_MANAGER_TYPE)
	private String managerType;

	@Column(name = COLUMN_IDENTIFIER)
	private String identifier;

	@Column(name = COLUMN_IS_READ)
	private Boolean read;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Metadata.class)
	@JoinTable(name = SQL_RELATION_METADATA, joinColumns = { @JoinColumn(name = COLUMN_CASE_ID) }, inverseJoinColumns = { @JoinColumn(name = Metadata.COLUMN_METADATA_ID) })
	private Set<Metadata> metadata;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = User.class)
	@JoinTable(name = CaseBMPBean.COLUMN_CASE_SUBSCRIBERS, joinColumns = { @JoinColumn(name = COLUMN_CASE_ID) }, inverseJoinColumns = { @JoinColumn(name = User.COLUMN_USER_ID) })
	private List<User> subscribers;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = CaseMileageReimbursement.class)
	@JoinTable(name = Case.ENTITY_NAME + "_ml", joinColumns = { @JoinColumn(name = COLUMN_CASE_ID) }, inverseJoinColumns = { @JoinColumn(name = CaseMileageReimbursement.COLUMN_ID, table = CaseMileageReimbursement.TABLE_NAME) })
	private List<CaseMileageReimbursement> mileageReimbursements;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = CaseMaterial.class)
	@JoinTable(name = Case.ENTITY_NAME + "_mt", joinColumns = { @JoinColumn(name = COLUMN_CASE_ID) }, inverseJoinColumns = { @JoinColumn(name = CaseMaterial.COLUMN_ID, table = CaseMaterial.TABLE_NAME) })
	private List<CaseMaterial> materials;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = CaseConsultant.class)
	@JoinTable(name = Case.ENTITY_NAME + "_cl", joinColumns = { @JoinColumn(name = COLUMN_CASE_ID) }, inverseJoinColumns = { @JoinColumn(name = CaseConsultant.COLUMN_ID, table = CaseConsultant.TABLE_NAME) })
	private List<CaseConsultant> consultants;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public User getUser() {
		user = DBUtil.getInstance().lazyLoad(user);
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getCreator() {
		creator = DBUtil.getInstance().lazyLoad(creator);
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Group getHandlerGroup() {
		handlerGroup = DBUtil.getInstance().lazyLoad(handlerGroup);
		return handlerGroup;
	}

	public void setHandlerGroup(Group handlerGroup) {
		this.handlerGroup = handlerGroup;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public User getExternalHandler() {
		externalHandler = DBUtil.getInstance().lazyLoad(externalHandler);
		return externalHandler;
	}

	public void setExternalHandler(User externalHandler) {
		this.externalHandler = externalHandler;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	private Metadata getMetadata(String key) {
		Set<Metadata> list = getMetadata();
		for (Metadata metaData : list) {
			if (metaData.getKey().equals(key)) {
				return metaData;
			}
		}

		return null;
	}

	@Override
	public String getMetaData(String metaDataKey) {
		Set<Metadata> list = getMetadata();
		for (Metadata metaData : list) {
			if (metaData.getKey().equals(metaDataKey)) {
				return metaData.getValue();
			}
		}

		return null;
	}

	@Override
	public Map<String, String> getMetaDataAttributes() {
		Map<String, String> map = new HashMap<String, String>();

		Set<Metadata> list = getMetadata();
		for (Metadata metaData : list) {
			map.put(metaData.getKey(), metaData.getValue());
		}

		return map;
	}

	@Override
	public Map<String, String> getMetaDataTypes() {
		Map<String, String> map = new HashMap<String, String>();

		Set<Metadata> list = getMetadata();
		for (Metadata metaData : list) {
			map.put(metaData.getKey(), metaData.getType());
		}

		return map;
	}

	@Override
	public boolean removeMetaData(String metaDataKey) {
		Metadata metadata = getMetadata(metaDataKey);
		if (metadata != null) {
			getMetadata().remove(metadata);
		}

		return false;
	}

	@Override
	public void renameMetaData(String oldKeyName, String newKeyName, String value) {
		Metadata metadata = getMetadata(oldKeyName);
		if (metadata != null) {
			metadata.setKey(newKeyName);
			if (value != null) {
				metadata.setValue(value);
			}
		}
	}

	@Override
	public void renameMetaData(String oldKeyName, String newKeyName) {
		renameMetaData(oldKeyName, newKeyName, null);
	}

	@Override
	public void setMetaData(String metaDataKey, String value, String type) {
		Metadata metadata = getMetadata(metaDataKey);
		if (metadata == null) {
			metadata = new Metadata();
			metadata.setKey(metaDataKey);
		}
		metadata.setValue(value);
		if (type != null) {
			metadata.setType(type);
		}

		getMetadata().add(metadata);

	}

	@Override
	public void setMetaData(String metaDataKey, String value) {
		setMetaData(metaDataKey, value, null);
	}

	@Override
	public void setMetaDataAttributes(Map<String, String> map) {
		for (String key : map.keySet()) {
			String value = map.get(key);

			Metadata metadata = getMetadata(key);
			if (metadata == null) {
				metadata = new Metadata();
				metadata.setKey(key);
			}
			metadata.setValue(value);

			getMetadata().add(metadata);
		}
	}

	@Override
	public void updateMetaData() throws SQLException {
		// Does nothing...
	}

	public Set<Metadata> getMetadata() {
		metadata = DBUtil.getInstance().lazyLoad(metadata);
		return this.metadata;
	}

	public void setMetadata(Set<Metadata> metadata) {
		this.metadata = metadata;
	}

	@PrePersist
	public void setDefaultValues() {
		if (getUniqueId() == null) {
			IdGenerator uidGenerator = IdGeneratorFactory.getUUIDGenerator();
			setUniqueId(uidGenerator.generateId());
		}
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	@Override
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Case getParentCase() {
		parentCase = DBUtil.getInstance().lazyLoad(parentCase);
		return parentCase;
	}

	public void setParentCase(Case parentCase) {
		this.parentCase = parentCase;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public List<CaseMileageReimbursement> getMileageReimbursements() {
		mileageReimbursements = DBUtil.getInstance().lazyLoad(mileageReimbursements);
		return mileageReimbursements;
	}

	public void setMileageReimbursements(List<CaseMileageReimbursement> mileageReimbursements) {
		this.mileageReimbursements = mileageReimbursements;
	}

	public List<CaseMaterial> getMaterials() {
		materials = DBUtil.getInstance().lazyLoad(materials);
		return materials;
	}

	public void setMaterials(List<CaseMaterial> materials) {
		this.materials = materials;
	}

	public List<CaseConsultant> getConsultants() {
		consultants = DBUtil.getInstance().lazyLoad(consultants);
		return consultants;
	}

	public void setConsultants(List<CaseConsultant> consultants) {
		this.consultants = consultants;
	}

	@Override
	public String toString() {
		return "Case ID: " + getId();
	}

}