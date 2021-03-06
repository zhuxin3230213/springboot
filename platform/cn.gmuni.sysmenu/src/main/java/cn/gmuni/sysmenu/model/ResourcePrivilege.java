package cn.gmuni.sysmenu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "权限信息")
public class ResourcePrivilege {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pf_gmuni_resource_privilege.ID
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pf_gmuni_resource_privilege.resource_code
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    @ApiModelProperty(value = "资源id")
    private String resourceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pf_gmuni_resource_privilege.priv_code
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    @ApiModelProperty(value = "部门，角色id")
    private String privCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pf_gmuni_resource_privilege.priv_type
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    @ApiModelProperty(value = "权限类型1:组织机构 2：角色 3：工作组")
    private String privType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pf_gmuni_resource_privilege.resource_type
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    @ApiModelProperty(value = "资源类型 1:菜单 2:按钮")
    private String resourceType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pf_gmuni_resource_privilege.ID
     *
     * @return the value of pf_gmuni_resource_privilege.ID
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pf_gmuni_resource_privilege.ID
     *
     * @param id the value for pf_gmuni_resource_privilege.ID
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pf_gmuni_resource_privilege.resource_code
     *
     * @return the value of pf_gmuni_resource_privilege.resource_code
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pf_gmuni_resource_privilege.resource_code
     *
     * @param resourceCode the value for pf_gmuni_resource_privilege.resource_code
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode == null ? null : resourceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pf_gmuni_resource_privilege.priv_code
     *
     * @return the value of pf_gmuni_resource_privilege.priv_code
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public String getPrivCode() {
        return privCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pf_gmuni_resource_privilege.priv_code
     *
     * @param privCode the value for pf_gmuni_resource_privilege.priv_code
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public void setPrivCode(String privCode) {
        this.privCode = privCode == null ? null : privCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pf_gmuni_resource_privilege.priv_type
     *
     * @return the value of pf_gmuni_resource_privilege.priv_type
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public String getPrivType() {
        return privType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pf_gmuni_resource_privilege.priv_type
     *
     * @param privType the value for pf_gmuni_resource_privilege.priv_type
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public void setPrivType(String privType) {
        this.privType = privType == null ? null : privType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pf_gmuni_resource_privilege.resource_type
     *
     * @return the value of pf_gmuni_resource_privilege.resource_type
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pf_gmuni_resource_privilege.resource_type
     *
     * @param resourceType the value for pf_gmuni_resource_privilege.resource_type
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType == null ? null : resourceType.trim();
    }
}