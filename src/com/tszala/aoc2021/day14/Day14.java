package com.tszala.aoc2021.day14;

import com.tszala.aoc2021.utils.FileOps;
import com.tszala.aoc2021.utils.Tuple;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 {

    public static final String STARTING_POLYMER = "SVCHKVFKCSHVFNBKKPOC";

    public static final Pattern INSTRUCTION_PATTERN = Pattern.compile("([A-Z])([A-Z])");

    public static void main(String[] args) throws IOException {
        List<String> lines = FileOps.readAllLines("src/com/tszala/aoc2021/day14/input.txt");

        List<Instruction> instructions = lines.stream().map(Day14::lineToInstruction).collect(Collectors.toList());

        Polymer polymer = initPolymer(STARTING_POLYMER);
        polymer.setInstructions(instructions);
        IntStream.range(0, 10).forEach(i -> polymer.step());
        List<Long> occurences = polymer.countOccurences().values().stream().sorted().collect(Collectors.toList());
        System.out.println("Problem one answer is " + (occurences.get(occurences.size()-1) - occurences.get(0)));

        OptimizedPolymer optimizedPolymer = new OptimizedPolymer();
        optimizedPolymer.setInstructionCache(instructions);
        optimizedPolymer.initFrom(STARTING_POLYMER);
        IntStream.range(0,40).forEach(i -> optimizedPolymer.step());
        List<Long> occurencesPartTwo = optimizedPolymer.occurences().stream().sorted().collect(Collectors.toList());
        System.out.println("Problem two answer is " + (occurencesPartTwo.get(occurencesPartTwo.size()-1) - occurencesPartTwo.get(0)));

    }

    static class OptimizedPolymer {

        private Map<Pair, Long> pairs = new HashMap<>();
        private Map<Pair, Instruction> instructionCache;
        private Map<Character, Long> occurences = new HashMap<>();

        static class Pair extends Tuple<Character, Character> {
            public Pair(Character character, Character character2) {
                super(character, character2);
            }
        }

        public void setInstructionCache(List<Instruction> instructions) {
            this.instructionCache = instructions.stream().collect(Collectors.toMap(i->new Pair(i.previous,i.next), Function.identity()));
        }

        public void initFrom(String input) {
            for(int i = 0; i < input.length()-1; i++) {
                Pair pair = new Pair(input.charAt(i), input.charAt(i + 1));
                Long count = pairs.getOrDefault(pair, 0L);
                pairs.put(pair, count + 1L);
            }
            for(int i =0; i < input.length(); i++) {
                Long count = occurences.getOrDefault(input.charAt(i), 0L);
                occurences.put(input.charAt(i), count + 1L);
            }
        }

        public void step() {
            Map<Pair, Long> newPairs = new HashMap<>(pairs);
            for(Map.Entry<Pair, Long> entries : pairs.entrySet()) {
                Pair pair = entries.getKey();
                Long count = pairs.getOrDefault(entries.getKey(), 0L);
                if(count > 0L) {
                    Instruction instruction = instructionCache.get(entries.getKey());
                    if(instruction != null) {
                        Pair p1 = new Pair(instruction.previous, instruction.newElement);
                        Pair p2 = new Pair(instruction.newElement, instruction.next);
                        Long countP1 = newPairs.getOrDefault(p1, 0L);
                        Long countP2 = newPairs.getOrDefault(p2, 0L);
                        if(pair.equals(p1)) {
                            newPairs.put(p1, countP1);
                        } else {
                            newPairs.put(p1, countP1 + count);
                        }
                        if(pair.equals(p2)) {
                            newPairs.put(p2, countP2);
                        } else {
                            newPairs.put(p2, countP2 + count);
                        }

                        if(!(pair.equals(p1) || pair.equals(p2))) {
                            newPairs.put(entries.getKey(), newPairs.get(entries.getKey()) - count);
                        }
                        Long o = occurences.getOrDefault(instruction.newElement, 0L);
                        occurences.put(instruction.newElement, o + count);
                    }
                }
            }
            pairs = newPairs;
        }

        public Collection<Long> occurences() {
            return occurences.values();
        }

    }

    public static Polymer initPolymer(String startingPolymer) {
        Polymer polymer = new Polymer(new PElement(startingPolymer.charAt(0)));
        for(int i = 1; i < startingPolymer.length(); i++) {
            polymer.append(new PElement(startingPolymer.charAt(i)));
        }

        return polymer;
    }

    public static Instruction lineToInstruction(String line) {
        return new Instruction(line.charAt(0), line.charAt(1), line.charAt(6));
    }


    static class Instruction {
        private Character previous;
        private Character next;
        private Character newElement;

        public Instruction(Character previous, Character next, Character newElement){
            this.previous = previous;
            this.next = next;
            this.newElement = newElement;
        }
    }

    static class Polymer {
        private final PElement first;
        private PElement last;
        private Map<String, Instruction> instructionCache;
        public Polymer(PElement first) {
            this.first = first;
            this.last = first;
        }

        public Polymer append(PElement next) {
            last.setNext(next);
            last = next;
            return this;
        }

        public void setInstructions(List<Instruction> instructions) {
            instructionCache = instructions.stream().collect(Collectors.toMap(i->""+i.previous+i.next, Function.identity()));
        }

        public void step() {
            PElement start = first;
            while(start != null && start.next != null) {
                PElement next = start.next;

                Instruction instruction = instructionCache.get(""+ start.symbol + next.symbol);

                if(instruction != null && instructionMatches(instruction, start, next)) {
                    insertPElement(instruction, start, next);
                }

                start = next;
            }
        }


        private void insertPElement(Instruction instruction, PElement start, PElement next) {
            start.next = new PElement(instruction.newElement);
            start.next.next = next;
        }

        private boolean instructionMatches(Instruction instruction, PElement start, PElement next) {
            return start.symbol.equals(instruction.previous) && next.symbol.equals(instruction.next);
        }

        public void printPolymer() {
            PElement start = first;
            while (start != null) {
                System.out.print(start.symbol);
                start = start.next;
            }
            System.out.println();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Polymer: ");
            PElement start = first;
            while(start != null) {
                sb.append(start.symbol);
                start = start.next;
            }
            return sb.toString();
        }

        public Map<Character, Long> countOccurences(){
            Map<Character, Long> occurences = new HashMap<>();
            PElement start = first;
            while(start != null) {
                Long count = occurences.getOrDefault(start.symbol, 0L);
                occurences.put(start.symbol, count + 1L);
                start = start.next;
            }
            return occurences;
        }
    }


    static class PElement {
        private Character symbol;
        private PElement next;

        public PElement(Character symbol) {
            this.symbol = symbol;
        }

        public void setNext(PElement pElement) {
            next = pElement;
        }
    }


}
