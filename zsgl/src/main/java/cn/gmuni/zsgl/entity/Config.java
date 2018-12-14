package cn.gmuni.zsgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author:ZhuXin
 * @Description: 招生系统配置信息类
 * @Date:Create in 16:59 2018/5/7
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_config")
public class Config implements Serializable {

    @Id
    @GenericGenerator(name = "config-uuid", strategy = "uuid")
    @GeneratedValue(generator = "config-uuid")
    private String id; //招生系统配置信息主键

    private String code; //编码
    private String name; //名称
    private String value; //值
    private Integer sort; //排序号

    public Config() {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", sort=" + sort +
                '}';
    }
}
