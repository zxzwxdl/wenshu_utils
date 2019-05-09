package com.sgx.spider.wenshu.docid;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DocIdDecryptor {
    private final static String algorithm = "AES/CBC/PKCS5Padding";
    private final static String keyAlgorithm = "AES";
    private final static byte[] iv = "abcd134556abcedf".getBytes();

    private final byte[] key;

    public DocIdDecryptor(String key) {
        this.key = key.getBytes();
    }

    public DocIdDecryptor(byte[] key) {
        this.key = key;
    }

    public String decryptDocId(String cipherText) throws Exception {
        String plainText = ZlibUtil.unzip(cipherText);
        plainText = decrypt(plainText, key);
        plainText = decrypt(plainText, key);
        return plainText;
    }

    private String decrypt(String data, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKeySpec secretKey = new SecretKeySpec(key, keyAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return new String(cipher.doFinal(hexStringToBytes(data)));
    }

    private byte[] hexStringToBytes(String s) {
        int len = s.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) (
                    (Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16)
            );
        }
        return bytes;
    }
}
