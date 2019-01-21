package cn.gmuni.sc.pay.utils;

import cn.gmuni.sc.pay.config.IWXPayConfig;
import cn.gmuni.sc.pay.wxpay.sdk.WXPay;
import cn.gmuni.sc.pay.wxpay.sdk.WXPayConstants;
import cn.gmuni.sc.pay.wxpay.sdk.WXPayUtil;
import cn.gmuni.sc.utils.RequestUtils;

import java.util.HashMap;
import java.util.Map;

public class GmWXPayUtils {

    /**
     * 微信统一下单
     * @param data {
     *      notifi_url:通知url
     *      body:商品描述
     *      attach:附加数据
     *      total_fee:总金额，单位为分
     *
     * }
     * @return
     */
    public static Map<String,String> pay(Map<String,String> data) throws Exception {
        WXPay pay = new WXPay(new IWXPayConfig());
        //添加必要的信息
        pay.fillRequestData(data);
        //设备号
        data.put("device_info","WEB");
        //生成随机字符串
        data.put("nonce_str",WXPayUtil.generateNonceStr());
        if(data.get("body")==null){
            //商品描述
            data.put("body",getBody("网费充值"));
        }
        //订单号
        data.put("out_trade_no",PayUtils.getTradeNo());
        //货币类型
        data.put("fee_type","CNY");
        //终端ip
        data.put("spbill_create_ip", RequestUtils.getIpAddress(RequestUtils.getRequest()));
        //交易类型
        data.put("trade_type","MWEB");
        //场景信息
        data.put("scene_info","{\"h5_info\": {\"type\":\"Android\",\"app_name\": \"琛寰校园\",\"package_name\": \"cn.gmuni\"}}");
        //生成签名
        //TODO
        data.put("sign",generateSign(data,""));
        data.put("sign_type", WXPayConstants.HMACSHA256);
        //统一下单
        Map<String, String> response = pay.unifiedOrder(data);
        return response;
    }

    /**
     * 获取商品描述
     * @param name
     * @return
     */
    public static String getBody(String name){
        return "琛寰校园-"+name;
    }


    /**
     * 生成签名
     * @param data 数据
     * @param key 私钥
     * @return
     * @throws Exception
     */
    private static String generateSign(Map<String,String> data,String key) throws Exception {
        Map<String,String> mp = new HashMap<>();
        mp.put("appid",data.get("appid"));
        mp.put("body",data.get("body"));
        mp.put("device_info",data.get("device_info"));
        mp.put("mch_id",data.get("mch_id"));
        mp.put("nonce_str",data.get("nonce_str"));
        return WXPayUtil.generateSignature(data,key, WXPayConstants.SignType.HMACSHA256);
    }
}
