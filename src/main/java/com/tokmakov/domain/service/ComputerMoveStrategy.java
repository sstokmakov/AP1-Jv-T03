package com.tokmakov.domain.service;

public interface ComputerMoveStrategy {
    /**
     * @return Возвращает ход {x, y}
     */
    int[] findMove(int[][] field);
}
