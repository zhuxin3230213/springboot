package cn.gmuni.sc.menu.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.menu.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api(value = "/menu", description = "菜单按钮管理控制类")
@RestController
@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    @Qualifier("menuServiceImpl")
    IMenuService menuService;

    @ApiOperation(value = "获取模块菜单")
    @RequestMapping(value = "getItems", method = RequestMethod.POST)
    public BaseResponse<Map<String, Object>> getMenuItem(@RequestBody @ApiIgnore Map<String, String> params) {
        return menuService.getMenuItem(params);
    }
}
