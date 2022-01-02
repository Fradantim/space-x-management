package com.fradantim.spacexmanagement.dto;

public class Issue {

	private String title;
	private String description;

	public static Issue fromGenericRequest(GenericRequest request) {
		Issue issue = new Issue();

		issue.setDescription(request.getDescription());
		issue.setTitle(request.getTitle());

		return issue;
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
}
