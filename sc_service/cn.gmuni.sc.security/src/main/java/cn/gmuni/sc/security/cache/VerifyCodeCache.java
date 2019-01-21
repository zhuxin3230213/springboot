package cn.gmuni.sc.security.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存验证码，五分钟有效
 */
public class VerifyCodeCache {

    //验证码五分钟内有效
    private static final int valTimes = 1000*60*5;

    private static Map<String,Verify> codes = new HashMap<>();

    public static void add(String phoneNumber,String code){
        codes.put(phoneNumber,new Verify(phoneNumber,code,new Date()));
    }

    public static boolean verify(String phoneNumber, String code){
        if(codes.get(phoneNumber)!=null){
            Verify v = codes.get(phoneNumber);
            if(v.code.equals(code) && new Date().getTime() - v.vaDate.getTime()<valTimes){
                codes.remove(phoneNumber);
                return true;
            }
        }
        return false;
    }

    static class Verify{
        String phoneNumber;

        String code;

        Date vaDate;

        public Verify(String phoneNumber, String code, Date vaDate) {
            this.phoneNumber = phoneNumber;
            this.code = code;
            this.vaDate = vaDate;
        }
    }

}
