package cn.gmuni.sc.integral.service;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.integral.model.IntegralStatistics;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 16:54
 * @Description:
 */
public interface IntegralStatisticsService {

    BaseResponse<IntegralStatistics> add(IntegralStatistics integralStatistics);

    BaseResponse<Map<String,String>> delete(Map<String,String> params);
    BaseResponse<Map<String,String>> update(IntegralStatistics integralStatistics);
    //根据id查询
    BaseResponse<Map<String,Object>> findIntegralStatisticsById(Map<String,String> params);
    //根据用户与任务编码查询
    BaseResponse<IntegralStatistics> findByUserInfoAndTaskCode(Map<String,String> params);

    BaseResponse<List<String>> list(Map<String,String> params);

}
