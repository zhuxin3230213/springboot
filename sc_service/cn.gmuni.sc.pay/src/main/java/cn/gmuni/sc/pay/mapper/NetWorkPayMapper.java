package cn.gmuni.sc.pay.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface NetWorkPayMapper {
    /**
     * 保存支付宝支付结果信息
     * @return
     */
    int saveAliPayInfo(Map<String, Object> params);

    /**
     * 保存网络缴费信息
     * @param params
     * @return
     */
    int saveNetWorkPayInfo(Map<String, Object> params);

    /**
     * 更新缴费信息以及缴费结果原因
     * @param params
     * @return
     */
    int updateNetWorkPayStatusAndReason(Map<String, Object> params);

    /**
     * 查询当前的支付宝通知id是否已处理
     * @param notifyId
     * @return
     */
    int getByNotifyId(@Param("notifyId") String notifyId);

    List<Map<String, Object>> getPayDetails(@Param("schoolCode") String schoolCode, @Param("stuCode") String stuCode);

    /**
     * 获取各个学校对应的热点的缴费地址
     */
    List<Map<String, String>> getUrlMap();
}
