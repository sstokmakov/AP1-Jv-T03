package com.tokmakov.domain.exception;

import com.tokmakov.domain.model.GameStatus;

public class GameNotInProgressException extends RuntimeException {
    public GameNotInProgressException(GameStatus status) {
        super("Game is not in progress. Current status: " + status);
    }
}
