package com.fradantim.spacexmanagement.dto;

import org.springframework.lang.NonNull;

public class GenericRequest {

	@NonNull
	private GenericRequestType type;

	private String title;

	private String description;

	private TaskCategory category;

	public GenericRequest() {
	}

	public GenericRequestType getType() {
		return type;
	}

	public void setType(GenericRequestType type) {
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

	public TaskCategory getCategory() {
		return category;
	}

	public void setCategory(TaskCategory category) {
		this.category = category;
	}
}
