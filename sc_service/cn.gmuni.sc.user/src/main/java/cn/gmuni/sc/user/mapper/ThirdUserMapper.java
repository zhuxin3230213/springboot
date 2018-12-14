package cn.gmuni.sc.user.mapper;

import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ThirdUserMapper {

    int saveUser(ThirdPartUser user);

    int saveUserInfo(ThirdPartUserInfo userInfo);

    ThirdPartUserInfo getUserInfo(@Param("indentifier") String indentifier);

    int updateUserInfo(ThirdPartUserInfo user);

    List<ThirdPartUserInfo> listAllUserInfo();

    int logout(ThirdPartUserInfo userInfo);

    ThirdPartUser getUserById(@Param("userId") String userId);

    boolean updateUser(ThirdPartUser user);

    List<ThirdPartUser> listAllUser();
}
