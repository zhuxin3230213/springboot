package cn.gmuni.zsgl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:ZhuXin
 * @Description: 正文实体类
 * @Date:Create in 16:28 2018/5/7
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_content")
public class Content implements Serializable {

    @Id
    @GenericGenerator(name = "content-uuid", strategy = "uuid")
    @GeneratedValue(generator = "content-uuid")
    private String id; //招生系统正文主键

    private String title; //正文标题
    private String content; //文档内容
    private String author; //作者
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime; //添加时间
    private String source; //来源
    @Column(name = "parent_code")
    private String parentCode; //文章对应菜单code
    @Column(name = "keywords")
    private String keyWords; //关键字
    private String cover; //封面
    private String attachment; //附件的保存地址
    @Column(name = "attachment_name")
    private String attachmentName; //附件文件名，带扩展名
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime; //修改时间
    private Integer sort; //排序号
    private String top; //是否置顶 0:否 1:是
    private String description; //描述
    @Column(name = "image_ids")
    private String imageIds; //保存正文中的所有图片id

    public Content() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
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

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
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

    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(String imageIds) {
        this.imageIds = imageIds;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", source='" + source + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", keyWords='" + keyWords + '\'' +
                ", cover='" + cover + '\'' +
                ", attachment='" + attachment + '\'' +
                ", attachmentName='" + attachmentName + '\'' +
                ", updateTime=" + updateTime +
                ", sort=" + sort +
                ", top='" + top + '\'' +
                ", description='" + description + '\'' +
                ", imageIds='" + imageIds + '\'' +
                '}';
    }
}
