package com.tokmakov.domain.exception;

public class InvalidUuidFormatException extends RuntimeException {
    public InvalidUuidFormatException(String uuid) {
        super("UUID '" + uuid + "' has invalid format");
    }
}
