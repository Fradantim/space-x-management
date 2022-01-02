package com.fradantim.spacexmanagement.service;

import com.fradantim.spacexmanagement.dto.trello.Board;
import com.fradantim.spacexmanagement.dto.trello.Column;

import reactor.core.publisher.Mono;

public interface TrelloBoardManager {

	/** Returns the Board defined by the program arguments */
	public Mono<Board> getWorkBoard();
	
	public Mono<Column> getWorkBoardListByName(String name);
}
