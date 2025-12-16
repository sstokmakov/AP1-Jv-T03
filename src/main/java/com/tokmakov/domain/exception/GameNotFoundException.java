package com.tokmakov.domain.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String uuid) {
        super("Game with uuid: " + uuid + " not found");
    }
}
