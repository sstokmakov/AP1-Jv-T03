package com.tokmakov.datasource;

import com.tokmakov.domain.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<Game, UUID> {
}
