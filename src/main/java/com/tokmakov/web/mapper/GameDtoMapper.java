package com.tokmakov.web.mapper;

import com.tokmakov.domain.model.CellValue;
import com.tokmakov.domain.model.Game;
import com.tokmakov.web.model.GameDto;
import org.springframework.stereotype.Component;

@Component
public class GameDtoMapper {
    public GameDto toDto(Game game) {
        return new GameDto(copyBoard(game.getGameField()), game.getGameStatus());
    }

    private CellValue[][] copyBoard(CellValue[][] board) {
        CellValue[][] copy = new CellValue[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[i].length);
        }
        return copy;
    }
}
