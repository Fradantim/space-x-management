package com.fradantim.spacexmanagement.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fradantim.spacexmanagement.service.TrelloService;
import com.fradantim.spacexmanagement.trello.dto.Board;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TrelloServiceImpl implements TrelloService {

	@Value("${trello.members.me.boards.url}")
	private String meBoardsUrl;

	@Value("${trello.boards.url}")
	private String boardsUrl;

	@Qualifier("trello.webclient")
	@Autowired
	private WebClient webclient;

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
}
