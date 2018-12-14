package cn.gmuni.org.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "兼职部门")
public class DeptPart {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "人员id")
    private String userId;
    @ApiModelProperty(value = "部门id")
    private String deptId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
