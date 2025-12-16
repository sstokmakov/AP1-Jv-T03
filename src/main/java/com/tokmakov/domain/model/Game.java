package com.tokmakov.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public final class Game {
    private final UUID uuid;
    private final CellValue[][] gameField;
    @Setter
    private GameStatus gameStatus;
    @Setter
    private TurnOwner turnOwner;

    public Game(UUID uuid, CellValue[][] gameField, TurnOwner turnOwner) {
        this.uuid = uuid;
        this.gameField = gameField;
        gameStatus = GameStatus.IN_PROGRESS;
        this.turnOwner = turnOwner;
    }

    public boolean updateField(int x, int y, TurnOwner turn) {
        if (gameField[y][x] != CellValue.EMPTY)
            return false;

        gameField[y][x] = switch (turn) {
            case PLAYER_TURN -> CellValue.X_MARK;
            case COMPUTER_TURN -> CellValue.O_MARK;
        };
        return true;
    }

    public CellValue[][] getGameField() {
        CellValue[][] copy = new CellValue[gameField.length][gameField[0].length];
        for (int i = 0; i < gameField.length; i++) {
            System.arraycopy(gameField[i], 0, copy[i], 0, gameField[i].length);
        }
        return copy;
    }
}
