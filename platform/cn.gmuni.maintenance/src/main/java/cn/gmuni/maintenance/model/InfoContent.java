package cn.gmuni.maintenance.model;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Transient;

import java.util.Date;

/**
 * 信息内容类，用于存储需要展示的标题内容
 */
@ApiModel(value = "信息内容类，用于存储需要展示的标题内容")
public class InfoContent {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 正文标题
     */
    @ApiModelProperty(value = "正文标题")
    private String title;
    /**
     * 正文内容，富文本
     */
    @ApiModelProperty(value = "正文内容，富文本")
    private String content;
    /**
     * 作者
     */
    @ApiModelProperty(value = "作者id")
    private String author;

    @ApiModelProperty(value = "作者名称")
    @Transient
    private String authorName;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private Date createTime;
    /**
     * 来源，待定
     */
    @ApiModelProperty(value = "来源，待定")
    private String source;
    /**
     * 文章对应菜单code
     */
    @ApiModelProperty(value = "文章对应菜单code")
    private String parentCode;
    /**
     * 文章关键字 |分割，使用 meta=keywords
     */
    @ApiModelProperty(value = "文章关键字")
    private String keywords;
    /**
     * 保存封面图片保存地址
     */
    @ApiModelProperty(value = "保存封面图片保存地址")
    private String cover;
    /**
     * 附件的保存地址
     */
    @ApiModelProperty(value = "附件的保存地址")
    private String attachment;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private int sort;
    /**
     * 0:否 1:是
     */
    @ApiModelProperty(value = "0:否 1:是")
    private String top;

    /**
     * 描述，用于 meta=description
     */
    private String description;

    /**
     * 用于保存富文本上传的正文内的图片，方便删除时清理脏数据
     */
    private String imageIds;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
