package com.tszala.aoc2021.day1;

import java.util.List;

class DepthsMeasurements {
    private final List<Integer> measurements;

    DepthsMeasurements(List<Integer> measurements) {
        this.measurements = measurements;
    }

    public int countLargerMeasurements() {
        int depthLargerThanPrevious = 0;
        for (int i = 1; i < measurements.size(); i++) {
            if (measurements.get(i - 1) < measurements.get(i)) {
                depthLargerThanPrevious++;
            }
        }
        return depthLargerThanPrevious;
    }
}
