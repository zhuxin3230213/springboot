package cn.gmuni.sc.express.service.impl;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.express.mapper.ExpressMapper;
import cn.gmuni.sc.express.model.Express;
import cn.gmuni.sc.express.service.ExpressService;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.log.utils.SysLogger;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/9/26 11:09
 * @Description:
 */
@Service
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    ExpressMapper expressMapper;

    @Override
    public BaseResponse<Express> add(Express express) {
        BaseResponse<Express> res = new BaseResponse<>();
        express.setCreateTime(new Date());
        express.setClickThrough(0);
        express.setId(IdGenerator.getId());
        express.setUpdateTime(new Date());
        int add = expressMapper.add(express);
        res.setData(express);
        SysLogger.info("添加物流信息",SysLogModule.LIVE_LOG,SysLogType.ADD_LOG);
        return res;
    }

    @Override
    public BaseResponse<Map<String, String>> delete(Map<String, String> params) {
        Map<String, String> map = new HashMap<>();
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        int i = expressMapper.delete(params.get("id"));
        map.put("flag", i > 0 ? "true" : "false");
        res.setData(map);
        return res;
    }

    @Override
    public BaseResponse<Map<String, String>> update(Express express) {
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        Map<String, String> map = new HashMap<>();
        int i = expressMapper.update(express);
        map.put("flag", i > 0 ? "true" : "false");
        res.setData(map);
        SysLogger.info("编辑物流信息",SysLogModule.LIVE_LOG,SysLogType.UPDATE_LOG);
        return res;
    }

    @Override
    public BaseResponse<Express> findExpressById(String id) {
        Express express = expressMapper.findExpressById(id);
        BaseResponse<Express> res = new BaseResponse<>();
        if(expressMapper != null){
            res.setData(express);
        }else{
            res.setCode(BaseResponse.ERROR_CODE);
            res.setMessage("数据查询失败");
        }
        return res;
    }

    @Override
    public BaseResponse<List<Express>> listExpress(Map<String, String> params) {
        List<Express> list = expressMapper.listExpress(params);
        BaseResponse<List<Express>> res = new BaseResponse<>();
        res.setData(list);
        return res;
    }
}
