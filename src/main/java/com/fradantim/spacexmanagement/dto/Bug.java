package com.fradantim.spacexmanagement.dto;

public class Bug {

	private String description;

	public static Bug fromGenericRequest(GenericRequest request) {
		Bug bug = new Bug();

		bug.setDescription(request.getDescription());

		return bug;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
