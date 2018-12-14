package cn.gmuni.enrollment.subject.controller;

import cn.gmuni.enrollment.subject.model.Subject;
import cn.gmuni.enrollment.subject.service.ISubjectService;
import cn.gmuni.maintenance.model.InfoContent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 学科管理控制类
 */
@Api(value = "/subject", description = "学科管理控制类")
@RestController
@RequestMapping(value = "subject")
public class SubjectController {

    @Autowired
    @Qualifier("subjectServiceImpl")
    ISubjectService subjectService;

    /**
     * 显示按学院分组的学科信息
     *
     * @return
     */
    @ApiOperation(value = "显示按学院分组的学科信息", notes = "返回二级的树形结构")
    @RequestMapping(value = "listAllSubject", method = RequestMethod.POST)
    @ResponseBody
    public Object listAllSubject() {
        return subjectService.listAllSubject();
    }

    /**
     * 同步学科信息
     *
     * @return
     */
    @ApiOperation(value = "同步学科信息", notes = "return {deleted:‘本次同步删除了多少个’,added:'本次同步添加的信息Subject对象列表'}")
    @RequestMapping(value = "syncSubjects", method = RequestMethod.POST)
    @ResponseBody
    public Object syncSubjects() {
        return subjectService.syncSubjects();
    }

    /**
     * 批量保存学科信息，用于同步之后的确定操作
     *
     * @return
     */
    @ApiOperation(value = "同步学科信息", notes = "return {flag：true or false}")
    @RequestMapping(value = "saveSubjectInfos", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveSubjectInfos(@RequestBody List<Subject> subjects) {
        return subjectService.saveSubjectInfos(subjects);
    }


    @ApiModelProperty(value="通过学科专业ID获取专业介绍",notes="return InfoContent")
    @PostMapping("/getArticleBySubId")
    @ResponseBody
    public InfoContent getArticleBySubId(@RequestBody  Map<String,String> params){
        return subjectService.getArticleBySubId(params.get("id"));
    }

}
