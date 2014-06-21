package com.hongweiyi.utils.varint;

/**
 *
 * Simple Varint implementation
 *
 * @author hongweiyi
 * @since 2014-Jun-21
 */
public class SimpleVarintUtils {

    public static byte[] int2varint(int value) {
        byte[] data = new byte[5];
        int count = 0;
        do {
            data[count] = (byte) ((value & 0x7F) | 0x80);
            count++;
        } while ((value >>= 7) != 0);
        data[count - 1] &= 0x7F;
        byte[] ret = new byte[count];
        System.arraycopy(data, 0, ret, 0, count);
        return ret;
    }

    public static int varint2int(byte... bytes) {
        int value = bytes[0];
        if ((value & 0x80) == 0)
            return value;
        value &= 0x7F;
        int chunk = bytes[1];
        value |= (chunk & 0x7F) << 7;
        if ((chunk & 0x80) == 0)
            return value;
        chunk = bytes[2];
        value |= (chunk & 0x7F) << 14;
        if ((chunk & 0x80) == 0)
            return value;
        chunk = bytes[3];
        value |= (chunk & 0x7F) << 21;
        if ((chunk & 0x80) == 0)
            return value;
        chunk = bytes[4];
        value |= chunk << 28;
        if ((chunk & 0xF0) == 0)
            return value;
        throw new RuntimeException("varint2int error");
    }

}
