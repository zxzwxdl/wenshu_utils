package com.sgx.spider.wenshu.vl5x;

import org.junit.Test;

public class GuidTest {
    @Test
    public void testGuid() {
        for (int i = 0; i < 1000000; i++) {
            assert new Guid().getValue().length() == 35;
        }
    }
}
