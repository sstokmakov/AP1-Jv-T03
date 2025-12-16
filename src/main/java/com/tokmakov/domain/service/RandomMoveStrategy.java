package com.tokmakov.domain.service;

import com.tokmakov.domain.model.Game;
import com.tokmakov.domain.model.TurnOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomMoveStrategy implements ComputerMoveStrategy {

    @Override
    public Game makeMove(Game game) {
        Random random = new Random();

        while (true) {
            int x = random.nextInt(3);
            int y = random.nextInt(3);
            if (game.updateField(x, y, TurnOwner.COMPUTER_TURN))
                break;
        }

        return game;
    }
}
