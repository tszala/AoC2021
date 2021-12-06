package com.tszala.aoc2021.day5;

import com.tszala.aoc2021.utils.Tuple;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class LineFactory {

    public static final Pattern LINE_PATTERN = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

    public static Line createLine(String line) {
        Matcher matchedLine = LINE_PATTERN.matcher(line);
        if(!matchedLine.find()) {
            throw new IllegalStateException("Not matched the line");
        }
        Point start = new Point(parseInt(matchedLine.group(1)), parseInt(matchedLine.group(2)));
        Point end = new Point(parseInt(matchedLine.group(3)), parseInt(matchedLine.group(4)));
        return new Line(start, end);
    }

    public static Vents createVentsWithHorizontalAndVerticalLines(List<String> entryLines) {
        List<Line> lines = entryLines.stream().map(LineFactory::createLine).filter(line -> line.isHorizontal() || line.isVertical()).collect(Collectors.toList());
        return new Vents(lines);
    }

    public static Vents createVentsWithHorizontalVerticalAndDiagonalLines(List<String> entryLines) {
        List<Line> lines = entryLines.stream().map(LineFactory::createLine).collect(Collectors.toList());
        return new Vents(lines);
    }

    public static class PointsComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
//            int vertical = o1.right.compareTo(o2.right);
//            int horizontal = o1.left.compareTo(o2.left);
//            if(vertical == 0 && horizontal == 0) {
//                return 0;
//            }
//            if(horizontal < )

            if(o1.left.equals(o2.left)) {
                return o1.right.compareTo(o2.right);
            } else {
                return o1.left.compareTo(o2.left);
            }
        }
    }
}
