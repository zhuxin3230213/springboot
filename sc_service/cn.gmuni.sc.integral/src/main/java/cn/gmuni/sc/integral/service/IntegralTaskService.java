package cn.gmuni.sc.integral.service;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.integral.model.IntegralTask;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 15:07
 * @Description:
 */
public interface IntegralTaskService {

    //根据任务编码查询任务对象
    BaseResponse<IntegralTask> findIntegralTaskByCode(String code);

    //根据任务编码获取积分
    BaseResponse<Map<String,Object>> integralTask(Map<String,String> params);
}
