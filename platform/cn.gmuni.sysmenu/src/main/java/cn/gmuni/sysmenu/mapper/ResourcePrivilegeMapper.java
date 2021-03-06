package cn.gmuni.sysmenu.mapper;

import cn.gmuni.sysmenu.model.ResourcePrivilege;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ResourcePrivilegeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_resource_privilege
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_resource_privilege
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    int insert(ResourcePrivilege record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_resource_privilege
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    ResourcePrivilege selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_resource_privilege
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    List<ResourcePrivilege> selectAll();
    List<ResourcePrivilege> selectByPrivCode(String privCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_resource_privilege
     *
     * @mbggenerated Mon May 28 11:00:22 CST 2018
     */
    int updateByPrimaryKey(ResourcePrivilege record);

    int deleteByPrivCode(String privCode);

    List<ResourcePrivilege> getPrivByDeptDlevelCode(@Param("list") List<String> codes);

    List<ResourcePrivilege> getPrivByUserCode(String code);
}