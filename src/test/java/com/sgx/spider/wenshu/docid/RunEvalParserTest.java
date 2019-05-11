package com.sgx.spider.wenshu.docid;

import org.junit.Test;

import javax.script.ScriptException;
import java.util.Objects;

public class RunEvalParserTest {
    @Test
    public void testParse() throws ScriptException {
        String runEval = "w61aS27CgzAQPQtRFsK2wqh6AcKUVcKOw5DDpcOIQhVJGxYNwpVDV1HDrl7CoMKUw7IxCRTCm07DicKTw5BEw5jDs3tvw6zDgcKWwrI+w4TCu8O9KcOSw7F7wrp5SnV8fH18w5HDicObw7bDsMKswrfDiW4vfMOPJwXDgVo8woAEwojDu1ghSwByJ8ObFV0JwrXChQDDr2AWAsOVA2MoLsKIAWrCsAPDvMKgA8KcwoAEMAHDgMKADsKeAA7CkFABCCwEwoZCBkHCuFlFw4nDscKUw6rCjyhNw7QqCClUw5kjwoR/wr7DpGrDlXPCvkjDusKywqRMKMOlC8KvwpjCkMOUw7TCmU/DnMKeKV7Dlz/Dv04gwpEPZxrDpMOlM8KkKMO7aQVswqp7wqVBf8KwVsOeRsOdwqs5w5XCvcKZw4fCq1BFw5Zdwoo6KRgxXMOxXSU5wozDkcOaw6vDjcK0B2rCjFEbwq3DuzvCgwlWU03CrcOYw5tzYsOZE0d0w4Ztw6AowoJNw7zDpcOew7/DrmTCjRlzwrdwwoTDh2E9XGY8E1kzwoDDqCxewqdxOXbCgzHDpcOuw501wr0fw7pZE8KcMRgnw6DDtiI6w63DosO/wq/CiTJYYAwOQ8KWw4PDvlFOw7ZiL8K3wqktwrxdc0vCoMOMwqJ9w7YYdMOpZcOJwpfDocKOw5sBR8KNM8KEecK0w67CkCbDnMOSGcKRwod8amHCpcKQw4En";

        try {
            String key = RunEvalParser.parse(runEval);
            assert Objects.equals(key, "81983f68dc734c15a690de02e28f9742");
        } catch (IllegalArgumentException e) {
            System.out.println("无效RunEval，返回了脏数据: " + e);
        } catch (ScriptException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
