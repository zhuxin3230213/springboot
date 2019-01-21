package cn.gmuni.sc.lost.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.log.anntation.SysLog;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.log.utils.SysLogger;
import cn.gmuni.sc.lost.model.LostFound;
import cn.gmuni.sc.lost.service.ILostFoundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
@Api(value = "/lostfound",description = "失物招领控制类")
@RestController
@RequestMapping(value = "lostfound")
public class LostFoundController {

    @Autowired
    @Qualifier("lostFoundServiceImpl")
    ILostFoundService lostFoundService;

    @ApiOperation(value = "发布信息")
    @RequestMapping(value = "add",method = RequestMethod.POST)
    @SysLog(desc = "发布失物招领信息",module = SysLogModule.LIVE_LOG,type = SysLogType.ADD_LOG)
    public BaseResponse<LostFound> add(@RequestBody LostFound info, HttpServletRequest request){
        return lostFoundService.add(info, request);
    }

    @ApiOperation(value = "删除信息")
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @SysLog(desc = "删除失物招领信息",module = SysLogModule.LIVE_LOG,type = SysLogType.REMOVE_LOG)
    public BaseResponse<Map<String, String>> delete(@RequestBody @ApiIgnore Map<String, String> params){
        return lostFoundService.delete(params);
    }
    @ApiOperation(value = "招领完成")
    @RequestMapping(value = "finish",method = RequestMethod.POST)
    @SysLog(desc = "标记招领信息完成",module = SysLogModule.LIVE_LOG,type = SysLogType.UPDATE_LOG)
    public BaseResponse<Map<String, String>> finish(@RequestBody @ApiIgnore Map<String, String> params){
        return lostFoundService.finish(params);
    }

    @ApiOperation(value = "编辑信息")
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @SysLog(desc = "编辑失物招领信息",module = SysLogModule.LIVE_LOG,type = SysLogType.UPDATE_LOG)
    public BaseResponse<Map<String, String>> edit(LostFound info){
        return lostFoundService.edit(info);
    }

    @ApiOperation(value = "列表查询")
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public BaseResponse<List<LostFound>> list(@RequestBody @ApiIgnore Map<String,Object> params){
        String logDesc = "查询失物招领信息列表";
        if(!params.containsKey("lfType")){
            if(params.containsKey("isMine") && "isMine".equals(params.get("isMine"))){
                logDesc += "--我的";
            }else{
                logDesc += "--全部";
            }
        }else if("1".equals(params.get("lfType"))){
            logDesc += "--捡到";
        }else if("1".equals(params.get("lfType"))){
            logDesc += "--丢失";
        }
        SysLogger.info(logDesc,SysLogModule.LIVE_LOG,SysLogType.VISIT_LOG);
        return lostFoundService.list(params);
    }
    @ApiOperation(value = "查询具体")
    @RequestMapping(value = "getOne",method = RequestMethod.POST)
    public BaseResponse<LostFound> getOne(@RequestBody @ApiIgnore Map<String,String> params){
        SysLogger.info("查询ID为：" + params.get("id") + "的失物招领信息",SysLogModule.LIVE_LOG,SysLogType.VISIT_LOG);
        return lostFoundService.get(params.get("id"));
    }
}
