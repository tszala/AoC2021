package com.tszala.aoc2021.Day12;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day12Test {

    @Test
    void shouldTraverseSmallGraph() {

        List<String> input = Arrays.asList("start-A", "start-b", "A-c", "A-b", "b-d", "A-end","b-end");

        Map<String, Day12.Node> nodeByName = Day12.createGraph(input);
        System.out.println("Parsed");

        List<List<Day12.Node>> allRoutes = new ArrayList<>();
        Day12.traverse(nodeByName.get("start"), new ArrayList<>(), allRoutes);
        System.out.println("Number of routes: " + allRoutes.size());
    }

    @Test
    void shouldTraverseSmallCavesTwice() {

        List<String> input = Arrays.asList("start-A", "start-b", "A-c", "A-b", "b-d", "A-end","b-end");

        List<List<Day12.Node>> lists = Day12.countSmallCavesTwice(input);
        Assertions.assertEquals(36, lists.size());
    }

}
