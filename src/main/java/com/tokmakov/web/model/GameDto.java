package com.tokmakov.web.model;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.tokmakov.domain.model.GameStatus;
import com.tokmakov.domain.service.util.GameUtils;
import lombok.Getter;

@Getter
public class GameDto {
    private final UUID uuid;
    private final Character[][] gameField;
    private final GameStatus gameStatus;

    public GameDto(UUID uuid, int[][] gameField, GameStatus gameStatus) {
        this.uuid = uuid;
        this.gameField = new Character[gameField.length][gameField.length];
        this.gameStatus = gameStatus;

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                this.gameField[i][j] = getSymbol(gameField[i][j]);
            }
        }
    }

    public List<String> getGameField() {
        return Arrays.stream(gameField)
                .map(this::rowToString)
                .toList();
    }

    private String rowToString(Character[] chars) {
        StringBuilder sb = new StringBuilder();
        for (Character ch :chars){
            sb.append(ch);
        }
        return sb.toString();
    }

    private char getSymbol(int n) {
        return switch (n) {
            case GameUtils.COMPUTER_CELL -> GameUtils.COMPUTER_SYMBOL;
            case GameUtils.PLAYER_CELL -> GameUtils.PLAYER_SYMBOL;
            default -> GameUtils.EMPTY_SYMBOL;
        };
    }
}
