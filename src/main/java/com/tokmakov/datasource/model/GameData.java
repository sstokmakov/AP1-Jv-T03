package com.tokmakov.datasource.model;

import com.tokmakov.domain.model.GameStatus;
import com.tokmakov.domain.model.TurnOwner;

import java.util.UUID;

public record GameData(UUID uuid, int[][] gameField, GameStatus gameStatus, TurnOwner turnOwner) {
}
