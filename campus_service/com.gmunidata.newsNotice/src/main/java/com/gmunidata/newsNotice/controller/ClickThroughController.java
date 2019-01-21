package com.gmunidata.newsNotice.controller;

import com.gmunidata.base.response.BaseResponse;
import com.gmunidata.newsNotice.service.IClickThroughService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/9/6 11:34
 * @Description:
 */
@Api(value = "/click",description = "新闻通知点击次数控制类")
@RestController
@RequestMapping(value = "/click")
public class ClickThroughController {

    @Autowired
    @Qualifier("clickThroughServiceImpl")
    IClickThroughService iClickThroughService;

    @ApiOperation(value = "更改点击次数并返回")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章的id",required = true),
            @ApiImplicitParam(name = "type",value = "文章类型",required = true)
    })
    @RequestMapping(value = "/updateClickThrough/{articleId}/{type}")
    public BaseResponse updateClickThrough(@PathVariable("articleId") String articleId,@PathVariable("type") String type){
        Map<String,Object> params = new HashMap<>();
        params.put("articleId",articleId);
        params.put("type",type);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("点击次数");
        response.setData(iClickThroughService.updateClickThrough(params));
        return response;
    }
}
