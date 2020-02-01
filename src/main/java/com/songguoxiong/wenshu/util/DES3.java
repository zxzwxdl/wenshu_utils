package com.songguoxiong.wenshu.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DES3 {
    private static final String TRANSFORMATION = "DESEde/CBC/PKCS5Padding";
    private static final String ALGORITHM = "DESEde";

    public static String encrypt(String plainText, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }

    public static String decrypt(String cipherText, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
    }
}
