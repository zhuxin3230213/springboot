package cn.gmuni.sc.express.mapper;

import cn.gmuni.sc.express.model.Express;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/9/26 10:15
 * @Description:
 */
@Service
@Mapper
public interface ExpressMapper {

    int add(Express express);
    int delete(String id);
    int update(Express express);

    Express findExpressById(String id);

    List<Express> listExpress(Map<String,String> params);
}
