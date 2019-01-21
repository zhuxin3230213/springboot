package cn.gmuni.sc.integral.service.impl;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.integral.mapper.IntegralStatisticsMapper;
import cn.gmuni.sc.integral.model.IntegralStatistics;
import cn.gmuni.sc.integral.service.IntegralStatisticsService;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.DateUtils;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 16:54
 * @Description:
 */
@Service
public class IntegralStatisticsServiceImpl implements IntegralStatisticsService {

    @Autowired
    IntegralStatisticsMapper integralStatisticsMapper;

    @Override
    public BaseResponse<IntegralStatistics> add(IntegralStatistics integralStatistics) {
        BaseResponse<IntegralStatistics> res = new BaseResponse<>();

        integralStatistics.setId(IdGenerator.getId());
        integralStatistics.setCreateTime(new Date());
        integralStatistics.setCurrentTime(new Date());
        integralStatistics.setCountInte(1);
        integralStatistics.setClickThrough(1);

        int add = integralStatisticsMapper.add(integralStatistics);
        res.setData(integralStatistics);
        return res;
    }

    @Override
    public BaseResponse<Map<String, String>> delete(Map<String, String> params) {
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        int i = integralStatisticsMapper.delete(params.get("id"));
        Map<String, String> map = new HashMap<>();
        map.put("flag", i > 0 ? "true" : "false");
        res.setData(map);
        return res;
    }

    @Override
    public BaseResponse<Map<String, String>> update(IntegralStatistics integralStatistics) {
        int i = integralStatisticsMapper.update(integralStatistics);
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        Map<String, String> map = new HashMap<>();
        map.put("flag", i > 0 ? "true" : "false");
        res.setData(map);
        return res;
    }

    @Override
    public BaseResponse<Map<String, Object>> findIntegralStatisticsById(Map<String, String> params) {
        Map<String, Object> map = integralStatisticsMapper.findIntegralStatisticsById(params.get("id"));
        BaseResponse<Map<String, Object>> res = new BaseResponse<>();
        if (integralStatisticsMapper != null) {
            res.setData(map);
        } else {
            res.setCode(BaseResponse.ERROR_CODE);
            res.setMessage("数据查询失败");
        }
        return res;
    }

    @Override
    public BaseResponse<IntegralStatistics> findByUserInfoAndTaskCode(Map<String, String> params) {
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();

        Map<String, String> map = new HashMap<>();
        map.put("userInfo",loginUserInfo.getIndentifier());
        map.put("taskCode", params.get("taskCode"));
        map.put("currentTime",params.get("currentTime"));
        IntegralStatistics byUserInfoAndTaskCode = integralStatisticsMapper.findByUserInfoAndTaskCode(map);
        BaseResponse<IntegralStatistics> res = new BaseResponse<>();
        if (integralStatisticsMapper != null) {
            res.setData(byUserInfoAndTaskCode);
        } else {
            res.setCode(BaseResponse.ERROR_CODE);
            res.setMessage("数据查询失败");
        }
        return res;
    }

    @Override
    public BaseResponse<List<String>> list(Map<String, String> params) {
        List<String> list = integralStatisticsMapper.list(params);
        BaseResponse<List<String>> res = new BaseResponse<>();
        res.setData(list);
        return res;
    }

}
