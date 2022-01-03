package com.fradantim.spacexmanagement.web.rest;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fradantim.spacexmanagement.dto.Task;
import com.fradantim.spacexmanagement.dto.trello.Card;
import com.fradantim.spacexmanagement.service.TrelloBoardManager;
import com.fradantim.spacexmanagement.service.TrelloService;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskResource {

	@Autowired
	private TrelloService trelloService;

	@Autowired
	private TrelloBoardManager trelloBoardManager;

	@Operation(description = "${task-resource.description}")
	@PostMapping
	public Mono<Card> createTask(@RequestBody Task task) {
		Card card = new Card();
		card.setName(task.getTitle());

		return trelloBoardManager.getOrCreateWorkBoardListByName("To Do").flatMap(list -> {
			card.setList(list);
			return trelloBoardManager.getOrCreateWorkBoardLabelByName(task.getCategory().toString());
		}).flatMap(label -> {
			card.setLabels(Arrays.asList(label));
			return trelloService.createCard(card);
		});
	}
}
