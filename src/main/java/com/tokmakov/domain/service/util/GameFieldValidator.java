package com.tokmakov.domain.service.util;

import com.tokmakov.domain.exception.CellAlreadyOccupiedException;
import com.tokmakov.domain.exception.CoordinatesOutOfBoundsException;
import com.tokmakov.domain.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameFieldValidator {
    public void validateField(Game game, int[][] newField) {
        if (newField == null || newField.length != GameUtils.FIELD_SIZE)
            throw new IllegalArgumentException("Invalid field size");

        boolean newMoveDetected = false;
        for (int y = 0; y < GameUtils.FIELD_SIZE; y++) {
            if (newField[y] == null || newField[y].length != GameUtils.FIELD_SIZE)
                throw new IllegalArgumentException("Invalid field size");

            for (int x = 0; x < GameUtils.FIELD_SIZE; x++) {
                int originalCell = game.getCell(x, y);
                int candidateCell = newField[y][x];

                if (originalCell == candidateCell)
                    continue;

                if (originalCell != GameUtils.EMPTY_CELL)
                    throw new CellAlreadyOccupiedException(x, y);

                if (candidateCell != GameUtils.PLAYER_CELL)
                    throw new IllegalArgumentException("Player move must be X");

                if (newMoveDetected)
                    throw new IllegalArgumentException("Multiple new moves detected");

                newMoveDetected = true;
            }
        }

        if (!newMoveDetected)
            throw new IllegalArgumentException("No new move detected");
    }

    public void validateTurn(Game game, int x, int y) {
        if (x < 0 || y < 0 || x >= GameUtils.FIELD_SIZE || y >= GameUtils.FIELD_SIZE)
            throw new CoordinatesOutOfBoundsException(x, y, GameUtils.FIELD_SIZE);
        if (game.getGameField()[y][x] != GameUtils.EMPTY_CELL)
            throw new CellAlreadyOccupiedException(x, y);
    }
}
