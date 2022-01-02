package com.fradantim.spacexmanagement.dto.trello;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Board {

	private String name;

	private String desc;

	@JsonProperty("idOrganization")
	private String organitzationId;

	@JsonProperty("idEnterprise")
	private String enterpriseId;

	@JsonProperty("idMemberCreator")
	private String memberCreatorId;

	private String id;

	private String url;

	private Map<String, Object> prefs;

	private List<Label> lables;

	private List<Column> lists;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOrganitzationId() {
		return organitzationId;
	}

	public void setOrganitzationId(String organitzationId) {
		this.organitzationId = organitzationId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getMemberCreatorId() {
		return memberCreatorId;
	}

	public void setMemberCreatorId(String memberCreatorId) {
		this.memberCreatorId = memberCreatorId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getPrefs() {
		return prefs;
	}

	public void setPrefs(Map<String, Object> prefs) {
		this.prefs = prefs;
	}

	public List<Label> getLables() {
		return lables;
	}

	public void setLables(List<Label> lables) {
		this.lables = lables;
	}

	public List<Column> getLists() {
		return lists;
	}

	public void setLists(List<Column> lists) {
		this.lists = lists;
	}
}
