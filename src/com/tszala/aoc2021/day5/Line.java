package com.tszala.aoc2021.day5;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = end;
        this.end = start;
    }

    public boolean isVertical() {
        return start.left.equals(end.left);
    }

    public boolean isHorizontal() {
        return start.right.equals(end.right);
    }

    public List<Point> points() {
        List<Point> points = new ArrayList<>();
        if(start.left.equals(end.left)) {
            int startingIndex = Math.min(start.right, end.right);
            int endingIndex = Math.max(start.right, end.right);
            for(int i = startingIndex; i<= endingIndex; i++) {
                points.add(new Point(start.left, i));
            }
            return points;
        } else if(start.right.equals(end.right)){
            int startingIndex = Math.min(start.left, end.left);
            int endingIndex = Math.max(start.left, end.left);
            for(int i = startingIndex; i<= endingIndex; i++) {
                points.add(new Point(i, start.right));
            }
            return points;
        } else if(start.left < end.left) {
            return pointsForDiagonal(start, end);
        } else {
            return pointsForDiagonal(end, start);
        }
    }

    private List<Point> pointsForDiagonal(Point start, Point end) {
        List<Point> points = new ArrayList<>();
        if(start.right < end.right) {
            for(int i = 0; i <= end.right - start.right; i++) {
                points.add(new Point(start.left + i, start.right + i));
            }
        }else {
            for(int i = 0; i <= start.right - end.right; i++) {
                points.add(new Point(start.left + i, start.right - i));
            }
        }
        return points;
    }

}
