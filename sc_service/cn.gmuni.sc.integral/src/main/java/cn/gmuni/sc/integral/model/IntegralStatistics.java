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
 * @Date: 2018/10/30 11:23
 * @Description:
 *用户积分统计
 */
@ApiModel(value = "用户积分统计实体类")
public class IntegralStatistics implements Serializable {

    @ApiModelProperty(value = "id")
    private String id; //uuid

    @ApiModelProperty(value = "登录用户")
    private String userInfo; //登录用户

    @ApiModelProperty(value = "积分总数")
    private Integer integralTotal; //积分总数

    @ApiModelProperty(value = "剩余积分")
    private Integer integralRemaining; //剩余积分

    @ApiModelProperty(value = "签到计数:连续签到时加1，断签了就归1")
    private Integer countInte; //签到计数:连续签到时加1，断签了就归1

    @ApiModelProperty(value = "当前获取积分时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date currentTime; //当前获取积分时间

    @ApiModelProperty(value = "获取积分开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime; //获取积分开始时间

    @ApiModelProperty(value = "获取积分次数 （默认为0）")
    private Integer clickThrough; //获取积分次数 （默认为0）

    @ApiModelProperty(value = "所属任务编码(登录、签到、完善信息等)")
    private String taskCode; //所属任务编码(登录、签到、完善信息等)

    @ApiModelProperty(value = "任务类型")
    private String type; //任务类型（预留）

    @ApiModelProperty(value = "排序号码")
    private Integer sort; //排序号码

    public IntegralStatistics() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getIntegralTotal() {
        return integralTotal;
    }

    public void setIntegralTotal(Integer integralTotal) {
        this.integralTotal = integralTotal;
    }

    public Integer getIntegralRemaining() {
        return integralRemaining;
    }

    public void setIntegralRemaining(Integer integralRemaining) {
        this.integralRemaining = integralRemaining;
    }

    public Integer getCountInte() {
        return countInte;
    }

    public void setCountInte(Integer countInte) {
        this.countInte = countInte;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getClickThrough() {
        return clickThrough;
    }

    public void setClickThrough(Integer clickThrough) {
        this.clickThrough = clickThrough;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "IntegralStatistics{" +
                "id='" + id + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", integralTotal=" + integralTotal +
                ", integralRemaining=" + integralRemaining +
                ", countInte=" + countInte +
                ", currentTime=" + currentTime +
                ", createTime=" + createTime +
                ", clickThrough=" + clickThrough +
                ", taskCode='" + taskCode + '\'' +
                ", type='" + type + '\'' +
                ", sort=" + sort +
                '}';
    }
}
