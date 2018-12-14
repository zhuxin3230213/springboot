package cn.gmuni.sc.integral.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.integral.model.IntegralStatistics;
import cn.gmuni.sc.integral.model.IntegralTask;
import cn.gmuni.sc.integral.service.IntegralDetailService;
import cn.gmuni.sc.integral.service.IntegralStatisticsService;
import cn.gmuni.sc.integral.service.IntegralTaskService;
import cn.gmuni.sc.log.anntation.SysLog;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 15:17
 * @Description:
 */
@Api(value = "/integral", description = "积分查询")
@RestController
@RequestMapping(value = "/integral")
public class IntegralController {

    @Autowired
    @Qualifier("integralTaskServiceImpl")
    IntegralTaskService integralTaskService;

    @Autowired
    @Qualifier("integralStatisticsServiceImpl")
    IntegralStatisticsService integralStatisticsService;

    @Autowired
    @Qualifier("integralDetailServiceImpl")
    IntegralDetailService integralDetailService;

    @ApiOperation(value = "根据任务编码获取任务对象并添加积分详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskCode", value = "任务编码", required = true)
    })
    @PostMapping(value = "/task")
    @SysLog(desc = "积分任务查询", module = SysLogModule.LIVE_LOG, type = SysLogType.VISIT_LOG)
    public BaseResponse<Map<String, Object>> integralTask(@RequestBody Map<String, String> params) {
        return integralTaskService.integralTask(params);
    }

    @ApiOperation(value = "根据任务编码、用户去积分统计表中查询此对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskCode", value = "任务编码", required = true),
            @ApiImplicitParam(name = "currentTime", value = "当天时间", required = true)

    })
    @PostMapping(value = "/StatisticsByTaskcode")
    @SysLog(desc = "积分统计查询", module = SysLogModule.LIVE_LOG, type = SysLogType.VISIT_LOG)
    public BaseResponse<Map<String, Object>> findIntegralStatisticsByTaskCodeAndUserInfo(@RequestBody Map<String, String> params) {
        BaseResponse<Map<String, Object>> res = new BaseResponse<>();
        Map<String, Object> map = new HashMap<>();
        BaseResponse<IntegralStatistics> byUserInfoAndTaskCode = integralStatisticsService.findByUserInfoAndTaskCode(params);
        IntegralStatistics data = byUserInfoAndTaskCode.getData();
        if (data != null) {
            map.put("integralStatistics", data);
            //获取签到排名
            //根据今天时间与任务编码获取统计列表，并按cu_time升序排列
            Date date = new Date();
            Map<String, String> map1 = new HashMap<>();
            map1.put("currentTime", DateUtils.date2String(date, DateUtils.LONG_DATE));
            map1.put("taskCode", params.get("taskCode"));
            BaseResponse<List<String>> response2 = integralStatisticsService.list(map1);
            List<String> datal = response2.getData();
            boolean flag = datal.contains(data.getId());
            if (flag) {
                int ranking = datal.indexOf(data.getId()); //获取签到对象在集合升序中位置
                map.put("ranking", ranking + 1); //排名
            }
            res.setData(map);
            return res;
        } else {
            res.setData(null);
            return res;
        }

    }

    //根就任务编码获取任务积分integral
    @ApiOperation(value = "根就任务编码获取任务积分integral")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskCode", value = "任务编码", required = true)
    })
    @PostMapping(value = "/findTaskByCode")
    @SysLog(desc = "积分任务查询", module = SysLogModule.LIVE_LOG, type = SysLogType.VISIT_LOG)
    public BaseResponse<Map<String, Object>> findTaskByCode(@RequestBody Map<String, String> params) {
        BaseResponse<IntegralTask> response = integralTaskService.findIntegralTaskByCode(params.get("taskCode"));
        IntegralTask data = response.getData();
        BaseResponse<Map<String, Object>> res = new BaseResponse<>();
        Map<String, Object> map = new HashMap<>();
        map.put("integralTask", data);
        res.setData(map);
        return res;
    }

    //根据任务编码\用户、学校与年月日查询
    @ApiOperation(value = " 根据任务编码、用户、学校与年月日查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskCode", value = "任务编码", required = true),
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "month", value = "月", required = true),
            @ApiImplicitParam(name = "day", value = "日", required = true)
    })
    @PostMapping(value = "/listByTaskCode")
    public BaseResponse<Map<String, Object>> listByTaskCode(@RequestBody Map<String, String> params) {
        return integralDetailService.listByTaskCode(params);
    }


    //根据任务编码、用户、学校、年月查询本月列表
    @ApiOperation(value = "根据任务编码、用户、学校、年月查询本月列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskCode", value = "任务编码", required = true),
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "month", value = "月", required = true)
    })
    @PostMapping(value = "/listByMonth")
    public BaseResponse<List<Map<String, Object>>> listByMonth(@RequestBody Map<String, String> params) {
        return integralDetailService.listByMonth(params);
    }


}
