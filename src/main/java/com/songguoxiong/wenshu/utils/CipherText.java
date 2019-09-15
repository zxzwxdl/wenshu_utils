package com.songguoxiong.wenshu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CipherText {
    private final String value;

    public CipherText() {
        value = cipher();
    }

    private String cipher() {
        Date date = new Date();

        long timestamp = date.getTime();
        String salt = new RequestVerificationToken(24).getValue();
        String iv = new SimpleDateFormat("yyyyMMdd").format(date);

        try {
            String enc = DES3.encrypt(Long.toString(timestamp), salt, iv);
            return str2binary(salt + iv + enc);
        } catch (Exception e) {
            return null;
        }
    }

    private String str2binary(String string) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            sb.append(Integer.toBinaryString(string.charAt(i))).append(" ");
        }
        return sb.toString().trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
