package com.fradantim.spacexmanagement.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fradantim.spacexmanagement.service.TrelloBoardManager;
import com.fradantim.spacexmanagement.service.TrelloService;
import com.fradantim.spacexmanagement.trello.dto.Board;

@Service
public class TrelloBoardManagerImpl implements TrelloBoardManager {

	private static Logger logger = LoggerFactory.getLogger(TrelloBoardManagerImpl.class);

	private Board workBoard;

	public TrelloBoardManagerImpl(TrelloService trelloService, @Value("${trello.work-board.id:}") String boardId,
			@Value("${trello.work-board.name:}") String boardName) {
		if (StringUtils.isEmpty(boardId) && StringUtils.isEmpty(boardName)) {
			throw new IllegalArgumentException(
					"Any of 'trello.work-board.id' or 'trello.work-board.name' must be set, they can't be both empty.");
		}

		if (!StringUtils.isEmpty(boardId)) {
			logger.info("Looking for board with id {}", boardId);
			workBoard = trelloService.findBoardById(boardId).block();
		} else {
			logger.info("Looking for board with name {}", boardName);
			workBoard = trelloService.findOneBoardByName(boardName).block();
		}
	}

	@Override
	public Board getWorkBoard() {
		return workBoard;
	}
}
