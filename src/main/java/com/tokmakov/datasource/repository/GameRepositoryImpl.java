package com.tokmakov.datasource.repository;

import com.tokmakov.datasource.mapper.GameDataMapper;
import com.tokmakov.datasource.storage.GameStorage;
import com.tokmakov.domain.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository {
    private final GameDataMapper mapper;
    private final GameStorage storage;

    @Override
    public void saveGame(Game game) {
        storage.save(mapper.toData(game));
    }

    @Override
    public Optional<Game> findByUuid(UUID uuid) {
        return storage.findByUuid(uuid)
                .map(mapper::toDomain);
    }
}
