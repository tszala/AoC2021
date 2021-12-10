package com.tszala.aoc2021.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileOps {

    public static final char ZERO = '0';

    public static List<Integer> getInputAsNumbers(InputStream input) {
        return getInputWithConverter(input, Integer::valueOf);
    }

    public static List<String> getInputAsText(InputStream input) {
        return getInputWithConverter(input, Function.identity());
    }

    public static List<String> readAllLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    public static char[][] readChars(String paths) throws IOException {
        List<String> lines = readAllLines(paths);
        return initChars(lines);
    }

    public static int[][] readInts(String path) throws IOException {
        List<String> lines = readAllLines(path);
        return initInts(lines);
    }

    public static char[][] initChars(List<String> lines) {
        char[][] vents = new char[lines.size()][];
        for(int i = 0; i < lines.size(); i++) {
            vents[i] = stringToCharArray(lines.get(i));
        }
        return vents;
    }

    public static int[][] initInts(List<String> lines) {
        int[][] vents = new int[lines.size()][];
        for(int i = 0; i < lines.size(); i++) {
            vents[i] = stringToIntArray(lines.get(i));
        }
        return vents;
    }

    private static char[] stringToCharArray(String line) {
        return line.toCharArray();
    }

    private static int[] stringToIntArray(String line) {
        return line.chars().map(c->c-ZERO).toArray();
    }

    private static <T> List<T> getInputWithConverter(InputStream inputStream, Function<String, T> converter) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().map(converter::apply).collect(Collectors.toList());
    }
}
