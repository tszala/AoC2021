package com.tszala.aoc2021.day6;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day6 {
    public static final String INPUT = "1,1,3,5,1,3,2,1,5,3,1,4,4,4,1,1,1,3,1,4,3,1,2,2,2,4,1,1,5,5,4,3,1,1,1,1,1,1,3,4,1,2,2,5,1,3,5,1,3,2,5,2,2,4,1,1,1,4,3,3,3,1,1,1,1,3,1,3,3,4,4,1,1,5,4,2,2,5,4,5,2,5,1,4,2,1,5,5,5,4,3,1,1,4,1,1,3,1,3,4,1,1,2,4,2,1,1,2,3,1,1,1,4,1,3,5,5,5,5,1,2,2,1,3,1,2,5,1,4,4,5,5,4,1,1,3,3,1,5,1,1,4,1,3,3,2,4,2,4,1,5,5,1,2,5,1,5,4,3,1,1,1,5,4,1,1,4,1,2,3,1,3,5,1,1,1,2,4,5,5,5,4,1,4,1,4,1,1,1,1,1,5,2,1,1,1,1,2,3,1,4,5,5,2,4,1,5,1,3,1,4,1,1,1,4,2,3,2,3,1,5,2,1,1,4,2,1,1,5,1,4,1,1,5,5,4,3,5,1,4,3,4,4,5,1,1,1,2,1,1,2,1,1,3,2,4,5,3,5,1,2,2,2,5,1,2,5,3,5,1,1,4,5,2,1,4,1,5,2,1,1,2,5,4,1,3,5,3,1,1,3,1,4,4,2,2,4,3,1,1";


    public static void main(String[] args) {
        System.out.println("Input is " + INPUT);

        LanternfishColony colony = LanternfishColonyFactory.create(INPUT);

        IntStream.range(0, 80).forEach(i -> colony.day());
        System.out.printf("Problem one solution is %d\n", colony.size());

        OptimizedLanternfishColony optimizedColony = LanternfishColonyFactory.createOptimizedColony(INPUT);
        IntStream.range(0, 80).forEach(i -> optimizedColony.day());
        System.out.printf("Problem two after 80 days solution is %d\n", optimizedColony.size());
        IntStream.range(0, 176).forEach(i -> optimizedColony.day());
        System.out.printf("Problem two solution is %d\n", optimizedColony.size());

    }

    static class LanternfishColonyFactory {
        public static LanternfishColony create(String initialState) {
            return new LanternfishColony(Arrays.stream(initialState.split(",")).map(age -> new Lanternfish(Integer.parseInt(age))).collect(Collectors.toList()));
        }
        public static OptimizedLanternfishColony createOptimizedColony(String initialState) {
            return new OptimizedLanternfishColony(Arrays.stream(initialState.split(",")).map(age -> new Lanternfish(Integer.parseInt(age))).collect(Collectors.toList()));
        }
    }

    static class LanternfishColony {
        private int days = 0;
        private List<Lanternfish> fishes = new LinkedList<>();

        public LanternfishColony(List<Lanternfish> initialColony) {
            fishes.addAll(initialColony);
        }

        public void day() {
            long newFishes = fishes.stream().filter(f -> f.day()).count();
            fishes.addAll(IntStream.range(0, (int) newFishes).mapToObj(n -> new Lanternfish(8)).collect(Collectors.toList()));
        }

        public int size() {
            return fishes.size();
        }
    }

    static class OptimizedLanternfishColony {
        private int days = 0;
        private Map<Long, Long> colony = emptyColony();

        private Map<Long, Long> emptyColony() {
            return Stream.of(new Long[][]{
                    {0l, 0l},
                    {1l, 0l},
                    {2l, 0l},
                    {3l, 0l},
                    {4l, 0l},
                    {5l, 0l},
                    {6l, 0l},
                    {7l, 0l},
                    {8l, 0l},
            }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        }

        public OptimizedLanternfishColony(List<Lanternfish> initialColony) {
            initialColony.forEach(fish -> colony.put(Long.valueOf(fish.day), colony.get(Long.valueOf(fish.day)) + 1));
        }

        public int getDays() {
            return days;
        }

        public void day() {
            days++;
            Map<Long, Long> newColony = emptyColony();
            Long newlyBorn = colony.get(Long.valueOf(0));
            newColony.put(Long.valueOf(8), newlyBorn);
            for(int i = 8; i > 0; i--) {
                newColony.put(Long.valueOf(i-1), colony.get(Long.valueOf(i)));
            }
            newColony.put(Long.valueOf(6), newColony.get(Long.valueOf(6)) + newlyBorn);
            colony = newColony;
        }

        public long size() {
            return colony.values().stream().reduce(0l, (a,b) -> a + b);
        }
    }

    static class Lanternfish {
        private int day;
        public Lanternfish(int days) {
            this.day = days;
        }

        public boolean day() {
            if(day == 0) {
                day = 6;
                return true;
            }
            day--;
            return false;
        }
    }

}
