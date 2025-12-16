package com.tokmakov.domain.service;

import com.tokmakov.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGameNotFound(GameNotFoundException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                Instant.now());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(value = {
            InvalidTurnException.class,
            CellAlreadyOccupiedException.class,
            GameNotInProgressException.class})
    public ResponseEntity<ErrorResponse> handleConflictException(RuntimeException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(),
                HttpStatus.CONFLICT.value(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler({CoordinatesOutOfBoundsException.class, InvalidUuidFormatException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}

