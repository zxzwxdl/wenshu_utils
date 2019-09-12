package com.songguoxiong.wenshu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CipherText {
    private final String value;

    public CipherText() {
        value = cipher();
    }

    private String cipher() {
        return null;
    }

    private byte[] str2binary() {
        return null;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public static void main(String[] args) {
        Date date = new Date();

        long timestamp = date.getTime();
        String salt = new RequestVerificationToken(24).getValue();
        String iv = new SimpleDateFormat("yyyyMMdd").format(date);

        
    }
}
