package com.tokmakov.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import com.tokmakov.datasource.GameFieldConverter;

@Getter
@Entity
@Table(name = "games")
public class Game {
    @Id
    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Setter
    @Convert(converter = GameFieldConverter.class)
    @Column(name = "game_field", nullable = false, columnDefinition = "text")
    private int[][] gameField;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "game_status", nullable = false)
    private GameStatus gameStatus;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "turn_owner", nullable = false)
    private TurnOwner turnOwner;

    protected Game() {
    }

    public Game(UUID uuid, int[][] gameField, TurnOwner turnOwner) {
        this.uuid = uuid;
        this.gameField = gameField;
        gameStatus = GameStatus.IN_PROGRESS;
        this.turnOwner = turnOwner;
    }

    public int[][] getGameField() {
        int[][] copy = new int[gameField.length][gameField[0].length];
        for (int i = 0; i < gameField.length; i++) {
            System.arraycopy(gameField[i], 0, copy[i], 0, gameField[i].length);
        }
        return copy;
    }

    public void updateField(int x, int y, int value) {
        gameField[y][x] = value;
    }

    public int getCell(int x, int y) {
        return gameField[y][x];
    }
}
