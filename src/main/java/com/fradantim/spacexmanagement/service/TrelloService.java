package com.fradantim.spacexmanagement.service;

import com.fradantim.spacexmanagement.trello.dto.Board;

import reactor.core.publisher.Mono;

public interface TrelloService {

	/**
	 * Searches Trello for a board by the given id, or {@link Mono#empty()}. Be
	 * aware, the logged user may not have permission to read that board.
	 */
	public Mono<Board> findBoardById(String id);

	/**
	 * Searches the logged user boards and returns one with the same name or
	 * {@link Mono#empty()}
	 */
	public Mono<Board> findOneBoardByName(String name);
}
