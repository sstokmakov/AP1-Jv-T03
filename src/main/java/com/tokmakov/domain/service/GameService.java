package com.tokmakov.domain.service;

import com.tokmakov.domain.model.Game;

public interface GameService {
    Game createGame();

    Game gameByUuid(String uuid);

    Game processTurn(String uuid, int[][] newField);

    void validateField(Game game, int[][] newField);

    boolean isGameFinished(Game game);
}
