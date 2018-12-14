package cn.gmuni.enrollment.guide.controller;

import cn.gmuni.enrollment.guide.model.YearlyScore;
import cn.gmuni.enrollment.guide.service.IYearlyScoreService;
import com.github.pagehelper.PageInfo;
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
 * 历年成绩管理控制类
 */
@Api(value = "/yearlyScore", description = "历年成绩管理控制类")
@RestController
@RequestMapping(value = "yearlyScore")
public class YearlyScoreController {
    @Autowired
    @Qualifier("yearlyScoreServiceImpl")
    IYearlyScoreService yearlyScoreService;

    /**
     * 分页查询历年成绩
     *
     * @return
     */
    @ApiOperation(value = "分页查询历年成绩")
    @RequestMapping(value = "getAllYearlyScore", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "provinces", value = "省份名称，不传则查询所有"),
            @ApiImplicitParam(name = "year", value = "年份四位数字，不传则查询所有"),
            @ApiImplicitParam(name = "family", value = "科目，不传则查询所有"),
            @ApiImplicitParam(name = "batch", value = "批次，不传则查询所有"),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true)})
    @ResponseBody
    public PageInfo<YearlyScore> getAllYearlyScore(@RequestBody @ApiIgnore Map<String, String> params) {
        return yearlyScoreService.getAllYearlyScore(params);
    }

    /**
     * 保存或者更新历年成绩信息列表
     *
     * @return
     */
    @ApiOperation(value = "保存或者更新历年成绩信息列表")
    @RequestMapping(value = "saveScores", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveScores(@RequestBody List<YearlyScore> scores) {
        return yearlyScoreService.saveScores(scores);
    }
    /**
     * 根据id删除
     *
     * @return
     */
    @ApiOperation(value = "根据id删除")
    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)
           })
    @ResponseBody
    public Map<String, Object> deleteById(@RequestBody @ApiIgnore Map<String, String> params) {
        return yearlyScoreService.deleteById(params);
    }

    /**
     * 修改单条信息
     *
     * @return
     */
    @ApiOperation(value = "修改单条信息")
    @RequestMapping(value = "updateScore", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateScore(@RequestBody YearlyScore score) {
        return yearlyScoreService.updateScore(score);
    }

    /**
     * 根据年份和省份查找可别和批次信息
     * @param params
     * @return
     */
    @ApiOperation(value = "根据年份和省份查找可别和批次信息")
    @RequestMapping(value = "listBAndC", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true),
            @ApiImplicitParam(name = "provinces", value = "省份", required = true)
    })
    @ResponseBody
    public List<Map<String, String>> listBAndC(@RequestBody @ApiIgnore Map<String, String> params){
        return yearlyScoreService.listBAndC(params);
    }
}
