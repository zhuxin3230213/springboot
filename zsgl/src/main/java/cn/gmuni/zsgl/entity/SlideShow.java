package cn.gmuni.zsgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author:ZhuXin
 * @Description: 轮播图
 * @Date:Create in 11:36 2018/6/28
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_slideshow")
public class SlideShow implements Serializable {

    @Id
    @GenericGenerator(name = "slideshow_uuid", strategy = "uuid")
    @GeneratedValue(generator = "slideshow_uuid")
    private String id; //主键
    private String module;  //模块code
    @Column(name = "article_id")
    private String articleId;  //正文id
    private Integer sort;  //排序号码


    public SlideShow() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SlideShow{" +
                "id='" + id + '\'' +
                ", module='" + module + '\'' +
                ", articleId='" + articleId + '\'' +
                ", sort=" + sort +
                '}';
    }
}
