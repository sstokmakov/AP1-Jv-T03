package com.tokmakov.di;

import com.tokmakov.datasource.mapper.GameDataMapper;
import com.tokmakov.datasource.repository.GameRepository;
import com.tokmakov.datasource.repository.GameRepositoryImpl;
import com.tokmakov.datasource.storage.GameStorage;
import com.tokmakov.domain.service.ComputerMoveStrategy;
import com.tokmakov.domain.service.GameService;
import com.tokmakov.domain.service.GameServiceImpl;
import com.tokmakov.domain.service.util.GameFieldValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public GameStorage gameStorage() {
        return new GameStorage();
    }

    @Bean
    public GameRepository gameRepository(GameStorage storage, GameDataMapper mapper) {
        return new GameRepositoryImpl(mapper, storage);
    }

    @Bean
    public GameService gameService(GameRepository repository,
                                   @Qualifier("minimaxMoveStrategy") ComputerMoveStrategy computerMoveStrategy,
                                   GameFieldValidator fieldValidator) {
        return new GameServiceImpl(repository, computerMoveStrategy, fieldValidator);
    }
}
