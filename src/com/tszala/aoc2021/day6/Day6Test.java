package com.tszala.aoc2021.day6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class Day6Test {

    public static final String TEST_INPUT = "3,4,3,1,2";

    @Test
    public void colonyShouldHave26FishesAfter18Days() {
        Day6.LanternfishColony colony = Day6.LanternfishColonyFactory.create(TEST_INPUT);
        IntStream.range(0, 18).forEach(i -> colony.day());
        Assertions.assertEquals(26, colony.size());
    }

    @Test
    public void colonyShouldHave5934FishesAfter80Days() {
        Day6.LanternfishColony colony = Day6.LanternfishColonyFactory.create(TEST_INPUT);
        IntStream.range(0, 80).forEach(i -> colony.day());
        Assertions.assertEquals(5934, colony.size());
    }

    @Test
    public void optimizedColonyShouldHave26FishesAfter18Days() {
        Day6.OptimizedLanternfishColony optimizedColony = Day6.LanternfishColonyFactory.createOptimizedColony(TEST_INPUT);
        Day6.LanternfishColony colony = Day6.LanternfishColonyFactory.create(TEST_INPUT);
        IntStream.range(0, 18).forEach(i -> {
            colony.day();
            optimizedColony.day();
            System.out.printf("Day %d, colony size %d, optimized colony size %d\n", optimizedColony.getDays(), colony.size(), optimizedColony.size());
        });
        Assertions.assertEquals(26, colony.size());
    }

    @Test
    public void optimizedColonyShouldHave5934FishesAfter80Days() {
        Day6.OptimizedLanternfishColony colony = Day6.LanternfishColonyFactory.createOptimizedColony(TEST_INPUT);
        IntStream.range(0, 80).forEach(i -> colony.day());
        Assertions.assertEquals(5934, colony.size());
    }
}
