package cn.gmuni.sc.pay.service.impl;

import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.college.cache.CollegeCache;
import cn.gmuni.sc.college.model.College;
import cn.gmuni.sc.pay.mapper.PayCountMapper;
import cn.gmuni.sc.pay.service.IPayCountService;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import org.sonatype.aether.impl.internal.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PayCountServiceImpl implements IPayCountService {

    @Autowired
    PayCountMapper payCountMapper;

    @Override
    public List<Map<String, Object>> getNetCount(Map<String, Object> params) {
        return payCountMapper.getNetCount(params);
    }

    @Override
    public List<Map<String, Object>> getNetCostTrend(Map<String, Object> params) {
        return payCountMapper.getNetCostTrend(params);
    }

    @Override
    public List<Map<String, Object>> getSetMeal(Map<String, Object> params) {
        return payCountMapper.getSetMeal(params);
    }

    @Override
    public List<Map<String, Object>> selectPay(Map<String, Object> params) {
        List<Map<String, Object>> list  = new ArrayList<>();
        String school = (String) params.get("schoolCode");
        if (school == null || school==""){
            List<College> schools = (List<College>) RedisCacheUtils.build().get(CollegeCache.COLLEGE_INFO_CACHE);
            if (schools == null  || schools.size() == 0){
                return list;
            }else {
                params.put("schoolCode",schools.get(0).getCode());
            }
        }
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return payCountMapper.selectPay(params);
    }

    @Override
    public List<Map<String, Object>> selectCount(Map<String, Object> params) {
        List<Map<String, Object>> list  = new ArrayList<>();
        String school = (String) params.get("schoolCode");
        if (school == null || school==""){
            List<College> schools = (List<College>) RedisCacheUtils.build().get(CollegeCache.COLLEGE_INFO_CACHE);
            if (schools == null  || schools.size() == 0){
                return list;
            }else {
                params.put("schoolCode",schools.get(0).getCode());
            }
        }
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return  payCountMapper.selectCount(params);
    }


}
