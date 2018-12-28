package cn.gmuni.sc.blower.service.impl;

import cn.gmuni.sc.blower.cache.BlowerCache;
import cn.gmuni.sc.blower.control.BlowerThread;
import cn.gmuni.sc.blower.control.BlowerThreadHardware;
import cn.gmuni.sc.blower.mapper.BlowerMapper;
import cn.gmuni.sc.blower.service.IBlowerService;
import cn.gmuni.sc.device.controller.BlowerDervice;
import cn.gmuni.sc.pay.mapper.NetWorkPayMapper;
import cn.gmuni.sc.utils.AliPayUtil;
import cn.gmuni.sc.utils.DateUtils;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import cn.gmuni.sc.wallet.mapper.WalletMapper;
import cn.gmuni.sc.wallet.model.WalletPayment;
import cn.gmuni.utils.IdGenerator;
import cn.gmuni.utils.Md5Util;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        Map<String,Object> map = new HashMap<>();
        if(mapStatus==null){
            return map;
        }
        String status = String.valueOf(mapStatus.get("status"));
        if (status.equals("1")){
            params.put("status","3");
            params.put("updateTime",new Date());
            params.put("workTime",1);
            map.put("flag",true);
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
                if (status.equals("2")){
                    map.put("status","2");
                    map.put("time",mapStatus.get("updateTime"));
                    map.put("workTime",mapStatus.get("workTime"));
                }
                if (status.equals("3")){

                    try {
                        thread.interrupt();
                    }catch (Exception e){}
                    params.put("status","3");
                    params.put("updateTime",new Date());
                    params.put("workTime",1);
                    mapper.lockStatus(params);
                    thread = new BlowerThread(params,mapper);
                    thread.start();
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
            map.put("flag","true");
            map.put("message","取消锁定成功！");
        }else {
            map.put("flag","false");
            map.put("message","取消锁定失败！");
        }
        return map;
    }

    @Override
    public Map<String, Object> endBlowerStatus(Map<String, Object> params) {
        Map<String,Object> map =new HashMap<>();
        try {
            thread.interrupt();
        }catch (Exception e){}
        String blowerCode =(String) params.get("blowerCode");
        Map<String,Object> maps = mapper.selectDeviceByCode(blowerCode);
        Map res = new BlowerDervice().requestDevice("close",maps);
        if (res.get("flag").equals("false")){
            BlowerCache.addBlowerClose(maps);
            map.put("flag","false");
            map.put("message","吹风机关闭异常！");
            return map;
        }
        int f = mapper.initStatus(params);
        if (f > 0){
            map.put("flag","true");
            map.put("message","吹风机中断成功！");
        }else {
            map.put("flag","false");
            map.put("message","吹风机中断失败！");
        }
        return map;
    }

    @Override
  /*  @SysLog(desc = "吹风机一卡通支付", module = SysLogModule.CONSUME_LOG, type = SysLogType.OPERATOR_LOG)*/
    public Map<String,Object> blowerPayByCard(Map<String, Object> params){
        Map<String,Object> map = new HashMap<>();

        //查询余额是否够支付，如果不够则返回
        Map<String,Object> temp = mapper.selectOneCardJinE(params);
        String pwd = (String) temp.get("pwd");
        if (!pwd.equals(Md5Util.encode(String.valueOf(params.get("password"))))){
            map.put("flag","false");
            map.put("msg","密码错误");
            return map;
        }
        Double yue = Double.valueOf(String.valueOf(temp.get("amount")));
        Double chargeJinE = Double.valueOf((String) params.get("chargeJinE"));
        if (yue >=  chargeJinE){
          int  i =  mapper.oneCardPayment(params);
          if (i > 0){
              Double yue1 = Double.valueOf(String.valueOf( mapper.selectOneCardJinE(params).get("amount")));
              if (yue1 < 0){
                  mapper.oneCardrefund(params);
                  map.put("flag","false");
                  map.put("msg","交易失败！");
                  return map;
              }else {
                  WalletPayment payment= new WalletPayment();
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
                      Map<String,String> res = new BlowerDervice().requestDevice("open",maps);
                      map.put("flag",res.get("flag"));
                        //异常,退费，关闭
                      if (res.get("flag").equals("false")){
                          mapper.oneCardrefund(params);
                          mapper.initStatus(params);
                          payment.setId(IdGenerator.getId());
                          payment.setAmount("-"+String.valueOf(params.get("chargeJinE")));
                          payment.setBuyerId("gmuni");
                          payment.setSellerId((String) params.get("userCode"));
                          payment.setTitle("共享吹风机-"+chargeJinE+"元");
                          payment.setRemark("退款成功");
                          Date currentTime1 = new Date();
                          payment.setPayTime(currentTime1);
                          payment.setCreateTime(currentTime1);
                          payment.setEndTime(currentTime1);
                          payment.setSeriesNumber("gmuni"+params.get("userCode")+ currentTime1.getTime());
                          walletMapper.saveBlowerPayment(payment);
                          map.put("msg",res.get("msg"));
                          return map;
                      }
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
              map.put("flag","false");
              map.put("msg","扣费失败");
          }
        }else {
            map.put("flag","false");
            map.put("msg","余额不足");
        }
        Map tempMap = mapper.getBlowerStatus(params);
        map.put("msg","交易成功");
        map.put("time",tempMap.get("updateTime"));
        map.put("workTime",tempMap.get("workTime"));
        return map;

    }

    @Override
    public boolean saveBlowerPayInfo(Map<String, String[]> payInfo) {
        String outTradeNo = payInfo.get("out_trade_no")[0];
        int hasPayFlag = netWorkPayMapper.getByOutTradeNo(outTradeNo);
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
        //try {
            String totalAmount = payInfo.get("total_amount")[0];
            Map<String, String> body = (Map) JSON.parse(payInfo.get("body")[0]);
            //调用网络缴费接口
            payment.setId(IdGenerator.getId());
            payment.setAmount(totalAmount);
            payment.setBuyerId(String.valueOf(param.get("buyer_id")));
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
           return true;
    }

    @Override
    public Map<String,Object> refundALi(String outTradeNo,String sign) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("outTradeNo",outTradeNo);
        Map<String,Object> map = mapper.selectALiBody(params);
        String amount = String.valueOf(map.get("amount"));
        String tradeNo = String.valueOf(map.get("tradeNo"));
        Map<String, String> body = JsonUtil.json2Object((String) map.get("body"),Map.class);
        String blowerCode = body.get("blowerId");
        //定时关掉
        params.put("userCode", body.get("loginCode"));
        params.put("status", "2");
        Date date = new Date();
        params.put("updateTime", date);
        params.put("workTime", body.get("workTime"));
        params.put("schoolCode", body.get("schoolCode"));
        params.put("blowerCode", blowerCode);
        Map<String, Object> maps = mapper.selectDeviceByCode(blowerCode);
        Map res = new BlowerDervice().requestDevice("open", maps);
        if (res.get("flag").equals("false")) {
            try {
                thread.interrupt();
                mapper.initStatus(params);
            } catch (Exception e) {
            }
            //吹风机通知失败，支付宝退款
            //先插入一条退款记录状态为失败
            String id = IdGenerator.getId();
            WalletPayment payment = new WalletPayment();
            payment.setId(id);
            payment.setAmount("-" + amount);
            payment.setBuyerId(String.valueOf(map.get("sellerId")));
            payment.setSellerId(String.valueOf(map.get("buyerId")));
            payment.setTitle("共享吹风机-" + amount+ "元");
            payment.setRemark("退款失败");
            payment.setStatus("2");
            Date currentTime1 = new Date();
            payment.setPayTime(currentTime1);
            payment.setCreateTime(currentTime1);
            payment.setEndTime(currentTime1);
            payment.setPaymentType("共享吹风机");
            payment.setPayMode("0");
            payment.setSign(sign);
            payment.setOrderNumber(tradeNo);
            payment.setSeriesNumber("gmuni"+body.get("loginCode")+ currentTime1.getTime());
            walletMapper.saveBlowerPayment(payment);
            boolean flag = false;
            try {
                flag = AliPayUtil.execute(outTradeNo,tradeNo,amount);
            } catch (AlipayApiException e) {
                result.put("flag", "false");
                result.put("msg", "交易退款失败，请联系人工客服。");
                return result;
            }
            if (flag) {
                payment.setRemark("退款成功");
                payment.setStatus("1");
                walletMapper.updatePayment(payment);
                result.put("msg", "交易退款成功，请在两小时内在支付宝查收。");
            } else {
                result.put("msg", "交易退款失败，请联系人工客服。");
            }
            result.put("flag", "false");
            return result;
        }
        //定时关闭
        mapper.lockStatus(params);
        try {
            thread.interrupt();
        } catch (Exception e) {
        }
        thread = new BlowerThreadHardware(params, mapper);
        thread.start();
        result.put("flag", "true");
        result.put("msg", "交易成功");
        result.put("time",DateUtils.date2String(date,DateUtils.COMMON_DATETIME));
        result.put("workTime", body.get("workTime"));
        return result;
    }
}
