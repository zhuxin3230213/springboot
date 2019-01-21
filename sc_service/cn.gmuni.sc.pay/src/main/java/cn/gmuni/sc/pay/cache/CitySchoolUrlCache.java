package cn.gmuni.sc.pay.cache;

import cn.gmuni.sc.pay.mapper.NetWorkPayMapper;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CitySchoolUrlCache {
    @Autowired
    NetWorkPayMapper mapper;
    private static Map<String, String> m;

    public static String getUrlBySchoolCode(String schoolCode){
        String url = m.get(schoolCode);
        if(StringUtils.isEmpty(url)){
            try{
                throw new Exception("编号为：" + schoolCode + " 的学校未配置缴费地址。");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return url;
    }
    @PostConstruct
    private void initMap(){
        m = new HashMap<>();
        List<Map<String, String>> urlMap = mapper.getUrlMap();
        if(null != urlMap){
            for (int i = 0; i < urlMap.size(); i++) {
                Map<String, String> map = urlMap.get(i);
                if(StringUtils.isEmpty(map.get("url"))){
                    System.out.println(map.get("name") + "未配置缴费地址字段信息，字段列名为：col1,服务未启动");
                    System.exit(0);
                }
                m.put(map.get("code"), map.get("url"));
            }
        }
    }
}
