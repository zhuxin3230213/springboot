package cn.gmuni.zsgl.util;

/**
 * @Author:ZhuXin
 * @Description: 信息枚举类
 * typeName：0：网站公告、1：新闻资讯、2：报考指南
 * @Date:Create in 9:29 2018/5/30
 * @Modified By:
 **/


public enum PostNewsRegulation {

    Post("网站公告", "0"), News("新闻资讯", "1"), Regulation("报考指南", "2");

    private String typeName;
    private String index;

    PostNewsRegulation(String typeName, String index) {
        this.typeName = typeName;
        this.index = index;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
