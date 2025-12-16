package com.tokmakov.domain.model;

import lombok.Getter;

@Getter
public enum CellValue {
    EMPTY(' '),
    X_MARK('X'),
    O_MARK('O');

    private final Character symbol;

    CellValue(Character symbol) {
        this.symbol = symbol;
    }
}
