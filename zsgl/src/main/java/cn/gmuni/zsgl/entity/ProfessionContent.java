package cn.gmuni.zsgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author:ZhuXin
 * @Description: 院系专业正文实体类
 * @Date:Create in 9:28 2018/7/2
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_profession_content")
public class ProfessionContent implements Serializable {

    @Id
    @GenericGenerator(name = "professionContent_uuid", strategy = "uuid")
    @GeneratedValue(generator = "professionContent_uuid")
    private String id; //主键

    @Column(name = "pro_id")
    private String proId; //学科专业的id
    @Column(name = "article_id")
    private String articleId; //文章的id

    public ProfessionContent() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "ProfessionContent{" +
                "id='" + id + '\'' +
                ", proId='" + proId + '\'' +
                ", articleId='" + articleId + '\'' +
                '}';
    }
}
