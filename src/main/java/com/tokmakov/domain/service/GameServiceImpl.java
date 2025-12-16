package com.tokmakov.domain.service;

import com.tokmakov.datasource.repository.GameRepository;
import com.tokmakov.domain.exception.CellAlreadyOccupiedException;
import com.tokmakov.domain.exception.CoordinatesOutOfBoundsException;
import com.tokmakov.domain.exception.GameNotFoundException;
import com.tokmakov.domain.exception.InvalidTurnException;
import com.tokmakov.domain.exception.InvalidUuidFormatException;
import com.tokmakov.domain.model.CellValue;
import com.tokmakov.domain.model.Game;
import com.tokmakov.domain.model.GameStatus;
import com.tokmakov.domain.model.TurnOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private static final int FIELD_SIZE = 3;

    private final GameRepository repository;
    private final ComputerMoveStrategy computerLogicService;

    @Override
    public Game createGame() {
        CellValue[][] field = createEmptyField();
        Game game = new Game(UUID.randomUUID(), field, TurnOwner.PLAYER_TURN);
        repository.saveGame(game);
        return game;
    }

    private CellValue[][] createEmptyField() {
        CellValue[][] field = new CellValue[GameServiceImpl.FIELD_SIZE][GameServiceImpl.FIELD_SIZE];
        for (int i = 0; i < GameServiceImpl.FIELD_SIZE; i++) {
            Arrays.fill(field[i], CellValue.EMPTY);
        }
        return field;
    }

    @Override
    public Game gameByUuid(String uuid) {
        return getGameByUuid(uuid);
    }

    @Override
    public Game makeMove(String uuid, Integer x, Integer y) {
        Game game = getGameByUuid(uuid);

        validateTurnOrder(game, TurnOwner.PLAYER_TURN);
        updateFieldWhenPlayerMove(game, x, y);
        game.setTurnOwner(TurnOwner.COMPUTER_TURN);

        if (isGameOver(game)) {
            game.setGameStatus(GameStatus.X_WIN);
        } else if (isGameDraw(game)) {
            game.setGameStatus(GameStatus.DRAW);
        }
        repository.saveGame(game);
        return game;
    }

    private void updateFieldWhenPlayerMove(Game game, Integer x, Integer y) {
        if (x < 0 || y < 0 || x >= FIELD_SIZE || y >= FIELD_SIZE)
            throw new CoordinatesOutOfBoundsException(x, y, FIELD_SIZE);
        boolean isUpdated = game.updateField(x, y, TurnOwner.PLAYER_TURN);
        if (!isUpdated)
            throw new CellAlreadyOccupiedException(x, y);
    }

    @Override
    public Game makeComputerMove(String uuid) {
        Game game = getGameByUuid(uuid);
        validateTurnOrder(game, TurnOwner.COMPUTER_TURN);
        game.setTurnOwner(TurnOwner.PLAYER_TURN);
        game = computerLogicService.makeMove(game);
        if (isGameOver(game)) {
            game.setGameStatus(GameStatus.O_WIN);
        } else if (isGameDraw(game)) {
            game.setGameStatus(GameStatus.DRAW);
        }
        repository.saveGame(game);
        return game;
    }

    private void validateTurnOrder(Game game, TurnOwner expectedTurn) {
        if (expectedTurn != game.getTurnOwner())
            throw new InvalidTurnException(game.getTurnOwner().name());
    }

    private boolean isGameOver(Game game) {
        return checkVertical(game) || checkHorizontal(game) || checkDiagonal(game);
    }

    private boolean isGameDraw(Game game) {
        CellValue[][] field = game.getGameField();
        for (CellValue[] cellValues : field) {
            for (CellValue cellValue : cellValues) {
                if (cellValue == CellValue.EMPTY) return false;
            }
        }
        return true;
    }

    private boolean checkVertical(Game game) {
        CellValue[][] field = game.getGameField();
        int size = field.length;
        for (int i = 0; i < size; i++) {
            CellValue current = field[0][i];
            boolean flag = true;
            for (int j = 1; j < size; j++) {
                if (field[j][i] == CellValue.EMPTY || field[j][i] != current) {
                    flag = false;
                    break;
                }
            }
            if (flag) return true;
        }
        return false;
    }

    private boolean checkHorizontal(Game game) {
        CellValue[][] field = game.getGameField();
        int size = field.length;
        for (CellValue[] cellValues : field) {
            CellValue current = cellValues[0];
            boolean flag = true;
            for (int j = 1; j < size; j++) {
                if (cellValues[j] == CellValue.EMPTY || cellValues[j] != current) {
                    flag = false;
                    break;
                }
            }
            if (flag) return true;
        }
        return false;
    }

    private boolean checkDiagonal(Game game) {
        CellValue[][] field = game.getGameField();
        int size = field.length;
        CellValue current = field[0][0];
        boolean flag = true;
        for (int i = 1; i < size; i++) {
            if (field[i][i] == CellValue.EMPTY || field[i][i] != current) {
                flag = false;
                break;
            }
        }
        if (flag) return true;

        flag = true;
        current = field[0][size - 1];
        for (int i = 0, j = size - 1; i < size; i++, j--) {
            if (field[i][j] == CellValue.EMPTY || field[i][j] != current) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private Game getGameByUuid(String uuid) {
        UUID parsedUuid = parseUuid(uuid);
        Optional<Game> game = repository.findByUuid(parsedUuid);
        return game.orElseThrow(() -> new GameNotFoundException(uuid));
    }

    private UUID parseUuid(String uuid) {
        if (uuid == null || uuid.isBlank())
            throw new InvalidUuidFormatException(String.valueOf(uuid));
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUuidFormatException(uuid);
        }
    }
}
