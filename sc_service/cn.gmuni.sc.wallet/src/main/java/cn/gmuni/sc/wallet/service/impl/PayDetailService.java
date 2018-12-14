package cn.gmuni.sc.wallet.service.impl;

import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.wallet.mapper.PayDetailMapper;
import cn.gmuni.sc.wallet.mapper.WalletMapper;
import cn.gmuni.sc.wallet.model.Wallet;
import cn.gmuni.sc.wallet.service.IPayDetailService;
import cn.gmuni.sc.wallet.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PayDetailService implements IPayDetailService {

    @Autowired
    PayDetailMapper mapper;


    @Override
    public List<Map<String, Object>> getPayDetailCount(Map<String, Object> params) {
        return mapper.getPayDetailCount(params);
    }

    @Override
    public List<Map<String, Object>> getShowChart(Map<String, Object> params) {
        return mapper.getShowChart(params);
    }

    @Override
    public List<Map<String, Object>> getShowList(Map<String, Object> params) {
        return mapper.getShowList(params);
    }
}
