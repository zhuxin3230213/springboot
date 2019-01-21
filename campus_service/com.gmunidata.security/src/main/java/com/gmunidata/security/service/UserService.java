package com.gmunidata.security.service;


import cn.gmuni.org.mapper.UserInfoMapper;
import cn.gmuni.org.model.UserInfo;
import com.gmunidata.student.mapper.StudentMapper;
import com.gmunidata.student.model.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    StudentMapper studentMapper;

    public UserInfo loadUserByUsername(String s) {
        UserInfo userInfo = userInfoMapper.login(s);
        return userInfo;
    }
    public LoginInfo appLogin(String code){
        return studentMapper.getByCode(code);
    }
}
