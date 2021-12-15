package com.tszala.aoc2021.day14;

import com.tszala.aoc2021.utils.FileOps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day14Test {

    static final String TEST_TEMPLATE = "NNCB";

    @Test
    void shouldInitTestPolymer() {
        Day14.Polymer polymer = Day14.initPolymer(TEST_TEMPLATE);
        Assertions.assertEquals("Polymer: NNCB", polymer.toString());


    }

    @Test
    void shouldExecuteTestInstructionForTestPolymer() {
        Day14.Polymer polymer = Day14.initPolymer(TEST_TEMPLATE);
        List<Day14.Instruction> instructions = Arrays.asList(
                new Day14.Instruction('N', 'N', 'C'),
                new Day14.Instruction('N', 'C', 'B'),
                new Day14.Instruction('C', 'B', 'H'));
        polymer.setInstructions(instructions);
        polymer.step();
        polymer.step();

        Assertions.assertEquals("Polymer: NCNBCHB", polymer.toString());


    }

    @Test
    void shouldExecuteTestInstructionForTestOptimizedPolymer() throws IOException {
        List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day14/input_test.txt");

        List<Day14.Instruction> instructions = lines.stream().map(Day14::lineToInstruction).collect(Collectors.toList());

       Day14.OptimizedPolymer optimizedPolymer = new Day14.OptimizedPolymer();
       optimizedPolymer.initFrom(TEST_TEMPLATE);
        optimizedPolymer.setInstructionCache(instructions);
        optimizedPolymer.step();
        optimizedPolymer.step();
        optimizedPolymer.step();
        optimizedPolymer.step();
        System.out.println("test");


    }
}
