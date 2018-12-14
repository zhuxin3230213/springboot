package cn.gmuni.org.service;

import cn.gmuni.org.model.User;
import cn.gmuni.org.model.UserInfo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IUserInfoService {
    /**
     * 列出所有非用户人员
     * @param params
     * @return
     */
    public PageInfo<User> listUndefinedUser(Map<String, String> params);
    /**
     * 根据传入的人员id列表添加用户
     */
    public Map<String, Object> addUserInfo(UserInfo userInfo);
    /**
     * 根据传入的用户id列表删除用户
     * @param infoIds
     * @return
     */
    public Map<String, Object> delUserInfo(String infoIds);
    /**
     * 列出所有用户
     * @param params
     * @return
     */
    public PageInfo<UserInfo> listAllUserInfo(Map<String, String> params);
    public PageInfo<UserInfo> listAllUserInfoByDeptId(Map<String, String> params);
    /**
     * 密码初始化
     * @param infoIds
     * @return
     */
    public Map<String, Object> initializePwd(String infoIds);

    /**
     * 启用：1，禁用：0用户
     * @param infoIds
     * @param status
     * @return
     */
    public Map<String, Object> changeState(String infoIds, String status);

    Map<String,Object> changePwd(String oldPwd, String newPwd, String code);

}
