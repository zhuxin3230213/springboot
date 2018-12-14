package cn.gmuni.enrollment.subject.model;

/**
 * 学科专业介绍中间表
 */
public class ProContent {
    private String id;

    //学科专业表ID
    private String proId;

    //文章表ID
    private String articleId;

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
}
