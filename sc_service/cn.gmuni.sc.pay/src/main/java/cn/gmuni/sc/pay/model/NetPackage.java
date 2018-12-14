package cn.gmuni.sc.pay.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NetPackage {
    //id
    private String id;
    //名称
    private String name;
    //编码
    private String code;
    //所属学校编码
    private String schoolCode;
    //所属套餐类型
    private String type;
    //添加时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //资费
    private String tariffs;
    //套餐组描述
    private String netDesc;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTariffs() {
        return tariffs;
    }

    public void setTariffs(String tariffs) {
        this.tariffs = tariffs;
    }

    public String getNetDesc() {
        return netDesc;
    }

    public void setNetDesc(String netDesc) {
        this.netDesc = netDesc;
    }
}
