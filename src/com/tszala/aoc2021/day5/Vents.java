package com.tszala.aoc2021.day5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vents {
    List<Line> lines;

    public Vents(List<Line> entryLines) {
        lines = entryLines;
    }

    public int doubledPoints() {
        Map<Point, Integer> count = new HashMap<>();
        lines.stream().map(Line::points).flatMap(List::stream).forEach(p -> {
            if(count.containsKey(p)) {
                count.put(p,count.get(p) + 1);
            } else {
                count.put(p, 1);
            }
        });

        return (int) count.values().stream().filter(v -> v > 1).count();
    }
}
