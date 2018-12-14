package cn.gmuni.sc.security.service;

import cn.gmuni.org.mapper.UserInfoMapper;
import cn.gmuni.org.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginUserService")
public class UserService {

    @Autowired
    private UserInfoMapper mapper;


    public UserInfo login(String code){
        return mapper.login(code);
    }
}
