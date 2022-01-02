package com.fradantim.spacexmanagement.service.impl;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fradantim.spacexmanagement.dto.trello.Board;
import com.fradantim.spacexmanagement.dto.trello.Card;
import com.fradantim.spacexmanagement.dto.trello.Label;
import com.fradantim.spacexmanagement.service.TrelloService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TrelloServiceImpl implements TrelloService {

	private static final Logger logger = LoggerFactory.getLogger(TrelloServiceImpl.class);

	@Value("#{${trello.query-params}}")
	private MultiValueMap<String, String> queryParams;

	@Value("${trello.members.me.boards.url}")
	private String meBoardsUrl;

	@Value("${trello.boards.url}")
	private String boardsUrl;

	@Value("${trello.lists.url}")
	private String listsUrl;

	@Value("${trello.cards.url}")
	private String cardsUrl;

	@Qualifier("trello.webclient")
	@Autowired
	private WebClient webclient;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Mono<Board> findBoardById(String id) {
		return webclient.get().uri(boardsUrl, Collections.singletonMap("id", id))
				.exchangeToMono(c -> c.bodyToMono(Board.class));
	}

	@Override
	public Mono<Board> findOneBoardByName(String name) {
		return webclient.get().uri(meBoardsUrl).exchangeToMono(c -> c.bodyToMono(Board[].class))
				.flatMapMany(Flux::fromArray).filter(board -> board != null && board.getName() != null
						&& name.toLowerCase().equals(board.getName().toLowerCase()))
				.next();
	}

	@Override
	public Mono<String> createList(Board board, String name) {
		MultiValueMap<String, String> allQueryParams = new LinkedMultiValueMap<>(queryParams);
		allQueryParams.add("name", name);
		allQueryParams.add("idBoard", board.getId());

		return webclient.post().uri(listsUrl, (ub) -> {
			ub.queryParams(allQueryParams);
			return ub.build();
		}).exchangeToMono(c -> c.bodyToMono(String.class));
	}

	@Override
	public Mono<Card> createCard(Card card) {
		MultiValueMap<String, String> allQueryParams = new LinkedMultiValueMap<>(queryParams);
		allQueryParams.add("name", card.getName());
		allQueryParams.add("desc", card.getDesc());
		allQueryParams.add("idList", card.getList().getId());

		if (card.getLabels() != null && !card.getLabels().isEmpty()) {
			String labels = card.getLabels().stream().map(Label::getId).reduce((idA, idB) -> idA + "," + idB).get();
			allQueryParams.add("idLabels", labels);
		}

		return webclient.post().uri(cardsUrl, (ub) -> {
			ub.queryParams(allQueryParams);
			return ub.build();
		}).exchangeToMono(c -> c.bodyToMono(String.class)).map(response -> {
			try {
				@SuppressWarnings("rawtypes")
				Map parsedResponse = objectMapper.readValue(response, Map.class);
				card.setId(String.valueOf(parsedResponse.get("id")));
			} catch (JsonProcessingException e) {
				logger.error("Error while trying to create new card, Trello answered {}", response);
				throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
						"Error while trying to create new card, Trello answered " + response);
			}
			return card;
		});
	}
}
