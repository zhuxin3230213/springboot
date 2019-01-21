package cn.gmuni.sc.utils;

import cn.gmuni.utils.IdGenerator;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundApplyModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 支付宝支付工具类
 */
public class AliPayUtil {
    private static final String APPID = "2018110161928966";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    private static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    private static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    private static final String RSA2_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCY4INF/MZQi65QFji+tPD9w1FSCPMZ2EHlbbxdmRHJ2cCgWSAriz5iLU5v1pS7hnJIr8Xd24GS5lsCHG4RmVBFpeLQYNiATw+7E5StpzqzIILQp2iqdLXm+eEzwoZO+5tgCa3zjn/Q/164qinMOFlY5lPW/eK5XNEZ2w7tytpSoxucduUHwsfFme2Sz5nsq4M2iX+Yfzev8JHWQzMswSgWcvlm9pTuoz01P6qtzpPn/6tEvBU2sPYPDVnu0DBosT/WDT1bKvjLWiRFcAaQ/luYrklz9Ko8WPd+OzpV9PHB+JZ7jHbYVvcFgFm/PGqlshP7IqOcVkt6ALELJr3yJ8obAgMBAAECggEAEzgOaMsJZ3DX/it9AMSgk1Ztr8F+Rl6AkkZEawPmT5j3dXEe8Tp1IkSN2tOAKT8SuP6YAncpB/27rT1DvSSoCcl8BTRXo3HNCXjBtT8TyxcoOvEA4IUBpUL+w9INrnpFI0K0Qq1EdlLUZqTF0m23Fc0G9z09to3rn7OBYfWhksfK2mocGFKt7ML4GwaL55ZCHTNT1Jpbgr/Bv4ltmiuyWl8hnYrPWVo08Vpf3PjZmvTXU8mYk69JgewkwOed73GG1luJjmjmFEk3bNN8X6gnJkSS+FY9Zn6p07wo+7ftB90RRZOxDsmlaOUdsUPIdLKry808ramIzp8+Ihk/iCsjqQKBgQDL1V4rlQwt8sbPJIUMhOdIf6CZ2eBtqdKyy1H7SdDp2P3J4lgLyyIZVV0yab5nIkOsPfDhPNnqfzkH8sCoeXl3ZTqalJ8OzmldTnrV/FBiiLkaWle+Jc0Kb4fklbvsledMVv2v0l+xhSsNxTFl6dNwYLfeajX4I0viG+ohSVX7NQKBgQDAAJyLwlttwAeSc+0PR/GqXz4gUSYSau+m8vsAZ6LzkpRJ8GzLEV9TLe1OnnWzChQLb4n43mhsf8awmLZ8AEMNpBxPQaN7dczl+RLLgAAh8I2Fc385zoU0WsusQiLvnWoGumr2jFRLS36yeHWKg4heXFWwUZhnOEEiyVk1nNYKDwKBgAcrRPmhb2E6VbW5YhiToBLTslFW8WbJ/0gckJCXAwsGf76XAGPBzDw53WJFh9XWxayMLSmu2zc9LENUTWRfiV8ip1ICgimiJuCKPxXBnw4oIuKDZOyiJbkFPTFT9t0WVKFBugByFL2IooAEfH1KIQ6lyDHBaI6Gp0eXlb9/9c81AoGAMA13IHtKCpOu0XZ19AcbcnpXwBrPHnjo9a2GKDnHzJH4zvB/Ket1IgoyhCSgbGivT5VgwIpZNB5eicAOm8YtAfL6qtNQqQt4dm35yDiGz/e2U5nuU3deWxYGN1xuBe4tpPVu47mHNzjzwpLnR/1XWdZeE7y4Gc04cA5YZ1STNP0CgYB1TeG7yHnLv0a1rLVvSpBdtR9sOgw4cfBuZZgZZeg4WbMfhVejjMoO84rF4XsNd3FlCmAg2D50IhsKSjw6DB38rvfj7N4Cz54bcCRSWqIctNTupP7dcoUHYc8cMcpPCY7lohd/LuCjeBwc6CvNokzH3nZpecV4A6voEj6yH9c9Jg==";
    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmOCDRfzGUIuuUBY4vrTw/cNRUgjzGdhB5W28XZkRydnAoFkgK4s+Yi1Ob9aUu4ZySK/F3duBkuZbAhxuEZlQRaXi0GDYgE8PuxOUrac6syCC0KdoqnS15vnhM8KGTvubYAmt845/0P9euKopzDhZWOZT1v3iuVzRGdsO7craUqMbnHblB8LHxZntks+Z7KuDNol/mH83r/CR1kMzLMEoFnL5ZvaU7qM9NT+qrc6T5/+rRLwVNrD2Dw1Z7tAwaLE/1g09Wyr4y1okRXAGkP5bmK5Jc/SqPFj3fjs6VfTxwfiWe4x22Fb3BYBZvzxqpbIT+yKjnFZLegCxCya98ifKGwIDAQAB";
    private static final String ALI_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs3ovg/r/Q96rcHyw8EM2g52VqM/rMk3Ng2+d2/3AMHDUi5gZBX7UFeuWGmkUOJNLSwPuISTITIYfykU4yd1NfKRbNYuz1VO9eAUI49Twe5nceCtSia29RgcHD8ZyeAuRdy8hlLxycjEv3Yc5+6e1oa7wQ5B/QIruvRNV6vLyyEckKv7ko+a20NA3r7ohA/oMK4Gg1B/5jltscmwv7il3BwXzf4cb4fmvI4E96MlWDipqqq2b1tAYr0l9mUcNAj2pmN2bNDUzyZ8PkIcZPYYw4WBDl3bXTQbeB2DKie68gW420HNewKatNlxWeRXhyFlznyDj6N056x4bEw4B30WBMQIDAQAB";
    /**
     * 支付超时时间
     */
    private static final String TIMEOUT_EXPRESS = "5m";

    /**
     * 支付宝缴费
     * @param response
     * @param backParam 返回值参数，例如用户的缴费账号等信息可以传入
     * @param productCode 产品编码  例如：NET40  代表网费40
     * @param returnUrl 返回的url，指支付成功用户页面跳转的Url
     * @param notifyUrl 通知的url，用户支付成功之后通知第三方调用的URL，为post请求
     * @param totalAmount 支付金额
     * @param subject 标题
     * @param body 描述
     */
    public static void doPay(HttpServletResponse response, String backParam, String productCode, String returnUrl, String notifyUrl, String totalAmount, String subject, String body) {
        try {
            AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APPID, RSA2_PRIVATE, "json", "GBK", PUBLIC_KEY, "RSA2");
            AlipayTradeWapPayRequest alirequest = new AlipayTradeWapPayRequest();
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            String tradeNo = "gm" + new Date().getTime() + IdGenerator.getId().substring(0, 6);
            model.setOutTradeNo(tradeNo);
            model.setSubject(subject);
            model.setTotalAmount(totalAmount);
            model.setBody(body);
            model.setTimeoutExpress(TIMEOUT_EXPRESS);
            model.setPassbackParams(URLEncoder.encode(backParam, "UTF-8"));
            model.setProductCode(productCode);
            alirequest.setNotifyUrl(notifyUrl);
            alirequest.setReturnUrl(returnUrl);
            alirequest.setBizModel(model);
            // 设置异步通知地址
            AlipayTradeWapPayResponse aliresponse = alipayClient.pageExecute(alirequest);
            if (aliresponse.isSuccess()) {
                response.setContentType("text/html;charset=GBK");
                response.getWriter().write(aliresponse.getBody());// 直接将完整的表单html输出到页面
                response.getWriter().flush();
                response.getWriter().close();
            } else {
                System.out.println("调用失败");
            }
        } catch (Exception e) {

        }
    }

    //

    /**
     * 支付宝退款接口
     * @param out_trade_no 原支付请求的商户订单号
     * @param trade_no 支付宝交易号
     * @param refund_amount 退款金额
     */
    public  static boolean  execute(String out_trade_no,String trade_no,String refund_amount) throws AlipayApiException {
        System.out.println("进入退款");
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APPID, RSA2_PRIVATE, "json", "GBK", ALI_PUBLIC_KEY, "RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(out_trade_no);
        model.setTradeNo(trade_no);
        model.setRefundAmount(refund_amount);
        model.setRefundCurrency("CNY");
        model.setRefundReason("正常退款");
        request.setBizModel(model);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
           return true;
        } else {
           return false;
        }
    }

}
