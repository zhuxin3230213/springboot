package cn.gmuni.sc.integral.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 11:22
 * @Description: 获取积分详情
 */
@ApiModel(value = "积分详情实体类")
public class IntegralDetail implements Serializable {

    @ApiModelProperty(value = "id")
    private String id; //uuid

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime; //创建时间

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime; //修改时间

    @ApiModelProperty(value = "当前年份")
    private Integer year; //年份

    @ApiModelProperty(value = "当前月份")
    private Integer month; //月份

    @ApiModelProperty(value = "当天")
    private Integer day; //天

    @ApiModelProperty(value = "积分状态: 0:未获取   1:获取")
    private String state; //积分状态: 0:未获取   1:获取

    @ApiModelProperty(value = "获取的积分")
    private String integral; //获取的积分

    @ApiModelProperty(value = "所属登录用户")
    private String userInfo; //所属登录用户

    @ApiModelProperty(value = "所属学校")
    private String campus; //所属学校

    @ApiModelProperty(value = "积分任务编码")
    private String taskCode; //任务编码

    @ApiModelProperty(value = "积分类型")
    private String type;  //积分类型

    @ApiModelProperty(value = "预留属性")
    private String ext; //预留属性

    @ApiModelProperty(value = "排序号码")
    private Integer sort; //排序号码

    public IntegralDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "IntegralDetail{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", state='" + state + '\'' +
                ", integral='" + integral + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", campus='" + campus + '\'' +
                ", taskCode='" + taskCode + '\'' +
                ", type='" + type + '\'' +
                ", ext='" + ext + '\'' +
                ", sort=" + sort +
                '}';
    }
}
