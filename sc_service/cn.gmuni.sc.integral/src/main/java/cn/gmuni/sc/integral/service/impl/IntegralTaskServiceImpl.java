package cn.gmuni.sc.integral.service.impl;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.integral.mapper.IntegralTaskMapper;
import cn.gmuni.sc.integral.model.IntegralDetail;
import cn.gmuni.sc.integral.model.IntegralStatistics;
import cn.gmuni.sc.integral.model.IntegralTask;
import cn.gmuni.sc.integral.service.IntegralDetailService;
import cn.gmuni.sc.integral.service.IntegralStatisticsService;
import cn.gmuni.sc.integral.service.IntegralTaskService;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.log.utils.SysLogger;
import cn.gmuni.sc.utils.DateUtils;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 15:08
 * @Description:
 */
@Service
public class IntegralTaskServiceImpl implements IntegralTaskService {

    @Autowired
    IntegralTaskMapper integralTaskMapper;

    @Autowired
    @Qualifier("integralDetailServiceImpl")
    IntegralDetailService integralDetailService;

    @Autowired
    @Qualifier("integralStatisticsServiceImpl")
    IntegralStatisticsService integralStatisticsService;

    @Override
    public BaseResponse<IntegralTask> findIntegralTaskByCode(String code) {
        IntegralTask integralTask = integralTaskMapper.findIntegralTaskByCode(code);
        BaseResponse<IntegralTask> res = new BaseResponse();
        if (integralTaskMapper != null) {
            res.setData(integralTask);
        } else {
            res.setCode(BaseResponse.ERROR_CODE);
            res.setMessage("数据查询失败");
        }
        return res;
    }

    @Override
    public BaseResponse<Map<String, Object>> integralTask(Map<String, String> params) {
        Map<String, Object> integralTask = new HashMap<>();
        BaseResponse<Map<String, Object>> res = new BaseResponse<>();
        BaseResponse<IntegralTask> response = findIntegralTaskByCode(params.get("taskCode"));
        //获取积分任务
        IntegralTask task = response.getData();
        if (task != null) {
            if ("1".equals(task.getTaskStatus())) {
                //1代表任务生效，0代表失效
                //可以获取积分(添加积分详情)
                IntegralDetail detail = new IntegralDetail();
                detail.setIntegral(task.getIntegralSet());
                detail.setTaskCode(task.getTaskCode());
                BaseResponse<IntegralDetail> integralDetailBaseResponse = integralDetailService.add(detail);
                if (BaseResponse.ERROR_CODE != integralDetailBaseResponse.getCode()) {
                    //积分详情添加成功
                    SysLogger.info("添加积分详情", SysLogModule.LIVE_LOG, SysLogType.ADD_LOG);
                    IntegralDetail integralDetail = integralDetailBaseResponse.getData();
                    //根据登录用户与任务编码先从用户行为积分统计表中查询，看是否存在当前任务的统计
                    if (!StringUtils.isEmpty(integralDetail.getUserInfo()) && !StringUtils.isEmpty(integralDetail.getTaskCode())) {
                        Map<String, String> map = new HashMap<>();
                        map.put("taskCode", integralDetail.getTaskCode());
                        BaseResponse<IntegralStatistics> response1 = integralStatisticsService.findByUserInfoAndTaskCode(map);
                        IntegralStatistics integralStatistics = response1.getData(); //获取统计对象
                        if (integralStatistics == null) { //从统计表中查询出来为空时
                            integralStatistics = new IntegralStatistics();
                            integralStatistics.setUserInfo(integralDetail.getUserInfo());
                            integralStatistics.setTaskCode(integralDetail.getTaskCode());
                            integralStatistics.setIntegralTotal(Integer.valueOf(integralDetail.getIntegral()));
                            integralStatistics.setIntegralRemaining(Integer.valueOf(integralDetail.getIntegral()));
                            integralStatisticsService.add(integralStatistics); //添加积分统计
                            SysLogger.info("添加积分统计详情", SysLogModule.LIVE_LOG, SysLogType.ADD_LOG);
                            integralTask.put("integralStatistics", integralStatistics);
                        } else {
                            integralStatistics.setIntegralTotal(integralStatistics.getIntegralTotal() + Integer.valueOf(integralDetail.getIntegral()));
                            integralStatistics.setIntegralRemaining(integralStatistics.getIntegralRemaining() + Integer.valueOf(integralDetail.getIntegral()));
                            //判断当前的连续状态
                            String current = DateUtils.date2String(integralStatistics.getCurrentTime(), DateUtils.COMMON_DATETIME).substring(0, 10);
                            //获取昨天日期
                            Date date = new Date();
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            calendar.add(calendar.DATE, -1);
                            date = calendar.getTime();
                            String next = DateUtils.date2String(date, DateUtils.COMMON_DATETIME).substring(0, 10);
                            //签到计数:连续签到时加1，断签了就归1
                            if (current.equals(next)) {
                                integralStatistics.setCountInte(integralStatistics.getCountInte() + 1);
                            } else {
                                integralStatistics.setCountInte(1);
                            }
                            integralStatistics.setClickThrough(integralStatistics.getClickThrough() + 1);
                            integralStatistics.setCurrentTime(new Date());
                            integralStatisticsService.update(integralStatistics);
                            SysLogger.info("编辑积分统计", SysLogModule.LIVE_LOG, SysLogType.UPDATE_LOG);
                            integralTask.put("integralStatistics", integralStatistics); //保存签到统计
                        }

                        //根据今天时间与任务编码获取统计列表，并按cu_time升序排列
                        if (!StringUtils.isEmpty(integralStatistics.getTaskCode())) {
                            Date date = new Date();
                            Map<String, String> map1 = new HashMap<>();
                            map1.put("currentTime", DateUtils.date2String(date, DateUtils.LONG_DATE));
                            map1.put("taskCode", integralStatistics.getTaskCode());
                            BaseResponse<List<String>> response2 = integralStatisticsService.list(map1);
                            List<String> data = response2.getData();
                            if (data.size() != 0) {
                                //今天有人签到
                                boolean flag = data.contains(integralStatistics.getId());
                                if (flag) {
                                    int ranking = data.indexOf(integralStatistics.getId()); //获取签到对象在集合升序中位置
                                    integralTask.put("ranking", ranking + 1); //排名
                                }
                                integralTask.put("integralDetail", integralDetail); //签到详情
                                res.setData(integralTask); //返回app所需内容
                            }
                        }

                    }
                }

            }

        }
        return res;
    }
}
