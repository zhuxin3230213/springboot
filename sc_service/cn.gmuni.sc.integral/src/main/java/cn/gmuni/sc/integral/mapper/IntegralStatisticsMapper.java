package cn.gmuni.sc.integral.mapper;

import cn.gmuni.sc.integral.model.IntegralStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 14:22
 * @Description:
 */
@Service
@Mapper
public interface IntegralStatisticsMapper {

    int add(IntegralStatistics integralStatistics);
    int delete(String id);
    int update(IntegralStatistics integralStatistics);

    Map<String,Object> findIntegralStatisticsById(String id);
    //根据用户和任务编码获取信息
    IntegralStatistics findByUserInfoAndTaskCode(Map<String,String> params);

    List<String> list(Map<String,String> params);
}
