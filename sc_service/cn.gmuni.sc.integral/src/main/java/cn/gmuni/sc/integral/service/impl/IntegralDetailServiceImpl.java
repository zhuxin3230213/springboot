package cn.gmuni.sc.integral.service.impl;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.integral.mapper.IntegralDetailMapper;
import cn.gmuni.sc.integral.model.IntegralDetail;
import cn.gmuni.sc.integral.service.IntegralDetailService;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.DateUtils;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 15:58
 * @Description:
 */
@Service
public class IntegralDetailServiceImpl implements IntegralDetailService {

    @Autowired
    IntegralDetailMapper integralDetailMapper;

    @Override
    public BaseResponse<IntegralDetail> add(IntegralDetail integralDetail) {
        BaseResponse<IntegralDetail> res = new BaseResponse<>();
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        if(!StringUtils.isEmpty(loginUser.getSchool()) && !StringUtils.isEmpty(loginUserInfo.getIndentifier())){
            integralDetail.setId(IdGenerator.getId());
            integralDetail.setCampus(loginUser.getSchool()); //获取学校
            integralDetail.setCreateTime(new Date());
            integralDetail.setUpdateTime(new Date());
            integralDetail.setState("1");
            integralDetail.setUserInfo(loginUserInfo.getIndentifier());  //获取登录用户
            integralDetail.setYear(DateUtils.getCurrYear());  //获取当前年份
            integralDetail.setMonth(DateUtils.getCurrMonth()); //获取当前月份
            integralDetail.setDay(DateUtils.getCurrDay()); //获取当天
            int add = integralDetailMapper.add(integralDetail);
            res.setData(integralDetail);
            res.setCode(BaseResponse.SUCCESS_CODE);
        }else {
            res.setCode(BaseResponse.ERROR_CODE);
            res.setMessage("数据添加失败");
        }
        return res;
    }

    @Override
    public BaseResponse<Map<String, String>> delete(Map<String, String> params) {
        int i = integralDetailMapper.delete(params.get("id"));
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        Map<String, String> map = new HashMap<>();
        map.put("flag", i > 0 ? "true" : "false");
        res.setData(map);
        return res;
    }

    @Override
    public BaseResponse<Map<String, String>> update(IntegralDetail integralDetail) {
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        int i = integralDetailMapper.update(integralDetail);
        Map<String, String> map = new HashMap<>();
        map.put("flag", i > 0 ? "true" : "false");
        res.setData(map);
        return res;
    }

    @Override
    public BaseResponse<Map<String, Object>> findById(Map<String, String> params) {
        BaseResponse<Map<String,Object>> res = new BaseResponse<>();
        Map<String, Object> map = integralDetailMapper.findIntegralDetailById(params.get("id"));
        if (integralDetailMapper != null){
            res.setData(map);
        }else {
            res.setCode(BaseResponse.ERROR_CODE);
            res.setMessage("数据查询失败");
        }
        return res;
    }

    @Override
    public BaseResponse<List<Map<String, Object>>> list(Map<String, String> params) {
        List<Map<String, Object>> list = integralDetailMapper.list(params);
        BaseResponse<List<Map<String,Object>>> res = new BaseResponse<>();
        res.setData(list);
        return res;
    }

    @Override
    public BaseResponse<Map<String, Object>> listByTaskCode(Map<String, String> params) {
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();

        Map<String,Object> map = new HashMap<>();
        map.put("taskCode",params.get("taskCode"));
        map.put("userInfo",loginUserInfo.getIndentifier());
        map.put("campus",loginUser.getSchool());
        map.put("year",Integer.parseInt(params.get("year")));
        map.put("month",Integer.parseInt(params.get("month")));
        map.put("day",Integer.parseInt(params.get("day")));

        BaseResponse<Map<String,Object>> res = new BaseResponse<>();
        res.setData(integralDetailMapper.listByTaskCode(map));
        return res;
    }

    @Override
    public BaseResponse<List<Map<String, Object>>> listByMonth(Map<String, String> params) {
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();

        Map<String,Object> map = new HashMap<>();
        map.put("taskCode",params.get("taskCode"));
        map.put("userInfo",loginUserInfo.getIndentifier());
        map.put("campus",loginUser.getSchool());
        map.put("year",Integer.parseInt(params.get("year")));
        map.put("month",Integer.parseInt(params.get("month")));

        BaseResponse<List<Map<String,Object>>> res = new BaseResponse<>();
        res.setData(integralDetailMapper.listByMonth(map));
        return res;
    }
}
