package cn.gmuni.org.mapper;

import cn.gmuni.org.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_role
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_role
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    int insert(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_role
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    Role selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_role
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    List<Role> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_role
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    int updateByPrimaryKey(Role record);

    List<Role> selectAllByPage(Map<String, Object> params);

    int countAll(Map<String, Object> params);

    int checkNameAndCode(@Param("name")String name, @Param("code")String code);

    List<Role> listRoleForAuthorize(String userId);

    List<Role> getRoleByUserCode(String code);
}