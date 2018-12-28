package com.gmunidata.security.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {

    /**
     * DES解密
     */
    /**
     * @param data 密文对应的字节数组
     * @param key  算法名字
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, "DES");
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
        }
        return null;
    }
}
