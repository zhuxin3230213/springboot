package cn.gmuni.sc.device.controller;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.sc.http.http.HttpAPIService;
import cn.gmuni.sc.http.http.HttpResult;

import java.util.Map;

public class BlowerDervice {

    //给发送信息
    public void requestDevice(String action, Map<String, Object> maps){
        String url = (String) maps.get("url");
        try {
            HttpAPIService httpAPIService = (HttpAPIService) ApplicationContextProvider.getBean("httpAPIService");
            HttpResult result = httpAPIService.doPost(url+"/blower/"+action,maps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
