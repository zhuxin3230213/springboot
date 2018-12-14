package cn.gmuni.enrollment.subject.model;

import cn.gmuni.maintenance.model.InfoContent;
import cn.gmuni.org.model.Department;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Transient;

@ApiModel(value = "学科信息类")
public class Subject {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "对应组织机构id")
    private String deptId;
    @ApiModelProperty(value = "类型：文理艺体，从字典表获取")
    private String type;
    @ApiModelProperty(value = "状态,0招生,1当年不招生")
    private String status;
    @ApiModelProperty(value = "排序号")
    private String sort;
    @Transient
    @ApiModelProperty(value = "对应组织机构信息")
    private Department dept;
    @ApiModelProperty(value = "描述信息")
    private String introduction;
    @Transient
    @ApiModelProperty(value = "学院名称")
    private String academyName;

    @Transient
    @ApiModelProperty(value = "对应学科专业介绍信息")
    private InfoContent article;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public InfoContent getArticle() {
        return article;
    }

    public void setArticle(InfoContent article) {
        this.article = article;
    }
}
