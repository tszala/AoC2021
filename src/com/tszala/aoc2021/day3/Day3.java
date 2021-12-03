package com.tszala.aoc2021.day3;

import com.tszala.aoc2021.utils.FileOps;
import com.tszala.aoc2021.utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class Day3 {
    public static void main(String[] args) throws IOException {
        final List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day3/input.txt");
        int[] counters = getCounters(lines);

        Function<Integer, Integer> gammaCounting = count -> count > lines.size() / 2 ? 1 : 0;
        Function<Integer, Integer> epsilonCounting = count -> count < lines.size() / 2 ? 1 : 0;

        String gamma = calculateNumber(counters, gammaCounting);
        String epsilon = calculateNumber(counters, epsilonCounting);
        System.out.printf("Gamma is %s, epsilon is %s\n", gamma, epsilon);
        System.out.printf("Problem one result is %d\n", parseInt(gamma, 2) * parseInt(epsilon, 2));

        String oxygen = calculateOxygen(lines);
        String co2 = calculateCO2(lines);
        System.out.printf("Oxygen is %s, CO2 is %s\n", oxygen, co2);
        System.out.printf("Problem two result is %d\n", parseInt(oxygen, 2) * parseInt(co2, 2));
    }

    public static int[] getCounters(List<String> lines) {
        int[] counters = IntStream.range(0, lines.get(0).length()).map(i -> 0).toArray();
        lines.forEach(line -> countOnes(line, counters));
        return counters;
    }

    public static String calculateOxygen(List<String> data) {
        BiPredicate<Tuple<Integer, Character>, Integer> oxygenBit = (t, size) -> t.left >= size / (double) 2 && t.right == '1'
                || t.left < size / (double) 2 && t.right == '0';
        return calculateOxygenOrCo2(data, oxygenBit);
    }

    public static String calculateCO2(List<String> data) {
        BiPredicate<Tuple<Integer, Character>, Integer> co2Bit = (t, size) -> t.left >= size / (double) 2 && t.right == '0'
                || t.left < size / (double) 2 && t.right == '1';
        return calculateOxygenOrCo2(data, co2Bit);
    }

    private static String calculateOxygenOrCo2(List<String> data, BiPredicate<Tuple<Integer, Character>, Integer> rateCounter) {
        List<String> entries = new ArrayList<String>(data);
        for (int i = 0; i < data.get(0).length(); i++) {
            final int k = i;
            int[] counters = getCounters(entries);
            final int size = entries.size();
            entries = entries.stream().filter(entry -> rateCounter.test(new Tuple<>(counters[k],
                    entry.charAt(k)),size )).collect(Collectors.toList());
            if (entries.size() == 1) {
                return entries.get(0);
            }
        }
        throw new IllegalStateException("Not able to get the value");
    }

    private static void countOnes(String line, int[] counters) {
        for (int i = 0; i < line.length(); i++) {
            if ('1' == line.charAt(i)) {
                counters[i]++;
            }
        }
    }

    private static String calculateNumber(int[] counters, Function<Integer, Integer> rateCounting) {
        return Arrays.stream(counters).map(rateCounting::apply).mapToObj(String::valueOf).collect(Collectors.joining());
    }
}
