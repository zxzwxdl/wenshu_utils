package com.songguoxiong.wenshu.utils.old.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class StringUtils {
    public static String toUnicode(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            stringBuilder.append("\\u").append(Integer.toHexString(c));
        }
        return stringBuilder.toString();
    }

    public static String toBase64(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

    public static byte[] hexToBytes(String string) {
        int length = string.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            int i1 = Character.digit(string.charAt(i), 16) << 4;
            int i2 = Character.digit(string.charAt(i + 1), 16);
            bytes[i / 2] = (byte) (i1 + i2);
        }
        return bytes;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String md5ToHex(String string) {
        byte[] digest = digest("MD5", string.getBytes());
        return digest != null ? StringUtils.bytesToHex(digest) : "";
    }

    public static String sha1ToHex(String string) {
        byte[] digest = digest("SHA1", string.getBytes());
        return digest != null ? StringUtils.bytesToHex(digest) : "";
    }

    private static byte[] digest(String algorithm, byte[] input) {
        try {
            return MessageDigest.getInstance(algorithm).digest(input);
        } catch (NoSuchAlgorithmException ignore) {
            return null;
        }
    }
}
