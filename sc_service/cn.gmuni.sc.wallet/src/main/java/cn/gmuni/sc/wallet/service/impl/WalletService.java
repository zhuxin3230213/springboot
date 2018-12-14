package cn.gmuni.sc.wallet.service.impl;

import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.wallet.mapper.WalletMapper;
import cn.gmuni.sc.wallet.model.Wallet;
import cn.gmuni.sc.wallet.model.WalletPayment;
import cn.gmuni.sc.wallet.model.WalletRecharge;
import cn.gmuni.sc.wallet.service.IWalletService;
import cn.gmuni.utils.DesUtils;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.Md5Util;
import com.alibaba.fastjson.JSON;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WalletService implements IWalletService {

    @Autowired
    WalletMapper mapper;

    @Autowired
    /**
     * 阿里返回的日期类型的数据的key
     * yyyy-MM-dd HH:mm:ss
     */
    private static final List<String> ALI_PAY_NOTIFY_DATE_KEYS = Arrays.asList(new String[]{"notify_time", "gmt_create", "gmt_payment", "gmt_close"});
    /**
     * 阿里返回的日期类型的数据的key
     * yyyy-MM-dd HH:mm:ss.S
     */
    private static final List<String> ALI_PAY_NOTIFY_DATE_KEYS_MILES = Arrays.asList(new String[]{"gmt_refund"});

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat SDF_MILES = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    @Override
    public boolean savePayInfo(Map<String, String[]> payInfo) {
        String notify_id = payInfo.get("notify_id")[0];
        int hasPayFlag = mapper.getByNotifyId(notify_id);
        if (hasPayFlag > 0) {
            System.out.println("该订单已处理过");
            return true;
        }
        Map<String, Object> param = new HashMap<>();
        //保存支付宝支付信息
        Set<Map.Entry<String, String[]>> entries = payInfo.entrySet();
        for (Map.Entry<String, String[]> item : entries) {
            String[] value = item.getValue();
            String key = item.getKey();
            if (null != value) {
                String val = value[0];
                if (ALI_PAY_NOTIFY_DATE_KEYS.indexOf(key) > -1) {
                    try {
                        param.put(key, SDF.parse(val));
                    } catch (Exception e) {
                        param.put(key, null);
                        e.printStackTrace();
                    }
                } else if (ALI_PAY_NOTIFY_DATE_KEYS_MILES.indexOf(key) > -1) {
                    try {
                        param.put(key, SDF_MILES.parse(val));
                    } catch (Exception e) {
                        param.put(key, null);
                        e.printStackTrace();
                    }
                } else {
                    param.put(key, StringUtils.join(value, ","));
                }
            } else {
                param.put(item.getKey(), null);
            }
        }
        int i = mapper.saveAliPayInfo(param);
        //保存缴费信息
        String tradeNo = payInfo.get("trade_no")[0];
        try {
            String account = URLDecoder.decode(payInfo.get("passback_params")[0], "UTF-8");
            String totalAmount = payInfo.get("total_amount")[0];
            String body = payInfo.get("body")[0];
            Map<String, String> bodyParam = (Map) JSON.parse(body);
            WalletRecharge recharge = new WalletRecharge();
            String id = IdGenerator.getId();
            recharge.setId(id);
            recharge.setAmount(totalAmount);
            recharge.setAccount(account);
            Date currentTime = new Date();
            recharge.setPayTime(currentTime);
            recharge.setCreateTime(currentTime);
            recharge.setEndTime(currentTime);
            recharge.setOrderNumber(tradeNo);
            recharge.setSeriesNumber("gmuni" + currentTime.getTime());
            recharge.setStatus("1");
            recharge.setPayMode("0");
            recharge.setRemark("支付宝缴费成功，待入账");
            mapper.saveRechargeInfo(recharge);
            Map<String, Object> accountParam = new HashMap<>();
            accountParam.put("amount", totalAmount);
            accountParam.put("userCode", account);
            accountParam.put("updateTime",new Date());
            mapper.addWalletMoney(accountParam);
            recharge.setStatus("2");
            recharge.setRemark("已入账");
            mapper.updateRechargeInfo(recharge);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Wallet getWallet() {
        String userCode = UserUtils.getLoginUserInfo().getIndentifier();
        return mapper.selectByUserCode(userCode);
    }

    @Override
    public Wallet openCard(String pwd) {
        String userCode = UserUtils.getLoginUserInfo().getIndentifier();
        Wallet wallet = new Wallet();
        wallet.setId(IdGenerator.getId());
        wallet.setAmount(0d);
        Date currentDate = new Date();
        wallet.setCreateTime(currentDate);
        wallet.setUpdateTime(currentDate);
        wallet.setSchoolCode(UserUtils.getLoginUser().getSchool());
        wallet.setPwd(Md5Util.encode(DesUtils.decode(pwd, UserUtils.getLoginUserInfo().getIndentifier())));
        wallet.setUserCode(userCode);
        mapper.saveInfo(wallet);
        return wallet;
    }

    @Override
    public List<Map<String, Object>> getRechargeRecord() {
        String account = UserUtils.getLoginUserInfo().getIndentifier();
        return mapper.getRechargeRecord(account);
    }

    @Override
    public List<Map<String, Object>> getConsumeRecord() {
        String buyerId = UserUtils.getLoginUserInfo().getIndentifier();
        return mapper.getConsumeRecord(buyerId);
    }

}
