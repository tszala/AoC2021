package com.tszala.aoc2021.day1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.management.DescriptorAccess;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DepthsMeasurementsTest {

    @Test
    void shouldReturnZeroAsDepthIfNoMeasurements() {
        Assertions.assertEquals(0, new DepthsMeasurements(Collections.emptyList()).countLargerMeasurements());
    }

    @Test
    void shouldReturnZeroAsDepthForOneMeasurement() {
        DepthsMeasurements depthsMeasurements = new DepthsMeasurements(Collections.singletonList(1));
        Assertions.assertEquals(0, depthsMeasurements.countLargerMeasurements());
    }

    @Test
    void shouldReturnOneAsDepthForRaisingMeasurements() {
        DepthsMeasurements depthsMeasurements = new DepthsMeasurements(Arrays.asList(1,2));
        Assertions.assertEquals(1, depthsMeasurements.countLargerMeasurements());
    }

    @Test
    void shouldReturnZeroAsDepthForFallingMeasurements() {
        DepthsMeasurements depthsMeasurements = new DepthsMeasurements(Arrays.asList(2,1));
        Assertions.assertEquals(0, depthsMeasurements.countLargerMeasurements());
    }

}