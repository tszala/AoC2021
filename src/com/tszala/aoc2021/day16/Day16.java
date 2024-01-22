package com.tszala.aoc2021.day16;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day16 {

    private static final int BINARY_RADIX = 2;

    static String hexToBinaryString(String hex) {
        return IntStream.range(0, hex.length())
                .mapToObj(i -> Integer.parseInt(hex.substring(i, i + 1), 16))
                .map(HEX_DIGITS::get).collect(Collectors.joining());
    }

    static class PacketFactory {

        static List<Packet> decode(String binaryString, List<Packet> packets) {
            if(binaryString == null || binaryString.length() == 0) {
                return packets;
            }
            if(isLiteral(readType(binaryString))) {
                LiteralPacket literalPacket = PacketFactory.literalPacket(binaryString);
                packets.add(literalPacket);
                return decode(binaryString.substring(literalPacket.length()), packets);
            } else {
                OperatorPacket operatorPacket = PacketFactory.operatorPacket(binaryString);
                packets.add(operatorPacket);
                return decode(binaryString.substring(operatorPacket.length()), packets);
            }
        }

        static LiteralPacket literalPacket(String binaryInput) {
            String message = binaryInput;
            String version = readVersion(binaryInput);
            message = message.substring(6);
            StringBuilder sb = new StringBuilder();
            StringBuilder literalString = new StringBuilder();
            boolean readNext = true;
            while(readNext) {
                literalString.append(message, 0, 5);
                sb.append(parseGroup(message.substring(0, 5)));
                readNext = message.startsWith("1");
                if(readNext) {
                    message = message.substring(5);
                }
            }
            String literal = sb.toString();
            return new LiteralPacket(version, literal);
        }

        static String parseGroup(String input) {
            return input.substring(1);
        }

        static OperatorPacket operatorPacket(String binaryInput) {
            String version = readVersion(binaryInput);
            String type = readType(binaryInput);
            String lengthType = binaryInput.substring(6,7);

            String length = "0".equals(lengthType) ? readLength(binaryInput) : readSubpacketsCount(binaryInput);

            if("0".equals(lengthType)) {
                String subpacketString = binaryInput.substring(22, 22 + Integer.parseInt(length, BINARY_RADIX));
                List<Packet> subpackets = literalPacketInternal(subpacketString, -1, new ArrayList<>());
                return new OperatorPacket(version, type, lengthType, length, subpacketString, subpackets);
            } else {
                List<Packet> subpackets = literalPacketInternal(binaryInput.substring(18), Integer.valueOf(length, BINARY_RADIX), new ArrayList<>());
                return new OperatorPacket(version, type, lengthType, length,
                        subpackets.stream().map(Packet::asBinary).collect(Collectors.joining()),
                        subpackets);
            }
        }

        static List<Packet> literalPacketInternal(String binaryInput, int expectedNumberOfPackets, List<Packet> literalPackets) {
            if(binaryInput == null || binaryInput.length() == 0 || literalPackets.size() == expectedNumberOfPackets) {
                return literalPackets;
            }
            Packet packet;
            if(isLiteral(readType(binaryInput))) {
                packet = literalPacket(binaryInput);
            } else {
                packet = operatorPacket(binaryInput);
            }
            literalPackets.add(packet);
            return literalPacketInternal(binaryInput.substring(packet.asBinary().length()),expectedNumberOfPackets, literalPackets);
        }

        static boolean isLiteral(String type) {
            return "100".equals(type);
        }


        static String readVersion(String binaryInput) {
            return binaryInput.substring(0, 3);
        }

        static String readType(String binaryInput) {
            return binaryInput.substring(3,6);
        }

        static String readLength(String binaryInput) {
            return binaryInput.substring(7,22);
        }

        static String readSubpacketsCount(String binaryInput) {
            return binaryInput.substring(7,18);
        }
    }

    static abstract  class Packet {
        String version;
        String type;
        public Packet(String version, String type) {
            this.version = version;
            this.type = type;
        }

        String asBinary() {
            return version + type;
        }

        String asBinaryFilled() {
            String s = asBinary();
            int uneven = s.length() % 8;
            if(uneven == 0) {
                return s;
            }
            return s + fillWithZeros(8 - uneven);
        }

        String fillWithZeros(int numberOfZero) {
            StringBuilder sb = new StringBuilder();
            IntStream.range(0, numberOfZero).forEach(i->sb.append('0'));
            return sb.toString();
        }
        int length() {
            return asBinaryFilled().length();
        }

        int getVersionSumAsInt() {
            return Integer.parseInt(version, BINARY_RADIX);
        }

        abstract long value();
    }

    static class LiteralPacket extends Packet{
        String literal;
        LiteralPacket(String version, String literal) {
            super(version, "100");
            this.version = version;
            this.literal = literal;
        }

        String asBinary() {
            return super.asBinary() + literalAsBinaryString();
        }

        @Override
        long value() {
            return Long.parseLong(literal, BINARY_RADIX);
        }

        String literalAsBinaryString() {
            StringBuilder sb = new StringBuilder();
            String l = literal;
            while(l.length() > 4) {
                sb.append('1');
                sb.append(l.substring(0,4));
                l = l.substring(4);
            }
            sb.append('0');
            sb.append(l.substring(0,4));
            return sb.toString();
        }

    }

    static class OperatorPacket extends Packet {

        String lengthType;
        String length;
        String subPacketString;
        List<Packet> subpackets;

        public OperatorPacket(String version, String type, String lengthType, String length, String subpacketString, List<Packet> subpackets) {
            super(version, type);
            this.lengthType = lengthType;
            this.length = length;
            this.subPacketString = subpacketString;
            this.subpackets = subpackets;
        }

        String asBinary() {
            return super.asBinary() + lengthType + length + subPacketString;
        }
        int getVersionSumAsInt() {
            return super.getVersionSumAsInt() + subpackets.stream().map(Packet::getVersionSumAsInt).reduce(0, Integer::sum);
        }

        int getTypeAsNumber() {
            return Integer.parseInt(type, BINARY_RADIX);
        }

        @Override
        long value() {
            switch (getTypeAsNumber()) {
                case 0 : return subpackets.stream().map(Packet::value).reduce(0L, Long::sum);
                case 1 : return subpackets.stream().map(Packet::value).reduce(1L, (a,b) -> a*b);
                case 2 : return subpackets.stream().map(Packet::value).min(Long::compareTo).get();
                case 3 : return subpackets.stream().map(Packet::value).max(Long::compareTo).get();
                case 5 : return subpackets.get(0).value() > subpackets.get(1).value() ? 1L : 0L;
                case 6 : return subpackets.get(0).value() < subpackets.get(1).value() ? 1L : 0L;
                case 7 : return subpackets.get(0).value() == subpackets.get(1).value() ? 1L : 0L;
            }
            return 0;
        }
    }

    final static Map<Integer, String> HEX_DIGITS = Stream.of(new Object[][]{
            {0, "0000"},
            {1, "0001"},
            {2, "0010"},
            {3, "0011"},
            {4, "0100"},
            {5, "0101"},
            {6, "0110"},
            {7, "0111"},
            {8, "1000"},
            {9, "1001"},
            {10, "1010"},
            {11, "1011"},
            {12, "1100"},
            {13, "1101"},
            {14, "1110"},
            {15, "1111"}
        }).collect(Collectors.toMap(d->(Integer)d[0], d->(String)d[1]));

    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    private static void partOne() {
        List<Packet> packets = PacketFactory.decode(hexToBinaryString(INPUT), new ArrayList<>());
        System.out.printf("Decoded %d packets with version sum %d\n", packets.size(),
                packets.stream().map(Packet::getVersionSumAsInt).reduce(0, Integer::sum));
    }

    public static void partTwo() {
        List<Packet> packets = PacketFactory.decode(hexToBinaryString(INPUT), new ArrayList<>());
        System.out.printf("Packet's value is %d\n", packets.get(0).value());
    }


    public static final String INPUT = """
            E20D72805F354AE298E2FCC5339218F90FE5F3A388BA60095005C3352CF7FBF27CD4B3DFEFC95354723006C401C8FD1A23280021D1763CC791006E25C198A6C01254BAECDED7A5A99CCD30C01499CFB948F857002BB9FCD68B3296AF23DD6BE4C600A4D3ED006AA200C4128E10FC0010C8A90462442A5006A7EB2429F8C502675D13700BE37CF623EB3449CAE732249279EFDED801E898A47BE8D23FBAC0805527F99849C57A5270C064C3ECF577F4940016A269007D3299D34E004DF298EC71ACE8DA7B77371003A76531F20020E5C4CC01192B3FE80293B7CD23ED55AA76F9A47DAAB6900503367D240522313ACB26B8801B64CDB1FB683A6E50E0049BE4F6588804459984E98F28D80253798DFDAF4FE712D679816401594EAA580232B19F20D92E7F3740D1003880C1B002DA1400B6028BD400F0023A9C00F50035C00C5002CC0096015B0C00B30025400D000C398025E2006BD800FC9197767C4026D78022000874298850C4401884F0E21EC9D256592007A2C013967C967B8C32BCBD558C013E005F27F53EB1CE25447700967EBB2D95BFAE8135A229AE4FFBB7F6BC6009D006A2200FC3387D128001088E91121F4DED58C025952E92549C3792730013ACC0198D709E349002171060DC613006E14C7789E4006C4139B7194609DE63FEEB78004DF299AD086777ECF2F311200FB7802919FACB38BAFCFD659C5D6E5766C40244E8024200EC618E11780010B83B09E1BCFC488C017E0036A184D0A4BB5CDD0127351F56F12530046C01784B3FF9C6DFB964EE793F5A703360055A4F71F12C70000EC67E74ED65DE44AA7338FC275649D7D40041E4DDA794C80265D00525D2E5D3E6F3F26300426B89D40094CCB448C8F0C017C00CC0401E82D1023E0803719E2342D9FB4E5A01300665C6A5502457C8037A93C63F6B4C8B40129DF7AC353EF2401CC6003932919B1CEE3F1089AB763D4B986E1008A7354936413916B9B080""";
}
