package com.songguoxiong.wenshu.utils.old.vl5x;

public class Number {
    private final String value;

    public Number() {
        this.value = String.format("%.2f", Math.random());
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
