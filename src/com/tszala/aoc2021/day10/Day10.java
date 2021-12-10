package com.tszala.aoc2021.day10;

import com.tszala.aoc2021.utils.FileOps;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 {

    static enum Bracket {
        ROUND('(', ')',3L, 1L),
        SQUARE('[',']', 57L, 2L),
        MUSTACHE('{','}', 1197L, 3L),
        POINTING('<','>', 25137L, 4L);
        private final char open;
        private final char close;
        private final long score;
        private final long autocompleteScore;
        private Bracket(char open, char close, long score, long autocompleteScore) {
            this.open = open;
            this.close = close;
            this.score = score;
            this.autocompleteScore = autocompleteScore;
        }

        public boolean isOpen(char charToCheck) {
            return charToCheck == open;
        }

        public boolean isClose(char charToCheck) {
            return charToCheck == close;
        }

        public boolean accept(char charToCheck) {
            return isOpen(charToCheck) || isClose(charToCheck);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Day 10");

        char[][] instructions = FileOps.readChars("src/com/tszala/aoc2021/day10/input.txt");
        Long result = calculateBrokenBrackets(instructions);
        System.out.printf("Problem one solution is %d\n", result);

        System.out.printf("Problem two solution is %d\n", findTotalScoreForMissingBrackets(instructions));
    }

    public static Long findTotalScoreForMissingBrackets(char[][] instructions) {
        List<Long> scores = new ArrayList<>();
        for(char[] instruction : instructions) {
            if(isCorrupted(instruction) == null) {
                long totalScore = 0;
                List<Bracket> missingClosingBrackets = findMissingBrackets(instruction);
                for (Bracket missingClosingBracket : missingClosingBrackets) {
                    totalScore = 5 * totalScore + missingClosingBracket.autocompleteScore;
                }
                scores.add(totalScore);
            }
        }
        List<Long> sortedScores = scores.stream().sorted().collect(Collectors.toList());
        return sortedScores.get(sortedScores.size()/2);
    }

    public static Long calculateBrokenBrackets(char[][] instructions) {
        List<Bracket> corruptingBrackets = new ArrayList<>();
        for (char[] instruction : instructions) {
            Bracket bracket = isCorrupted(instruction);
            if (bracket != null) {
                corruptingBrackets.add(bracket);
            }
        }

        return corruptingBrackets.stream().map(b -> b.score).reduce(0L, Long::sum);
    }

    private static Bracket isCorrupted(char[] line) {
        Deque<Character> brackets = new ArrayDeque<>();
        List<Bracket> allBracket = Arrays.asList(Bracket.ROUND,
                Bracket.SQUARE,
                Bracket.MUSTACHE,
                Bracket.POINTING);
        for (char currentChar : line) {
            Bracket bracket = findBracket(allBracket, currentChar);
            if(bracket.isOpen(currentChar)) {
                brackets.push(currentChar);
            } else {
                Character previous = brackets.pop();
                if(!(bracket.accept(previous) && bracket.isOpen(previous))) {
                    return bracket;
                }
            }
        }
        return null;
    }

    private static List<Bracket> findMissingBrackets(char[] line) {
        Deque<Character> brackets = new ArrayDeque<>();
        List<Bracket> allBracket = Arrays.asList(Bracket.ROUND,
                Bracket.SQUARE,
                Bracket.MUSTACHE,
                Bracket.POINTING);
        for (char currentChar : line) {
            Bracket bracket = findBracket((List<Bracket>) allBracket, currentChar);
            if(bracket.isOpen(currentChar)) {
                brackets.push(currentChar);
            } else {
                brackets.pop();
            }
        }

        if(brackets.isEmpty()) {
            return Collections.emptyList();
        }

        return brackets.stream().map(b -> findBracket(allBracket, b)).collect(Collectors.toList());
    }

    private static Bracket findBracket(List<Bracket> allBracket, char currentChar) {
        return allBracket.stream().filter(b -> b.accept(currentChar)).findFirst().get();
    }
}


