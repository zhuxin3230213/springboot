package cn.gmuni.sc.user.model;

import java.io.Serializable;
import java.util.Date;

public class ThirdPartUser implements Serializable {

    //id
    private String id;

    //手机号码
    private String phoneNumber;

    //微信号
    private String wechat;

    //qq
    private String qq;

    //邮箱
    private String email;

    //微博
    private String weibo;

    //所属院校编码
    private String school;

    //学号
    private String studentId;

    //所属院系
    private String faculty;

    //所属专业
    private String subject;

    //所属班级
    private String clazz;

    //头像
    private String avatar;

    //名称
    private String name;

    //性别
    private String sex;

    //生日
    private Date birthday;

    //身份证号码
    private String identityCard;

    //所属院校名称，不保存数据库
    private String schoolName;

    //学生真是姓名
    private String realName;

    //院校方系统登录密码
    private String password;

    //院校方系统登录token
    private String endpointToken;

    //院校方登录loginId
    private String endpointLoginId;
    //上网账号
    private String netAccount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEndpointToken() {
        return endpointToken;
    }

    public void setEndpointToken(String endpointToken) {
        this.endpointToken = endpointToken;
    }

    public String getEndpointLoginId() {
        return endpointLoginId;
    }

    public void setEndpointLoginId(String endpointLoginId) {
        this.endpointLoginId = endpointLoginId;
    }

    public String getNetAccount() {
        return netAccount;
    }

    public void setNetAccount(String netAccount) {
        this.netAccount = netAccount;
    }
}
