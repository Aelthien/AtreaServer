package atrea.server.engine.io;

import java.math.BigInteger;

public class ByteUtils {

    public static byte[] intToBytes(int value) {
        BigInteger bigInteger = BigInteger.valueOf(value);

        return bigInteger.toByteArray();
    }

    public static byte[] shortToBytes(int value) {
        byte[] bytes = new byte[2];

        bytes[0] = (byte) value;
        bytes[1] = (byte) (value >> 8);

        return bytes;
    }
}