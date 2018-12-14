package cn.gmuni.sc.express.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhuxin
 * @Date: 2018/9/26 09:42
 * @Description: 物流信息实体类
 */
@ApiModel(value = "物流信息实体类")
public class Express implements Serializable {

    @ApiModelProperty(value = "id")
    private String id; //uuid

    @ApiModelProperty(value = "电商id")
    private String bussinessId; //电商id

    @ApiModelProperty(value = "订单编号")
    private String orderCode; //订单编号

    @ApiModelProperty(value = "物流单号")
    private String expNo; //物流单号

    @ApiModelProperty(value = "物流公司名称")
    private String expName; //物流公司名称

    @ApiModelProperty(value = "物流公司编码")
    private String expCode; //物流公司编码

    @ApiModelProperty(value = "物流公司logo")
    private String expLogo; //物流公司logo

    @ApiModelProperty(value = "物流轨迹详情")
    private String information; //物流详情Traces

    @ApiModelProperty(value = "物流状态 0： 无轨迹   1：已揽收  2：在途中 3：签收 4：问题件")
    private String state; //物流状态 0： 无轨迹   1：已揽收  2：在途中 3：签收 4：问题件

    @ApiModelProperty(value = "当前用户")
    private String userInfo; //当前用户

    @ApiModelProperty(value = "当前用户所属校园")
    private String campus; //当前用户所属校园

    @ApiModelProperty(value = "查询次数")
    private Integer clickThrough; //查询次数

    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime; //添加时间

    @ApiModelProperty(value = "更改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime; //更改时间

    public Express() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getExpNo() {
        return expNo;
    }

    public void setExpNo(String expNo) {
        this.expNo = expNo;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpCode() {
        return expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
    }

    public String getExpLogo() {
        return expLogo;
    }

    public void setExpLogo(String expLogo) {
        this.expLogo = expLogo;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Integer getClickThrough() {
        return clickThrough;
    }

    public void setClickThrough(Integer clickThrough) {
        this.clickThrough = clickThrough;
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

    @Override
    public String toString() {
        return "Express{" +
                "id='" + id + '\'' +
                ", bussinessId='" + bussinessId + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", expNo='" + expNo + '\'' +
                ", expName='" + expName + '\'' +
                ", expCode='" + expCode + '\'' +
                ", expLogo='" + expLogo + '\'' +
                ", information='" + information + '\'' +
                ", state='" + state + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", campus='" + campus + '\'' +
                ", clickThrough=" + clickThrough +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
