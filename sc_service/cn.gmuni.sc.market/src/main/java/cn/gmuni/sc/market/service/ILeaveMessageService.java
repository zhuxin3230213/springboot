package cn.gmuni.sc.market.service;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.market.model.LeaveMessage;

import java.util.List;
import java.util.Map;

public interface ILeaveMessageService {

    //添加留言信息
    Map<String,Object> addLeaveMessage(LeaveMessage leaveMessage);

    //删除留言信息
   Content delLeaveMessage(List<String> list);

   //刷新
    List<Map<String,Object>> listLeaveMessage(Map<String,Object> param);

    //获取我的留言
    List<Map<String,Object>> getMyLeaveMessage(Map<String,Object> params);

}
