package cn.gmuni.sc.wallet.service;

import cn.gmuni.sc.wallet.model.Wallet;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IWalletService {
    Wallet getWallet();

    Wallet openCard(String pwd);

    /**
     * 保存支付信息，并充值
     *
     * @param payInfo
     * @return
     */
    boolean savePayInfo(Map<String, String[]> payInfo);

    List<Map<String,Object>> getRechargeRecord();

    List<Map<String,Object>> getConsumeRecord();

}
