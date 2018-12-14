package cn.gmuni.zsgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author:ZhuXin
 * @Description: 历年分数实体类
 * @Date:Create in 11:55 2018/5/31
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_yearly_score")
public class YearlyScore implements Serializable {

    @Id
    @GenericGenerator(name = "yearlyScore_uuid", strategy = "uuid")
    @GeneratedValue(generator = "yearlyScore_uuid")
    private String id;

    private Integer year; //年份
    private String provinces; //省份
    private String batch; //批次
    private String family; //科类
    @Column(name = "scoreline")
    private double scoreLine; //分数线
    @Column(name = "max_score")
    private double maxScore; //最高分
    @Column(name = "min_score")
    private double minScore; //最低分
    @Column(name = "avg_score")
    private double avgScore; //平均分
    private String sort; //排序号
    private String pro_course; //预留:专业课

    public YearlyScore() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public double getScoreLine() {
        return scoreLine;
    }

    public void setScoreLine(double scoreLine) {
        this.scoreLine = scoreLine;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public double getMinScore() {
        return minScore;
    }

    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPro_course() {
        return pro_course;
    }

    public void setPro_course(String pro_course) {
        this.pro_course = pro_course;
    }

    @Override
    public String toString() {
        return "YearlyScore{" +
                "id='" + id + '\'' +
                ", year=" + year +
                ", provinces='" + provinces + '\'' +
                ", batch='" + batch + '\'' +
                ", family='" + family + '\'' +
                ", scoreLine=" + scoreLine +
                ", maxScore=" + maxScore +
                ", minScore=" + minScore +
                ", avgScore=" + avgScore +
                ", sort='" + sort + '\'' +
                ", pro_course='" + pro_course + '\'' +
                '}';
    }
}
