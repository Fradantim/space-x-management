package com.fradantim.spacexmanagement.dto;

public class Task {

	private String title;
	private TaskCategory category;

	public static Task fromGenericRequest(GenericRequest request) {
		Task task = new Task();

		task.setCategory(request.getCategory());
		task.setTitle(request.getTitle());

		return task;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TaskCategory getCategory() {
		return category;
	}

	public void setCategory(TaskCategory category) {
		this.category = category;
	}
}
