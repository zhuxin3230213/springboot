package cn.gmuni.org.service.impl;

import cn.gmuni.org.mapper.DepartmentMapper;
import cn.gmuni.org.mapper.UserInfoMapper;
import cn.gmuni.org.mapper.UserMapper;
import cn.gmuni.org.model.User;
import cn.gmuni.org.service.IDepartmentService;
import cn.gmuni.org.service.IUserInfoService;
import cn.gmuni.org.service.IUserService;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    @Qualifier("departmentServiceImpl")
    IDepartmentService departmentService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    DepartmentMapper deptMapper;
    //用户管理控制类
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public User addUser(User user) {
        user.setId(IdGenerator.getId());
        user.setCreateTime(new Date());
        return userMapper.insert(user) > 0 ? user : null;
    }

    @Override
    public User updateUser(User user) {
        userInfoMapper.updateUserName(user);
        return userMapper.updateByPrimaryKey(user) > 0 ? user : null;
    }

    @Override
    public PageInfo<User> listUserByDeptId(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("name"));
        queryPara.put("deptid", params.get("deptId"));
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(userMapper.listUserByDeptId(queryPara));
    }
    @Override
    public PageInfo<User> listUserByDeptIdForPart(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("name"));
        queryPara.put("deptid", params.get("deptId"));
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(userMapper.listUserByDeptIdForPart(queryPara));
    }

    @Override
    public List<User> listPartUserByDeptId(Map<String, String> params) {
        return userMapper.listPartUserByDeptId(params.get("deptId"));
    }

    @Override
    public Map<String, Object> delUser(String uId) {
        //直接删除
        List<String> ids = Arrays.asList(uId.split(","));
        Map<String, Object> res = new HashMap<>();
        res.put("flag", userMapper.deleteByIdsIn(ids) + deptMapper.delDeptPartsByUserIds(ids)> 0);
        return res;
    }

    @Override
    public Map<String, Object> transferDept(String uId, String deptId) {
        List<String> ids = Arrays.asList(uId.split(","));
        Map<String, Object> res = new HashMap<>();
        res.put("flag", userMapper.transferDept(deptId, ids) > 0);
        return res;
    }

    @Override
    public Map<String, Object> checkUserCode(String code) {
        Map<String, Object> res = new HashMap<>();
        res.put("flag", userMapper.checkUserCode(code) > 0);
        return res;
    }
}
