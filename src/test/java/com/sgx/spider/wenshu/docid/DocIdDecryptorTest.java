package com.sgx.spider.wenshu.docid;

import org.junit.Test;

public class DocIdDecryptorTest {
    @Test
    public void testDecrypt() {
        String key = "631eba1a7e0349b2bdaceffb806d914c";
        String cipherText = "DcKMwrcRADAIw4RWIsKHEsKww5l/JMK7w7w/ScOHbMKMw6DDosOhIlTCo8OMBTbCuFF4DsOSwrXCnsKQDjg3w4vCiWPDicORYsKwwoDCisKIw6nDu0kxYw1LwrDCs8Oow6x7EMKmbFDCmcOHVicTw6XCg8OWw5/ClsKDfS/Cg8OEw40Nwo3CpnAHw6XDocK6UsKbwpLDhsKaP1/Dv0LCjSrDn1bCmcKwwo/DucOUwrJWw6bCoCHCnTYPbxHDll8XCsKuDcK7w67Din/Dp8OoeQ==";

        DocIdDecryptor decryptor = new DocIdDecryptor(key);
        //13d4c01a-0734-4ec1-bbac-658f8bb8ec62
        String docId = null;
        try {
            docId = decryptor.decryptDocId(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(docId);
    }
}
