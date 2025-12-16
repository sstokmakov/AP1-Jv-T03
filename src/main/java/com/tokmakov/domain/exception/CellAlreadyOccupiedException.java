package com.tokmakov.domain.exception;

public class CellAlreadyOccupiedException extends RuntimeException {
    public CellAlreadyOccupiedException(int x, int y) {
        super("Cell x: " + x + " y: " + y + " already occupied");
    }
}
