package cn.gmuni.sc.integral.mapper;

import cn.gmuni.sc.integral.model.IntegralTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 14:19
 * @Description:
 */
@Service
@Mapper
public interface IntegralTaskMapper {

    int add(IntegralTask integralTask);
    int delete(String id);
    int update(IntegralTask integralTask);

    IntegralTask findIntegralTaskById(String id);
    //根据任务编码查询任务对象
    IntegralTask findIntegralTaskByCode(String code);
    List<Map<String,Object>> list(Map<String,String> params);
}
