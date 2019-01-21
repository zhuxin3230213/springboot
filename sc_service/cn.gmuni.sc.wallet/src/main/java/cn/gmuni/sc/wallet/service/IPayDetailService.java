package cn.gmuni.sc.wallet.service;

import cn.gmuni.sc.wallet.model.Wallet;

import java.util.List;
import java.util.Map;

public interface IPayDetailService {

    //列表展示充值数据记录
    List<Map<String,Object>> getPayDetailCount(Map<String, Object> params);

    //获取学校的充值金额
    List<Map<String,Object>> getShowChart(Map<String, Object> params);

    //获取个时间段每天的充值总额
    List<Map<String,Object>> getShowList(Map<String, Object> params);
   

}
