package cn.gmuni.sc.market.mapper;

import cn.gmuni.sc.market.model.LeaveMessage;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface LeaveMessageMapper {

    int addLeaveMessage(LeaveMessage leaveMessage);

    int delLeaveMessage(List<String> list);

    List<Map<String,Object>> getLeaveMessage(Map<String,Object> params);

    List<Map<String,Object>> getMyLeaveMessage(Map<String,Object> params);
}
