package com.tokmakov.web.mapper;

import com.tokmakov.domain.service.util.GameUtils;
import com.tokmakov.web.model.GameUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class GameFieldMapper {
    public int[][] toDomainField(GameUpdateRequest request) {
        if (request == null || request.getGameField() == null)
            throw new IllegalArgumentException("Game field must be provided");

        String[] rows = request.getGameField();
        if (rows.length != GameUtils.FIELD_SIZE)
            throw new IllegalArgumentException("Invalid field size");

        int[][] field = new int[GameUtils.FIELD_SIZE][GameUtils.FIELD_SIZE];
        for (int y = 0; y < GameUtils.FIELD_SIZE; y++) {
            String row = rows[y];
            if (row == null || row.length() != GameUtils.FIELD_SIZE)
                throw new IllegalArgumentException("Invalid field size");

            for (int x = 0; x < GameUtils.FIELD_SIZE; x++) {
                field[y][x] = mapSymbol(row.charAt(x));
            }
        }
        return field;
    }

    private int mapSymbol(char symbol) {
        return switch (symbol) {
            case GameUtils.PLAYER_SYMBOL -> GameUtils.PLAYER_CELL;
            case GameUtils.COMPUTER_SYMBOL -> GameUtils.COMPUTER_CELL;
            case GameUtils.EMPTY_SYMBOL -> GameUtils.EMPTY_CELL;
            default -> throw new IllegalArgumentException("Unknown symbol: " + symbol);
        };
    }
}
