package com.tszala.aoc2021.day15;

import com.tszala.aoc2021.utils.FileOps;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Day15A {
    public static void main(String[] args) {
       // firstPart();
        //secondPart();
    }

    private static void firstPart() {
        Map<Coord, Integer> map = readMap();
        //System.out.println(lowestCost(map));
    }

    public static void secondPart(int[][] risks) {
        Map<Coord, Integer> map = readMap();
        increaseMap(map);
        System.out.println(lowestCost(map, risks));
    }

    private static int lowestCost(Map<Coord, Integer> map, int[][] risks) {
        Coord endCoord = findEndCoord(map);
        Queue<Path> queue = new PriorityQueue<>();
        Set<Coord> seen = new HashSet<>();
        queue.add(new Path(new Coord(0, 0), 0));
        while (!queue.isEmpty() && !queue.peek().at().equals(endCoord)) {
            var top = queue.poll();
            top.at().adjecents().stream().filter(map::containsKey).filter(c -> !seen.contains(c))
                    .map(c -> new Path(c, top.cost() + map.get(c))).forEach(s -> {
                seen.add(s.at());
                queue.add(s);
            });

        }
        return queue.peek().cost();
    }

    private static Coord findEndCoord(Map<Coord, Integer> map) {
        return new Coord(map.keySet().stream().mapToInt(Coord::x).max().orElseThrow(),
                map.keySet().stream().mapToInt(Coord::y).max().orElseThrow());
    }

    public static Map<Coord, Integer> readMap() {
        List<String> lines = null;
        try {
            lines = FileOps.readAllLines("src/com/tszala/aoc2021/day15/input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Coord, Integer> map = new HashMap<>();
        for (int y = 0; y < lines.size(); ++y) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); ++x) {
                map.put(new Coord(x, y), Integer.parseInt(line.substring(x, x + 1)));
            }
        }
        return map;
    }

    public static void increaseMap(Map<Coord, Integer> map) {
        int maxX = map.keySet().stream().mapToInt(Coord::x).max().orElseThrow();
        int maxY = map.keySet().stream().mapToInt(Coord::y).max().orElseThrow();
        for (int x = maxX + 1; x < 5 * (maxX + 1); ++x) {
            for (int y = 0; y <= maxY; ++y) {
                map.put(new Coord(x, y), turn(map.get(new Coord(x - maxX - 1, y)) + 1));
            }
        }
        maxX = map.keySet().stream().mapToInt(Coord::x).max().orElseThrow();
        for (int y = maxY + 1; y < 5 * (maxY + 1); ++y) {
            for (int x = 0; x <= maxX; ++x) {
                map.put(new Coord(x, y), turn(map.get(new Coord(x, y - maxY - 1)) + 1));
            }
        }
    }

    private static int turn(int i) {
        int n = i;
        while (n > 9) {
            n = n - 9;
        }
        return n;
    }

    static record Coord(int x, int y) {
        List<Coord> adjecents() {
            return List.of(new Coord(x - 1, y), new Coord(x + 1, y), new Coord(x, y - 1), new Coord(x, y + 1));
        }
    }

    private static record Path(Coord at, int cost) implements Comparable<Path> {
        private static final Comparator<Path> COST_COMPARATOR = Comparator.comparingInt(Path::cost);

        @Override
        public int compareTo(Path o) {
            return COST_COMPARATOR.compare(this, o);
        }
    }
}