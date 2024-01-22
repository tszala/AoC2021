package com.tszala.aoc2021.day15;

import com.tszala.aoc2021.utils.FileOps;
import com.tszala.aoc2021.utils.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day15Test {


    @Test
    void shouldCalculate() throws IOException {
        int[][] risks = FileOps.readInts("src/com/tszala/aoc2021/day15/input_test.txt");
        int[][] newRisks = new int[risks.length][];
        newRisks[0] = new int[risks[0].length];
        newRisks[0][0] = 0;
        for(int i = 1; i < risks[0].length; i++) {
            Day15.calculateRiskForRowAndColumn(risks, newRisks, i);
        }
        Assertions.assertEquals(40, newRisks[9][9]);
    }

    @Test
    void shouldCalculateRiskForUnduplicatedTable() throws IOException {
        int[][] risks = FileOps.readInts("src/com/tszala/aoc2021/day15/input_test.txt");
        int result = Day15.risk(risks);
        Assertions.assertEquals(40, result);
    }

    @Test
    void shouldCalculateRiskForDuplicatedTable() throws IOException {
        int[][] risks = FileOps.readInts("src/com/tszala/aoc2021/day15/input_test.txt");
        int[][] ints = Day15.duplicateRisksTable(risks, 5);
        System.out.println("Table duplicated");
        int result = Day15.risk(ints);
        Assertions.assertEquals(315, result);
    }

    @Test
    void shouldCompareTables() throws IOException {
        Day15.compareTables();
    }
}
