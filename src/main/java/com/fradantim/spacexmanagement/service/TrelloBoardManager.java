package com.fradantim.spacexmanagement.service;

import com.fradantim.spacexmanagement.trello.dto.Board;

public interface TrelloBoardManager {

	/** Returns the Board defined by the program arguments */
	public Board getWorkBoard();
}
