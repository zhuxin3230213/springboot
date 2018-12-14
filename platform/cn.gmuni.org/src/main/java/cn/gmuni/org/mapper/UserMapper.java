package cn.gmuni.org.mapper;

import cn.gmuni.org.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_user
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_user
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_user
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    User selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_user
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    List<User> selectAll();
    List<User> selectAllByIdsIn(List<String> ids);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pf_gmuni_user
     *
     * @mbg.generated Mon May 14 14:36:47 CST 2018
     */
    int updateByPrimaryKey(User record);

    List<User> listUserByDeptId(Map<String, Object> queryPara);
    List<User> listUserByDeptIdForPart(Map<String, Object> queryPara);

    long countByDeptId(Map<String, Object> queryPara );

    List<User>  listUndefinedUser(Map<String, Object> queryPara);

    int countUndefinedUser(Map<String, Object> queryPara);

    int deleteByIdsIn(List<String> ids);

    int transferDept(@Param("deptId")String deptId, @Param("list")List<String> ids);
    int checkUserCode(String code);

    List<User> listPartUserByDeptId(String params);
}