package com.gmunidata.newsNotice.model;

//组学生实体类
public class GroupPeople {
    //id
    private String id;
    //所属组
    private String groupCode;
    //学生编码
    private String  studentCode;
    //所属学校
    private String  schoolCode;
    //创建者
    private String createPeople;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
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
        schoolCode = schoolCode;
    }

    public String getCreatePeople() {
        return createPeople;
    }

    public void setCreatePeople(String createPeople) {
        this.createPeople = createPeople;
    }
}
