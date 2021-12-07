package com.tszala.aoc2021.day7;

import com.tszala.aoc2021.utils.Tuple;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day7 {
    public static final String INPUT = "1101,1,29,67,1102,0,1,65,1008,65,35,66,1005,66,28,1,67,65,20,4,0,1001,65,1,65,1106,0,8,99,35,67,101,99,105,32,110,39,101,115,116,32,112,97,115,32,117,110,101,32,105,110,116,99,111,100,101,32,112,114,111,103,114,97,109,10,33,133,43,1060,890,12,15,136,42,25,96,694,522,893,204,204,1168,311,1046,1699,26,399,299,66,644,402,65,480,711,72,894,244,249,337,331,774,126,23,484,1299,662,404,235,86,1492,556,73,478,210,82,433,597,154,130,178,491,578,856,532,1191,544,256,831,252,1001,109,37,1290,317,376,22,742,496,930,118,28,376,73,247,942,895,38,675,138,387,203,271,104,65,1099,981,167,67,57,607,1095,202,225,1067,1757,324,127,785,266,518,135,914,1006,1402,578,28,548,211,673,302,525,208,115,92,514,518,71,1298,796,780,166,1341,475,273,101,1155,838,1219,901,727,497,168,543,416,174,31,755,865,106,358,236,186,369,550,465,617,375,535,1639,513,419,1377,1024,704,77,38,0,149,5,28,1163,149,1654,614,1201,89,1141,844,1390,1081,132,1385,52,1027,80,572,377,340,39,630,875,692,289,339,358,68,205,54,149,41,1208,1528,171,204,438,571,308,556,1372,426,204,18,31,51,40,287,1845,1721,441,240,875,901,328,800,341,59,530,134,275,11,7,7,1,1571,218,374,536,992,464,234,398,300,74,99,1163,1039,430,43,659,667,1115,407,257,717,657,249,46,109,734,67,1010,581,1070,738,478,621,183,224,1372,560,1573,1026,338,485,1138,1007,910,16,846,556,423,200,962,103,570,540,900,839,319,171,14,22,205,225,569,81,381,132,127,139,123,788,1571,35,830,65,677,1745,819,804,854,346,190,480,1500,76,1049,306,17,668,113,163,755,1015,718,1037,156,267,158,74,377,26,294,203,334,1186,88,384,853,404,290,135,620,668,234,1158,2,1102,137,884,287,15,638,1003,187,24,534,24,647,683,934,275,1844,887,1746,1614,1788,632,100,332,1565,1352,341,1027,475,958,289,1564,89,1138,233,535,790,990,863,889,45,44,169,251,522,11,41,104,45,828,1206,1515,645,39,544,382,1413,995,188,310,51,39,474,14,7,1387,809,428,77,8,867,1105,718,426,146,486,191,1251,677,1139,802,585,1140,46,39,128,867,49,33,198,731,349,661,296,103,22,444,1198,1149,188,245,492,1147,230,213,300,551,295,1313,365,975,587,1416,1213,233,257,631,564,876,434,1353,51,748,1179,1428,915,115,57,90,1312,892,200,1349,35,1010,445,619,1261,108,14,1424,481,381,209,154,23,972,646,593,6,289,171,543,97,28,401,290,298,14,37,1326,1177,533,67,75,294,328,527,449,455,176,345,226,729,210,55,45,0,834,887,123,1326,931,278,449,1278,608,217,411,143,447,16,1043,29,165,88,860,582,21,811,920,162,1788,15,423,1172,842,801,845,20,155,155,642,40,1036,560,348,689,328,505,84,1013,58,93,1653,233,233,383,380,84,617,1128,305,123,508,205,6,322,118,359,1186,84,677,640,80,1357,868,1035,8,64,995,1246,266,443,346,112,523,625,206,66,565,1878,25,1277,936,283,148,987,282,368,883,542,631,946,118,53,4,235,16,950,4,998,106,25,151,1013,27,1038,77,140,82,1119,236,125,947,1446,680,301,301,936,21,609,516,280,264,281,108,43,215,36,126,401,402,693,360,321,92,1809,305,551,86,77,278,81,524,400,1458,1342,897,49,35,518,288,655,91,398,38,251,647,79,400,151,520,459,960,425,663,298,584,90,533,690,610,755,56,19,21,244,548,1116,773,43,115,171,1127,103,1199,1470,176,451,693,65,186,262,963,137,1422,431,533,210,799,17,388,600,1113,2,181,815,1153,6,618,590,719,196,39,301,424,193,560,175,351,279,603,171,423,146,158,48,398,513,115,1,1051,817,200,473,143,261,435,856,1057,503,51,846,1020,177,1091,232,500,372,475,70,485,1227,1032,64,743,299,159,1077,18,204,944,1075,29,78,63,67,9,1007,354,1046,491,448,206,222,121,955,290,381,147,146,104,576,722,163,715,1475,130,1104,586,97,352,173,713,315,1482,1221,38,10,81,457,745,323,47,197,1012,1593,128,463,373,272,90,121,1248,1451,540,681,63,950,19,208,230,1362,1225,1500,207,81,739,288,626,261,1188,356,889,408,3,368,94,858,1512,834,43,5,833,826,33,791,800,39,299,1587,41,783,498,899,296,1189,470,66,307,892,47,207,199,902,17,14,1831,11,576,729,1436,153,142,81,165,214,1543,1464,561,737,180,162,515,867,65,74,200,9,11,539,19,305,996,334,297,1825,427,169,225,53,688,420,623,111,313,324,5,376,433,135,308,94";

    public static void main(String[] args) {
        List<Integer> crabPositions = Stream.of(INPUT.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        List<Integer> sorted = crabPositions.stream().sorted().collect(Collectors.toList());
        Integer lowestPosition = sorted.get(0);
        Integer highestPosition = sorted.get(sorted.size()-1);
        System.out.printf("Lowest crab position is %d, highest crab position is %d\n", lowestPosition, highestPosition);
        List<Tuple<Integer, Integer>> positionsWithSimpleFuelUsage = calculateFuelConsumption(highestPosition, crabPositions, simpleFuelUsageCalculator);
        System.out.printf("Most economic position is %d with fuel consumption %d\n", positionsWithSimpleFuelUsage.get(0).left, positionsWithSimpleFuelUsage.get(0).right);

        List<Tuple<Integer, Integer>> positionsWithNonConstantFuel = calculateFuelConsumption(highestPosition, crabPositions, nonConstantFuelUsageCalculator);
        System.out.printf("Most economic position is %d with non constant fuel consumption %d\n", positionsWithNonConstantFuel.get(0).left, positionsWithNonConstantFuel.get(0).right);

    }

    private static List<Tuple<Integer, Integer>> calculateFuelConsumption(Integer highestPosition, List<Integer> crabPositions, Function<Integer, Function<Integer, Integer>> fuelForMoveCalculator) {
        return  IntStream.rangeClosed(0, highestPosition).boxed()
                .map(pos -> new Tuple<>(pos, countFuelForPosition(crabPositions, fuelForMoveCalculator.apply(pos))))
                .sorted(Comparator.comparing(a -> a.right))
                .collect(Collectors.toList());
    }

    private static Integer countFuelForPosition(List<Integer> crabPositions, Function<Integer, Integer> fuelConsumptionCalculator) {
        return crabPositions.stream().map(fuelConsumptionCalculator).reduce(0, Integer::sum);
    }

    static Function<Integer, Function<Integer, Integer>> simpleFuelUsageCalculator = position -> crabPosition -> Math.abs(crabPosition - position);

    static Function<Integer, Function<Integer, Integer>> nonConstantFuelUsageCalculator = position -> crabPosition -> {
        int distance = Math.abs(crabPosition - position);
        if(distance == 0) {
            return 0;
        }
        int fuel = 0;
        for(int i =1; i <= distance; i++) {
            fuel += i;
        }
        return fuel;
    };

}