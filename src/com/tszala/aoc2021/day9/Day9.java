package com.tszala.aoc2021.day9;

import com.tszala.aoc2021.utils.FileOps;
import com.tszala.aoc2021.utils.Point;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day9 {

    public static final char ZERO = '0';

    public static void main(String[] args) throws IOException {
        System.out.println("Day 9");
        List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day9/input.txt");
        int[][] vents = initVents(lines);

        int sum = sumLowPoints(vents);
        System.out.printf("Sum of low points %d\n", sum);

        int sumOfBasins = calculateSumOfBasins(vents);
        System.out.printf("Sum of basins %d\n", sumOfBasins);
    }

    private static int calculateSumOfBasins(int[][] vents) {
        List<Set<Point>> basins = findBasins(vents);
        List<Set<Point>> sortedBasins = basins.stream().sorted(Comparator.comparing(Set::size)).collect(Collectors.toList());
        int n = sortedBasins.size();
        return sortedBasins.get(n-1).size() * sortedBasins.get(n-2).size() * sortedBasins.get(n-3).size();
    }

    public static List<Set<Point>> findBasins(int[][] vents) {
        Set<Point> lowPoints = findLowPoints(vents);
        return lowPoints.stream().map(p -> countBasins(vents, p)).collect(Collectors.toList());
    }

    public static int[][] initVents(List<String> lines) {
        int[][] vents = new int[lines.size()][];
        for(int i = 0; i < lines.size(); i++) {
            vents[i] = stringToIntArray(lines.get(i));
        }
        return vents;
    }

    public static int sumLowPoints(int[][] vents) {
        Set<Point> lowPoints = findLowPoints(vents);
        Integer sumOfLowPoints = lowPoints.stream().map(p -> vents[p.right][p.left]).reduce(0, Integer::sum);
        return sumOfLowPoints + lowPoints.size();
    }

    public static Set<Point> findLowPoints(int[][] vents) {
        Set<Point> points = new HashSet<>();
        for(int i = 0; i < vents.length; i++) {
            for(int j = 0; j < vents[i].length; j++) {
                if(isLowPoint(vents, i, j)) {
                    points.add(new Point(j, i));
                }
            }
        }
        return points;
    }

    public static Set<Point> countBasins(int[][] vents, Point point) {
        Set<Point> points = new HashSet<>();
        int basePoint = vents[point.right][point.left];
        countBasins(vents, point.right, point.left+1, basePoint, points);
        countBasins(vents,point.right, point.left-1, basePoint, points);
        countBasins(vents,point.right + 1, point.left, basePoint,  points);
        countBasins(vents,point.right - 1, point.left, basePoint, points);
        points.add(point);
        return points;
    }

    public static void countBasins(int[][] vents, int y, int x,int basePoint, Set<Point> points) {
        if(isBasin(vents, y, x, basePoint)) {
            points.add(new Point(x,y));
            countBasins(vents,y, x+1, vents[y][x], points);
            countBasins(vents,y, x-1, vents[y][x], points);
            countBasins(vents,y + 1, x, vents[y][x], points);
            countBasins(vents,y - 1, x, vents[y][x], points);
        }
    }

    private static boolean isBasin(int[][] vents, int y, int x, int basePoint) {
        return y >= 0 && y < vents.length && x >= 0 && x < vents[y].length && vents[y][x] != 9 && vents[y][x] > basePoint;
    }

    private static boolean isLowPoint(int[][] vents, int y, int x) {
        boolean aboveRiskIsHigher = y == 0 || vents[y-1][x] > vents[y][x];
        boolean belowRiskIsHigher = y == (vents.length - 1) || vents[y+1][x] > vents[y][x];
        boolean leftRiskIsHigher = x == 0 || vents[y][x-1] > vents[y][x];
        boolean rightRiskIsHigher = x == (vents[y].length - 1) || vents[y][x+1] > vents[y][x];
        return aboveRiskIsHigher && belowRiskIsHigher && leftRiskIsHigher && rightRiskIsHigher;
    }

    private static int[] stringToIntArray(String line) {
        return line.chars().map(c->c-ZERO).toArray();
    }
}
