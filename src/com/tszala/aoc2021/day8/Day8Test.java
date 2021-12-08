package com.tszala.aoc2021.day8;

import com.tszala.aoc2021.utils.FileOps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day8Test {

    @Test
    void shouldTestUniqueSegments() {
        Assertions.assertEquals(true, Day8.composedOfUniqueSegments("gcbe"));
        Assertions.assertEquals(true, Day8.composedOfUniqueSegments("dgebacf"));
        Assertions.assertEquals(true, Day8.composedOfUniqueSegments("fadegcb"));

        Assertions.assertEquals(false, Day8.composedOfUniqueSegments("dcbef"));
        Assertions.assertEquals(false, Day8.composedOfUniqueSegments("bagce"));

    }

    @Test
    void shouldCountSignalsForTestInput() throws IOException {
        List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day8/input_test.txt");
        Assertions.assertEquals(26, Day8.getNumberOfUniqueSignals(lines));
    }

    @Test
    void shouldTestDecoder() {
        String input = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab";

        Day8.SegmentDecoder segmentDecoder = new Day8.SegmentDecoder(input);

        Assertions.assertEquals(1, segmentDecoder.getSignalDigit("ab"));
        Assertions.assertEquals(7, segmentDecoder.getSignalDigit("abd"));
        Assertions.assertEquals(4, segmentDecoder.getSignalDigit("abef"));
        Assertions.assertEquals(8, segmentDecoder.getSignalDigit("abcdefg"));
        Assertions.assertEquals(5, segmentDecoder.getSignalDigit("cdfbe"));
        Assertions.assertEquals(2, segmentDecoder.getSignalDigit("gcdfa"));
        Assertions.assertEquals(3, segmentDecoder.getSignalDigit("fbcad"));

        String input2 = "abceg abdeg ce ceg abcfg abdefg abcdefg abcdeg acde bcdefg";
        Day8.SegmentDecoder segmentDecoder2 = new Day8.SegmentDecoder(input2);
        Assertions.assertEquals(1, segmentDecoder2.getSignalDigit("ce"));
        Assertions.assertEquals(7, segmentDecoder2.getSignalDigit("ceg"));
        Assertions.assertEquals(4, segmentDecoder2.getSignalDigit("acde"));
        Assertions.assertEquals(8, segmentDecoder2.getSignalDigit("abcdefg"));
        Assertions.assertEquals(5, segmentDecoder2.getSignalDigit("abdeg"));
        Assertions.assertEquals(2, segmentDecoder2.getSignalDigit("abcfg"));
        Assertions.assertEquals(3, segmentDecoder2.getSignalDigit("abceg"));

    }


    @Test
    void shouldTestCommonPart() {
        Assertions.assertEquals("b", Day8.commonPart("cdfgeb", "ab"));

    }

}
