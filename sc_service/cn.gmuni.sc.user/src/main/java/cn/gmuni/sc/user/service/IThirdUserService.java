package cn.gmuni.sc.user.service;

import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;

import java.util.List;

public interface IThirdUserService {

    //保存登录用户
    boolean saveUserInfo(ThirdPartUserInfo userInfo);

    //保存登录用户详细信息
    boolean saveUser(ThirdPartUser user);

    boolean updateUserInfo(ThirdPartUserInfo user);

    //通过账号获取用户
    ThirdPartUserInfo getUserInfo(String indentifier);

    List<ThirdPartUserInfo> listAllUserInfo();

    boolean logout(ThirdPartUserInfo userInfo);

    ThirdPartUser getUserById(String userId);

    boolean updateUser(ThirdPartUser user);

    List<ThirdPartUser> listAllUser();
}
