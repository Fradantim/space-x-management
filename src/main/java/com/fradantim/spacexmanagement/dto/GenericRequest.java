package com.fradantim.spacexmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class GenericRequest {

	@Schema(allowableValues = { "bug", "issue", "task" })
	private String type;

	private String title;

	private String description;

	private String category;

	public GenericRequest() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
