package cn.gmuni.sc.devicmanagement.model;

import java.util.Date;

/**
 * 设备报修实体类
 */
public class Repair {
    //id
    private String id;

    //设备编码
    private String deviceCode;

    //报修原因
    private String reasonR;

    //报修人
    private String userR;

    //故障描述
    private String failureCause;

    //维护人
    private String maintenance;

    //报修时间
    private Date timeRepair;

    //维护时间
    private Date timeMaint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getReasonR() {
        return reasonR;
    }

    public void setReasonR(String reasonR) {
        this.reasonR = reasonR;
    }

    public String getUserR() {
        return userR;
    }

    public void setUserR(String userR) {
        this.userR = userR;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public Date getTimeRepair() {
        return timeRepair;
    }

    public void setTimeRepair(Date timeRepair) {
        this.timeRepair = timeRepair;
    }

    public Date getTimeMaint() {
        return timeMaint;
    }

    public void setTimeMaint(Date timeMaint) {
        this.timeMaint = timeMaint;
    }
}
