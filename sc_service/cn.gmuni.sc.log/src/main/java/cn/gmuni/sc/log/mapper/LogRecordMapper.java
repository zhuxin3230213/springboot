package cn.gmuni.sc.log.mapper;

import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.model.OperatorLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogRecordMapper {
    void insert(List<OperatorLog> logs);

    void insertLogin(List<LoginLog> logs);
}
