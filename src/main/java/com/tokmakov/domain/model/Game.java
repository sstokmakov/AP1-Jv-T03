package com.tokmakov.domain.model;

import com.tokmakov.domain.service.util.GameUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public final class Game {
    private final UUID uuid;
    private final int[][] gameField;
    @Setter
    private GameStatus gameStatus;
    @Setter
    private TurnOwner turnOwner;

    public Game(UUID uuid, int[][] gameField, TurnOwner turnOwner) {
        this.uuid = uuid;
        this.gameField = gameField;
        gameStatus = GameStatus.IN_PROGRESS;
        this.turnOwner = turnOwner;
    }

    public boolean updateField(int x, int y, TurnOwner turn) {
        if (gameField[y][x] != 0)
            return false;

        gameField[y][x] = switch (turn) {
            case PLAYER_TURN -> GameUtils.PLAYER_CELL;
            case COMPUTER_TURN -> GameUtils.COMPUTER_CELL;
        };
        return true;
    }

    public int[][] getGameField() {
        int[][] copy = new int[gameField.length][gameField[0].length];
        for (int i = 0; i < gameField.length; i++) {
            System.arraycopy(gameField[i], 0, copy[i], 0, gameField[i].length);
        }
        return copy;
    }

    public int getCell(int x, int y) {
        return gameField[y][x];
    }
}
