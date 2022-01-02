package com.fradantim.spacexmanagement.dto.trello;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

public class Card {

	@Schema(accessMode = AccessMode.READ_ONLY)
	private String id;
	private String name;
	private String desc;
	private Column list;
	private List<Label> labels;

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

	public Column getList() {
		return list;
	}

	public void setList(Column list) {
		this.list = list;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
