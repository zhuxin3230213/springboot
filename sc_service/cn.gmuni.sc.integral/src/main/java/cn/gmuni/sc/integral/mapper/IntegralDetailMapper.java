package cn.gmuni.sc.integral.mapper;

import cn.gmuni.sc.integral.model.IntegralDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 14:21
 * @Description:
 */
@Service
@Mapper
public interface IntegralDetailMapper {
    int add(IntegralDetail integralDetail);
    int delete(String id);
    int update(IntegralDetail integralDetail);

    Map<String,Object> findIntegralDetailById(String id);

    List<Map<String,Object>>  list(Map<String,String> params);

    //根据任务编码\用户、学校与年月日查询
    Map<String,Object> listByTaskCode(Map<String,Object> params);
    //根据任务编码、用户、学校、年月查询本月列表
    List<Map<String,Object>> listByMonth(Map<String,Object> params);
}
