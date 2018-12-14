package cn.gmuni.zsgl.util;

/**
 * @Author:ZhuXin
 * @Description: 全局搜索查询
 * @Date:Create in 15:03 2018/7/6
 * @Modified By:
 **/


public class GlobalSearchUtil {

    private String id; //主键(菜单code，其它全是主键)
    private String name; //关键字查询
    private String type; //类型：0：菜单  1：content(正文)  2：pnr(信息正文) 3：faculty(学科专业) 4：famousTeacherStyle(教师信息)
    private String sort; //排序号

    public GlobalSearchUtil() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "GlobalSearchUtil{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
