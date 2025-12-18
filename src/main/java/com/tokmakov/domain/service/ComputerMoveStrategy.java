package com.tokmakov.domain.service;

public interface ComputerMoveStrategy {
    /**
     * @Return Возвращает ход {x, y}
     */
    int[] findMove(int[][] field);
}
