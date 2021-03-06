package com.fradantim.spacexmanagement.service.impl;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fradantim.spacexmanagement.dto.trello.Board;
import com.fradantim.spacexmanagement.dto.trello.Column;
import com.fradantim.spacexmanagement.dto.trello.Label;
import com.fradantim.spacexmanagement.dto.trello.Member;
import com.fradantim.spacexmanagement.service.TrelloBoardManager;
import com.fradantim.spacexmanagement.service.TrelloService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TrelloBoardManagerImpl implements TrelloBoardManager {

	private static Logger logger = LoggerFactory.getLogger(TrelloBoardManagerImpl.class);

	private TrelloService trelloService;
	private String boardId;
	private String boardName;

	public TrelloBoardManagerImpl(TrelloService trelloService, @Value("${trello.work-board.id:}") String boardId,
			@Value("${trello.work-board.name:}") String boardName) {
		if (StringUtils.isEmpty(boardId) && StringUtils.isEmpty(boardName)) {
			throw new IllegalArgumentException(
					"Any of 'trello.work-board.id' or 'trello.work-board.name' must be set, they can't be both empty.");
		}

		this.trelloService = trelloService;
		this.boardId = boardId;
		this.boardName = boardName;
	}

	@Override
	public Mono<Board> getWorkBoard() {
		Mono<Board> workBoard;
		if (!StringUtils.isEmpty(boardId)) {
			logger.trace("Looking for board with id '{}'", boardId);
			workBoard = trelloService.findBoardById(boardId);
		} else {
			logger.trace("Looking for board with name '{}'", boardName);
			workBoard = trelloService.findOneBoardByName(boardName);
		}

		return workBoard;
	}

	@Override
	public Mono<Column> getWorkBoardListByName(String name) {
		return getWorkBoard().flatMapMany(b -> {
			if (b.getLists() != null)
				return Flux.fromIterable(b.getLists());

			return Flux.empty();
		}).filter(l -> l != null && l.getName() != null && name.toLowerCase().equals(l.getName().toLowerCase())).next();
	}

	@Override
	public Mono<Column> getOrCreateWorkBoardListByName(String name) {
		return getWorkBoardListByName(name).switchIfEmpty(createList(name));
	}

	private Mono<Column> createList(String name) {
		return getWorkBoard().flatMap(board -> {
			logger.info("Creating List '{}'", name);
			return trelloService.createList(board, name);
		}).flatMap(s -> getWorkBoardListByName(name));
	}

	@Override
	public Mono<Label> getWorkBoardLabelByName(String name) {
		return getWorkBoard().flatMapMany(b -> {
			if (b.getLabels() != null)
				return Flux.fromIterable(b.getLabels());

			return Flux.empty();
		}).filter(l -> l != null && l.getName() != null && name.toLowerCase().equals(l.getName().toLowerCase())).next();
	}

	@Override
	public Mono<Label> getOrCreateWorkBoardLabelByName(String name) {
		return getWorkBoardLabelByName(name).switchIfEmpty(createLabel(name));
	}

	private Mono<Label> createLabel(String name) {
		return getWorkBoard().flatMap(board -> {
			logger.info("Creating Label '{}'", name);
			return trelloService.createLabel(board, name);
		}).flatMap(s -> getWorkBoardLabelByName(name));
	}

	@Override
	public Mono<Member> getRandomMember() {
		return getWorkBoard().flatMap(b -> {
			if (b.getMembers() != null)
				return Mono.just(b.getMembers().get(new Random().nextInt(b.getMembers().size())));
			return Mono.empty();
		});
	}
}
