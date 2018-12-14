package cn.gmuni.zsgl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Author:ZhuXin
 * @Description: 资源目录
 * @Date:Create in 11:27 2018/7/5
 * @Modified By:
 **/


public class ResourceCatalog {


    private String id; //主键
    private String code; //目录编码
    private String name; //名称
    private Integer sort; //排序号
    @Column(name = "parent_id")
    private String parentId; //父级id
    private String remark; //标识
    @Column(name = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date create_time; //创建时间
}
