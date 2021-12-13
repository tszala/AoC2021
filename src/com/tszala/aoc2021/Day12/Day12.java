package com.tszala.aoc2021.Day12;

import com.tszala.aoc2021.utils.FileOps;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12 {

    public static final Pattern ROUTE_PATTERN = Pattern.compile("([a-zA-Z]+)-([a-zA-Z]+)");

    public static void main(String[] args) throws IOException {
        List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day12/input.txt");

        List<List<Node>> allRoutes = countSmallCavesOnce(lines);
        System.out.println("Number of routes: " + allRoutes.size());

        List<List<Node>> allRoutesTwice = countSmallCavesTwice(lines);
        System.out.println("Number of routes twice: " + allRoutesTwice.size());
    }

    private static List<List<Node>> countSmallCavesOnce(List<String> lines) {
        Map<String, Node> nodeByName = createGraph(lines);

        List<List<Node>> allRoutes = new ArrayList<>();
        traverse(nodeByName.get("start"), new ArrayList<>(), allRoutes);
        return allRoutes;
    }

    public static List<List<Node>> countSmallCavesTwice(List<String> lines) {
        Map<String, Node> nodeByName = createGraph(lines);

        List<List<Node>> allRoutes = new ArrayList<>();
        traverse2(nodeByName.get("start"), new ArrayList<>(), allRoutes);
        return allRoutes;
    }

    public static Map<String, Node> createGraph(List<String> lines) {
        Map<String, Node> nodeByName = new HashMap<>();
        for(String route : lines) {
            parseRoute(route, nodeByName);
        }
        return nodeByName;
    }

    private static void parseRoute(String route, Map<String, Node> nodeMap) {
        Matcher matcher = ROUTE_PATTERN.matcher(route);
        if(!matcher.find()){
            throw new IllegalStateException("Couldn't parse the route: " + route);
        }
        String start = matcher.group(1);
        String end = matcher.group(2);
        Node startingNode = getOrCreate(nodeMap, start);
        Node endingNode = getOrCreate(nodeMap, end);
        startingNode.addSubnode(endingNode);
        endingNode.addSubnode(startingNode);
    }

    public static void traverse(Node node, List<Node> route, List<List<Node>> allRoutes) {
        route.add(node);
        if(!"end".equals(node.name)) {
            for (Node subnode : node.subnodes) {
                if (subnode.large || routeNotContainNode(route, subnode, 0)) {
                    traverse(subnode, new ArrayList<>(route), allRoutes);
                }
            }
        } else {
            allRoutes.add(route);
        }
    }

    public static void traverse2(Node node, List<Node> route, List<List<Node>> allRoutes) {
        route.add(node);
        if(!"end".equals(node.name)) {
            for (Node subnode : node.subnodes) {
                if (subnode.large || !"start".equals(subnode.name) && routeHavingSmallCaveNot(route, subnode, 1)) {
                    traverse2(subnode, new ArrayList<>(route), allRoutes);
                }
            }
        } else {
            System.out.println(route);
            allRoutes.add(route);
        }
    }

    private static boolean routeNotContainNode(List<Node> route, Node node, int times) {
        return route.stream().filter(n -> n.name.equals(node.name)).count() <= times;
    }

    private static boolean routeHavingSmallCaveNot(List<Node> route, Node node, int times) {
        Map<String, List<Node>> collect = route.stream().collect(Collectors.groupingBy(n -> n.name));
        List<String> onlySmallCaves = collect.keySet().stream().filter(k -> k.matches("[a-z]+")).collect(Collectors.toList());
        boolean hasSmallCaveTwice = hasSmallCaveTwice(collect, onlySmallCaves);
        return !collect.containsKey(node.name) || !hasSmallCaveTwice;
    }

    private static boolean hasSmallCaveTwice(Map<String, List<Node>> nodes, List<String> smallCaves) {
        for(String smallCave : smallCaves) {
            if(nodes.containsKey(smallCave) && nodes.get(smallCave).size() == 2) {
                return true;
            }
        }
        return false;
    }

    private static Node getOrCreate(Map<String, Node> nodeMap, String end) {
        if(!nodeMap.containsKey(end)) {
            Node endingNode = new Node(end);
            nodeMap.put(end, endingNode);
            return endingNode;
        } else {
            return nodeMap.get(end);
        }
    }


    static class Node {
        String name;
        boolean large;
        Set<Node> subnodes = new HashSet<>();

        public Node(String name) {
            this.name = name;
            large = !name.matches("[a-z]+");
        }

        public boolean isLarge() {
            return large;
        }

        public void addSubnode(Node subnode) {
            if(!hasSubnode(subnode)) {
                subnodes.add(subnode);
            }
        }

        public boolean hasSubnode(Node node) {
            return subnodes.contains(node);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return name.equals(node.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}