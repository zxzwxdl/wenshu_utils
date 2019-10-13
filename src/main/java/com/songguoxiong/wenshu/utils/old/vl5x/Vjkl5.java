package com.songguoxiong.wenshu.utils.old.vl5x;

import com.songguoxiong.wenshu.utils.old.common.StringUtils;

import java.util.UUID;

public class Vjkl5 {
    private final String value;

    public Vjkl5() {
        this(StringUtils.sha1ToHex(UUID.randomUUID().toString()).toLowerCase());
    }

    public Vjkl5(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
