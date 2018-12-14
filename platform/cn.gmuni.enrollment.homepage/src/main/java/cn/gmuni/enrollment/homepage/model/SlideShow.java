package cn.gmuni.enrollment.homepage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "轮播图实体类")
public class SlideShow {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "轮播图所属模块")
    private String module;

    @ApiModelProperty(value = "文章id")
    private String aritcleId;

    @ApiModelProperty(value = "文章排序号")
    private String sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAritcleId() {
        return aritcleId;
    }

    public void setAritcleId(String aritcleId) {
        this.aritcleId = aritcleId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
