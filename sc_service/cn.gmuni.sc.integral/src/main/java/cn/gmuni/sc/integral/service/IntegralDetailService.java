package cn.gmuni.sc.integral.service;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.integral.model.IntegralDetail;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 15:58
 * @Description:
 */
public interface IntegralDetailService {

    BaseResponse<IntegralDetail> add(IntegralDetail integralDetail);
    BaseResponse<Map<String,String>> delete(Map<String,String> params);
    BaseResponse<Map<String,String>> update(IntegralDetail integralDetail);

    BaseResponse<Map<String,Object>> findById(Map<String,String> params);

    BaseResponse<List<Map<String,Object>>> list(Map<String,String> params);

    //根据任务编码\用户、学校与年月日查询
    BaseResponse<Map<String,Object>> listByTaskCode(Map<String,String> params);
    //根据任务编码、用户、学校、年月查询本月列表
    BaseResponse<List<Map<String,Object>>> listByMonth(Map<String,String> params);
}
