package com.gmunidata.newsNotice.model;

//点击次数实体类
public class Click {

    //id
    private String id;
    //文章id
    private String articleId;
    //点击次数
    private String clickThrough;
    //文章类型
    private String type;

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

    public String getClickThrough() {
        return clickThrough;
    }

    public void setClickThrough(String clickThrough) {
        this.clickThrough = clickThrough;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Click(String id, String articleId, String type) {
        this.id = id;
        this.articleId = articleId;
        this.type = type;
    }
}
