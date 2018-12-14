package cn.gmuni.zsgl.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:ZhuXin
 * @Description: 公告、新闻、报考指南
 * @Date:Create in 17:11 2018/5/28
 * @Modified By:
 **/


@Entity
@Table(name = "zs_gmuni_post_news_regulations")
public class PostNewsRegulation implements Serializable {

    @Id
    @GenericGenerator(name = "postNewsRegulation_uuid", strategy = "uuid")
    @GeneratedValue(generator = "postNewsRegulation_uuid")
    private String id; //信息id
    private String type; //类型;0 公告、1 新闻资讯、2 报考指南
    private String status; //状态：0：终止，1生效
    private String title;  //信息、标题
    private String content; //信息正文
    private String author; //作者
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime; //添加时间
    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;  //终止时间
    private String source; //来源
    private String keywords; //关键字
    private String cover; //文章封面
    private String attachment; //对应附件id，多个用逗号隔开
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;  //文章最后修改时间
    private Integer sort; //文章排序号
    private String top;  //是否置顶 0 否 1 是
    private String description; //文章描述

    public PostNewsRegulation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PostNewsRegulation{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", source='" + source + '\'' +
                ", keywords='" + keywords + '\'' +
                ", cover='" + cover + '\'' +
                ", attachment='" + attachment + '\'' +
                ", updateTime=" + updateTime +
                ", sort=" + sort +
                ", top='" + top + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
