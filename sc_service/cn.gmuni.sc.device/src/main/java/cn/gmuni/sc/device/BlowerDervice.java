package cn.gmuni.sc.device;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.sc.http.http.HttpAPIService;
import cn.gmuni.sc.http.http.HttpResult;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class BlowerDervice {

    //给发送信息
    public Map<String, String> requestDevice(String action, Map<String, Object> maps) {
        Map<String, String> map = new HashMap<>();
        String url = (String) maps.get("url");
        try {
              HttpAPIService httpAPIService = (HttpAPIService) ApplicationContextProvider.getBean("httpAPIService");
              HttpResult result = httpAPIService.doPost(url + "/blower/" + action, maps);
              map = JsonUtil.json2Object(result.getBody(), Map.class);
            }catch (Exception e) {
              map.put("flag", "false");
              map.put("msg", "无法链接设备!");
              return map;
        }
        return map;
    }


}
