package com.tokmakov.web.model;

import com.tokmakov.domain.model.CellValue;
import com.tokmakov.domain.model.GameStatus;
import lombok.Getter;

public class GameDto {
    private final Character[][] gameField;
    @Getter
    private final GameStatus gameStatus;

    public GameDto(CellValue[][] gameField, GameStatus gameStatus) {
        this.gameField = new Character[gameField.length][gameField.length];
        this.gameStatus = gameStatus;

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                this.gameField[i][j] = gameField[i][j].getSymbol();
            }
        }
    }

    public String getGameField() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                sb.append(gameField[i][j]);
            }
            if (i < gameField.length - 1)
                sb.append('\n');
        }
        return sb.toString();
    }
}
