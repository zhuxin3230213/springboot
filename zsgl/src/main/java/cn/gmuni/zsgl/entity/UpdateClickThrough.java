package cn.gmuni.zsgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Author:ZhuXin
 * @Description: content与pnr点击次数
 * @Date:Create in 11:15 2018/7/19
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_update_click_through")
public class UpdateClickThrough {

    @Id
    @GenericGenerator(name = "update_click_through_uuid", strategy = "uuid")
    @GeneratedValue(generator = "update_click_through_uuid")
    private String id; //主键
    @Column(name = "article_id")
    private String articleId; //content或者pnr 的主键
    @Column(name = "click_through")
    private Integer clickThrough; //点击次数
    @Column(name = "type")
    private String type; //0:content 1:pnr

    public UpdateClickThrough() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getClickThrough() {
        return clickThrough;
    }

    public void setClickThrough(Integer clickThrough) {
        this.clickThrough = clickThrough;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UpdateClickThrough{" +
                "id='" + id + '\'' +
                ", articleId='" + articleId + '\'' +
                ", clickThrough=" + clickThrough +
                ", type='" + type + '\'' +
                '}';
    }
}
