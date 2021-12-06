package com.tszala.aoc2021.day5;

import com.tszala.aoc2021.utils.FileOps;
import com.tszala.aoc2021.utils.Tuple;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Day5 {


    public static void main(String[] args) throws IOException {
        List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day5/input.txt");
        Vents vents = LineFactory.createVentsWithHorizontalAndVerticalLines(lines);
        System.out.printf("Problem one answer is %d\n", vents.doubledPoints());
        Vents ventsDiagonal = LineFactory.createVentsWithHorizontalVerticalAndDiagonalLines(lines);
        System.out.printf("Problem two answer is %d\n", ventsDiagonal.doubledPoints());
    }


    public List<Tuple<Integer, Integer>> points(Tuple<Integer, Integer> start, Tuple<Integer, Integer> end) {
        return Collections.emptyList();
    }
}
