package cn.gmuni.sc.market.service.impl;

import cn.gmuni.sc.base.response.Content;
import cn.gmuni.sc.market.mapper.LeaveMessageMapper;
import cn.gmuni.sc.market.model.LeaveMessage;
import cn.gmuni.sc.market.service.ILeaveMessageService;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveMessageServiceImpl implements ILeaveMessageService {

    @Autowired
    LeaveMessageMapper mapper;

    @Override
    public Map<String,Object> addLeaveMessage(LeaveMessage leaveMessage) {
        String id = IdGenerator.getId();
        leaveMessage.setId(id);
        leaveMessage.setMessageUser(UserUtils.getLoginUserInfo().getIndentifier());
        leaveMessage.setCreateTime(new Date());
       int flag = mapper.addLeaveMessage(leaveMessage) > 0 ? 0 : 1;
        if (flag == 0){
            Map<String,Object> map = new HashMap<>();
            map.put("id",id);
            return mapper.getLeaveMessage(map).get(0);
        }
        return new HashMap<>();
    }

    @Override
    public Content delLeaveMessage(List<String> list) {
        Content con  =new Content();
        int flag = mapper.delLeaveMessage(list) > 0 ? 0 : 1;
        con.setFlag(flag);
        if (flag == 0){
            con.setMessage("删除成功");
        }else {
            con.setMessage("删除失败");
        }
        return con;
    }

    @Override
    public List<Map<String, Object>> listLeaveMessage(Map<String, Object> param) {
        return mapper.getLeaveMessage(param);
    }

    @Override
    public List<Map<String, Object>> getMyLeaveMessage(Map<String, Object> params) {
        return mapper.getMyLeaveMessage(params);
    }

}
