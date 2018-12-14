package cn.gmuni.enrollment.config.controller;

import cn.gmuni.enrollment.config.model.Config;
import cn.gmuni.enrollment.config.service.IContactUsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 联系我们控制类
 */
@Api(value = "contactUs", description = "联系我们控制类")
@RestController
@RequestMapping("contactUs")
public class ContactUsController {
    @Autowired
    @Qualifier("contactUsServiceImpl")
    IContactUsService contactUsService;

    /**
     * 查询联系我们配置列表
     * @return
     */
    @ApiOperation(value = "查询联系我们配置列表")
    @RequestMapping(value = "getContactUsConfigs", method = RequestMethod.POST)
    @ResponseBody
    public List<Config> getContactUsConfigs(){
        return contactUsService.getContactUsConfigs();
    }
    /**
     * 检查code是否存在
     * @param params
     * @return
     */
    @ApiOperation(value = "检查code是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true)})
    @RequestMapping(value = "checkCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkCode(@RequestBody @ApiIgnore Map<String, String> params){
        return contactUsService.checkCode(params);
    }

    /**
     * 保存配置
     * @param config
     * @return
     */
    @ApiOperation(value = "保存配置")
    @RequestMapping(value = "saveConfig", method = RequestMethod.POST)
    @ResponseBody
    public Config saveConfig(@RequestBody Config config){
        return contactUsService.saveConfig(config);
    }

    /**
     * 修改配置
     * @param config
     * @return
     */
    @ApiOperation(value = "修改配置")
    @RequestMapping(value = "updateConfig", method = RequestMethod.POST)
    @ResponseBody
    public Config updateConfig(@RequestBody Config config){
        return contactUsService.updateConfig(config);
    }
    /**
     * 修改配置——批量
     * @param configs
     * @return
     */
    @ApiOperation(value = "修改配置——批量")
    @RequestMapping(value = "updateConfigs", method = RequestMethod.POST)
    @ResponseBody
    public List<Config> updateConfigs(@RequestBody List<Config> configs){
        return contactUsService.updateConfigs(configs);
    }
    /**
     * 根据id删除信息
     * @param params
     * @return
     */
    @ApiOperation(value = "根据id删除信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)})
    @RequestMapping(value = "deleteConfig", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteConfig(@RequestBody @ApiIgnore Map<String, String> params){
        return contactUsService.deleteConfig(params);
    }
}
