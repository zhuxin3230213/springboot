package cn.gmuni.sc.information.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:ZhuXin
 * @Description: 消息
 * @Date:Create 2018/11/22 9:21
 * @Modified By:
 **/
@ApiModel(value = "推送消息实体类")
public class MessageModel implements Serializable {

    @ApiModelProperty(value = "uuid")
    private String id; //uuid

    @ApiModelProperty(value = "标题")
    private String title; //标题

    @ApiModelProperty(value = "正文")
    private String content; //正文

    @ApiModelProperty(value = "发布者")
    private String publisher; //发布者

    @ApiModelProperty(value = "发布人部门")
    private String deptName; //发布人部门

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date publishTime; //发布时间或者交易时间

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime; //创建时间

    @ApiModelProperty(value = "登录用户indentifier")
    private String userInfo; //登录用户(indentifier)

    @ApiModelProperty(value = "学号")
    private String studentCode; //学号

    @ApiModelProperty(value = "学校编码")
    private String schoolCode; //学校编码

    @ApiModelProperty(value = "消息类型 ： 0 ：通知 1：网费")
    private String type; //信息类型： 0 ：通知 1：网费

    @ApiModelProperty(value = "阅读状态：默认为 0：未读  1:已读 ")
    private String state; //阅读状态: 默认为 0：未读  1:已读

    @ApiModelProperty(value = "缴费订单号")
    private String tradeNo; //缴费订单号

    @ApiModelProperty(value = "出账金额")
    private String totalAmount; //出账金额

    @ApiModelProperty(value = "上网账号")
    private String netId; //上网账号

    @ApiModelProperty(value = "费用类型： 0:支出 1:收入")
    private String netType; //费用类型: 0:支出 1:收入


    public MessageModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publisher='" + publisher + '\'' +
                ", deptName='" + deptName + '\'' +
                ", publishTime=" + publishTime +
                ", createTime=" + createTime +
                ", userInfo='" + userInfo + '\'' +
                ", studentCode='" + studentCode + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", netId='" + netId + '\'' +
                ", netType='" + netType + '\'' +
                '}';
    }
}
