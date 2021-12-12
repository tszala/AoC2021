package com.tszala.aoc2021.day11;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day11Test {

    @Test
    void shouldCountFlashesForSmallTest() {
        List<String> testSmallInput = Arrays.asList("11111", "19991", "19191", "19991", "11111");
        Day11.OctosColony colony = new Day11.OctosColony(testSmallInput);
        System.out.printf("Number of flashes after step 1 %d\n", colony.step());
        System.out.printf("Number of flashes after step 2 %d\n", colony.step());
        System.out.printf("Number of flashes after step 3 %d\n", colony.step());

    }

    @Test
    void shouldCountFlashesForLargeTest() {
        List<String> testLargeInput = Arrays.asList("5483143223", "2745854711", "5264556173", "6141336146", "6357385478", "4167524645", "2176841721", "6882881134", "4846848554", "5283751526");
        Day11.OctosColony colony = new Day11.OctosColony(testLargeInput);

        System.out.printf("Number of flashes after step 1 %d\n", colony.step());
        System.out.printf("Number of flashes after step 2 %d\n", colony.step());
        System.out.printf("Number of flashes after step 3 %d\n", colony.step());

        int sumOfFlashes = IntStream.range(0, 100).map(i -> colony.step().left).reduce(0, Integer::sum);
        System.out.println("Sum of flashes after 100 steps is " + sumOfFlashes);
    }

}
