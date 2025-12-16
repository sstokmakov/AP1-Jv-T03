package com.tokmakov.datasource.model;

import com.tokmakov.domain.model.CellValue;
import com.tokmakov.domain.model.GameStatus;

import java.util.UUID;

public record GameData(UUID uuid, CellValue[][] gameField, GameStatus gameStatus) {
}
