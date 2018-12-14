package cn.gmuni.zsgl.entity;

import cn.gmuni.zsgl.util.LongJsonDeserializer;
import cn.gmuni.zsgl.util.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:ZhuXin
 * @Description: 招生计划实体类
 * @Date:Create in 11:54 2018/5/31
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_enrollment_plan")
public class EnrollmentPlan implements Serializable {

    @Id
    @GenericGenerator(name = "enrollmentPlan_uuid", strategy = "uuid")
    @GeneratedValue(generator = "enrollmentPlan_uuid")
    private String id; //主键

    private Integer enrolment; //招生人数
    @Column(name = "actual_enrollment")
    private Integer actualEnrollment; //实际招生人数
    private String provinces;  //省份
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;  //添加时间
    private Integer year;    //年
    @Column(name = "edu_systme")
    private Short eduSystme; //学制
    private Long cost; //学费

    @ManyToOne(cascade = javax.persistence.CascadeType.REMOVE)
    //@Cascade(value = {CascadeType.DELETE})
    @JoinColumn(name = "fap_id", referencedColumnName = "id")
    private FacultyProfessional facultyProfessional; //学科专业id

    public EnrollmentPlan() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FacultyProfessional getFacultyProfessional() {
        return facultyProfessional;
    }

    public void setFacultyProfessional(FacultyProfessional facultyProfessional) {
        this.facultyProfessional = facultyProfessional;
    }

    public Integer getEnrolment() {
        return enrolment;
    }

    public void setEnrolment(Integer enrolment) {
        this.enrolment = enrolment;
    }

    public Integer getActualEnrollment() {
        return actualEnrollment;
    }

    public void setActualEnrollment(Integer actualEnrollment) {
        this.actualEnrollment = actualEnrollment;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Short getEduSystme() {
        return eduSystme;
    }

    public void setEduSystme(Short eduSystme) {
        this.eduSystme = eduSystme;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "EnrollmentPlan{" +
                "id='" + id + '\'' +
                ", enrolment=" + enrolment +
                ", actualEnrollment=" + actualEnrollment +
                ", provinces='" + provinces + '\'' +
                ", createTime=" + createTime +
                ", year=" + year +
                ", eduSystme=" + eduSystme +
                ", cost=" + cost +
                ", facultyProfessional=" + facultyProfessional +
                '}';
    }
}