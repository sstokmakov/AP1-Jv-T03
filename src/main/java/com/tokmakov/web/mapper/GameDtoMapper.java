package com.tokmakov.web.mapper;

import com.tokmakov.domain.model.Game;
import com.tokmakov.web.model.GameDto;
import org.springframework.stereotype.Component;

@Component
public class GameDtoMapper {
    public GameDto toDto(Game game) {
        return new GameDto(game.getUuid(), game.getGameField(), game.getGameStatus());
    }
}
