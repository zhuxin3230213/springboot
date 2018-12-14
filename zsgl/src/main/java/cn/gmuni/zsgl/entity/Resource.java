package cn.gmuni.zsgl.entity;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Author:ZhuXin
 * @Description: 资源实体类
 * @Date:Create in 11:24 2018/7/5
 * @Modified By:
 **/


public class Resource {


    private String id; //主键
    private String name; //资源名称
    @Column(name = "file_format")
    private String fileFormat; //资源格式,zip,doc等等
    private Double size; //资源大小KB
    @Column(name = "catalog_id")
    private String catalogId; //资源所属目录
    @Column(name = "create_time")
    private Date createTime; //创建时间
}
