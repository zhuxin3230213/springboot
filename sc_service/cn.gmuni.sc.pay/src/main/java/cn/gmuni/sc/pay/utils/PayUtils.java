package cn.gmuni.sc.pay.utils;

import cn.gmuni.utils.IdGenerator;

import java.util.Date;

public class PayUtils {

    /**
     * 获取订单号
     * @return
     */
    public static String getTradeNo(){
        return "gm" + new Date().getTime() + IdGenerator.getId().substring(0, 6);
    }
}
