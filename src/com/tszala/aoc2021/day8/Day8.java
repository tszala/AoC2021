package com.tszala.aoc2021.day8;

import com.tszala.aoc2021.utils.FileOps;
import com.tszala.aoc2021.utils.Tuple;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {

    private static final Set<Integer> UNIQUE_NUMBERS = Set.of(2,4,3,7);

    public static void main(String[] args) throws IOException {
        List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day8/input.txt");

        long numberOfUniqueSignals = getNumberOfUniqueSignals(lines);
        System.out.printf("Number of unique signals is %d\n", numberOfUniqueSignals);

        Integer sum = lines.stream().map(line -> new Tuple<>(line.substring(0, line.indexOf('|')-1), line.substring(line.indexOf('|') + 1)))
                .map(t -> new Tuple<>(new SegmentDecoder(t.left), t.right)).map(t -> decodeOutputs(t.left, t.right)).reduce(0, Integer::sum);

        System.out.printf("Sum of outputs is %d\n", sum);
    }

    public static long getNumberOfUniqueSignals(List<String> lines) {
        List<String> outputSignals = lines.stream()
                .map(line -> line.substring(line.indexOf('|') + 1)).collect(Collectors.toList());
        List<String> stringStream = outputSignals.stream()
                .map(signal -> signal.split(" "))
                .flatMap(Stream::of)
                .filter(StringUtils::isNotBlank).collect(Collectors.toList());
        long numberOfUniqueSignals = stringStream.stream()
                .filter(Day8::composedOfUniqueSegments).count();
        return numberOfUniqueSignals;
    }

    public static boolean composedOfUniqueSegments(String signal) {
        return UNIQUE_NUMBERS.contains(signal.length());
    }

    public static Integer decodeOutputs(SegmentDecoder segmentDecoder, String output) {
        return Integer.parseInt(Arrays.stream(output.split(" "))
                .map(s -> s.chars().sorted().mapToObj(e -> String.valueOf((char) e)).collect(Collectors.joining()))
                .filter(signal -> signal.length() > 0)
                .map(signal -> String.valueOf(segmentDecoder.getSignalDigit(signal)))
                .collect(Collectors.joining()));
    }

    static class SegmentDecoder {
        Map<String, Integer> mapping = new HashMap<>();
        Map<Integer, String> inverseMapping = new HashMap<>();

        SegmentDecoder(String input) {
            List<String> standarizedSignals = Arrays.stream(input.split(" "))
                    .map(s -> s.chars().sorted().mapToObj(e -> String.valueOf((char) e)).collect(Collectors.joining()))
                    .collect(Collectors.toList());

            Rule isOne = new LengthBasedRule(1,2);
            Rule isFour = new LengthBasedRule(4,4);
            Rule isSeven = new LengthBasedRule(7,3);
            Rule isEight = new LengthBasedRule(8,7);

            List<Rule> basicRules = Arrays.asList(isOne, isFour, isSeven, isEight);

            for(String signal : standarizedSignals) {
                Optional<Rule> rule = basicRules.stream().filter(r -> r.match(signal)).findFirst();
                if(rule.isPresent()) {
                    mapping.put(signal, rule.get().digit());
                    inverseMapping.put(rule.get().digit(), signal);
                }
            }

            Rule isNine = new ContainingRule(9, 6, inverseMapping.get(4));
            Rule isZero = new ContainingRule(0, 6, inverseMapping.get(1));
            Rule isThree = new ContainingRule(3, 5, inverseMapping.get(1));

            List<Rule> advancedRules = Arrays.asList(isNine, isZero, isThree);

            for(String signal : standarizedSignals) {
                Optional<Rule> canBeRule = advancedRules.stream().filter(r -> r.match(signal)).findFirst();
                if(canBeRule.isPresent()) {
                    mapping.put(signal, canBeRule.get().digit());
                    inverseMapping.put(canBeRule.get().digit(), signal);
                }
            }
            Rule isSix = new ConditionRule(6, 6, signal -> !signal.contains(inverseMapping.get(4)) && !signal.contains(inverseMapping.get(1)));
            for(String signal : standarizedSignals) {
                if(!mapping.containsKey(signal)) {
                    if(isSix.match(signal)) {
                        mapping.put(signal, 6);
                        inverseMapping.put(6, signal);
                    }
                }
            }

            Rule isFive = new ConditionRule(5, 5, signal -> !signal.contains(commonPart(inverseMapping.get(6), inverseMapping.get(1))));
            for(String signal : standarizedSignals) {
                if(!mapping.containsKey(signal)) {
                    if(isFive.match(signal)) {
                        mapping.put(signal, 2);
                    } else {
                        mapping.put(signal, 5);
                    }
                }
            }


        }
        int getSignalDigit(String signal) {
            String key = signal.chars().sorted().mapToObj(e -> String.valueOf((char) e)).collect(Collectors.joining());
            if(!mapping.containsKey(key) || mapping.get(key) == null) {
                System.out.println("Couldn't find key " + key);
            }
            return mapping.get(key);
        }
    }

    public static String commonPart(String num1, String num2) {
        Set<Character> characters1 = new TreeSet<Character>();
        for(int i = 0; i < num1.length(); i++) {
            characters1.add(num1.charAt(i));
        }

        Set<Character> characters2 = new TreeSet<Character>();
        for(int i = 0; i < num2.length(); i++) {
            characters2.add(num2.charAt(i));
        }

        characters1.retainAll(characters2);
        return characters1.stream().map(String::valueOf).collect(Collectors.joining());
    }

    interface Rule {
        boolean match(String signal);
        int digit();
    }

    static class LengthBasedRule implements Rule {
        int digit;
        int length;

        LengthBasedRule(int digit, int length) {
            this.digit = digit;
            this.length = length;
        }

        @Override
        public boolean match(String signal) {
            return signal.length() == length;
        }

        @Override
        public int digit() {
            return digit;
        }
    }

    static class ContainingRule extends LengthBasedRule {
        private String stringToContain;

        ContainingRule(int digit, int length, String stringToContain) {
            super(digit, length);

            this.stringToContain = stringToContain;
        }

        @Override
        public boolean match(String signal) {
            return super.match(signal) && stringToContain.chars().allMatch(a -> signal.chars().anyMatch(any -> any == a));
        }
    }

    static class ConditionRule extends LengthBasedRule {
        private Predicate<String> condition;

        ConditionRule(int digit, int length, Predicate<String> condition) {
            super(digit, length);

            this.condition = condition;
        }

        @Override
        public boolean match(String signal) {
            return super.match(signal) && condition.test(signal);
        }
    }

}
