package com.tokmakov.datasource.repository;

import com.tokmakov.datasource.mapper.GameDataMapper;
import com.tokmakov.datasource.model.GameData;
import com.tokmakov.domain.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {
    private final Map<UUID, GameData> games = new ConcurrentHashMap<>();
    private final GameDataMapper mapper;

    @Override
    public void saveGame(Game game) {
        games.put(game.getUuid(), mapper.toData(game));
    }

    @Override
    public Optional<Game> findByUuid(UUID uuid) {
        return Optional.ofNullable(games.get(uuid))
                .map(mapper::toDomain);
    }
}
