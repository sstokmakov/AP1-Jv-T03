package com.tokmakov.domain.service;

import com.tokmakov.datasource.GameRepository;
import com.tokmakov.domain.exception.*;
import com.tokmakov.domain.model.Game;
import com.tokmakov.domain.model.GameStatus;
import com.tokmakov.domain.model.TurnOwner;
import com.tokmakov.domain.service.util.GameFieldValidator;
import com.tokmakov.domain.service.util.GameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository repository;
    private final ComputerMoveStrategy computerLogicService;
    private final GameFieldValidator fieldValidator;

    @Override
    public Game createGame() {
        int[][] field = GameUtils.createEmptyField();
        Game game = new Game(UUID.randomUUID(), field, TurnOwner.PLAYER_TURN);
        repository.save(game);
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
    public Game processTurn(String uuid, int[][] newField) {
        Game game = getGameByUuid(uuid);
        if (isGameFinished(game)) throw new GameNotInProgressException(game.getGameStatus());
        makePlayerMove(game, newField);
        if (isGameFinished(game)) return game;
        makeComputerMove(game);
        return game;
    }

    @Override
    public void validateField(Game game, int[][] newField) {
        fieldValidator.validateField(game, newField);
    }

    private void makePlayerMove(Game game, int[][] newField) {
        fieldValidator.validateField(game, newField);
        game.setGameField(newField);
        game.setGameStatus(GameUtils.calculateGameStatus(game.getGameField()));
        repository.save(game);
    }

    public void makeComputerMove(Game game) {
        if (isGameFinished(game)) throw new GameNotInProgressException(game.getGameStatus());
        int[] move = computerLogicService.findMove(game.getGameField());
        applyMove(game, move[0], move[1], GameUtils.COMPUTER_CELL);
        game.setGameStatus(GameUtils.calculateGameStatus(game.getGameField()));
        repository.save(game);
    }

    private void applyMove(Game game, int x, int y, int value) {
        fieldValidator.validateTurn(game, x, y);
        game.updateField(x, y, value);
    }

    private Game getGameByUuid(String uuid) {
        UUID parsedUuid = parseUuid(uuid);
        Optional<Game> game = repository.findById(parsedUuid);
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
