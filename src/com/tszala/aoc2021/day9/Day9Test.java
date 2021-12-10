package com.tszala.aoc2021.day9;

import com.tszala.aoc2021.utils.FileOps;
import com.tszala.aoc2021.utils.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day9Test {

    private final List<String> lines = Arrays.asList("2199943210", "3987894921", "9856789892", "8767896789", "9899965678");
    private final int[][] vents = FileOps.initInts(lines);

    @Test
    void shouldSumLowPoints() {
        Assertions.assertEquals(15, Day9.sumLowPoints(vents));
    }

    @Test
    void shouldCalculateBasins() {
        List<Set<Point>> basins = Day9.findBasins(vents);
        System.out.println(basins);
        List<Set<Point>> sortedBasins = basins.stream().sorted(Comparator.comparing(Set::size)).collect(Collectors.toList());
        Assertions.assertEquals(4, basins.size());
        int n = sortedBasins.size();
        Assertions.assertEquals(1134, sortedBasins.get(n-1).size() * sortedBasins.get(n-2).size() * sortedBasins.get(n-3).size());

    }
}
