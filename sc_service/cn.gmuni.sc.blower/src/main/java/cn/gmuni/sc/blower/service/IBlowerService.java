package cn.gmuni.sc.blower.service;

import java.util.Map;

public interface IBlowerService {

    //查询吹风机状态
    Map<String,Object> getBlowerStatus(Map<String, Object> params);

    //取消吹风机锁定
    Map<String,Object> cancelBlowerLock(Map<String, Object> params);

    //中断吹风机使用
    Map<String,Object> endBlowerStatus(Map<String, Object> params);

    Map<String,Object> blowerPayByCard(Map<String, Object> params);


    boolean saveBlowerPayInfo(Map<String, String[]> payInfo);
}
