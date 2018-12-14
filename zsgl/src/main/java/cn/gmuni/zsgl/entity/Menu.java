package cn.gmuni.zsgl.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @Author:ZhuXin
 * @Description: 招生系统一级菜单与二级菜单类
 * @Date:Create in 16:28 2018/5/7
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_menu")
public class Menu implements Serializable {

    @Id
    @GenericGenerator(name = "menu-uuid", strategy = "uuid")//JPA uuid 主键生成策略
    @GeneratedValue(generator = "menu-uuid")
    private String id; //菜单主键

    private String name; //菜单名称
    private String code; //菜单编码
    private Integer level; //菜单层次
    @Column(name = "parent_id")
    private String parentId; //菜单父级code
    private String type; //菜单类型:0:内部链接，1:外部链接
    @Column(name = "click_through")
    private Integer clickThrough; //访问次数
    private String status; //状态，0禁用，1启用
    private String url; //外部链接地址
    @Column(name = "built_in")
    private String builtIn;  //0:内置不可删,1:外部
    private String sort; //排序号

    public Menu() {
    }

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getClickThrough() {
        return clickThrough;
    }

    public void setClickThrough(Integer clickThrough) {
        this.clickThrough = clickThrough;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(String builtIn) {
        this.builtIn = builtIn;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", level=" + level +
                ", parentId='" + parentId + '\'' +
                ", type='" + type + '\'' +
                ", clickThrough=" + clickThrough +
                ", status='" + status + '\'' +
                ", url='" + url + '\'' +
                ", builtIn='" + builtIn + '\'' +
                ", sort=" + sort +
                '}';
    }
}
