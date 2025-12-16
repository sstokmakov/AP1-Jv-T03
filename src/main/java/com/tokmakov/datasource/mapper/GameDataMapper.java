package com.tokmakov.datasource.mapper;

import com.tokmakov.datasource.model.GameData;
import com.tokmakov.domain.model.CellValue;
import com.tokmakov.domain.model.Game;
import org.springframework.stereotype.Component;

@Component
public class GameDataMapper {
    public GameData toData(Game game) {
        return new GameData(game.getUuid(), copyBoard(game.getGameField()), game.getGameStatus(), game.getTurnOwner());
    }

    public Game toDomain(GameData data) {
        Game game = new Game(data.uuid(), copyBoard(data.gameField()), data.turnOwner());
        game.setGameStatus(data.gameStatus());
        return game;
    }

    private CellValue[][] copyBoard(CellValue[][] board) {
        CellValue[][] copy = new CellValue[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[i].length);
        }
        return copy;
    }
}
