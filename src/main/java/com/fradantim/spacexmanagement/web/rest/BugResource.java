package com.fradantim.spacexmanagement.web.rest;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fradantim.spacexmanagement.dto.Bug;
import com.fradantim.spacexmanagement.dto.trello.Card;
import com.fradantim.spacexmanagement.service.TrelloBoardManager;
import com.fradantim.spacexmanagement.service.TrelloService;
import com.github.javafaker.Faker;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bugs")
public class BugResource {

	private Faker faker = Faker.instance();

	@Autowired
	private TrelloService trelloService;

	@Autowired
	private TrelloBoardManager trelloBoardManager;

	@Operation(description = "${bug-resource.description}")
	@PostMapping
	public Mono<Card> createBug(@RequestBody Bug bug) {
		Card card = new Card();
		card.setDesc(bug.getDescription());
		card.setName("bug-" + faker.animal().name() + "-" + faker.number().numberBetween(364, 10000));

		return trelloBoardManager.getOrCreateWorkBoardListByName("To Do").flatMap(list -> {
			card.setList(list);
			return trelloBoardManager.getOrCreateWorkBoardLabelByName("Bug");
		}).flatMap(label -> {
			card.setLabels(Arrays.asList(label));
			return trelloBoardManager.getRandomMember();
		}).flatMap(member -> {
			card.setMembers(Arrays.asList(member));
			return trelloService.createCard(card);
		});
	}
}
