package com.tszala.aoc2021.day2;

import com.tszala.aoc2021.utils.FileOps;
import com.tszala.aoc2021.utils.Tuple;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) throws IOException {
        List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day2/input.txt");

        List<Tuple<String, Integer>> instructions = lines.stream()
                .map(l -> l.split(" "))
                .map(a -> new Tuple<>(a[0], Integer.parseInt(a[1])))
                .collect(Collectors.toList());

        Tuple<Integer, Integer> finalPositions = calculatePositions(instructions);

        System.out.printf("Problem one result is %d\n", finalPositions.left * finalPositions.right);
        Tuple<Integer, Integer> finalPositionsReduce = calculatePositionsWithReduce(instructions);
        System.out.printf("Problem one result with reduce is %d\n", finalPositionsReduce.left * finalPositionsReduce.right);

        Tuple<Integer, Integer> finalPositionsAim = calculatePositionsWithAim(instructions);
        System.out.printf("Problem two result is %d\n", finalPositionsAim.left * finalPositionsAim.right);

        Tuple<Integer, Integer> finalPositionsAimWithReduce = calculatePositionWithAimReduce(instructions);
        System.out.printf("Problem two result with reduce is %d\n", finalPositionsAimWithReduce.left * finalPositionsAimWithReduce.right);
    }

    public static Tuple<Integer, Integer> calculatePositions(List<Tuple<String, Integer>> instructions) {
        int forward = 0;
        int depth = 0;
        for (Tuple<String, Integer> instruction : instructions) {
            switch (instruction.left) {
                case "forward" -> forward += instruction.right;
                case "down" -> depth += instruction.right;
                case "up" -> depth -= instruction.right;
                default -> throw new IllegalStateException("unsupported operation " + instruction.left);
            }
        }
        return new Tuple<>(forward, depth);
    }

    public static Tuple<Integer, Integer> calculatePositionsWithAim(List<Tuple<String, Integer>> instructions) {
        int forward = 0;
        int depth = 0;
        int aim = 0;
        for (Tuple<String, Integer> instruction : instructions) {
            switch (instruction.left) {
                case "forward" -> {forward += instruction.right; depth = depth + aim * instruction.right;}
                case "down" -> aim += instruction.right;
                case "up" -> aim -= instruction.right;
                default -> throw new IllegalStateException("unsupported operation " + instruction.left);
            }
        }
        return new Tuple<>(forward, depth);
    }

    public static Tuple<Integer, Integer> calculatePositionsWithReduce(List<Tuple<String, Integer>> instructions) {
        if(instructions.size() == 0) {
            return new Tuple<>(0, 0);
        }
        return instructions.stream()
                .map(i -> "forward".equals(i.left) ?
                        new Tuple<>(i.right, 0) :
                        "down".equals(i.left) ?
                                new Tuple<>(0, i.right) : new Tuple<>(0, -i.right))
                .reduce((a, b) -> new Tuple<>(a.left + b.left, a.right + b.right)).get();
    }

    public static Tuple<Integer, Integer> calculatePositionWithAimReduce(List<Tuple<String, Integer>> instructions) {
        if(instructions.size() == 0) {
            return new Tuple<>(0, 0);
        }
        Tuple<Integer, Tuple<Integer, Integer>> calculation = instructions.stream()
                .map(i -> "forward".equals(i.left) ?
                        new Tuple<>(i.right, new Tuple<>(0, 0)) :
                        "down".equals(i.left) ?
                                new Tuple<>(0, new Tuple<>(i.right, 0)) : new Tuple<>(0, new Tuple<>(-i.right, 0)))
                .reduce(new Tuple<>(0,new Tuple<>(0, 0)), Day2::combine);
        return new Tuple<>(calculation.left, calculation.right.right);
    }

    private static Tuple<Integer, Tuple<Integer, Integer>> combine(Tuple<Integer, Tuple<Integer, Integer>> a, Tuple<Integer, Tuple<Integer, Integer>> b) {
        return new Tuple<>(a.left + b.left, new Tuple<>(a.right.left + b.right.left, a.right.right + a.right.left * b.left));
    }
}
