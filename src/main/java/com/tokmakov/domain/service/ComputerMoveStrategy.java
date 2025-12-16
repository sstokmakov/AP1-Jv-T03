package com.tokmakov.domain.service;

import com.tokmakov.domain.model.Game;

public interface ComputerMoveStrategy {
    Game makeMove(Game game);
}
