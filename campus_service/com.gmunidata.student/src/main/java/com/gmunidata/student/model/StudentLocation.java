package com.gmunidata.student.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create 2018/11/29 10:20
 * @Modified By:
 **/
@ApiModel(value = "app学生位置信息实体类")
public class StudentLocation implements Serializable {

    @ApiModelProperty(value = "uuid")
    private String id; //uuid

    @ApiModelProperty(value = "app用户")
    private String userInfo; //app用户

    @ApiModelProperty(value = "学校")
    private String school; //学校

    @ApiModelProperty(value = "学号")
    private String studentId; //学号

    @ApiModelProperty(value = "定位时间")
    private String gprsTime; //定位时间

    @ApiModelProperty(value = "经度")
    private String longitude; //经度

    @ApiModelProperty(value = "纬度")
    private String latitude; //纬度

    @ApiModelProperty(value = "海拔")
    private String altitude; //海拔

    @ApiModelProperty(value = "当前城市")
    private String city; //当前城市

    @ApiModelProperty(value = "地址信息")
    private String address;  //地址信息

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime; //创建时间

    @ApiModelProperty(value = "学生签到时间")
    private String stuSignTime; //学生签到时间

    public StudentLocation() {
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGprsTime() {
        return gprsTime;
    }

    public void setGprsTime(String gprsTime) {
        this.gprsTime = gprsTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStuSignTime() {
        return stuSignTime;
    }

    public void setStuSignTime(String stuSignTime) {
        this.stuSignTime = stuSignTime;
    }

    @Override
    public String toString() {
        return "StudentLocation{" +
                "id='" + id + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", school='" + school + '\'' +
                ", studentId='" + studentId + '\'' +
                ", gprsTime='" + gprsTime + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", altitude='" + altitude + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", stuSignTime='" + stuSignTime + '\'' +
                '}';
    }
}
