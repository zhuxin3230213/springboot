package cn.gmuni.sc.information.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.information.service.MessageService;
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

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: 消息控制层
 * @Date:Create 2018/11/22 10:15
 * @Modified By:
 **/
@Api(value = "/message", description = "消息")
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    @Qualifier("messageServiceImpl")
    MessageService messageService;


    @ApiOperation(value = "查询消息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "currentPage", name = "当前页", required = true),
            @ApiImplicitParam(value = "pageSize", name = "条目数", required = true),
            @ApiImplicitParam(value = "messageType", name = "信息类型", required = true)
    })
    @PostMapping(value = "/listMessage")
    public BaseResponse<List<Map<String, Object>>> listMessage(@RequestBody Map<String, String> params) {
        BaseResponse<List<Map<String, Object>>> res = new BaseResponse<>();
        List<Map<String, Object>> list = messageService.list(params);
        if (list.size() != 0) {
            res.setData(list);
        } else {
            res.setData(list);
        }
        return res;
    }


    @ApiOperation(value = "下拉刷新数据更新")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "currentPage", name = "当前页", required = true),
            @ApiImplicitParam(value = "pageSize", name = "条目数", required = true),
            @ApiImplicitParam(value = "messageType", name = "信息类型", required = true),
            @ApiImplicitParam(value = "initDataFirstTime", name = "列表最大时间", required = true)
    })
    @PostMapping(value = "/addMessageTop")
    public BaseResponse<List<Map<String, Object>>> addMessageTop(@RequestBody Map<String, String> params) {
        BaseResponse<List<Map<String, Object>>> res = new BaseResponse<>();
        List<Map<String, Object>> list = messageService.addMessageTop(params);
        if (list.size() != 0) {
            res.setData(list);
        } else {
            res.setData(list);
        }
        return res;
    }


    @ApiOperation(value = "上拉加载数据更新")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "currentPage", name = "当前页", required = true),
            @ApiImplicitParam(value = "pageSize", name = "条目数", required = true),
            @ApiImplicitParam(value = "messageType", name = "信息类型", required = true),
            @ApiImplicitParam(value = "initDataLastTime", name = "列表最大时间", required = true)
    })
    @PostMapping(value = "/addMessageBottom")
    public BaseResponse<List<Map<String, Object>>> addMessageBottom(@RequestBody Map<String, String> params) {
        BaseResponse<List<Map<String, Object>>> res = new BaseResponse<>();
        List<Map<String, Object>> list = messageService.addMessageBottom(params);
        if (list.size() != 0) {
            res.setData(list);
        } else {
            res.setData(list);
        }
        return res;
    }


    @ApiOperation(value = "更改阅读状态")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "id", name = "主键", required = true),
            @ApiImplicitParam(value = "type", name = "类型", required = true)
    })
    @PostMapping(value = "/updateState")
    public BaseResponse<Map<String, Object>> updateMessageState(@RequestBody Map<String, String> params) {
        BaseResponse<Map<String, Object>> res = new BaseResponse<>();
        Map<String, Object> map = messageService.updateMessageState(params);
        res.setData(map);
        return res;
    }


    @ApiOperation(value = "获取列表消息状态")
    @PostMapping(value = "/getMessageState")
    public BaseResponse<Map<String, Object>> getMessageState() {
        BaseResponse<Map<String, Object>> res = new BaseResponse<>();
        Map<String, Object> messageState = messageService.getMessageState();
        res.setData(messageState);
        return res;
    }

}
