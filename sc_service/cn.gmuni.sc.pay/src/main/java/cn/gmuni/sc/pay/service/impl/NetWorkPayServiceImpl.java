package cn.gmuni.sc.pay.service.impl;

import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.information.model.MessageModel;
import cn.gmuni.sc.information.service.MessageService;
import cn.gmuni.sc.pay.cache.CitySchoolUrlCache;
import cn.gmuni.sc.pay.cache.NetPackageCache;
import cn.gmuni.sc.pay.mapper.NetWorkPayMapper;
import cn.gmuni.sc.pay.model.NetAccountInfo;
import cn.gmuni.sc.pay.model.NetPackage;
import cn.gmuni.sc.pay.service.INetPackageService;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NetWorkPayServiceImpl extends BaseNetworkFeeService {

    @Autowired
    NetWorkPayMapper mapper;
    @Autowired
    @Qualifier("netPackageServiceImpl")
    INetPackageService netPackageServiceImpl;

    @Autowired
    @Qualifier("messageServiceImpl")
    MessageService messageService;
    /**
     * 支付宝缴费操作员id
     */
    private String OPERATOR_ID_ALIPAY = "6665";
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

    static {
        PAY_WAY_MAP.put("ali", "");
        PAY_WAY_MAP.put("onecard", "一卡通支付宝支付支付");
        PAY_WAY_MAP.put("weichat", "微信支付");
    }

    @Override
    public Object listPayDetails(Object obj) {
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        if (null != loginUser && loginUser.getStudentId() != null) {
            String schoolCode = loginUser.getSchool();
            String studentId = loginUser.getStudentId();
            /**
             * 按日期倒叙查询的数据
             */
            List<Map<String, Object>> payDetails = mapper.getPayDetails(schoolCode, studentId);
            //根据日期封装数据
            for (int i = 0; i < payDetails.size(); i++) {
                Map<String, Object> map = payDetails.get(i);
                Date pay_time = (Date) map.get("pay_time");
                map.put("pay_time", SDF.format(pay_time));
                map.put("pay_way", PAY_WAY_MAP.get(map.get("pay_way")));
            }
            return payDetails;
        }
        return null;
    }

    @Override
    public String getFlag() {
        return "netWork";
    }

    @Override
    public Object queryAccountInfo(Object obj) {
        String srtResult;
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        if (null != obj) {
            Map<String, String> params = (Map) obj;
            srtResult = getAccountInfo(params.get("account"), loginUser.getSchool());
        } else {
            srtResult = getAccountInfo(loginUser.getNetAccount(), loginUser.getSchool());
        }
        if (srtResult.startsWith("E00")) {
            NetAccountInfo model = new NetAccountInfo();
            String[] strs = srtResult.split("\t");
            model.setCode(strs[0]);
            model.setStatus(strs[1]);
            model.setStatusTime(strs[2]);
            model.setStateNote(strs[3]);
            model.setStoredValueBalance(strs[4]);
            model.setThisFee(strs[5]);
            model.setCurrentTime(strs[6]);
            model.setCurrentFlow(strs[7]);
            model.setInitialBilling(strs[8]);
            model.setPackageCode(strs[10]);
            Map o = (Map) RedisCacheUtils.build().get(NetPackageCache.NET_PACKAGE_CACHE);
            model.setPackageName("未查询到具体的套餐信息");
            model.setPackageType("1");
            if (null != o) {
                List<NetPackage> packageList = (List<NetPackage>) o.get(loginUser.getSchool());
                if (null != packageList) {
                    for (int i = 0; i < packageList.size(); i++) {
                        NetPackage netPackage = packageList.get(i);
                        if (strs[10].equals(netPackage.getCode())) {
                            model.setPackageName(netPackage.getName());
                            model.setPackageType(netPackage.getType());
                            break;
                        }
                    }
                }
            }
            return model;
        }
        return null;
    }

    @Override
    public Object getAccountState(Object obj) {
        return null;
    }

    @Override
    public Object listPlans(Object obj) {
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        if (null == loginUser) {
            return null;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("schoolCode", loginUser.getSchool());
        return netPackageServiceImpl.getNetPackage(params);
    }

    @Override
    public Object listNetPlays(Object obj) {
        return null;
    }

    @Override
    public Object stopService(Object obj) {
        Map<String, String> result = new HashMap<>();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        String cityAddr = CitySchoolUrlCache.getUrlBySchoolCode(loginUser.getSchool());
        if (StringUtils.isEmpty(cityAddr)) {
            result.put("msg", "服务器未配置该学校的缴费地址");
            return result;
        }
        cityAddr = cityAddr.replaceAll("\n","").replaceAll("\r", "");
        String netAccount = getLoginUserNetAccount();
        if (StringUtils.isEmpty(netAccount)) {
            result.put("msg", "请先绑定用户上网账号");
            return result;
        }
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String liushuihao = new Date().getTime() + netAccount;
        //先强制离线
        String para0 = "014" + netAccount + "\t" + OPERATOR_ID_ALIPAY + "\t" + liushuihao;
        HttpGet httpGet = new HttpGet(cityAddr + toBase64(para0));
        httpGet.setConfig(requestConfig);
        String str = executeClient(httpCilent, httpGet, "");
        //再报停
        String para = "004" + netAccount + "\t0\t" + OPERATOR_ID_ALIPAY + "\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        result.put("msg", getMsgByResultCode(srtResult));
        return result;
    }

    @Override
    public Object stopDelay(Object obj) {
        Map<String, String> result = new HashMap<>();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        String cityAddr = CitySchoolUrlCache.getUrlBySchoolCode(loginUser.getSchool());
        if (StringUtils.isEmpty(cityAddr)) {
            result.put("msg", "服务器未配置该学校的缴费地址");
            return result;
        }
        cityAddr = cityAddr.replaceAll("\n","").replaceAll("\r", "");
        String netAccount = getLoginUserNetAccount();
        if (StringUtils.isEmpty(netAccount)) {
            result.put("msg", "请先绑定用户上网账号");
            return result;
        }
        String dateStr = String.valueOf(obj);
        dateStr = dateStr.substring(0, 10);
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String liushuihao = new Date().getTime() + netAccount;
        String para = "008" + netAccount + "\t" + dateStr + "\t" + OPERATOR_ID_ALIPAY + "\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        result.put("msg", getMsgByResultCode(srtResult));
        return result;
    }

    @Override
    public Object startService(Object obj) {
        Map<String, String> result = new HashMap<>();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        String cityAddr = CitySchoolUrlCache.getUrlBySchoolCode(loginUser.getSchool());
        if (StringUtils.isEmpty(cityAddr)) {
            result.put("msg", "服务器未配置该学校的缴费地址");
            return result;
        }
        cityAddr = cityAddr.replaceAll("\n","").replaceAll("\r", "");
        String netAccount = getLoginUserNetAccount();
        if (StringUtils.isEmpty(netAccount)) {
            result.put("msg", "请先绑定用户上网账号");
            return result;
        }
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String liushuihao = new Date().getTime() + netAccount;
        String para = "005" + netAccount + "\t0\t" + OPERATOR_ID_ALIPAY + "\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        result.put("msg", getMsgByResultCode(srtResult));
        return result;
    }

    @Override
    public Object startDelay(Object obj) {
        Map<String, String> result = new HashMap<>();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        String cityAddr = CitySchoolUrlCache.getUrlBySchoolCode(loginUser.getSchool());
        if (StringUtils.isEmpty(cityAddr)) {
            result.put("msg", "服务器未配置该学校的缴费地址");
            return result;
        }
        cityAddr = cityAddr.replaceAll("\n","").replaceAll("\r", "");
        String netAccount = getLoginUserNetAccount();
        if (StringUtils.isEmpty(netAccount)) {
            result.put("msg", "请先绑定用户上网账号");
            return result;
        }
        String dateStr = String.valueOf(obj);
        dateStr = dateStr.substring(0, 10);
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String liushuihao = new Date().getTime() + netAccount;
        String para = "015" + netAccount + "\t" + dateStr + "\t" + OPERATOR_ID_ALIPAY + "\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        result.put("msg", getMsgByResultCode(srtResult));
        return result;
    }

    @Override
    public Object subscribePlan(Object obj) {
        Map<String, String> result = new HashMap<>();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        String cityAddr = CitySchoolUrlCache.getUrlBySchoolCode(loginUser.getSchool());
        if (StringUtils.isEmpty(cityAddr)) {
            result.put("msg", "服务器未配置该学校的缴费地址");
            return result;
        }
        cityAddr = cityAddr.replaceAll("\n","").replaceAll("\r", "");
        String netAccount = getLoginUserNetAccount();
        if (StringUtils.isEmpty(netAccount)) {
            result.put("msg", "请先绑定用户上网账号");
            return result;
        }
        Map<String, String> param = (Map) obj;
        String dateStr = param.get("time");
        String packageId = param.get("packageId");
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String liushuihao = new Date().getTime() + netAccount;
        String para = "009" + netAccount + "\t" + packageId + "\t" + dateStr + "\t" + OPERATOR_ID_ALIPAY + "\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        result.put("msg", getMsgByResultCode(srtResult));
        return result;
    }

    @Override
    public Map<String, String> checkNetAccountExist(Map<String, String> param) {
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        Map<String, String> res = new HashMap<>();
        String account = param.get("account");
        String srtResult = getAccountInfo(account, loginUser.getSchool());
        res.put("exist", "f");
        if (srtResult.startsWith("E00")) {
            res.put("exist", "t");
        } else {
            res.put("msg", "账号信息异常");
        }
        return res;
    }

    @Override
    public boolean savePayInfo(Map<String, String[]> payInfo) {
        String notify_id = payInfo.get("notify_id")[0];
        int hasPayFlag = mapper.getByNotifyId(notify_id);
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
                System.out.println("key:" + e.getKey() + "--------------------------" + "value:" + StringUtils.join(value, ","));
            } else {
                param.put(e.getKey(), null);
            }
        }
        int i = mapper.saveAliPayInfo(param);
        //保存缴费信息
        String tradeNo = payInfo.get("trade_no")[0];
        try {
            String netAccount = URLDecoder.decode(payInfo.get("passback_params")[0], "UTF-8");
            String totalAmount = payInfo.get("total_amount")[0];
            String body = payInfo.get("body")[0];
            Map<String, String> bodyParam = (Map) JSON.parse(body);
            //调用网络缴费接口
            param = new HashMap<>();
            param.put("trade_no", tradeNo);
            param.put("total_amount", totalAmount);
            param.put("net_id", netAccount);
            param.put("pay_way", "ali");//支付方式ali，weichat
            param.put("pay_status", "0");//0缴费失败6成功
            param.put("login_code", bodyParam.get("loginCode"));
            param.put("stu_code", bodyParam.get("stuCode"));
            param.put("school_code", bodyParam.get("schoolCode"));
            param.put("package_code", bodyParam.get("packageCode"));
            param.put("pay_time", new Date());
            param.put("module", "net");
            param.put("reason", "支付宝已完成缴费");
            //先将数据保存
            mapper.saveNetWorkPayInfo(param);
            System.out.println("第一先保存一条失败信息");
            Map o = (Map) doPay(netAccount, totalAmount, bodyParam.get("schoolCode"));
            param = new HashMap<>();
            param.put("tradeNo", tradeNo);
            param.put("status", o.get("status").equals("t") ? "6" : "0");
            System.out.println(o.get("msg") == null ? "缴费成功" : o.get("msg"));
            param.put("reason", o.get("msg") == null ? "缴费成功" : o.get("msg"));
            mapper.updateNetWorkPayStatusAndReason(param);

            //保存网络缴费记录
            MessageModel messageModel = new MessageModel();
            messageModel.setType("1");
            messageModel.setUserInfo(bodyParam.get("loginCode"));
            messageModel.setStudentCode(bodyParam.get("stuCode"));
            messageModel.setSchoolCode(bodyParam.get("schoolCode"));
            messageModel.setPublishTime(DateUtils.string2Date(String.valueOf(param.get("pay_time")), DateUtils.COMMON_DATETIME));
            messageModel.setCreateTime(new Date());
            messageModel.setTotalAmount(totalAmount);
            messageModel.setNetId(netAccount);
            messageModel.setNetType("0");  //0: 支出 1:收入
            messageModel.setTradeNo(tradeNo);
            messageService.add(messageModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 缴费
     * name 城市热点用户名
     * amount 缴费金额
     */
    private Object doPay(String name, String amount, String schoolCode) {
        /**
         * 缴费流程
         * 先获取用户账户基本信息，读取余额，如果出现异常，则返回
         * 城市热点发起缴费请求，如果缴费成功，则继续复通，否则返回
         */
        Map<String, String> res = new HashMap<>();
        //获取账户信息
        String srtResult = getAccountInfo(name, schoolCode);
        Double blance = 0.0;
        //扣费失败返回到前台做提示
        res.put("status", "f");
        //如果查询用户信息成功
        if (srtResult.startsWith("E00")) {
            String[] split = srtResult.split("\t");
            if (split[4] != null && !"".equals(split[4])) {
                blance = Double.parseDouble(split[4]);
            }
            //如果查询余额小于0，则提示异常
            if (blance < 0) {
                res.put("msg", "查询账号信息异常");
                return res;
            }
        } else {
            res.put("msg", "查询账号信息异常");
        }
        String liushuihao = new Date().getTime() + name;
        //城市热点费用缴纳
        Map<String, String> jiaofejieguo = feiYongYuJiao(name, amount, liushuihao, schoolCode);
        if (jiaofejieguo == null) {
            res.put("msg", "缴费出现神秘错误");
            return res;
        }
        if (jiaofejieguo.get("status").equals("f")) {
            return jiaofejieguo;
        }
        //账号复通
        Map<String, String> zhanghaoFutong = zhanghaoFutong(name, liushuihao, schoolCode);
        String code = zhanghaoFutong.get("code");
        //复通成功
        if (code.startsWith("E00") || code.startsWith("E84")) {
            res.put("status", "t");
            return res;
        } else {
            res.put("status", "f");
            res.put("msg", "账号复通异常，请联系营业厅");
            return res;
        }
    }

    private String getAccountInfo(String name, String schoolCode) {
        String cityAddr = CitySchoolUrlCache.getUrlBySchoolCode(schoolCode);
        if (StringUtils.isEmpty(cityAddr)) {
            return "";
        }
        cityAddr = cityAddr.replaceAll("\n","").replaceAll("\r", "");
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String para = "091" + name;

        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));

        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        Map<String, String> result = new HashMap<>();
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        return srtResult;
    }

    private Map<String, String> feiYongYuJiao(String name, String amount, String liushuihao, String schoolCode) {
        String cityAddr = CitySchoolUrlCache.getUrlBySchoolCode(schoolCode);
        if (StringUtils.isEmpty(cityAddr)) {
            return null;
        }
        cityAddr = cityAddr.replaceAll("\n","").replaceAll("\r", "");
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String para = "010" + name + "\t" + amount + "\t0\t" + OPERATOR_ID_ALIPAY + "\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        Map<String, String> result = new HashMap<>();
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        String status = "f";
        if (srtResult.startsWith("E00")) {
            status = "t";
        } else {
            result.put("msg", getMsgByResultCode(srtResult));
        }
        result.put("status", status);
        return result;
    }

    private Map<String, String> zhanghaoFutong(String name, String liushuihao, String schoolCode) {
        Map<String, String> result = new HashMap<>();
        String cityAddr = CitySchoolUrlCache.getUrlBySchoolCode(schoolCode);
        if (StringUtils.isEmpty(cityAddr)) {
            result.put("code", "error");
            return result;
        }
        cityAddr = cityAddr.replaceAll("\n","").replaceAll("\r", "");
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String para = "005" + name + "\t0\t" + OPERATOR_ID_ALIPAY + "\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        result.put("code", srtResult);
        return result;
    }

    private String executeClient(CloseableHttpClient httpCilent, HttpGet httpGet2, String srtResult) {
        try {
            HttpResponse httpResponse = httpCilent.execute(httpGet2);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpCilent.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return srtResult;
    }

    private RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
    }

    private String toBase64(String str) {
        try {
            return new String(Base64Utils.encode(str.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getLoginUserNetAccount() {
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        if (null == loginUser) {
            return null;
        }
        return loginUser.getNetAccount();
    }

    private String getMsgByResultCode(String resCode) {
        if (resCode.startsWith("E00")) {
            return "办理成功";
        } else if (resCode.startsWith("E14")) {
            return "账号不存在";
        } else if (resCode.startsWith("E30")) {
            return "套餐ID非法";
        } else if (resCode.startsWith("E32")) {
            return "套餐优惠ID非法";
        } else if (resCode.startsWith("E33")) {
            return "套餐优惠金额不符";
        } else if (resCode.startsWith("E34")) {
            return "存在预约套餐记录";
        } else if (resCode.startsWith("E43")) {
            return "金额非法";
        } else if (resCode.startsWith("E44")) {
            return "终端代码非法";
        } else if (resCode.startsWith("E45")) {
            return "流水号非法";
        } else if (resCode.startsWith("E46")) {
            return "日期格式非法";
        } else if (resCode.startsWith("E81")) {
            return "销账金额不符,（收费金额 + 账号余额）不足以将（历史欠费 + 中途账单）销账";
        } else if (resCode.startsWith("E82")) {
            return "账号在线";
        } else if (resCode.startsWith("E84")) {
            return "账号状态不符";
        } else if (resCode.startsWith("E99")) {
            return "未预期错误";
        } else {
            return "服务器出现其他异常，请前往营业厅办理";
        }
    }
}
