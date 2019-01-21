package cn.gmuni.sc.express.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.express.model.Express;
import cn.gmuni.sc.express.service.ExpressService;
import cn.gmuni.sc.express.service.impl.Kd100QueryAPI;
import cn.gmuni.sc.express.service.impl.KdniaoTrackQueryAPI;
import cn.gmuni.sc.log.anntation.SysLog;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;
import java.util.Map;


/**
 * @Author: zhuxin
 * @Date: 2018/9/17 14:33
 * @Description:
 */
@Api(value = "/express",description = "快递查询")
@RestController
@RequestMapping(value = "/express")
public class ExpressController {

    @Autowired
    KdniaoTrackQueryAPI kdniaoTrackQueryAPI;

    @Autowired
    Kd100QueryAPI kd100QueryAPI;


    @Autowired
    @Qualifier("expressServiceImpl")
    ExpressService expressService;



    @ApiOperation(value = "添加信息")
    @PostMapping(value = "/add")
    public  BaseResponse<Express> add(@RequestBody Express express){
        return expressService.add(express);
    }

    @ApiOperation(value = "删除信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键",required = true)
    })
    @PostMapping(value = "/delete")
    public  BaseResponse<Map<String,String>> delete(@RequestBody @ApiIgnore Map<String,String> params){
        return expressService.delete(params);
    }

    @ApiOperation(value = "更改信息")
    @PostMapping(value = "/update")
    public  BaseResponse<Map<String,String>> update(@RequestBody Express express){
        return expressService.update(express);
    }

    @ApiOperation(value = "根据id查询对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键",required = true)
    })
    @PostMapping(value = "/findById")
    public BaseResponse<Express> findExpressById(@RequestBody @ApiIgnore Map<String,String> params){
       return expressService.findExpressById(params.get("id"));
    }

    @ApiOperation(value = "根据条件获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userInfo",value = "用户"),
            @ApiImplicitParam(name = "campus",value = "学校"),
            @ApiImplicitParam(name = "exp_no",value = "物流编码"),
            @ApiImplicitParam(name = "exp_code",value = "物流公司编码")
    })
    @PostMapping(value = "/list")
    public BaseResponse<List<Express>> listExpress(@RequestBody @ApiIgnore Map<String,String> params){
       return expressService.listExpress(params);
    }


    //快递100
    //单号识别物流公司获取物流信息
    //单号3711770019447,9893579277452 ,百世71248927189893，顺丰 256607779537 ，天天668913423887
    @ApiOperation(value = "快递100单号获取物流信息")
    @PostMapping(value = "/query")
    @SysLog(desc = "物流信息查询",module = SysLogModule.LIVE_LOG,type = SysLogType.VISIT_LOG)
    public  BaseResponse  queryLogisticsInfoByKuadi100(@RequestBody Map<String,String> map){
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("queryLogisticsInfoByKuadi100");
        try {
            response.setData(kd100QueryAPI.getOrderTracesByJson(map.get("expNo")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }


    //快递鸟
    //根据 expcode 快递公司编码与expNo 物流编码查询
    @ApiOperation(value = "快递鸟公司编码与物流编码查询信息")
    @PostMapping(value = "/information")
    @SysLog(desc = "物流信息查询",module = SysLogModule.LIVE_LOG,type = SysLogType.VISIT_LOG)
    public BaseResponse expressInformationQuery(@RequestBody Map<String,String> map){
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("information");
        try {
            response.setData(kdniaoTrackQueryAPI.getOrderTracesByJson(map.get("expCode"),map.get("expNo")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return  response;
    }

    //根据expNo 查询物流公司信息
    @ApiOperation(value = "快递鸟物流编码查询识别物流公司")
    @PostMapping(value ="/company")
    @SysLog(desc = "查询物流信息",module = SysLogModule.LIVE_LOG,type = SysLogType.VISIT_LOG)
    public BaseResponse expressByExpNo(@RequestBody Map<String,String> params){
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("company");
        try {
            String orderTracesByJson = kdniaoTrackQueryAPI.getOrderTracesByJson(params.get("expNo"));
            JSONObject jsonObject = JSONObject.parseObject(orderTracesByJson);
            Map map = jsonObject.toJavaObject(Map.class);
           /* System.out.println(map.get("LogisticCode")); //物流编码
            System.out.println(map.get("Shippers")); //物流公司
            System.out.println(map.get("EBusinessID")); //用户id*/
            Object shippers = map.get("Shippers");
            List<Map> maps = JSONArray.parseArray(JsonUtil.object2Json(shippers), Map.class);
            response.setData(maps);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  response;
    }

}
