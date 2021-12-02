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
    public static List<Integer> getInputAsNumbers(InputStream input) {
        return getInputWithConverter(input, Integer::valueOf);
    }

    public static List<String> getInputAsText(InputStream input) {
        return getInputWithConverter(input, Function.identity());
    }

    public static List<String> readAllLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    private static <T> List<T> getInputWithConverter(InputStream inputStream, Function<String, T> converter) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().map(converter::apply).collect(Collectors.toList());
    }
}