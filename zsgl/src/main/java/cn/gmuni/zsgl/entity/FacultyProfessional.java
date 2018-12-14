package cn.gmuni.zsgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author:ZhuXin
 * @Description: 院系专业实体类
 * @Date:Create in 11:31 2018/5/28
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_faculty_professional")
public class FacultyProfessional implements Serializable {

    @Id
    @GenericGenerator(name = "facultyProfessional_uuid", strategy = "uuid")
    @GeneratedValue(generator = "facultyProfessional_uuid")
    private String id; //院系专业主键
    @Column(name = "deptid")
    private String deptId; //对应组织机构id
    private String type; //类型：文理艺体，从字典表获取
    private String status; //状态:0招生,1当年不招生
    private String introduction; //专业简介
    private String sort; //排序号

    public FacultyProfessional() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "FacultyProfessional{" +
                "id='" + id + '\'' +
                ", deptId='" + deptId + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", introduction='" + introduction + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
