package com.idega.block.process.business;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class CasesCacheCriteria implements Serializable {

	private static final long serialVersionUID = 5256480297285408587L;

	private Integer userId;
	private String type;
	private List<String> caseCodes, statusesToHide, statusesToShow, codes;
	private boolean onlySubscribedCases, showAllCases;
	private Set<String> roles;
	private List<Integer> groups;

	public CasesCacheCriteria(Integer userId, String type, List<String> caseCodes, List<String> statusesToHide, List<String> statusesToShow,
			boolean onlySubscribedCases, Set<String> roles,	List<Integer> groups, List<String> codes, boolean showAllCases) {

		super();

		this.userId = userId == null ? -1 : userId;
		this.type = type;
		this.caseCodes = caseCodes;
		this.statusesToHide = statusesToHide;
		this.statusesToShow = statusesToShow;
		this.onlySubscribedCases = onlySubscribedCases;
		this.roles = roles;
		this.groups = groups;
		this.codes = codes;
		this.showAllCases = showAllCases;
	}

	public String getKey() {
		return new StringBuffer().append(userId).append(type == null ? "-" : type).append(caseCodes).append(statusesToHide)
		.append(statusesToShow).append(onlySubscribedCases).append(roles).append(groups).append(codes).toString();
	}

	@Override
	public String toString() {
		return getKey();
	}

	public Integer getUserId() {
		return userId;
	}

	public String getType() {
		return type;
	}

	public List<String> getCaseCodes() {
		return caseCodes;
	}

	public List<String> getStatusesToHide() {
		return statusesToHide;
	}

	public List<String> getStatusesToShow() {
		return statusesToShow;
	}

	public List<String> getCodes() {
		return codes;
	}

	public boolean isOnlySubscribedCases() {
		return onlySubscribedCases;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public List<Integer> getGroups() {
		return groups;
	}

	public boolean isShowAllCases() {
		return showAllCases;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CasesCacheCriteria) {
			CasesCacheCriteria criteria = (CasesCacheCriteria) obj;
			return getKey().equals(criteria.getKey()) && hashCode() == criteria.hashCode();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getKey().hashCode();
	}

}
