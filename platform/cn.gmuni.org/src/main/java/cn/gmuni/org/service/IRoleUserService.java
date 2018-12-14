package cn.gmuni.org.service;

import cn.gmuni.org.model.Role;
import cn.gmuni.org.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface IRoleUserService {
    /**
     * 根据角色id授权给该角色的用户
     *
     * @param params
     * @return
     */
    public List<UserInfo> listUserForAuthorize(Map<String, String> params);
    /**
     * 根据用户id授权给该角色的用户
     *
     * @param params
     * @return
     */
    public List<Role> listRoleForAuthorize(Map<String, String> params);
    /**
     * 角色管理模块，保存选中的用户授权信息
     * @param params
     * @return
     */
    public Map<String, Object> authorizeUserToRole(Map<String, String> params);
    /**
     * 用户管理模块，保存选中的角色授权信息
     * @param params
     * @return
     */
    public Map<String, Object> authorizeRoleToUser(Map<String, String> params);
}
