package com.tokmakov.datasource.mapper;

import com.tokmakov.datasource.model.GameData;
import com.tokmakov.domain.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameDataMapper {
    public GameData toData(Game game) {
        return new GameData(game.getUuid(), game.getGameField(), game.getGameStatus(), game.getTurnOwner());
    }

    public Game toDomain(GameData data) {
        Game game = new Game(data.uuid(), data.gameField(), data.turnOwner());
        game.setGameStatus(data.gameStatus());
        return game;
    }
}
