package cn.gmuni.org.model;

public class RoleUser {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pf_gmuni_role_user.ID
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pf_gmuni_role_user.role_id
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    private String roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pf_gmuni_role_user.user_id
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    private String userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pf_gmuni_role_user.ID
     *
     * @return the value of pf_gmuni_role_user.ID
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pf_gmuni_role_user.ID
     *
     * @param id the value for pf_gmuni_role_user.ID
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pf_gmuni_role_user.role_id
     *
     * @return the value of pf_gmuni_role_user.role_id
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pf_gmuni_role_user.role_id
     *
     * @param roleId the value for pf_gmuni_role_user.role_id
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pf_gmuni_role_user.user_id
     *
     * @return the value of pf_gmuni_role_user.user_id
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pf_gmuni_role_user.user_id
     *
     * @param userId the value for pf_gmuni_role_user.user_id
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}