package cn.gmuni.org.mapper;

import cn.gmuni.org.model.RoleUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleUserMapper {
    /**
     * 新增
     * @param record
     * @return
     */
    int insert(RoleUser record);

    /**
     * 查询所有
     * @return
     */
    List<RoleUser> selectAll();

    /**
     * 统计角色id被授权次数，删除角色时使用
     * @param roleId
     * @return
     */
    int countByRoleId(String roleId);

    /**
     * 根据角色id删除
     */
    int delByRoleId(String roleId);
    /**
     * 根据用户id删除
     */
    int delByUserId(String userId);

    /**
     * 批量插入
     */
    int insertRoleUsers(@Param("list")List<RoleUser> rus);
}