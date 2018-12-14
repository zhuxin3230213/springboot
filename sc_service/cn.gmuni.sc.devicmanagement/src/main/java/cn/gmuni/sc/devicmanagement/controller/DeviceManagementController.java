package cn.gmuni.sc.devicmanagement.controller;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.devicmanagement.model.Device;
import cn.gmuni.sc.devicmanagement.service.IDeviceManagementService;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

import java.util.Arrays;
import java.util.Map;

@Api(value = "/device",description = "设备管理控制类")
@RequestMapping(value = "/device")
@RestController
public class DeviceManagementController {

    @Autowired
    @Qualifier("deviceManagementServiceImpl")
    IDeviceManagementService deviceManagementServiceImpl;

    @ApiOperation(value = "新增设备信息")
    @PostMapping(value = "/add")
    public Content add(@RequestBody Device device){
        return deviceManagementServiceImpl.addDevice(device);
    }

    @ApiOperation(value = "维护设备信息")
    @PostMapping(value = "/update")
    public Content update(@RequestBody Device device){
        return deviceManagementServiceImpl.updateDevice(device);
    }

    @ApiOperation(value = "删除设备信息")
    @PostMapping(value = "/del")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "所要删除的设备id",required = true)
    })
    public Content del(@RequestBody @ApiIgnore Map<String,Object> params){
        return deviceManagementServiceImpl.delDevice(Arrays.asList(String.valueOf(params.get("ids")).split(",")));
    }

    @ApiOperation(value = "查询设备信息")
    @PostMapping(value = "/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "设备编号"),
            @ApiImplicitParam(name = "type",value = "设备类型"),
            @ApiImplicitParam(name = "schoolCode",value = "所属学校编码"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    public PageInfo<Map<String,Object>> list(@RequestBody @ApiIgnore Map<String,Object> params){
        PageUtils page = PageUtils.getPage(params);
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(deviceManagementServiceImpl.listDevice(params));
    }

}
