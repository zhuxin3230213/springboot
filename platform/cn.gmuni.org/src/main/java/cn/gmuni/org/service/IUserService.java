package cn.gmuni.org.service;

import cn.gmuni.org.model.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public User addUser(User user);

    public User updateUser(User user);

    public PageInfo<User> listUserByDeptId(Map<String, String> params);
    public PageInfo<User> listUserByDeptIdForPart(Map<String, String> params);
    public List<User> listPartUserByDeptId(Map<String, String> params);

    public Map<String, Object> delUser(String uId);

    public Map<String, Object> transferDept(String uId, String DeptId);

    public Map<String, Object> checkUserCode(String code);
}
