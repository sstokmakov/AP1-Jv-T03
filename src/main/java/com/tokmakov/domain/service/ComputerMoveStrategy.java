package com.tokmakov.domain.service;

public interface ComputerMoveStrategy {
    /**
     * @Return Возвращает ход по {x, y}
     */
    int[] findMove(int[][] field);
}
