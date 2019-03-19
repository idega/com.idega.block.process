package com.idega.block.process.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;

public class CasesCacheCriteria implements Serializable {

	private static final long serialVersionUID = 5256480297285408587L;

	private Integer userId;
	private String type;
	private List<String> caseCodes, statusesToHide, statusesToShow, codes;
	private boolean onlySubscribedCases, showAllCases;
	private Set<String> roles;
	private List<Integer> groups;
	private List<Long> procInstIds;
	private Collection<Long> handlercategoryIds;
	private List<Integer> exceptOwnersIds;

	public CasesCacheCriteria(
			Integer userId,
			String type,
			Collection<String> caseCodes,
			Collection<String> statusesToHide,
			Collection<String> statusesToShow,
			Collection<String> roles,
			Collection<Integer> groups,
			Collection<String> codes,
			Collection<Long> procInstIds,
			Collection<Long> handlercategoryIds,
			boolean onlySubscribedCases,
			boolean showAllCases,
			List<Integer> exceptOwnersIds
	) {
		super();

		this.userId = userId == null ? -1 : userId;
		this.type = type;
		this.onlySubscribedCases = onlySubscribedCases;
		this.showAllCases = showAllCases;

		if (!ListUtil.isEmpty(caseCodes)) {
			this.caseCodes = new ArrayList<String>(caseCodes);
		}

		if (!ListUtil.isEmpty(statusesToHide)) {
			this.statusesToHide = new ArrayList<String>(statusesToHide);
		}

		if (!ListUtil.isEmpty(statusesToShow)) {
			this.statusesToShow = new ArrayList<String>(statusesToShow);
		}

		if (!ListUtil.isEmpty(roles)) {
			this.roles = new HashSet<String>(roles);
		}

		if (!ListUtil.isEmpty(groups)) {
			this.groups = new ArrayList<Integer>(groups);
		}

		if (!ListUtil.isEmpty(codes)) {
			this.codes = new ArrayList<String>(codes);
		}

		if (!ListUtil.isEmpty(procInstIds)) {
			this.procInstIds = new ArrayList<Long>(procInstIds);
		}

		this.handlercategoryIds = handlercategoryIds;

		this.exceptOwnersIds = exceptOwnersIds;
	}

	public CasesCacheCriteria(
			Integer userId,
			String type,
			List<String> caseCodes,
			List<String> statusesToHide,
			List<String> statusesToShow,
			boolean onlySubscribedCases,
			Set<String> roles,
			List<Integer> groups,
			List<String> codes,
			boolean showAllCases,
			List<Long> procInstIds,
			List<Integer> exceptOwnersIds
	) {
		this(userId, type, caseCodes, statusesToHide, statusesToShow, roles, groups, codes, procInstIds, null, onlySubscribedCases, showAllCases, exceptOwnersIds);
	}

	public String getKey() {
		return new StringBuffer()
			.append(userId)
			.append(type == null ? CoreConstants.MINUS : type)
			.append(ListUtil.isEmpty(caseCodes) ? CoreConstants.MINUS : caseCodes)
			.append(ListUtil.isEmpty(statusesToHide) ? CoreConstants.MINUS : statusesToHide)
			.append(ListUtil.isEmpty(statusesToShow) ? CoreConstants.MINUS : statusesToShow)
			.append(onlySubscribedCases)
			.append(ListUtil.isEmpty(roles) ? CoreConstants.MINUS : roles)
			.append(ListUtil.isEmpty(groups) ? CoreConstants.MINUS : groups)
			.append(ListUtil.isEmpty(codes) ? CoreConstants.MINUS : codes)
			.append(ListUtil.isEmpty(procInstIds) ? CoreConstants.MINUS : procInstIds)
			.append(ListUtil.isEmpty(handlercategoryIds) ? CoreConstants.MINUS : handlercategoryIds)
			.append(ListUtil.isEmpty(exceptOwnersIds) ? CoreConstants.MINUS : exceptOwnersIds)
		.toString();
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

	public List<Long> getProcInstIds() {
		return procInstIds;
	}

	public Collection<Long> getHandlercategoryIds() {
		return handlercategoryIds;
	}

	public void setHandlercategoryIds(Collection<Long> handlercategoryIds) {
		this.handlercategoryIds = handlercategoryIds;
	}

	public List<Integer> getExceptOwnersIds() {
		return exceptOwnersIds;
	}

	public void setExceptOwnersIds(List<Integer> exceptOwnersIds) {
		this.exceptOwnersIds = exceptOwnersIds;
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