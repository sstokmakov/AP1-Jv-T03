package com.tokmakov.domain.service;

import com.tokmakov.datasource.repository.GameRepository;
import com.tokmakov.domain.exception.*;
import com.tokmakov.domain.model.CellValue;
import com.tokmakov.domain.model.Game;
import com.tokmakov.domain.model.GameStatus;
import com.tokmakov.domain.model.TurnOwner;
import com.tokmakov.domain.service.util.GameUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository repository;
    private final ComputerMoveStrategy computerLogicService;

    public GameServiceImpl(GameRepository repository,
                           @Qualifier("minimaxMoveStrategy") ComputerMoveStrategy computerLogicService) {
        this.repository = repository;
        this.computerLogicService = computerLogicService;
    }

    @Override
    public Game createGame() {
        CellValue[][] field = createEmptyField();
        Game game = new Game(UUID.randomUUID(), field, TurnOwner.PLAYER_TURN);
        repository.saveGame(game);
        return game;
    }

    private CellValue[][] createEmptyField() {
        CellValue[][] field = new CellValue[GameUtils.FIELD_SIZE][GameUtils.FIELD_SIZE];
        for (int i = 0; i < GameUtils.FIELD_SIZE; i++) {
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
        if (game.getGameStatus() != GameStatus.IN_PROGRESS)
            throw new GameNotInProgressException(game.getGameStatus());
        validateTurnOrder(game, TurnOwner.PLAYER_TURN);
        updateFieldWhenPlayerMove(game, x, y);
        game.setTurnOwner(TurnOwner.COMPUTER_TURN);
        game.setGameStatus(GameUtils.calculateGameStatus(game.getGameField()));
        repository.saveGame(game);
        return game;
    }

    private void updateFieldWhenPlayerMove(Game game, Integer x, Integer y) {
        if (x < 0 || y < 0 || x >= GameUtils.FIELD_SIZE || y >= GameUtils.FIELD_SIZE)
            throw new CoordinatesOutOfBoundsException(x, y, GameUtils.FIELD_SIZE);
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
        game.setGameStatus(GameUtils.calculateGameStatus(game.getGameField()));
        repository.saveGame(game);
        return game;
    }

    private void validateTurnOrder(Game game, TurnOwner expectedTurn) {
        if (expectedTurn != game.getTurnOwner())
            throw new InvalidTurnException(game.getTurnOwner().name());
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
