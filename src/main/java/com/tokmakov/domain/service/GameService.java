package com.tokmakov.domain.service;

import com.tokmakov.domain.model.Game;

import java.util.UUID;

public interface GameService {
    UUID createGame();

    Game gameByUuid(String uuid);

    Game makeMove(String uuid, Integer x, Integer y);

    Game makeComputerMove(String uuid);

    boolean isGameOver(String uuid);

}
