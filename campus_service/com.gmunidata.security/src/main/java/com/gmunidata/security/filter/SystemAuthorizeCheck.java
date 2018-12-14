package com.gmunidata.security.filter;

import cn.gmuni.utils.AESUtil;
import cn.gmuni.utils.DESUtil;
import cn.gmuni.utils.MacUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class SystemAuthorizeCheck {

    @Value("${gmuni.platform.authkey}")
    private String authorizeKey;

    @PostConstruct
    public void init() {
        Map<String, Object> config = null;
        try{
            config = getAuthorizeConfigFromAuthorizeKey(authorizeKey);
        }catch (Exception e){
            System.out.println("授权码格式错误");
            System.exit(0);
        }
        try{
            boolean useMac = (boolean)config.get("useMac");
            if(useMac){
                String macAuthorize = config.get("mac").toString();
                String curMac = MacUtils.getMac();
                if(!curMac.equalsIgnoreCase(macAuthorize)){
                    System.out.println("未授权的机器");
                    System.exit(0);
                }
            }
            boolean userLimitUser = (boolean)config.get("userLimitUser");
            if(userLimitUser){
                UserCodeCache.authorizedUserNum = (int)config.get("limitUser");
                System.out.println("系统授权登录人数为" + config.get("limitUser"));
            }else{
                UserCodeCache.authorizedUserNum = Integer.MAX_VALUE;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String timeLine = config.get("timeLine").toString();
            System.out.println("系统授权日期为：" + timeLine);
            Date limitTime = sdf.parse(timeLine);
            if(limitTime.before(new Date())){
                System.out.println("系统授权已到期");
                System.exit(0);
            }
        }catch (Exception e){
            System.out.println("配置信息有误");
            System.exit(0);
        }
    }
    private static Map getAuthorizeConfigFromAuthorizeKey(String key) throws Exception{
        String k1 = "1234567887654321";
        String k2 = "12345678";
        byte[] desDecryptResult = DESUtil.decrypt(Base64.getDecoder().decode(key.getBytes()),k2.getBytes());
        //再进行aes解密
        String result = new String(AESUtil.decrypt(desDecryptResult, k1.getBytes()));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(result,Map.class);
    }

}
