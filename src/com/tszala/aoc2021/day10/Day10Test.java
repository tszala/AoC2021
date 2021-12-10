package com.tszala.aoc2021.day10;

import com.tszala.aoc2021.utils.FileOps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Day10Test {
    List<String> lines = Arrays.asList("[({(<(())[]>[[{[]{<()<>>", "[(()[<>])]({[<{<<[]>>(", "{([(<{}[<>[]}>{[]{[(<()>", "(((({<>}<{<{<>}{[]{[]{}",
                "[[<[([]))<([[{}[[()]]]", "[{[{({}]{}}([{[{{{}}([]", "{<[[]]>}<{[{[{[]{()[[[]", "[<(<(<(<{}))><([]([]()",
                "<{([([[(<>()){}]>(<<{{", "<{([{{}}[<[[[<>{}]]]>[]]");

    @Test
    void shouldCalculateBrackets() {
        Long calculateBrokenBrackets = Day10.calculateBrokenBrackets(FileOps.initChars(lines));
        Assertions.assertEquals(26397L, calculateBrokenBrackets);
    }

    @Test
    void shouldFindMissingBrackets() {
        Assertions.assertEquals(288957L, Day10.findTotalScoreForMissingBrackets(FileOps.initChars(lines)));
    }

}
