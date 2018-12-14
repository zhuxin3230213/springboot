package cn.gmuni.security.service;


import cn.gmuni.org.mapper.UserInfoMapper;
import cn.gmuni.org.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserInfoMapper userInfoMapper;

    public UserInfo loadUserByUsername(String s) {
        UserInfo userInfo = userInfoMapper.login(s);
        return userInfo;
    }
}
