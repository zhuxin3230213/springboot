package com.gmunidata.schoolbus.model;
//通勤班车
public class ScheduledBus {

    //id
    private String id;

    //季节
    private String season;

    //运行时间类型
    private String runningTimeType;

    //起点
    private String origin;

    //终点
    private String destination;

    //乘车地点
    private String ridingPoint;

    //乘车时间
    private String time;

    //车牌号
    private String busCode;

    //司机工号
    private String driverCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getRunningTimeType() {
        return runningTimeType;
    }

    public void setRunningTimeType(String runningTimeType) {
        this.runningTimeType = runningTimeType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRidingPoint() {
        return ridingPoint;
    }

    public void setRidingPoint(String ridingPoint) {
        this.ridingPoint = ridingPoint;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }
}
