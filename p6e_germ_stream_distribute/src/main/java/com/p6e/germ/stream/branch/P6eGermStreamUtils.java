package com.p6e.germ.stream.branch;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eGermStreamUtils {

    /**
     * hex 转 byte 数组
     * @param hex hex 内容
     * @return byte 数组
     */
    public static byte[] hexToByte(String hex){
        int m, n;
        final int byteLen = hex.length() / 2;
        final byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            final int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = (byte) intVal;
        }
        return ret;
    }

    /**
     * byte 数组转 hex
     * @param bytes byte 数组
     * @return hex 内容
     */
    public static String bytesToHex(byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (final byte aByte : bytes) {
            final String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * byte 数组转高8低8 int 数据
     * @param bytes byte 数组
     * @return int 数据
     */
    public static int bytesToHigh8Low8(byte[] bytes) {
        return (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
    }

    /**
     * int 数据高8低8 byte 数组
     * @param data int 数据
     * @return byte 数组
     */
    public static byte[] intToHigh8Low8(int data) {
        return new byte[] { (byte) ((data >> 8) & 0xFF), (byte) (data & 0xFF) };
    }

}
