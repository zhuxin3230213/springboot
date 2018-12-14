package cn.gmuni.sc.integral.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhuxin
 * @Date: 2018/10/30 11:22
 * @Description: 积分任务管理
 */
@ApiModel(value = "积分任务管理实体类")
public class IntegralTask implements Serializable {


    @ApiModelProperty(value = "id")
    private String id;  //uuid

    @ApiModelProperty(value = "任务名称")
    private String taskName; //任务名称

    @ApiModelProperty(value = "任务编码")
    private String taskCode; //任务编码

    @ApiModelProperty(value = "积分设置")
    private String integralSet; //积分设置

    @ApiModelProperty(value = "任务期： 长期、自定义")
    private String taskValidity;  //任务期： 长期、自定义

    @ApiModelProperty(value = "任务状态: 0：未生效 1:生效")
    private String taskStatus; //任务状态: 0：未生效 1:生效

    @ApiModelProperty(value = "任务限制次数：0为不限")
    private Integer taskLimit; //任务限制次数：0为不限

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime; //创建时间

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime; //更新时间

    @ApiModelProperty(value = "任务简介")
    private String taskIntroduction; //任务简介

    @ApiModelProperty(value = "任务内容")
    private String taskContent; //任务内容

    @ApiModelProperty(value = "任务发布人")
    private String userInfo; //发布人

    @ApiModelProperty(value = "发布人所属学校")
    private String campus; //所属学校

    @ApiModelProperty(value = "预留属性")
    private String taskType; //预留属性

    @ApiModelProperty(value = "排序号")
    private Integer sort; //排序号码

    public IntegralTask() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getIntegralSet() {
        return integralSet;
    }

    public void setIntegralSet(String integralSet) {
        this.integralSet = integralSet;
    }

    public String getTaskValidity() {
        return taskValidity;
    }

    public void setTaskValidity(String taskValidity) {
        this.taskValidity = taskValidity;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskLimit() {
        return taskLimit;
    }

    public void setTaskLimit(Integer taskLimit) {
        this.taskLimit = taskLimit;
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

    public String getTaskIntroduction() {
        return taskIntroduction;
    }

    public void setTaskIntroduction(String taskIntroduction) {
        this.taskIntroduction = taskIntroduction;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
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

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "IntegralTask{" +
                "id='" + id + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskCode='" + taskCode + '\'' +
                ", integralSet='" + integralSet + '\'' +
                ", taskValidity='" + taskValidity + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskLimit=" + taskLimit +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", taskIntroduction='" + taskIntroduction + '\'' +
                ", taskContent='" + taskContent + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", campus='" + campus + '\'' +
                ", taskType='" + taskType + '\'' +
                ", sort=" + sort +
                '}';
    }
}
