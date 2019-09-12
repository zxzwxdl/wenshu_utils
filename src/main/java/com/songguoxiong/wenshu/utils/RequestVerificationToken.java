package com.songguoxiong.wenshu.utils;

public class RequestVerificationToken {
    private final String value;

    public RequestVerificationToken(int size) {
        StringBuilder sb = new StringBuilder();

        String arr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < size; i++) {
            sb.append(arr.charAt(Long.valueOf(Math.round(Math.random() * (arr.length() - 1))).intValue()));
        }

        value = sb.toString();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
