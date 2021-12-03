package com.tszala.aoc2021.day3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Day3Test {

    @Test
    void shouldCalculateOxygen() {
        List<String> data = Arrays.asList("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010");

        String oxygen = Day3.calculateOxygen(data);
        Assertions.assertEquals("10111", oxygen);
    }
    @Test
    void shouldCalculateCO2() {
        List<String> data = Arrays.asList("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010");

        String cO2 = Day3.calculateCO2(data);
        Assertions.assertEquals("01010", cO2);
    }

}
