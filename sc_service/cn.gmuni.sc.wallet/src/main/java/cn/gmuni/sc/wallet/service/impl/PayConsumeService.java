package cn.gmuni.sc.wallet.service.impl;

import cn.gmuni.sc.wallet.mapper.PayConsumeMapper;
import cn.gmuni.sc.wallet.mapper.PayDetailMapper;
import cn.gmuni.sc.wallet.service.IPayConsumeService;
import cn.gmuni.sc.wallet.service.IPayDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PayConsumeService implements IPayConsumeService {

    @Autowired
    PayConsumeMapper mapper;

    @Override
    public List<Map<String, Object>> getPayConsumeCount(Map<String, Object> params) {
        return mapper.getPayConsumeCount(params);
    }

    @Override
    public List<Map<String, Object>> showConsumeCount(Map<String, Object> params) {
        return mapper.showConsumeCount(params);
    }

    @Override
    public List<Map<String, Object>> getCountByType(Map<String, Object> params) {
        return mapper.getCountByType(params);
    }

    @Override
    public List<Map<String, Object>> getDayPeopleAndOrder(Map<String, Object> params) {
        return mapper.getDayPeopleAndOrder(params);
    }

}
