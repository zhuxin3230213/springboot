package cn.gmuni.sc.wallet.mapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PayConsumeMapper {

    //列表展示消费数据记录
    List<Map<String,Object>> getPayConsumeCount(Map<String, Object> params);

    //按学校获取消费数据
    List<Map<String,Object>> showConsumeCount(Map<String, Object> params);

    //按类型获取消费数据
    List<Map<String,Object>> getCountByType(Map<String, Object> params);

    //时间段获取人数和订单数
    List<Map<String,Object>> getDayPeopleAndOrder(Map<String, Object> params);


}
