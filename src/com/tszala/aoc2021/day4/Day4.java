package com.tszala.aoc2021.day4;

import com.tszala.aoc2021.utils.FileOps;

import java.io.IOException;
import java.util.List;

public class Day4 {
    public static void main(String[] args) throws IOException {
        List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day4/input.txt");
        Bingo bingo = BingoFactory.createBingo(lines);
        System.out.printf("Problem one result is %d\n", bingo.solveProblemOne());
        System.out.printf("Problem two result is %d\n", bingo.solveProblemTwo());
    }
}
