package cn.gmuni.sc.information.service;

import cn.gmuni.sc.information.model.MessageModel;

import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create 2018/11/22 10:07
 * @Modified By:
 **/
public interface MessageService {

    //添加消息
    MessageModel add(MessageModel messageModel);

    //根据登录用户与学校获取消息列表
    List<Map<String,Object>> list(Map<String,String> params);

    //下拉刷新
    List<Map<String,Object>> addMessageTop(Map<String,String> params);
    //上拉加载
    List<Map<String,Object>> addMessageBottom(Map<String,String> params);
    //获取列表最后一条数据
    Map<String,Object> lastMessage(Map<String,String> params);
    //更改消息阅读状态
    Map<String,Object> updateMessageState(Map<String,String> params);
    //获取消息阅读状态
    Map<String,Object> getMessageState();

}
