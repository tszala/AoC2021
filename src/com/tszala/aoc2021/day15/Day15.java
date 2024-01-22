package com.tszala.aoc2021.day15;

import com.tszala.aoc2021.utils.FileOps;
import com.tszala.aoc2021.utils.Point;
import com.tszala.aoc2021.utils.Tuple;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.tszala.aoc2021.day15.Day15A.increaseMap;

public class Day15 {

    public static void main(String[] args) throws IOException {
        int[][] risks = FileOps.readInts("src/com/tszala/aoc2021/day15/input.txt");

        //System.out.println("PartOne: Lowest risk is: " + risk(risks));

        int[][] ints = Day15.duplicateRisksTable(risks, 5);
        int result = Day15.risk(ints);
        System.out.println("PartTwo: Lowest risk is: " + result);
    }

    public static void compareTables() throws IOException {
        int[][] risks = FileOps.readInts("src/com/tszala/aoc2021/day15/input.txt");
        risks = duplicateRisksTable(risks, 5);

        Map<Day15A.Coord, Integer> map = Day15A.readMap();
        increaseMap(map);

        for(int y = 0; y< 500;y++) {
            for(int x = 0; x< 500;x++) {
                if(risks[y][x] != map.get(new Day15A.Coord(x,y))) {
                    System.out.println("Not matching at index " + x + "," + y);
                }
            }
        }

    }


    public static int[][] duplicateRisksTable(int[][] risks, int factor) {
        int[][] newRisks = new int[risks.length * factor][];
        for (int row = 0; row < risks.length; row++) {
            for(int p = 0; p < factor; p++) {
                newRisks[row + p * risks.length] = new int[risks[0].length*factor];
            }
            for (int col = 0; col < risks[row].length; col++) {
                for (int i = 0; i < factor; i++) {
                    for (int j = 0; j < factor; j++) {
                        newRisks[row + i * risks.length][col + risks[row].length * j] = calculateNewValue(risks[row][col], i, j);
                    }
                }
            }

        }
        return newRisks;
    }

    private static int calculateNewValue(int previous, int row, int col) {
        int v = previous + col + row;
        if(v <= 9) {
            return v;
        }
        if(v == 10 || v == 19) {
            return 1;
        }
        return v - 9;
    }

    public static int risk(int[][] risks) {
        int[][] newRisks = new int[risks.length][];
        newRisks[0] = new int[risks[0].length];
        newRisks[0][0] = 0;
        for(int i = 1; i < risks[0].length; i++) {
            Day15.calculateRiskForRowAndColumn(risks, newRisks, i);
        }
        Day15A.secondPart(newRisks);
        return newRisks[newRisks.length-1][newRisks[newRisks.length-1].length-1];
    }

    public static int[][] calculateRiskForRowAndColumn(int[][] risks, int[][] previous, int x) {
        if(previous[x] == null) {
            previous[x] = new int[risks[x].length];
        }

        int col = 0;
        int row = 0;
        while(col <= x && row <= x) {
            previous[row][x] = calculatePrevious(previous, risks[row][x], row, x);
            previous[x][col] = calculatePrevious(previous, risks[x][col], x, col);
            col++;
            row++;
        }

        return previous;
    }

    public static int calculatePrevious(int[][] previous, int currentRisk, int row, int col) {
        int top = row > 0 ? previous[row-1][col]: Integer.MAX_VALUE;
        int left = col > 0 ? previous[row][col-1] : Integer.MAX_VALUE;
        return Math.min(top, left) + currentRisk;
    }


//
//    private static List<Tuple<List<Point>, Long>> filterPaths(List<Tuple<List<Point>, Long>> candidates) {
//        Map<Point, Tuple<List<Point>, Long>> m = new HashMap<>();
//        for(Tuple<List<Point>, Long> t : candidates) {
//            Tuple<List<Point>, Long> p = m.getOrDefault(t.left.get(t.left.size()-1), null);
//            if(p == null || p.right > t.right) {
//                m.put(t.left.get(t.left.size()-1), t);
//            }
//        }
//        return new ArrayList<>(m.values());
//    }
//
//    public static long traverseCave(Point p, int[][] risks, long risk, Set<Point> path) {
//        if(isEnd(p, risks)) {
//            return risk;
//        }
//        path.add(p);
//        RiskNode tree = buildTreeWithLevels(p, risks, 1, path);
//        List<List<Point>> allPaths = new ArrayList<>();
//        traverseRiskTree(tree, allPaths, new ArrayList<>());
//        List<Tuple<List<Point>, Long>> preCandidates = allPaths.stream()
//                .filter(l -> l.size() == 20 || isEnd(l.get(l.size()-1), risks))
//                .map(l->l.subList(1, l.size()))
//                .map(l -> new Tuple<>(l, calculateRisk(l, risks)))
//                .collect(Collectors.toList());
//
//        List<Tuple<List<Point>, Long>> candidates = filterPaths(preCandidates).stream()
//                .sorted(Comparator.comparing(Tuple::getRight))
//                .collect(Collectors.toList());
//
//
//        List<Tuple<List<Point>, Long>> pathsToConsider = candidates.size() > 4 ? candidates.subList(0, 4) : candidates;
//        //System.out.println(pathsToConsider);
//        long min = Integer.MAX_VALUE;
//        for(Tuple<List<Point>, Long> t : pathsToConsider) {
//            Set<Point> newPath = new HashSet<>(path);
//            newPath.addAll(t.left);
//            if(risk + t.right < 557) {
//                //if(t.left.get(t.left.size() - 1).equals(new Point(99,99))) {
//                    System.out.println("Risk: " + (risk + t.right) + ", path size: " + newPath.size() + ", s.point" + p + ", e.point: " + t.left.get(t.left.size() - 1) +", path: " + t.left);
//                //}
//                long r = traverseCave(t.left.get(t.left.size() - 1), risks, risk + t.right, newPath);
//                if (r < min) {
//                    min = r;
//                }
//            }
//        }
//        return min;
////        Tuple<List<Point>, Long> lowestRiskRoute = candidates.get(0);
////
////        path.addAll(lowestRiskRoute.left);
////
////        return traverseCave(lowestRiskRoute.left.get(lowestRiskRoute.left.size()-1), risks, risk + lowestRiskRoute.right, path);
//    }
//
//
//    public static Long calculateRisk(List<Point> path, int[][] risk) {
//        return path.stream().map(p -> (long) risk[p.right][p.left]).reduce(0L, Long::sum);
//    }
//
//    public static void traverseRiskTree(RiskNode root, List<List<Point>> result, List<Point> path) {
//        path.add(root.coordinates);
//        if(root.children == null || root.children.size() == 0) {
//            result.add(path);
//            return;
//        }
//
//        root.children.forEach(c -> traverseRiskTree(c, result, new ArrayList<>(path)));
//    }
//
//    public static void traverseRiskTree(Point p, int[][] risks, List<Long> result, Long risk) {
//
//        if(isEnd(p, risks)) {
//            result.add(risk + risks[p.right][p.left]);
//            return;
//        }
//        if(risks.length-1 > p.right) {
//            traverseRiskTree(new Point(p.left, p.right+1), risks, result, risk + (long) risks[p.right][p.left]);
//        }
//        if(risks[p.right].length-1 > p.left) {
//            traverseRiskTree(new Point(p.left + 1, p.right), risks, result, risk + (long) risks[p.right][p.left]);
//        }
//    }
//
//    private static boolean isEnd(Point p, int[][] risks) {
//        return risks.length - 1 == p.right && risks[p.right].length - 1 == p.left;
//    }
//
//    public static RiskNode buildTree(Point p, int[][] risks) {
//        RiskNode riskNode = new RiskNode(p, risks[p.right][p.left]);
//        List<RiskNode> children = new ArrayList<>();
//        Point p1 = new Point(p.left + 1, p.right);
//        if(p1.left < risks[p.right].length) {
//            children.add(buildTree(p1, risks));
//        }
//        Point p2 = new Point(p.left, p.right + 1);
//        if(p2.right < risks.length) {
//            children.add(buildTree(p2, risks));
//        }
//        riskNode.children = children;
//        return riskNode;
//    }
//
//    public static RiskNode buildTreeWithLevels(Point p, int[][] risks, int level, Set<Point> path) {
//        RiskNode riskNode = new RiskNode(p, risks[p.right][p.left], level);
//        if(level == 20) {
//            return riskNode;
//        }
//        List<RiskNode> children = new ArrayList<>();
//        Point p1 = new Point(p.left + 1, p.right);
//        if(!path.contains(p1) && p1.left < risks[p.right].length) {
//            Set<Point> s = new HashSet<>(path);
//            s.add(p1);
//            children.add(buildTreeWithLevels(p1, risks, level + 1, s));
//        }
//        Point p2 = new Point(p.left, p.right + 1);
//        if(!path.contains(p2) && p2.right < risks.length) {
//            Set<Point> s = new HashSet<>(path);
//            s.add(p2);
//            children.add(buildTreeWithLevels(p2, risks, level + 1, s));
//        }
////        Point p3 = new Point(p.left, p.right - 1);
////        if(!path.contains(p3) && p3.right >= 0) {
////            Set<Point> s = new HashSet<>(path);
////            s.add(p3);
////            children.add(buildTreeWithLevels(p3, risks, level + 1, s));
////        }
////        Point p4 = new Point(p.left-1, p.right);
////        if(!path.contains(p4) && p4.left >= 0) {
////            Set<Point> s = new HashSet<>(path);
////            s.add(p4);
////            children.add(buildTreeWithLevels(p4, risks, level + 1,s));
////        }
//        riskNode.children = children;
//        return riskNode;
//    }
//
//    static class RiskNode {
//        private Point coordinates;
//        private int risk;
//        private int level;
//
//        RiskNode(Point p, int risk) {
//            coordinates = p;
//            this.risk = risk;
//        }
//
//        RiskNode(Point p, int risk, int level) {
//            coordinates = p;
//            this.risk = risk;
//        }
//
//        private List<RiskNode> children;
//    }
}


