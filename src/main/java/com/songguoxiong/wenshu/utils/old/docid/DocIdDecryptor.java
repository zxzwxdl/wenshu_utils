package com.songguoxiong.wenshu.utils.old.docid;

import com.songguoxiong.wenshu.utils.old.common.AES;
import com.songguoxiong.wenshu.utils.old.common.StringUtils;

public class DocIdDecryptor {
    private final static byte[] IV = "abcd134556abcedf".getBytes();
    private final byte[] key;

    public DocIdDecryptor(String key) {
        this(key.getBytes());
    }

    public DocIdDecryptor(byte[] key) {
        this.key = key;
    }

    public String decrypt(String cipherText) throws Exception {
        String plainText = ZlibUtil.unzip(cipherText);
        plainText = AES.decryptToString(StringUtils.hexToBytes(plainText), key, IV);
        plainText = AES.decryptToString(StringUtils.hexToBytes(plainText), key, IV);
        return plainText;
    }
}