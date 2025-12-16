package com.tokmakov.datasource.repository;

import com.tokmakov.domain.model.Game;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository {
    void saveGame(Game game);

    Optional<Game> findByUuid(UUID uuid);
}
