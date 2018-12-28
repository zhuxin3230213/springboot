package cn.gmuni.sc.blower.mapper;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BlowerMapper {
    //查询一卡余额
    Map<String, Object> selectOneCardJinE(Map<String, Object> params);

    //一卡通扣费
    int oneCardPayment(Map<String, Object> params);

    //一卡通扣费
    int oneCardrefund(Map<String, Object> params);

    //锁定吹风机
    int lockStatus(Map<String, Object> params);

    //初始化吹风机
    int initStatus(Map<String, Object> params);

    //查询吹风机状态
    Map<String,Object> getBlowerStatus(Map<String, Object> params);

    //更改吹风机状态
    int updateBlowerStatus(Map<String, Object> params);

    //查询设备详细信息
    Map<String,Object> selectDeviceByCode(String blowerCode);

    //查询支付宝交易信息
    Map<String,Object> selectALiBody(Map<String, Object> params);

}
