package cn.gmuni.sc.express.service;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.express.model.Express;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/9/26 11:08
 * @Description:
 */
public interface ExpressService {

    BaseResponse<Express> add(Express express);
    BaseResponse<Map<String,String>> delete(Map<String,String> params);
    BaseResponse<Map<String,String>> update(Express express);

    BaseResponse<Express> findExpressById(String id);
    BaseResponse<List<Express>> listExpress(Map<String,String> params);

}
