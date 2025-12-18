package com.tokmakov.domain.service;

import com.tokmakov.datasource.repository.GameRepository;
import com.tokmakov.domain.exception.*;
import com.tokmakov.domain.model.Game;
import com.tokmakov.domain.model.GameStatus;
import com.tokmakov.domain.model.TurnOwner;
import com.tokmakov.domain.service.util.GameFieldValidator;
import com.tokmakov.domain.service.util.GameUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository repository;
    private final ComputerMoveStrategy computerLogicService;
    private final GameFieldValidator fieldValidator;

    public GameServiceImpl(GameRepository repository,
                           @Qualifier("minimaxMoveStrategy") ComputerMoveStrategy computerLogicService,
                           GameFieldValidator fieldValidator) {
        this.repository = repository;
        this.computerLogicService = computerLogicService;
        this.fieldValidator = fieldValidator;
    }

    @Override
    public Game createGame() {
        int[][] field = GameUtils.createEmptyField();
        Game game = new Game(UUID.randomUUID(), field, TurnOwner.PLAYER_TURN);
        repository.saveGame(game);
        return game;
    }

    @Override
    public boolean isGameFinished(Game game) {
        return game.getGameStatus() != GameStatus.IN_PROGRESS;
    }

    @Override
    public Game gameByUuid(String uuid) {
        return getGameByUuid(uuid);
    }

    @Override
    public Game makeMove(String uuid, Integer x, Integer y) {
        Game game = getGameByUuid(uuid);
        if (isGameFinished(game)) throw new GameNotInProgressException(game.getGameStatus());
        validateTurnOrder(game, TurnOwner.PLAYER_TURN);
        applyMove(game, TurnOwner.PLAYER_TURN, x, y);
        game.setTurnOwner(TurnOwner.COMPUTER_TURN);
        game.setGameStatus(GameUtils.calculateGameStatus(game.getGameField()));
        repository.saveGame(game);
        return game;
    }

    @Override
    public Game makeComputerMove(String uuid) {
        Game game = getGameByUuid(uuid);
        if (isGameFinished(game)) throw new GameNotInProgressException(game.getGameStatus());
        validateTurnOrder(game, TurnOwner.COMPUTER_TURN);
        int[] move = computerLogicService.findMove(game.getGameField());
        applyMove(game, TurnOwner.COMPUTER_TURN, move[0], move[1]);
        game.setTurnOwner(TurnOwner.PLAYER_TURN);
        game.setGameStatus(GameUtils.calculateGameStatus(game.getGameField()));
        repository.saveGame(game);
        return game;
    }

    @Override
    public void validateField(Game game, int[][] newField) {
        fieldValidator.validate(game, newField);
    }

    private void applyMove(Game game, TurnOwner turnOwner, int x, int y) {
        validateCoordinates(x, y);
        boolean updated = game.updateField(x, y, turnOwner);
        if (!updated)
            throw new CellAlreadyOccupiedException(x, y);
    }

    private void validateCoordinates(int x, int y) {
        if (x < 0 || y < 0 || x >= GameUtils.FIELD_SIZE || y >= GameUtils.FIELD_SIZE)
            throw new CoordinatesOutOfBoundsException(x, y, GameUtils.FIELD_SIZE);
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
