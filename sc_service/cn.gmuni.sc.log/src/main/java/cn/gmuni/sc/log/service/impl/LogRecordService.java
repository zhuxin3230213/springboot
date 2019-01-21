package cn.gmuni.sc.log.service.impl;

import cn.gmuni.sc.log.mapper.LogRecordMapper;
import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.model.OperatorLog;
import cn.gmuni.sc.log.service.ILogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogRecordService implements ILogRecordService {

    @Autowired
    private LogRecordMapper mapper;

    @Override
    public void insert(List<OperatorLog> logs) {
        mapper.insert(logs);
    }

    @Override
    public void insertLogin(List<LoginLog> logs) {
        mapper.insertLogin(logs);
    }
}
