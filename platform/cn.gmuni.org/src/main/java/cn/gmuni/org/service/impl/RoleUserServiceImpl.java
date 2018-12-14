package cn.gmuni.org.service.impl;

import cn.gmuni.org.mapper.RoleMapper;
import cn.gmuni.org.mapper.RoleUserMapper;
import cn.gmuni.org.mapper.UserInfoMapper;
import cn.gmuni.org.model.Role;
import cn.gmuni.org.model.RoleUser;
import cn.gmuni.org.model.UserInfo;
import cn.gmuni.org.service.IRoleUserService;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleUserServiceImpl implements IRoleUserService {
    @Autowired
    RoleUserMapper roleUserMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    RoleMapper roleMapper;
    @Override
    public List<UserInfo> listUserForAuthorize(Map<String, String> params) {
       return userInfoMapper.listUserForAuthorize(params.get("roleId"));
    }

    @Override
    public List<Role> listRoleForAuthorize(Map<String, String> params) {
        return roleMapper.listRoleForAuthorize(params.get("userId"));
    }

    @Override
    public Map<String, Object> authorizeUserToRole(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        String roleId = params.get("roleId");
        String userIds = params.get("userIds");
        List<String> users = Arrays.asList(userIds.split(","));
        roleUserMapper.delByRoleId(roleId);
        if(users.size()>0 &&!"".equals(userIds)){
            List<RoleUser> rus = new ArrayList<>();
            for (int i = 0; i < users.size(); i++) {
                RoleUser ru = new RoleUser();
                ru.setId(IdGenerator.getId());
                ru.setRoleId(roleId);
                ru.setUserId(users.get(i));
                rus.add(ru);
            }
            int num = 0;
            if(rus.size() > 0){
                num = roleUserMapper.insertRoleUsers(rus);
            }
            res.put("flag", num > 0);
        }else{
            res.put("flag",true);
        }

        return res;
    }

    @Override
    public Map<String, Object> authorizeRoleToUser(Map<String, String> params) {
        Map<String, Object> res = new HashMap<>();
        String roleIds = params.get("roleIds");
        String userId = params.get("userId");
        List<String> roles = Arrays.asList(roleIds.split(","));
        roleUserMapper.delByUserId(userId);
        List<RoleUser> rus = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            RoleUser ru = new RoleUser();
            ru.setId(IdGenerator.getId());
            ru.setRoleId(roles.get(i));
            ru.setUserId(userId);
            rus.add(ru);
        }
        int num = 0;
        if(rus.size() > 0){
            num = roleUserMapper.insertRoleUsers(rus);
        }
        res.put("flag", num > 0);
        return res;
    }
}
