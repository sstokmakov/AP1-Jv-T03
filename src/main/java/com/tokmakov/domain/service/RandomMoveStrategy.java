package com.tokmakov.domain.service;

import com.tokmakov.domain.service.util.GameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomMoveStrategy implements ComputerMoveStrategy {

    @Override
    public int[] findMove(int[][] board) {
        int[] move = {0, 0};
        Random random = new Random();
        while (true) {
            int x = random.nextInt(GameUtils.FIELD_SIZE);
            int y = random.nextInt(GameUtils.FIELD_SIZE);
            if (board[y][x] == GameUtils.EMPTY_CELL) {
                move[0] = x;
                move[1] = y;
                break;
            }
        }

        return move;
    }
}
