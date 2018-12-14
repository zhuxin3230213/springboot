package cn.gmuni.enrollment.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "系统配置信息类")
public class Config {
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "编码，全局唯一")
    private String code;
    @ApiModelProperty(value = "配置项名称")
    private String name;
    @ApiModelProperty(value = "值")
    private String value;
    @ApiModelProperty(value = "排序号")
    private int sort;

    public Config() {
    }

    public Config(String id, String code, String name, String value, int sort) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.value = value;
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
