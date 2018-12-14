package cn.gmuni.org.service.impl;

import cn.gmuni.org.mapper.RoleMapper;
import cn.gmuni.org.mapper.RoleUserMapper;
import cn.gmuni.org.model.Role;
import cn.gmuni.org.service.IRoleService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    RoleMapper mapper;
    @Autowired
    RoleUserMapper ruMapper;

    @Override
    public Role addRole(Role role){
        role.setId(IdGenerator.getId());
        role.setCreateTime(new Date());
        return mapper.insert(role) > 0 ? role : null;
    }

    @Override
    public Map<String, Object> delRole(String roleId){
        Map<String, Object> res = new HashMap<>();
        if(ruMapper.countByRoleId(roleId) > 0){
            res.put("flag", false);
            res.put("msg", "hasBeenAuthorized");
            return res;
        }
        res.put("flag",  mapper.deleteByPrimaryKey(roleId) > 0);
        return res;
    }

    @Override
    public Role updateRole(Role role){
        return mapper.updateByPrimaryKey(role) > 0 ? role : null;
    }

    @Override
    public PageInfo<Role> listRole(Map<String, String> params){
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("name"));
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(mapper.selectAllByPage(queryPara));
    }

    @Override
    public Map<String, Object> checkNameAndCode(String name, String code) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", mapper.checkNameAndCode(name, code) > 0);
        return res;
    }

    @Override
    public List<Role> listAllRole() {
        return mapper.selectAll();
    }
}
