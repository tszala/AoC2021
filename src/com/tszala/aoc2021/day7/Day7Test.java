package com.tszala.aoc2021.day7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day7Test {


    @Test
    void shouldComputeNonConstantFuelConsumption() {
        Assertions.assertEquals(66, Day7.nonConstantFuelUsageCalculator.apply(16).apply(5));
        Assertions.assertEquals(10, Day7.nonConstantFuelUsageCalculator.apply(1).apply(5));
        Assertions.assertEquals(6, Day7.nonConstantFuelUsageCalculator.apply(2).apply(5));
        Assertions.assertEquals(15, Day7.nonConstantFuelUsageCalculator.apply(0).apply(5));
        Assertions.assertEquals(1, Day7.nonConstantFuelUsageCalculator.apply(4).apply(5));
        Assertions.assertEquals(6, Day7.nonConstantFuelUsageCalculator.apply(5).apply(2));
        Assertions.assertEquals(3, Day7.nonConstantFuelUsageCalculator.apply(7).apply(5));
        Assertions.assertEquals(45, Day7.nonConstantFuelUsageCalculator.apply(14).apply(5));
    }
}
