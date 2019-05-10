package com.sgx.spider.wenshu.docid;

import org.junit.Test;

import java.util.Objects;

public class DocIdDecryptorTest {
    @Test
    public void testDecrypt() throws Exception {
        String key = "631eba1a7e0349b2bdaceffb806d914c";
        String cipherText = "DcKMwrcRADAIw4RWIsKHEsKww5l/JMK7w7w/ScOHbMKMw6DDosOhIlTCo8OMBTbCuFF4DsOSwrXCnsKQDjg3w4vCiWPDicORYsKwwoDCisKIw6nDu0kxYw1LwrDCs8Oow6x7EMKmbFDCmcOHVicTw6XCg8OWw5/ClsKDfS/Cg8OEw40Nwo3CpnAHw6XDocK6UsKbwpLDhsKaP1/Dv0LCjSrDn1bCmcKwwo/DucOUwrJWw6bCoCHCnTYPbxHDll8XCsKuDcK7w67Din/Dp8OoeQ==";

        DocIdDecryptor decryptor = new DocIdDecryptor(key);
        try {
            String docId = decryptor.decryptDocId(cipherText);
            assert Objects.equals(docId, "13d4c01a-0734-4ec1-bbac-658f8bb8ec62");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
