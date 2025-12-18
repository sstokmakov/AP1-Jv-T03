package com.tokmakov.domain.service;

import com.tokmakov.domain.model.GameStatus;
import com.tokmakov.domain.service.util.GameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MinimaxMoveStrategy implements ComputerMoveStrategy {
    @Override
    public int[] findMove(int[][] board) {
        return bestMove(board);
    }

    private static int[] bestMove(int[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] best = {0, 0};

        for (int[] cell : emptyCells(board)) {
            int x = cell[0], y = cell[1];
            board[y][x] = GameUtils.COMPUTER_CELL;

            int score = minimax(board, false);
            board[y][x] = GameUtils.EMPTY_CELL;

            if (score > bestScore) {
                bestScore = score;
                best[0] = x;
                best[1] = y;
            }
        }
        return best;
    }

    private static int minimax(int[][] board, boolean isMaximizing) {
        GameStatus status = GameUtils.calculateGameStatus(board);
        if (status != GameStatus.IN_PROGRESS) {
            if (status == GameStatus.O_WIN) return 1;
            if (status == GameStatus.X_WIN) return -1;
            return 0;
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int[] cell : emptyCells(board)) {
                int x = cell[0], y = cell[1];
                board[y][x] = GameUtils.COMPUTER_CELL;
                best = Math.max(best, minimax(board, false));
                board[y][x] = GameUtils.EMPTY_CELL;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int[] cell : emptyCells(board)) {
                int x = cell[0], y = cell[1];
                board[y][x] = GameUtils.PLAYER_CELL;
                best = Math.min(best, minimax(board, true));
                board[y][x] = GameUtils.EMPTY_CELL;
            }
            return best;
        }
    }

    private static List<int[]> emptyCells(int[][] board) {
        List<int[]> cells = new ArrayList<>();
        for (int y = 0; y < GameUtils.FIELD_SIZE; y++) {
            for (int x = 0; x < GameUtils.FIELD_SIZE; x++) {
                if (board[y][x] == GameUtils.EMPTY_CELL)
                    cells.add(new int[]{x, y});
            }
        }
        return cells;
    }
}
