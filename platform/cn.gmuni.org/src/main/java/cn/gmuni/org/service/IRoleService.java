package cn.gmuni.org.service;

import cn.gmuni.org.model.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    /**
     * 新增角色
     * @param role
     * @return
     */
    public Role addRole(Role role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    public Map<String, Object> delRole(String roleId);

    /**
     * 修改角色
     * @param role
     * @return
     */
    public Role updateRole(Role role);

    /**
     * 查询角色列表
     * @param params
     * @return
     */
    public PageInfo<Role> listRole(Map<String, String> params);

    List<Role> listAllRole();

    public Map<String, Object> checkNameAndCode(String name, String code);
}
