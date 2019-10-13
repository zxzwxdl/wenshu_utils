package com.songguoxiong.wenshu.utils.old.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private final static String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private final static String ALGORITHM = "AES";

    public static byte[] decrypt(byte[] input, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(input);
    }

    public static String decryptToString(byte[] input, byte[] key, byte[] iv) throws Exception {
        return new String(decrypt(input, key, iv));
    }
}
