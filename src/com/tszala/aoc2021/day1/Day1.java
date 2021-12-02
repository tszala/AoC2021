package com.tszala.aoc2021.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/com/tszala/aoc2021/day1/input.txt"));
        List<Integer> depths = lines.stream().map(Integer::parseInt).collect(Collectors.toList());
        DepthsMeasurements depthsMeasurements = new DepthsMeasurements(depths);
        System.out.printf("Larger depths %d\n", depthsMeasurements.countLargerMeasurements());

        DepthsMeasurements depthsMeasurements2 = new DepthsMeasurements(toWindowedMeasurements(depths, 3));
        System.out.printf("Windowed depts %d\n", depthsMeasurements2.countLargerMeasurements());

        Partition<Integer> partition = new Partition<>(depths, 3);
        List<Integer> summedDepths = partition.stream().filter(l -> l.size() == 3).map(l -> l.stream().collect(Collectors.summingInt(Integer::intValue))).collect(Collectors.toList());
        System.out.printf("Windowd depths with partitions %d\n", new DepthsMeasurements(summedDepths).countLargerMeasurements());

    }

    static List<Integer> toWindowedMeasurements(List<Integer> measurements, int windowSize) {
        if (measurements.size() < windowSize) {
            return Collections.emptyList();
        }
        List<Integer> windowedMeasurements = new ArrayList<>();
        for (int i = 0; i <= (measurements.size() - windowSize); i++) {
            int sum = 0;
            for (int j = i; j < (i + windowSize); j++) {
                sum += measurements.get(j);
            }
            windowedMeasurements.add(sum);
        }
        return windowedMeasurements;
    }

}
