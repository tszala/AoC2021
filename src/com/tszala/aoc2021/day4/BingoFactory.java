package com.tszala.aoc2021.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BingoFactory {

    public static Bingo createBingo(List<String> lines) {
        List<Integer> moves = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<BingoBoard> boards = createBoards(lines.subList(2, lines.size()), new ArrayList<>(), 0);
        return new Bingo(boards, moves);
    }

    private static List<BingoBoard> createBoards(List<String> lines, List<BingoBoard> b, int number) {
        if(lines.size() == 0) {
            return b;
        }
        BingoBoard board = new BingoBoard(number, lines.subList(0, 5));
        b.add(board);
        if(lines.size() > 5) {
            return createBoards(lines.subList(6, lines.size()), b, ++number);
        } else {
            return b;
        }
    }

}
