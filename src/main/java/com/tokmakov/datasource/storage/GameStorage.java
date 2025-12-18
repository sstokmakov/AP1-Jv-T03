package com.tokmakov.datasource.storage;

import com.tokmakov.datasource.model.GameData;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameStorage {
    private final Map<UUID, GameData> games = new ConcurrentHashMap<>();

    public void save(GameData gameData) {
        games.put(gameData.uuid(), gameData);
    }

    public Optional<GameData> findByUuid(UUID uuid) {
        return Optional.ofNullable(games.get(uuid));
    }
}
