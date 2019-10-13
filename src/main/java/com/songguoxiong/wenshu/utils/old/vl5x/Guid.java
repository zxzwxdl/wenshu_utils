package com.songguoxiong.wenshu.utils.old.vl5x;

public class Guid {
    private final String value;

    public Guid() {
        this.value = String.format(
                "%s%s-%s-%s%s-%s%s%s",
                createGuid(), createGuid(),
                createGuid(), createGuid(),
                createGuid(), createGuid(),
                createGuid(), createGuid()
        );
    }

    private String createGuid() {
        return Integer.toHexString((int) ((1 + Math.random()) * 0x10000)).substring(1);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
