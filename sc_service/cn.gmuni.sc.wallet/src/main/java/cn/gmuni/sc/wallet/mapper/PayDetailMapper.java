package cn.gmuni.sc.wallet.mapper;

import cn.gmuni.sc.wallet.model.Wallet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PayDetailMapper {

    //列表展示支付数据记录
    List<Map<String,Object>> getPayDetailCount(Map<String, Object> params);

    //获取学校的支付金额
    List<Map<String,Object>> getShowChart(Map<String, Object> params);

    //获取个时间段每天的收入总额
    List<Map<String,Object>> getShowList(Map<String, Object> params);


}
