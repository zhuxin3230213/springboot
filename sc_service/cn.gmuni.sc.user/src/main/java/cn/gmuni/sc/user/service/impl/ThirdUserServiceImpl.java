package cn.gmuni.sc.user.service.impl;

import cn.gmuni.sc.user.mapper.ThirdUserMapper;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.service.IThirdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("thirdUserServiceImpl")
public class ThirdUserServiceImpl implements IThirdUserService {

    @Autowired
    ThirdUserMapper mapper;

    @Override
    public boolean saveUserInfo(ThirdPartUserInfo userInfo) {
        return mapper.saveUserInfo(userInfo)>0;
    }

    @Override
    public boolean saveUser(ThirdPartUser user) {
        return mapper.saveUser(user)>0;
    }

    @Override
    public boolean updateUserInfo(ThirdPartUserInfo user) {
        return mapper.updateUserInfo(user) > 0;

    }

    @Override
    public ThirdPartUserInfo getUserInfo(String indentifier) {
        return mapper.getUserInfo(indentifier);
    }

    @Override
    public List<ThirdPartUserInfo> listAllUserInfo() {
        return mapper.listAllUserInfo();
    }

    @Override
    public boolean logout(ThirdPartUserInfo userInfo) {
        return mapper.logout(userInfo)>0;
    }

    @Override
    public ThirdPartUser getUserById(String userId) {
        return mapper.getUserById(userId);
    }

    @Override
    public boolean updateUser(ThirdPartUser user) {
        return mapper.updateUser(user);
    }

    @Override
    public List<ThirdPartUser> listAllUser() {
        return mapper.listAllUser();
    }
}
