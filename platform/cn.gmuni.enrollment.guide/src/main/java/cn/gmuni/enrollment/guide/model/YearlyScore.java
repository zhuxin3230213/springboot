package cn.gmuni.enrollment.guide.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "历年分数实体")
public class YearlyScore {
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "年份")
    private String year;
    @ApiModelProperty(value = "省份id")
    private String provinces;
    @ApiModelProperty(value = "批次：一批二批 ")
    private String batch;
    @ApiModelProperty(value = "科类：文、理、艺、体")
    private String family;
    @ApiModelProperty(value = "分数线")
    private double scoreline;
    @ApiModelProperty(value = "最高分")
    private double maxScore;
    @ApiModelProperty(value = "最低分")
    private double minScore;
    @ApiModelProperty(value = "平均分")
    private double avgScore;
    @ApiModelProperty(value = "排序号")
    private String sort;
    @ApiModelProperty(value = "预留:专业课")
    private String proCourse;
    @ApiModelProperty(value = "省份名称")
    private String areaName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public double getScoreline() {
        return scoreline;
    }

    public void setScoreline(double scoreline) {
        this.scoreline = scoreline;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public double getMinScore() {
        return minScore;
    }

    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getProCourse() {
        return proCourse;
    }

    public void setProCourse(String proCourse) {
        this.proCourse = proCourse;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
