package com.hongweiyi.utils.varint;

/**
 *
 * This Varunit (var unsigned integer) can use in stream protocol
 *
 * I took 2 bits as integer bytes length, so the max value is (Integer.MAX_VALUE) >> 2 => 0111 1111 1111 1111 1111 1111 1111 11[00]
 *
 * Why cannot support Integer.MAX_VALUE?
 *  If it support Integer.MAX_VALUE, it will cost 3 bits as integer bytes length, one byte can only present 2^5 = 32,
 *  this is not what I wanted.
 * Why cannot support negative integer?
 *  1. Because this util class is mainly used in counter scenario, counter number must be positive.
 *  2. I'm too lazy to code it!!!
 *
 *
 * @author hongweiyi
 * @since 2014-Jun-21
 */
public class StreamVaruintUtils {
    static final byte MASK = (byte) 0x03;

    public static byte[] int2varint(int value) {
        if (value < 0) {
            throw new RuntimeException("cannot support negative integer");
        }

        byte[] bytes;
        value <<= 2;
        // 1111 1100
        // create byte[] separately
        // it can avoid array copy after
        if (value <= 0xFC) {
            bytes = new byte[1];
            // 1111 1111 1111 1100
        } else if (value <= 0xFFFC) {
            bytes = new byte[2];
            value |= (2 - 1);
            // 1111 1111 1111 1111 1111 1100
        } else if (value <= 0xFFFFFC) {
            bytes = new byte[3];
            value |= (3 - 1);
            // 0111 1111 1111 1111 1111 1111 1111 1100
        } else if (value <= 0x7FFFFFFC) {
            bytes = new byte[4];
            value |= (4 - 1);
        } else {
            throw new RuntimeException("integer overflow: " + value);
        }
        for (int j = bytes.length - 1; j >= 0; j--) {
            bytes[j] = (byte) ((value >> (8 * j)) & 0xFF);
        }

        return bytes;
    }

    public static int varint2int(byte... bytes) {
        int len = bytes.length;
        int value = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (bytes[i] < 0) {
                value |= (bytes[i] + 256) << (i * 8);
            } else {
                value |= bytes[i] << (i * 8);

            }
        }

        value &= ~(len - 1);
        value >>= 2;

        return value;
    }

    public static int getByteNum(byte b) {
        return (b & MASK) + 1;
    }
}
