package com.tszala.aoc2021.day11;

import com.tszala.aoc2021.utils.Point;
import com.tszala.aoc2021.utils.Tuple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static com.tszala.aoc2021.utils.FileOps.initInts;

public class Day11 {
    public static final List<String> INPUT = Arrays.asList("3113284886","2851876144","2774664484", "6715112578","7146272153","6256656367","3148666245","3857446528", "7322422833", "8152175168");

    public static void main(String[] args) {
        System.out.println("Day 11");
        OctosColony octosColony = new OctosColony(INPUT);

        int flashes = IntStream.range(0, 100).map(i -> octosColony.step().left).reduce(0, Integer::sum);
        System.out.printf("Number of flashes after 100 days is %d\n", flashes);

        int step = 0;
        while (true) {
            Tuple<Integer, Boolean> output = octosColony.step();
            step++;
            if(output.right) {
                break;
            }
        }

        System.out.printf("Number of step with all octopuses flash is %d\n", step + 100);
    }

    static class OctosColony {
        int[][] octos;
        int colonySize;
        OctosColony(List<String> input) {
            octos = initInts(input);
            colonySize = octos.length * octos[0].length;
        }

        public Tuple<Integer, Boolean> step() {
            for(int i = 0; i < octos.length; i++) {
                for(int j = 0; j < octos[i].length; j++) {
                    increaseLevel(i,j);
                }
            }

            Set<Point> alreadyFlashed = new HashSet<>();
            for(int i = 0; i < octos.length; i++) {
                for(int j = 0; j < octos[i].length; j++) {
                    if(octos[i][j] > 9 && !alreadyFlashed.contains(new Point(j, i))) {
                        flash(i,j, alreadyFlashed);
                    }
                }
            }

            resetFlash();

            int countOfFlashes = countFlashes();
            return new Tuple<>(countOfFlashes, countOfFlashes == colonySize);
        }

        public int countFlashes() {
            int flashes = 0;
            for(int[] row: octos) {
                for(int octo : row) {
                    if(octo == 0) {
                        flashes++;
                    }
                }
            }
            return flashes;
        }

        private boolean increaseLevel(int y, int x) {
            octos[y][x] = octos[y][x] + 1;
            return octos[y][x] == 9;
        }

        private void resetFlash() {
            for(int i = 0; i < octos.length; i++) {
                for(int j = 0; j < octos[i].length; j++) {
                    if(octos[i][j] > 9) {
                        octos[i][j] = 0;
                    }
                }
            }
        }

        private void flash(int y, int x, Set<Point> alreadyFlashed) {
            if(y >= 0 && y <= octos.length - 1 && x >= 0 && x <= octos[y].length - 1) {
                int e = octos[y][x];
                octos[y][x] = e + 1;
                if(octos[y][x] > 9 && !alreadyFlashed.contains(new Point(x, y))) {
                    alreadyFlashed.add(new Point(x,y));
                    for(int i = y - 1; i <= y + 1; i++) {
                        for(int j = x - 1; j <= x + 1; j++) {
                            if(!(i == y && j == x)) {
                                flash(i, j, alreadyFlashed);
                            }
                        }
                    }
                }
            }
        }
    }
}
