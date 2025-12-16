package com.tokmakov.domain.service.util;

import com.tokmakov.domain.model.CellValue;
import com.tokmakov.domain.model.GameStatus;

public class GameUtils {
    public static final int FIELD_SIZE = 3;

    public static GameStatus calculateGameStatus(CellValue[][] boardGame) {
        for (int i = 0; i < 3; i++) {
            if (boardGame[i][0] != CellValue.EMPTY && boardGame[i][0] == boardGame[i][1] && boardGame[i][1] == boardGame[i][2])
                return boardGame[i][0] == CellValue.O_MARK ? GameStatus.O_WIN : GameStatus.X_WIN;
            if (boardGame[0][i] != CellValue.EMPTY && boardGame[0][i] == boardGame[1][i] && boardGame[1][i] == boardGame[2][i])
                return boardGame[0][i]== CellValue.O_MARK ? GameStatus.O_WIN : GameStatus.X_WIN;
        }

        if (boardGame[0][0] != CellValue.EMPTY && boardGame[0][0] == boardGame[1][1] && boardGame[1][1] == boardGame[2][2])
            return boardGame[0][0] == CellValue.O_MARK ? GameStatus.O_WIN : GameStatus.X_WIN;
        if (boardGame[0][2] != CellValue.EMPTY && boardGame[0][2] == boardGame[1][1] && boardGame[1][1] == boardGame[2][0])
            return boardGame[0][2] == CellValue.O_MARK ? GameStatus.O_WIN : GameStatus.X_WIN;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (boardGame[r][c] == CellValue.EMPTY)
                    return GameStatus.IN_PROGRESS;
            }
        }
        return GameStatus.DRAW;
    }
}
