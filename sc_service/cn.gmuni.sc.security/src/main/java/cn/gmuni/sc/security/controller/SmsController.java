package cn.gmuni.sc.security.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.http.http.HttpAPIService;
import cn.gmuni.sc.http.http.HttpResult;
import cn.gmuni.sc.security.cache.VerifyCodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SmsController {

    @Autowired
    HttpAPIService httpAPIService;

    //短信模块ID
    private final int tplId = 104585;

    //返回数据格式
    private final String dType = "json";

    private final String appKey = "5bf0d9cae2096742606dbc447a952976";

    private Logger logger = LoggerFactory.getLogger(SmsController.class);


    /**
     * 发送验证码
     */
    @PostMapping("sendSms")
    @ResponseBody
    public BaseResponse<Map<String,Object>> sendSms(@RequestParam("phoneNumber") String phoneNumber){
        String code = (Math.random()+"").substring(2,8);
        logger.info("验证码: "+code);
        VerifyCodeCache.add(phoneNumber,code);
        boolean send = true;
        if(send){
            Map<String,Object> result = new HashMap<>();
            result.put("phoneNumber",phoneNumber);
            return new BaseResponse(result);
        }else{
            return new BaseResponse(BaseResponse.ERROR_CODE,"验证码发送失败");
        }

    }


    /**
     * 发送短信验证码
     * @param phoneNumber
     * @param code
     * @return
     */
    private boolean send(String phoneNumber,String code){
        Map<String,Object> params = new HashMap<>();

        try {
            logger.info("#code#="+code+"&#app#=琛寰校园");
            params.put("mobile",phoneNumber);
            params.put("tpl_id",tplId);
            params.put("tpl_value", URLEncoder.encode("#code#="+code+"&#app#=琛寰校园","utf-8"));
            params.put("key",appKey);
            params.put("dtype",dType);
            HttpResult httpResult = httpAPIService.doPost("http://v.juhe.cn/sms/send", params);
            if(httpResult.getCode()!=200){
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
