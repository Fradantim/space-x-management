package com.fradantim.spacexmanagement.service;

import com.fradantim.spacexmanagement.dto.trello.Board;
import com.fradantim.spacexmanagement.dto.trello.Column;
import com.fradantim.spacexmanagement.dto.trello.Label;
import com.fradantim.spacexmanagement.dto.trello.Member;

import reactor.core.publisher.Mono;

public interface TrelloBoardManager {

	/** Returns the Board defined by the program arguments */
	public Mono<Board> getWorkBoard();

	/** May return {@link Mono#empty()} */
	public Mono<Column> getWorkBoardListByName(String name);

	public Mono<Column> getOrCreateWorkBoardListByName(String name);

	/** May return {@link Mono#empty()} */
	public Mono<Label> getWorkBoardLabelByName(String name);

	public Mono<Label> getOrCreateWorkBoardLabelByName(String name);

	/** May return {@link Mono#empty()} if board hast no members */
	public Mono<Member> getRandomMember();
}
