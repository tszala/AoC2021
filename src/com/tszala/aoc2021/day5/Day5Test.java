package com.tszala.aoc2021.day5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Day5Test {

    @Test
    void shouldCompareEqualPoints() {
        Point p1 = new Point(1,2);
        Point p2 = new Point(1,2);
        LineFactory.PointsComparator pointsComparator = new LineFactory.PointsComparator();
        Assertions.assertTrue(pointsComparator.compare(p1,p2) == 0);
    }

    @Test
    void shouldComparePoints() {
        Point p1 = new Point(1,2);
        Point p2 = new Point(2,2);
        LineFactory.PointsComparator pointsComparator = new LineFactory.PointsComparator();
        Assertions.assertTrue(pointsComparator.compare(p1,p2) < 0);
    }

    @Nested
    class Points {
        @Test
        void equalPointsShouldEqual() {
            Point p1 = new Point(3, 5);
            Point p2 = new Point(3, 5);
            Assertions.assertEquals(p1, p2);
        }

        @Test
        void notEqualPointsShouldNotEqual() {
            Point p1 = new Point(3, 5);
            Point p2 = new Point(3, 7);
            Assertions.assertNotEquals(p1, p2);
        }
    }

    @Nested
    class Lines {
        @Test
        void shouldConvertLineToPoints() {
            Point p1 = new Point(3, 5);
            Point p2 = new Point(3, 6);
            Line line = new Line(p1, p2);
            Assertions.assertEquals(Arrays.asList(new Point(3,5), new Point(3,6)), line.points());
        }
        @Test
        void shouldConvertLineToThreePoints() {
            Point p1 = new Point(3, 7);
            Point p2 = new Point(3, 5);
            Line line = new Line(p1, p2);
            Assertions.assertEquals(Arrays.asList(new Point(3,5), new Point(3,6), new Point(3,7)), line.points());
        }
    }

    @Nested
    class VentsTest {

        @Test
        void shouldHaveOneCommonVent() {
            Vents vents = LineFactory.createVentsWithHorizontalAndVerticalLines(Arrays.asList("3,5 -> 3,5", "3,5 -> 3,5"));
            Assertions.assertEquals(1, vents.doubledPoints());
        }

        @Test
        void shouldHaveOneCommonVentForDifferentLines() {
            Vents vents = LineFactory.createVentsWithHorizontalAndVerticalLines(Arrays.asList("3,5 -> 3,5", "3,5 -> 3,6"));
            Assertions.assertEquals(1, vents.doubledPoints());
        }

        @Test
        void shouldHaveOneCommonVentForDifferentLines2() {
            Vents vents = LineFactory.createVentsWithHorizontalAndVerticalLines(Arrays.asList("1,4 -> 5,4", "3,2 -> 3,9"));
            Assertions.assertEquals(1, vents.doubledPoints());
        }

        @Test
        void example() {
            Vents vents = LineFactory.createVentsWithHorizontalAndVerticalLines(Arrays.asList("0,9 -> 5,9", "8,0 -> 0,8", "9,4 -> 3,4", "2,2 -> 2,1", "7,0 -> 7,4", "6,4 -> 2,0", "0,9 -> 2,9", "3,4 -> 1,4", "0,0 -> 8,8", "5,5 -> 8,2"));
            Assertions.assertEquals(5, vents.doubledPoints());
        }

    }
}
