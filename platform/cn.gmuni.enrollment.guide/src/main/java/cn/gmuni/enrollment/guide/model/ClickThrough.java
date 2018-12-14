package cn.gmuni.enrollment.guide.model;

public class ClickThrough {
    private String id;
    private String articleId;
    private int clickThrough;
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

    public int getClickThrough() {
        return clickThrough;
    }

    public void setClickThrough(int clickThrough) {
        this.clickThrough = clickThrough;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
