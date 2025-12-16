package com.tokmakov.domain.service;

import com.tokmakov.domain.model.Game;

public interface GameService {
    Game createGame();

    Game gameByUuid(String uuid);

    Game makeMove(String uuid, Integer x, Integer y);

    Game makeComputerMove(String uuid);
}
