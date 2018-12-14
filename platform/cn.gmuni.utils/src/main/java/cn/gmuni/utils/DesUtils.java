package cn.gmuni.utils;

//import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class DesUtils<main> {

    private static final String CODE_TYPE = "UTF-8";

    /**
     * 加密
     *
     * @param datasource 加密对象
     * @param key        盐
     * @return
     */
    public static String encode(String datasource, String key) {
        try {
            key = processSalt(key);
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes(CODE_TYPE));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密

            byte[] temp = Base64.getEncoder().encode(cipher.doFinal(datasource.getBytes()));
            return new String(temp, "UTF-8");
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String processSalt(String salt) {
        if (salt.length() >= 16) {
            return salt.substring(0, 16);
        }
        for (int i = salt.length(); i < 16; i++) {
            salt += "1";
        }
        return salt;
    }

    /**
     * 解密
     * @param src 解密对象
     * @param key 盐
     * @return
     */
    public static String decode(String src, String key) {
        // DES算法要求有一个可信任的随机数源
        try {
            key = processSalt(key);
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(key.getBytes(CODE_TYPE));
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 真正开始解密操作
            return new String(cipher.doFinal(Base64.getDecoder().decode(src)), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args){
        String k = encode("11111","1111");
        System.out.println(decode(k,"1111"));
    }
}
