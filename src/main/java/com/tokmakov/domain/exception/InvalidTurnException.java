package com.tokmakov.domain.exception;

public class InvalidTurnException extends RuntimeException {
    public InvalidTurnException(String turnOwner) {
        super("Invalid turn order is the " + turnOwner + " turn now");
    }
}
