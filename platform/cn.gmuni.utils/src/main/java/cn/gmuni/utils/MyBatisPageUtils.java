package cn.gmuni.utils;

import java.util.HashMap;
import java.util.Map;

public class MyBatisPageUtils {
    public static Map<String,Integer> getPage(Map<String,Object> params){
        int cPage = 0;
        int size = 10;
        try {
            cPage = Integer.parseInt(params.get("page").toString()) - 1;
            size = Integer.parseInt(params.get("pageSize").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Integer> result = new HashMap<>();
        result.put("page",cPage);
        result.put("size",size);
        return result;
    }
}
