package cn.gmuni.zsgl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author:ZhuXin
 * @Description: 文件上传下载
 * @Date:Create in 11:49 2018/6/15
 * @Modified By:
 **/


@Entity
@Table(name = "pf_gmuni_upload")
public class Upload implements Serializable {

    @Id
    @GenericGenerator(name = "upload_uuid", strategy = "uuid")
    @GeneratedValue(generator = "upload_uuid")
    private String id; //主键

    private String name; //文件名称
    private String format; //文件类型
    private Integer size; //文件大小
    @Column(name = "last_modified")
    private String lastModified; //文件最后修改时间
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String createTime; //添加时间

    public Upload() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                ", lastModified='" + lastModified + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
