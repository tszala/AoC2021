package com.tszala.aoc2021.day4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Bingo {
    private List<BingoBoard> boards;
    private List<Integer> moves;

    public Bingo(List<BingoBoard> boards, List<Integer> moves) {
        this.boards = boards;
        this.moves = moves;
    }

    public Integer solveProblemOne() {
        for(Integer move : moves) {
            for(BingoBoard board : boards) {
                board.tick(move);
                if(board.completed()) {
                    return move * board.sumUnticked();
                }
            }
        }
        throw new IllegalStateException("No winning board");
    }

    public Integer solveProblemTwo() {
        BingoBoard lastBoard = null;
        int winningMove = -1;
        List<BingoBoard> remainingBoards = new ArrayList<>(boards);
        for(Integer move : moves) {
            for(BingoBoard board : remainingBoards) {
                board.tick(move);
            }

            for(int i = remainingBoards.size() - 1; i >= 0; i--) {
                if(remainingBoards.get(i).completed()) {
                    winningMove = move;
                    lastBoard = remainingBoards.get(i);
                    break;
                }
            }
            remainingBoards = remainingBoards.stream().filter(board -> !board.completed()).collect(Collectors.toList());
        }
        if(lastBoard == null) {
            throw new IllegalStateException("No winning board");
        }
        return lastBoard.sumUnticked() * winningMove;
    }
}
