package com.tokmakov.domain.service;

import com.tokmakov.domain.model.CellValue;
import com.tokmakov.domain.model.Game;
import com.tokmakov.domain.model.GameStatus;
import com.tokmakov.domain.model.TurnOwner;
import com.tokmakov.domain.service.util.GameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MinimaxMoveStrategy implements ComputerMoveStrategy {
    @Override
    public Game makeMove(Game game) {
        CellValue[][] board = game.getGameField();
        int[] move = bestMove(board);
        if (move[0] == -1) {
            return game;
        }

        game.updateField(move[1], move[0], TurnOwner.COMPUTER_TURN);
        return game;
    }

    private static int[] bestMove(CellValue[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int[] best = {-1, -1};

        for (int[] cell : emptyCells(board)) {
            int r = cell[0], c = cell[1];
            board[r][c] = CellValue.O_MARK;

            int score = minimax(board, false);
            board[r][c] = CellValue.EMPTY;

            if (score > bestScore) {
                bestScore = score;
                best[0] = r;
                best[1] = c;
            }
        }
        return best;
    }

    private static int minimax(CellValue[][] board, boolean isMaximizing) {
        GameStatus status = GameUtils.calculateGameStatus(board);
        if (status != GameStatus.IN_PROGRESS) {
            if (status == GameStatus.O_WIN) return 1;
            if (status == GameStatus.X_WIN) return -1;
            return 0;
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int[] cell : emptyCells(board)) {
                int r = cell[0], c = cell[1];
                board[r][c] = CellValue.O_MARK;
                best = Math.max(best, minimax(board, false));
                board[r][c] = CellValue.EMPTY;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int[] cell : emptyCells(board)) {
                int r = cell[0], c = cell[1];
                board[r][c] = CellValue.X_MARK;
                best = Math.min(best, minimax(board, true));
                board[r][c] = CellValue.EMPTY;
            }
            return best;
        }
    }

    private static List<int[]> emptyCells(CellValue[][] board) {
        List<int[]> cells = new ArrayList<>();
        for (int r = 0; r < GameUtils.FIELD_SIZE; r++) {
            for (int c = 0; c < GameUtils.FIELD_SIZE; c++) {
                if (board[r][c] == CellValue.EMPTY)
                    cells.add(new int[]{r, c});
            }
        }
        return cells;
    }
}
