package com.gmunidata.course.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

//科目实体类
public class Course {
   //id
   private String id;
   //课程名称
   private String name;
   //课程编码
   private String code;
   //课程类型
   private String cType;
   //学习类型
   private String sType;
   //课程学时
   private int periods;
   //课程学分
   private double credit;
   //教室要求
   private String requires;
   //课程总学时
   private int totalPeriod;
   //基础课程
   private String baseC;
   //开课学期数
   private String semester;
    //添加时间
    @JsonFormat()
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getRequires() {
        return requires;
    }

    public void setRequires(String requires) {
        this.requires = requires;
    }

    public int getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(int totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public String getBaseC() {
        return baseC;
    }

    public void setBaseC(String baseC) {
        this.baseC = baseC;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
