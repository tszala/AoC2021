package com.tszala.aoc2021.day2;

import com.tszala.aoc2021.utils.Tuple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day2Test {

    @Test
    void shouldReportZeroWhenNoInstructions() {
        List<Tuple<String, Integer>> instructions = new ArrayList<>();
        Tuple<Integer, Integer> finalPositions = Day2.calculatePositions(instructions);
        Assertions.assertEquals(0, finalPositions.left);
        Assertions.assertEquals(0, finalPositions.right);
    }

    @Test
    void shouldReportHorizontalPositionForSingleInstruction() {
        List<Tuple<String, Integer>> instructions = Collections.singletonList(new Tuple<>("forward", 10));
        Tuple<Integer, Integer> finalPositions = Day2.calculatePositions(instructions);
        Assertions.assertEquals(10, finalPositions.left);
        Assertions.assertEquals(0, finalPositions.right);
    }

    @Test
    void shouldReportDepthForSingleInstruction() {
        List<Tuple<String, Integer>> instructions = Collections.singletonList(new Tuple<>("down", 10));
        Tuple<Integer, Integer> finalPositions = Day2.calculatePositions(instructions);
        Assertions.assertEquals(0, finalPositions.left);
        Assertions.assertEquals(10, finalPositions.right);
    }

}
