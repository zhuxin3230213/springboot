package com.gmunidata.schoolbus.model;

import java.util.Date;

//校车管理实体类
public class SchoolBus {
    //id
    private String id;
    //车牌号
    private String code;
    //车型
    private String busType;
    //车载容量
    private String volume;
    //车系
    private String busSeries;
    //停车位
    private String parkingSpace;
    //状态
    private String status;
    //行驶证日期
    private Date drivingTime;
    //添加日期
    private Date createTime;
    //备注
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getBusSeries() {
        return busSeries;
    }

    public void setBusSeries(String busSeries) {
        this.busSeries = busSeries;
    }

    public String getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(String parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(Date drivingTime) {
        this.drivingTime = drivingTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
