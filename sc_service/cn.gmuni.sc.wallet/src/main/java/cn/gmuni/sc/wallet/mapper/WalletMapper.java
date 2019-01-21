package cn.gmuni.sc.wallet.mapper;

import cn.gmuni.sc.wallet.model.Wallet;
import cn.gmuni.sc.wallet.model.WalletPayment;
import cn.gmuni.sc.wallet.model.WalletRecharge;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface WalletMapper {

    /**
     * 查询钱包信息
     * @param userCode
     * @return
     */
    Wallet selectByUserCode(@Param("userCode") String userCode);

    int saveInfo(Wallet w);

    /**
     * 保存支付宝支付结果信息
     * @return
     */
    int saveAliPayInfo(Map<String, Object> params);
    /**
     * 查询当前的支付宝通知id是否已处理
     * @param notifyId
     * @return
     */
    int getByNotifyId(@Param("notifyId") String notifyId);

    /**
     * 根据订单id是否已处理
     * @param outTradeNo
     * @return
     */
    int getByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    /**
     * 保存钱包充值信息
     * @param recharge
     * @return
     */
    int saveRechargeInfo(WalletRecharge recharge);

    /**
     * 更新钱包充值信息
     * @param recharge
     * @return
     */
    int updateRechargeInfo(WalletRecharge recharge);

    /**
     * 给账户添加金额
     * @param paramn
     * @return
     */
    int addWalletMoney(Map<String, Object> paramn);

    List<Map<String,Object>> getRechargeRecord(@Param("account") String account);

    List<Map<String,Object>> getConsumeRecord(@Param("buyerId") String buyerId);

    /**
     * 添加吹风机的消费记录
     * @return
     */
   int saveBlowerPayment(WalletPayment walletPayment);

    /**
     * 修改退款记录状态
     * @return
     */
    int updatePayment(WalletPayment walletPayment);
}
