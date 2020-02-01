package com.songguoxiong.wenshu.util;

public class PageID {
    private final String value;

    public PageID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append(Integer.toHexString(Double.valueOf(Math.floor(Math.random() * 16)).intValue()));
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
