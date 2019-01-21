package cn.gmuni.sc.information.service.impl;

import cn.gmuni.sc.information.mapper.MessageMapper;
import cn.gmuni.sc.information.model.MessageModel;
import cn.gmuni.sc.information.service.MessageService;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.utils.IdGenerator;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create 2018/11/22 10:07
 * @Modified By:
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public MessageModel add(MessageModel messageModel) {
        messageModel.setId(IdGenerator.getId());
        messageModel.setCreateTime(new Date());
        messageModel.setState("0");
        messageMapper.add(messageModel);
        return messageModel;
    }

    @Override
    public List<Map<String, Object>> list(Map<String, String> params) {
        List<Map<String, Object>> list = new ArrayList<>();

        PageHelper.startPage(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();

        Map<String, String> map = new HashMap<>();
        map.put("userInfo", loginUserInfo.getIndentifier());
        map.put("schoolCode", loginUser.getSchool());
        map.put("messageType", params.get("messageType"));
        list = messageMapper.list(map);

        Map<String, Object> lastMessage = messageMapper.lastMessage(map);
        if (lastMessage != null) {
            list.add(lastMessage);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> addMessageTop(Map<String, String> params) {
        PageHelper.startPage(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("userInfo", loginUserInfo.getIndentifier());
        queryParam.put("schoolCode", loginUser.getSchool());
        queryParam.put("messageType", params.get("messageType"));
        queryParam.put("initDataFirstTime", params.get("initDataFirstTime"));
        return messageMapper.listByInitDataFirstTime(queryParam);
    }

    @Override
    public List<Map<String, Object>> addMessageBottom(Map<String, String> params) {
        PageHelper.startPage(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("userInfo", loginUserInfo.getIndentifier());
        queryParam.put("schoolCode", loginUser.getSchool());
        queryParam.put("messageType", params.get("messageType"));
        queryParam.put("initDataLastTime", params.get("initDataLastTime"));
        return messageMapper.listByInitDataLastTime(queryParam);
    }

    @Override
    public Map<String, Object> lastMessage(Map<String, String> params) {
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("userInfo", loginUserInfo.getIndentifier());
        queryParam.put("schoolCode", loginUser.getSchool());
        queryParam.put("messageType", params.get("messageType"));

        return messageMapper.lastMessage(queryParam);
    }

    @Override
    public Map<String, Object> updateMessageState(Map<String, String> params) {
        Map<String, String> map = new HashMap<>();
        map.put("id", params.get("id"));
        map.put("type", params.get("type"));
        map.put("state", "1"); //"1" 表示app端已经阅读此状态
        int i = messageMapper.updateMessageState(map);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("flag", i > 0 ? "true" : "false");
        return queryParams;
    }

    @Override
    public Map<String, Object> getMessageState() {
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("userInfo", loginUserInfo.getIndentifier());
        queryParam.put("schoolCode", loginUser.getSchool());
        List<String> state = messageMapper.getState(queryParam);
        boolean contains = state.contains("0"); //0:未读 1:已读

        Map<String, Object> map = new HashMap<>();
        map.put("flag", contains ? "true" : "false");
        return map;
    }
}
