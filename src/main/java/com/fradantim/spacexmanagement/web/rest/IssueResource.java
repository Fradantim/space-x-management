package com.fradantim.spacexmanagement.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fradantim.spacexmanagement.dto.Issue;
import com.fradantim.spacexmanagement.dto.trello.Card;
import com.fradantim.spacexmanagement.service.TrelloBoardManager;
import com.fradantim.spacexmanagement.service.TrelloService;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/issues")
public class IssueResource {

	@Autowired
	private TrelloService trelloService;

	@Autowired
	private TrelloBoardManager trelloBoardManager;

	@Operation(description = "${issue-resource.description}")
	@PostMapping
	public Mono<Card> createIssue(@RequestBody Issue issue) {
		Card card = new Card();
		card.setDesc(issue.getDescription());
		card.setName(issue.getTitle());

		return trelloBoardManager.getOrCreateWorkBoardListByName("To Do").flatMap(list -> {
			card.setList(list);
			return trelloService.createCard(card);
		});
	}
}
