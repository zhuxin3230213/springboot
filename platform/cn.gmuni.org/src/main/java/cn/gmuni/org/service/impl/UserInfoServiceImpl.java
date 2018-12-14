package cn.gmuni.org.service.impl;

import cn.gmuni.org.mapper.UserInfoMapper;
import cn.gmuni.org.mapper.UserMapper;
import cn.gmuni.org.model.User;
import cn.gmuni.org.model.UserInfo;
import cn.gmuni.org.service.IUserInfoService;
import cn.gmuni.utils.DesUtils;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.Md5Util;
import cn.gmuni.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Value("${initpwd}")
    private String initpwd;
    @Autowired
    UserInfoMapper mapper;

    @Autowired
    UserMapper userMapper;
    @Override
    public PageInfo<User> listUndefinedUser(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("name"));
        queryPara.put("deptId", params.get("deptId"));
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(userMapper.listUndefinedUser(queryPara));
    }

    @Override
    public Map<String, Object> addUserInfo(UserInfo userInfo) {
       userInfo.setId(IdGenerator.getId());
       userInfo.setCreateTime(new Date());
       userInfo.setState("1");
       String code = userInfo.getCode();
       String password = userInfo.getPassword();
       password = DesUtils.decode(password, code);
       password = Md5Util.encode(password);
       userInfo.setPassword(password);
       int count = mapper.insert(userInfo);
        Map<String, Object> res = new HashMap<>();
        res.put("flag", count > 0);
        return res;
    }

    @Override
    public Map<String, Object> delUserInfo(String infoIds) {
        List<String> ids = Arrays.asList(infoIds.split(","));
        int num = mapper.deleteByIdsIn(ids);
        Map<String, Object> res = new HashMap<>();
        res.put("flag", num > 0);
        return res;
    }

    @Override
    public PageInfo<UserInfo> listAllUserInfo(Map<String, String> params) {
       PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("userName"));
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(mapper.selectAll(queryPara));
    }

    @Override
    public PageInfo<UserInfo> listAllUserInfoByDeptId(Map<String, String> params) {
        PageUtils page = PageUtils.getPage(params);
        Map<String, Object> queryPara = new HashMap<>();
        queryPara.put("deptId", params.get("deptId"));
        queryPara.put("code", params.get("code"));
        queryPara.put("name", params.get("userName"));
        PageHelper.startPage(page.getPage(),page.getSize());
        return new PageInfo<>(mapper.selectAllPageByDeptId(queryPara));
    }

    @Override
    public Map<String, Object> initializePwd(String infoIds) {
        List<String> ids = Arrays.asList(infoIds.split(","));
        int num = mapper.initializePwd(ids, Md5Util.encode(initpwd));
        Map<String, Object> res = new HashMap<>();
        res.put("flag", num > 0);
        return res;
    }

    @Override
    public Map<String, Object> changeState(String infoIds, String state) {
        List<String> ids = Arrays.asList(infoIds.split(","));
        int num = mapper.changeState(ids, state);
        Map<String, Object> res = new HashMap<>();
        res.put("flag", num > 0);
        return res;
    }

    @Override
    public Map<String, Object> changePwd(String oldPwd, String newPwd, String code) {
        newPwd = DesUtils.decode(newPwd, code);
        oldPwd = DesUtils.decode(oldPwd, code);
        Map<String,Object> result = new HashMap<>();
        if(mapper.checkOldPwd(code,Md5Util.encode(oldPwd))==1){
            mapper.changePwd(code,Md5Util.encode(newPwd));
            result.put("flag",true);
        }else{
            result.put("flag",false);
            result.put("msg","oldPwdError");
        }
        return result;
    }


}
