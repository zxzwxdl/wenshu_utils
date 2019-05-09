package com.sgx.spider.wenshu.vl5x;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Vjkl5 {
    private final static String algorithm = "SHA1";
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private String value;

    public Vjkl5() {
        this.value = createVjkl5();
    }

    public Vjkl5(String value) {
        this.value = value;
    }

    private String createVjkl5() {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            String randomStr = Math.random() + "";
            byte[] digest = md.digest(randomStr.getBytes());
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0f];
        }
        return new String(hexChars).toLowerCase();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
