package com.tszala.aoc2021.day4;

import com.tszala.aoc2021.utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BingoBoard {
    List<List<Tuple<Integer, Boolean>>> board = new ArrayList<>();
    int rowCompleted = -1;
    int columnCompleted = -1;
    private int number;

    public BingoBoard(int number, List<String> lines) {
        this.number = number;
        for (int i = 0; i < lines.size(); i++) {
            board.add(Arrays.stream(lines.get(i).split(" ")).filter(s -> s.length() > 0)
                    .map(Integer::parseInt).map(n -> new Tuple<>(n, false))
                    .collect(Collectors.toList()));
        }
    }

    public void tick(Integer number) {
        for (int i = 0; i < board.size(); i++) {
            List<Tuple<Integer, Boolean>> row = board.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j).left.equals(number)) {
                    row.set(j, new Tuple<Integer, Boolean>(number, true));
                }
            }
        }
    }

    public boolean completed() {
        rowCompleted = -1;
        for (int i = 0; i < board.size() && rowCompleted < 0; i++) {
            List<Tuple<Integer, Boolean>> row = board.get(i);
            if (rowCompleted(row)) {
                rowCompleted = i;
            }
        }
        if (rowCompleted >= 0) {
            return true;
        }
        columnCompleted = -1;
        for (int i = 0; i < board.get(0).size() && columnCompleted < 0; i++) {
            if (columnCompleted(i)) {
                columnCompleted = i;
            }
        }
        return columnCompleted >= 0;
    }

    private boolean rowCompleted(List<Tuple<Integer, Boolean>> row) {
        Optional<Tuple<Integer, Boolean>> notSet = row.stream().filter(t -> t.right == Boolean.FALSE).findAny();
        if (notSet.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean columnCompleted(int column) {
        for (int i = 0; i < board.size(); i++) {
            if (!board.get(i).get(column).right) {
                return false;
            }
        }
        return true;
    }

    public int result() {
        for (int i = 0; i < board.size(); i++) {
            if (rowCompleted(board.get(i))) {
                return board.get(i).stream().map(t -> t.left).reduce(0, (a, b) -> a + b);
            }
        }
        return -1;
    }

    public Integer sumUnticked() {
        return board.stream().flatMap(List::stream).filter(t -> t.right == Boolean.FALSE).map(t->t.left).reduce(0, (a,b) -> a+b);
    }
}
