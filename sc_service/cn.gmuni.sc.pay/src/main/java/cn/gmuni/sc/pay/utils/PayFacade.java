package cn.gmuni.sc.pay.utils;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.sc.pay.service.impl.BaseNetworkFeeService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class PayFacade {

    public static final String NET_WORK_SERVICE_FLAG = "netWork";

    private static Map<String, BaseNetworkFeeService> services = new HashMap<>();

    @PostConstruct
    public void init(){
        services = ApplicationContextProvider.getApplicationContext().getBeansOfType(BaseNetworkFeeService.class);
    }

    public static BaseNetworkFeeService getService(String flag){
        for(Map.Entry<String, BaseNetworkFeeService> entry : services.entrySet()){
            if(flag.equals(entry.getValue().getFlag())){
                return entry.getValue();
            }
        }
        try {
            throw new Exception("接口找不到，请检查是否flag错误 ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
