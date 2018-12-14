package com.gmunidata.schedule.model;

//课程的节数实体类
public class ScheduleNc {

    //id
    private String id;
    //课程id
    private String schId;
    //节数
    private String nc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }
}
