package com.tokmakov.domain.service;

import com.tokmakov.domain.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinimaxMoveStrategy implements ComputerMoveStrategy {
    @Override
    public Game makeMove(Game game) {
        return null;
    }
}
