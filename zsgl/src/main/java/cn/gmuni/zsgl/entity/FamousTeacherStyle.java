package cn.gmuni.zsgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:ZhuXin
 * @Description: 名师风采实体类
 * @Date:Create in 16:19 2018/6/12
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_famous_teachers_style")
public class FamousTeacherStyle implements Serializable {

    @Id
    @GenericGenerator(name = "famousTeacherStyle", strategy = "uuid")
    @GeneratedValue(generator = "famousTeacherStyle")
    private String id; //主键

    private String code; //教师编号
    private String name; //教师姓名
    private String sex; //性别
    @Column(name = "faculty_code")
    private String facultyCode; //院系编码
    @Column(name = "subject_code")
    private String subjectCode; //专业编码
    private String degree; //学位
    @Column(name = "academic_title")
    private String academicTitle; //职称
    private String honor;  //荣誉
    private String eduction; //学历
    @Column(name = "e_mail")
    private String eMail; //邮箱
    private String telephone; //联系方式
    private String avatars; //图片
    @Column(name = "teacher_introduction")
    private String teacherIntroduction; //教师简介
    @Column(name = "create_time")
    private Date createTime; //创建时间
    private Integer sort; //排序
    private String homepage; //个人主页

    public FamousTeacherStyle() {
    }

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

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getEduction() {
        return eduction;
    }

    public void setEduction(String eduction) {
        this.eduction = eduction;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAvatars() {
        return avatars;
    }

    public void setAvatars(String avatars) {
        this.avatars = avatars;
    }

    public String getTeacherIntroduction() {
        return teacherIntroduction;
    }

    public void setTeacherIntroduction(String teacherIntroduction) {
        this.teacherIntroduction = teacherIntroduction;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String toString() {
        return "FamousTeacherStyle{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", facultyCode='" + facultyCode + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                ", degree='" + degree + '\'' +
                ", academicTitle='" + academicTitle + '\'' +
                ", honor='" + honor + '\'' +
                ", eduction='" + eduction + '\'' +
                ", eMail='" + eMail + '\'' +
                ", telephone='" + telephone + '\'' +
                ", avatars='" + avatars + '\'' +
                ", teacherIntroduction='" + teacherIntroduction + '\'' +
                ", createTime=" + createTime +
                ", sort=" + sort +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}
