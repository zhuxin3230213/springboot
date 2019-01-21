package cn.gmuni.sc.log.service;

import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.model.OperatorLog;

import java.util.List;

public interface ILogRecordService {
    void insert(List<OperatorLog> logs);

    void insertLogin(List<LoginLog> logs);
}
