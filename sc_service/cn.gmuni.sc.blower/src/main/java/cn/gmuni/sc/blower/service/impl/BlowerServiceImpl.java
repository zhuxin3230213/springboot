package cn.gmuni.sc.blower.service.impl;

import cn.gmuni.sc.blower.control.BlowerThread;
import cn.gmuni.sc.blower.control.BlowerThreadHardware;
import cn.gmuni.sc.blower.mapper.BlowerMapper;
import cn.gmuni.sc.blower.service.IBlowerService;
import cn.gmuni.sc.device.controller.BlowerDervice;
import cn.gmuni.sc.log.anntation.SysLog;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.pay.mapper.NetWorkPayMapper;
import cn.gmuni.sc.wallet.mapper.WalletMapper;
import cn.gmuni.sc.wallet.model.WalletPayment;
import cn.gmuni.utils.IdGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BlowerServiceImpl implements IBlowerService {

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
    private static Map<String, String> PAY_WAY_MAP = new HashMap<>();

    private Thread thread;

    @Autowired
    WalletMapper walletMapper;

    @Autowired
    BlowerMapper mapper;



    @Autowired
    NetWorkPayMapper netWorkPayMapper;

    @Override
    public Map<String, Object> getBlowerStatus(Map<String, Object> params) {
        Map mapStatus =mapper.getBlowerStatus(params);
        String status = String.valueOf(mapStatus.get("status"));
        Map<String,Object> map = new HashMap<>();
        if (status.equals("1")){
            map.put("flag",true);
            params.put("status","3");
            params.put("updateTime",new Date());
            params.put("workTime",1);
            //吹风机锁定
            mapper.lockStatus(params);
            thread = new BlowerThread(params,mapper);
            thread.start();
        }else {
            if (status.equals("0")){
                map.put("flag",false);
                map.put("status","0");
                map.put("message","机器故障，无法使用！");
                return map;
            }
            String userCode = String.valueOf(mapper.getBlowerStatus(params).get("userCode"));
            if (!params.get("userCode").equals(userCode)){
                map.put("flag",false);
                if (status.equals("2")||status.equals("3")){
                    map.put("status","1");
                    map.put("message","机器正在使用中，请稍后再试！");
                }
            }else {
                map.put("flag",false);
                map.put("time",mapStatus.get("updateTime"));
                map.put("workTime",mapStatus.get("workTime"));
                if (status.equals("2")){
                    map.put("status","2");
                }
                if (status.equals("3")){
                    map.put("status","3");
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> cancelBlowerLock(Map<String, Object> params) {
        try {
            thread.interrupt();
        }catch (Exception e){}
        int f = mapper.initStatus(params);
        Map<String,Object> map =new HashMap<>();
        if (f > 0){
            map.put("flag",true);
            map.put("message","取消锁定成功！");
        }else {
            map.put("flag",false);
            map.put("message","取消锁定失败！");
        }
        return map;
    }

    @Override
    public Map<String, Object> endBlowerStatus(Map<String, Object> params) {
        try {
            thread.interrupt();
        }catch (Exception e){}
        String blowerCode =(String) params.get("blowerCode");
        Map<String,Object> maps = mapper.selectDeviceByCode(blowerCode);
        new BlowerDervice().requestDevice("close",maps);
        int f = mapper.initStatus(params);
        Map<String,Object> map =new HashMap<>();
        if (f > 0){
            map.put("flag",true);
            map.put("message","吹风机中断成功！");
        }else {
            map.put("flag",false);
            map.put("message","吹风机中断失败！");
        }
        return map;
    }

    @Override
  /*  @SysLog(desc = "吹风机一卡通支付", module = SysLogModule.CONSUME_LOG, type = SysLogType.OPERATOR_LOG)*/
    public Map<String,Object> blowerPayByCard(Map<String, Object> params){
        Map<String,Object> map = new HashMap<>();
        params.put("workTime",3);
        //查询余额是否够支付，如果不够则返回
        Double yue = Double.valueOf(mapper.selectOneCardJinE(params));
        Double chargeJinE = Double.valueOf((String) params.get("chargeJinE"));
        if (yue >=  chargeJinE){
          int  i =  mapper.oneCardPayment(params);
          if (i > 0){
              Double yue1 = Double.valueOf(mapper.selectOneCardJinE(params));
              if (yue1 < 0){
                  mapper.oneCardrefund(params);
              }else {
                  WalletPayment payment= new WalletPayment();
                      //调用网络缴费接口
                      String id =IdGenerator.getId();
                      payment.setId(id);
                      payment.setAmount(String.valueOf(params.get("chargeJinE")));
                      payment.setBuyerId((String) params.get("userCode"));
                      payment.setSellerId("gmuni");
                      payment.setPaymentType("共享吹风机");
                      payment.setStatus("1");
                      payment.setTitle("共享吹风机"+chargeJinE+"元");
                      payment.setRemark("支付成功");
                      Date currentTime = new Date();
                      payment.setPayTime(currentTime);
                      payment.setCreateTime(currentTime);
                      payment.setEndTime(currentTime);
                      payment.setOrderNumber("gmuni" + id);
                      payment.setSeriesNumber("gmuni"+params.get("userCode")+ currentTime.getTime());
                      payment.setPayMode("1");
                      //先将数据保存
                      walletMapper.saveBlowerPayment(payment);
                      String blowerCode =(String) params.get("blowerCode");
                      Map<String,Object> maps = mapper.selectDeviceByCode(blowerCode);
                      new BlowerDervice().requestDevice("open",maps);
                    //定时关掉
                     params.put("status","2");
                     params.put("updateTime",new Date());
                     mapper.lockStatus(params);
                     try {
                         thread.interrupt();
                     }catch (Exception e){}
                     thread=new BlowerThreadHardware(params,mapper);
                     thread.start();
              }
          }else {
              map.put("flag",false);
              map.put("message","扣费失败!");
          }

        }else {
            map.put("flag",false);
            map.put("message","余额不足!");

        }
        return map;

    }

    @Override
    public boolean saveBlowerPayInfo(Map<String, String[]> payInfo) {
        String notify_id = payInfo.get("notify_id")[0];
        int hasPayFlag = netWorkPayMapper.getByNotifyId(notify_id);
        if (hasPayFlag > 0) {
            System.out.println("该订单已处理过");
            return true;
        }
        Map<String, Object> param = new HashMap<>();
        //TODO 判断支付结果code？？？？
        //保存支付宝支付信息
        Set<Map.Entry<String, String[]>> entries = payInfo.entrySet();
        for (Map.Entry<String, String[]> e : entries) {
            String[] value = e.getValue();
            String key = e.getKey();
            if (null != value) {
                String val = value[0];
                if (ALI_PAY_NOTIFY_DATE_KEYS.indexOf(key) > -1) {
                    try {
                        param.put(key, SDF.parse(val));
                    } catch (Exception ex) {
                        param.put(key, null);
                        ex.printStackTrace();
                    }
                } else if (ALI_PAY_NOTIFY_DATE_KEYS_MILES.indexOf(key) > -1) {
                    try {
                        param.put(key, SDF_MILES.parse(val));
                    } catch (Exception ex) {
                        param.put(key, null);
                        ex.printStackTrace();
                    }
                } else {
                    param.put(key, StringUtils.join(value, ","));
                }
            } else {
                param.put(e.getKey(), null);
            }
        }
         netWorkPayMapper.saveAliPayInfo(param);
        WalletPayment payment= new WalletPayment();
        //保存缴费信息
        String tradeNo = payInfo.get("trade_no")[0];
        try {
            String totalAmount = payInfo.get("total_amount")[0];
            Map<String, String> body = (Map) JSON.parse(payInfo.get("body")[0]);
            //调用网络缴费接口
            payment.setId(IdGenerator.getId());
            payment.setAmount(totalAmount);
            payment.setBuyerId(String.valueOf(param.get("buyer_logon_id")));
            payment.setSellerId(String.valueOf(param.get("seller_id")));
            payment.setPaymentType("共享吹风机");
            payment.setStatus("1");
            payment.setSign(String.valueOf(param.get("sign")));
            payment.setTitle(body.get("subject"));
            payment.setRemark("支付成功");
            Date currentTime = new Date();
            payment.setPayTime(currentTime);
            payment.setCreateTime(currentTime);
            payment.setEndTime(currentTime);
            payment.setOrderNumber(tradeNo);
            payment.setSeriesNumber("gmuni"+body.get("loginCode")+ currentTime.getTime());
            payment.setPayMode("0");
            //先将数据保存
            walletMapper.saveBlowerPayment(payment);
            String blowerCode = body.get("blowerId");
            Map<String,Object> maps = mapper.selectDeviceByCode(blowerCode);
            System.out.println(maps.size());
            new BlowerDervice().requestDevice("open",maps);
            //定时关掉
            Map<String,Object> params = new HashMap<>();
            params.put("userCode",body.get("loginCode"));
            params.put("status","2");
            params.put("updateTime",new Date());
            params.put("workTime",body.get("workTime"));
            params.put("schoolCode",body.get("schoolCode"));
            params.put("blowerCode",blowerCode);
            mapper.lockStatus(params);
            try {
                thread.interrupt();
            }catch (Exception e){}
            thread=new BlowerThreadHardware(params,mapper);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
