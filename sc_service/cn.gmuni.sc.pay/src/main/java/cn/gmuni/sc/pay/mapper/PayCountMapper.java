package cn.gmuni.sc.pay.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface PayCountMapper {

    /**
     * 获取网费统计模块的数据
     * @param params
     * @return
     */
    List<Map<String,Object>> getNetCount(Map<String,Object> params);

    /**
     * 获取网络缴费趋势图数据
     * @param params
     * @return
     */
    List<Map<String,Object>> getNetCostTrend(Map<String,Object> params);


    /**
     * 获取网络套餐类型对比数据
     * @param params
     * @return
     */
    List<Map<String,Object>> getSetMeal(Map<String,Object> params);

    /**
     * 获取支付记录
     * @param params
     * @return
     */
    List<Map<String,Object>> selectPay(Map<String,Object> params);

    /**
     * 获取支付详情
     * @param params
     * @return
     */
    List<Map<String,Object>> selectCount(Map<String,Object> params);


}
