package com.gmunidata.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gmunidata.pay.model.ChangeModel;
import com.gmunidata.pay.service.IPayService;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "pay")
public class PayController {

    @Value("${city_hot_port_addr}")
    private String cityAddr;
    @Value("${school_account_addr}")
    private String schoolAddr;

    @Value("${pay_log_file}")
    private String payLogFile;
    @Value("${package_change_log_file}")
    private String packageChangeLogFile;

    @Autowired
    @Qualifier("payService")
    IPayService payService;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "listLog/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Object listLog(@PathVariable("name") String name) {
        return payService.list(name);
    }
    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "login/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Object login(@PathVariable("name") String name) {
        String srtResult = getAccountInfo(name);
        Map<String, String> result = new HashMap<>();
        String status = "f";
        if (srtResult.startsWith("E00") && srtResult.length() > 0 && srtResult.split("\t").length > 9) {
            String[] split = srtResult.split("\t");
            status = "t";
            result.put("yue", split[4]);
            result.put("name", split[9]);
            result.put("typeId",split[10]);
            //查询用户最后一次变更套餐时间
            Date date = payService.getMaxTime(name);
            if(date==null){
                result.put("enableChange","t");
            }else{
                SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
                String d1 = df.format(new Date());
                String d2 = df.format(date);
                if(d1.equals(d2)){
                    result.put("enableChange","f");
                }else{
                    result.put("enableChange","t");
                }
            }

        } else if (srtResult.startsWith("E14")) {
            result.put("msg", "账号不存在");
        } else if (srtResult.startsWith("E99")) {
            result.put("msg", "取数据出错");
        } else {
            result.put("msg", "出现神秘未知错误，请联系网络管理中心");
        }
        result.put("status", status);

        return result;
    }

    public String getAccountInfo(String name){
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String para = "091" + name;

        HttpGet  httpGet2 = new HttpGet(cityAddr + toBase64(para));

        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        Map<String, String> result = new HashMap<>();
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        return srtResult;
    }

    /**
     * 缴费
     * name 城市热点用户名
     * cardAccount 一卡通用户名
     * pwd 一卡通密码
     */
    @RequestMapping(value = "dopay", method = RequestMethod.GET)
    @ResponseBody
    public Object doPay(@Param("name") String name, @Param("pwd") String pwd, @Param("amount") String amount,@Param("cardAccount") String cardAccount) {
        /**
         * 缴费流程
         * 先获取用户账户基本信息，读取余额，如果出现异常，则返回
         * 城市热点发起缴费请求，如果缴费成功，则继续复通，否则返回
         * 缴费成功后复通，如果复通成功，则从一卡通扣费，否则，需要重置城市热点余额
         * 复通成功，从一卡通扣费，一卡通扣费成功后，返回缴费成功，否则重置城市热点余额
         */
        Map<String, String> res = new HashMap<>();
        //获取账户信息
        String srtResult = getAccountInfo(name);
        Double blance = 0.0;
        //扣费失败返回到前台做提示
        res.put("status","f");
        //如果查询用户信息成功
        if(srtResult.startsWith("E00")){
            String[] split = srtResult.split("\t");
            if(split[4]!=null &&!"".equals(split[4])){
                blance = Double.parseDouble(split[4]);
            }
            //如果查询余额小于0，则提示异常
            if(blance<0){
                res.put("msg","查询账号信息异常");
                return res;
            }
        }else{
            res.put("msg","查询账号信息异常");
        }

        String liushuihao = new Date().getTime() + name;
        //城市热点费用缴纳
        Map<String, String> jiaofejieguo = feiYongYuJiao(name, amount, liushuihao);
        if (jiaofejieguo == null) {
            res.put("msg", "缴费出现神秘错误");
            return res;
        }
        if (jiaofejieguo.get("status").equals("f")) {
            return jiaofejieguo;
        }


        //一卡通缴费
        JSONObject jsonObject = kouFei(cardAccount, pwd, amount);
        //如果扣费失败，重置余额
        if(jsonObject == null || "".equals(jsonObject)){
            return resetBlance(name,blance,liushuihao);
        }
        //如果扣费失败，重置余额
        if(!jsonObject.getString("retCode").equals("0")){
            return resetBlance(name,blance,liushuihao,jsonObject.getString("retMsg"));
        }
        //保存扣费记录
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id",liushuihao);
            params.put("name",name);
            params.put("create_time",SDF.format(new Date()));
            params.put("amount",amount);
            payService.saveLog(params);
            logMethod(liushuihao + "\t" + name + "\t" + SDF.format(new Date()) + "\t" + amount, payLogFile);
        }catch (Exception e){
            //保存日志异常 TODO
            e.printStackTrace();
        }

//        账号复通
        Map<String, String> zhanghaoFutong = zhanghaoFutong(name, liushuihao);
        String code = zhanghaoFutong.get("code");
        //复通成功
        if (code.startsWith("E00") || code.startsWith("E84")) {
            return login(name);
        }else{
            res.put("status","f");
            res.put("msg","账号复通异常，请联系营业厅");
            return res;
        }
    }

    /**
     * 日志写入到文件
     */
    public void logMethod(String msg, String file) {
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File(file);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(msg);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  Map<String, String> resetBlance(String name,double blance,String liushuihao,String msg){
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String para = "011" + name + "\t"+blance+"\t6666\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        //账号复通失败，重置城市热点余额
        Map<String, String> res = new HashMap<>();
        if(srtResult.equals("E00")){
            if(msg==null){
                res.put("msg","缴费出现异常，您的余额已返还");
            }else{
                res.put("msg",msg);
            }

            res.put("status","f");
            return res;
        }else{
            res.put("status","t");
        }
        return res;
    }

    /**
     * 重置余额
     * @param name
     * @param blance
     * @param liushuihao
     * @return
     */
    private  Map<String, String> resetBlance(String name,double blance,String liushuihao){
       return resetBlance(name,blance,liushuihao,null);
    }


    /**
     * 变更套餐
     * @param account
     * @param typeId
     * @param money
     * @return
     */
    @RequestMapping("/change")
    @ResponseBody
    public Object change(@Param("account") String account,
                                     @Param("typeId") String typeId){
        Map<String, String> res = new HashMap<>();
        //获取账户信息
        String srtResult = getAccountInfo(account);
        Double blance = 0.0;
        //扣费失败返回到前台做提示
        res.put("status","f");
        //如果查询用户信息成功
        if(srtResult.startsWith("E00")){
            String[] split = srtResult.split("\t");
            String status = split[3];
            if(status!=null && status.indexOf("停机")>=0){
                res.put("msg","您已停机，无法变更套餐，请检查是否余额不足或联系营业厅");
                return res;
            }
            if(split[4]!=null &&!"".equals(split[4])){
                blance = Double.parseDouble(split[4]);
            }
            //如果查询余额小于0，则提示异常
            if(blance<0){
                res.put("msg","套餐变更出现异常");
                return res;
            }
        }else{
            res.put("msg","套餐变更出现异常");
        }

        //先让用户强制离线，否则无法变更套餐
        String offline = offLine(account);
        if(offline!=null){
            res.put("msg",offline);
            return res;
        }
        //保存变更套餐记录
        String liushuihao = new Date().getTime() + account;
        ChangeModel model = new ChangeModel();
        model.setId(liushuihao);
        model.setCreateTime(new Date());
        model.setExpenses(typeId);
        model.setMoney("0");
        model.setName(account);

        //变更套餐
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        //修改套餐金额为固定的0
        String para = "006"+account+"\t"+typeId+"\t"+0+"\t6666\t"+liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        Map<String, String> result = new HashMap<>();
        result.put("status","f");
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        if(srtResult.startsWith("E00")){
            //变更套餐成功，存储变更记录
            payService.saveChange(model);
//            //存储缴费记录
//            Map<String, String> params = new HashMap<>();
//            params.put("id",liushuihao);
//            params.put("name",account);
//            params.put("create_time",SDF.format(new Date()));
//            params.put("amount",money);
//            payService.saveLog(params);
//            logMethod(liushuihao + "\t" + account + "\t" + SDF.format(new Date()) + "\t" + money, packageChangeLogFile);
            return login(account);
        }else if(srtResult.startsWith("E14")){
            result.put("msg", "账号不存在");
        }else if(srtResult.startsWith("E31")){
            result.put("msg","套餐变更无效");
        }else if(srtResult.startsWith("E43")){
            result.put("msg","金额非法");
        }else if(srtResult.startsWith("E82")){
            result.put("msg","账号在线");
        }else if(srtResult.startsWith("E99")){
            result.put("msg","未预期错误");
        }else{
            result.put("msg","出现未预期异常，请联系营业厅人员");
        }
//        if("f".equals(result.get("status"))){
//            return result;
//        }
        return result;

//        //一卡通扣费
//        JSONObject jsonObject = kouFei(cardAccount, pwd, money);
//        //扣费失败返回到前台做提示
//        res.put("status","f");
//        if(jsonObject == null){
//            res.put("msg","扣费出现神秘错误");
//            return resetBlance(account,blance,liushuihao);
//        }
//        if(!jsonObject.getString("retCode").equals("0")){
//            res.put("msg",jsonObject.getString("retMsg"));
//            return res;
//        }else{
//            return resetBlance(account,blance,liushuihao);
//        }
    }

    /**
     * 变更套餐前需要将用户强制离线
     * @param name
     * @return
     */
    private String offLine(String name){
        String liushuihao = new Date().getTime() + name;
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String para = "014"+name+"\t6666\t"+liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        Map<String, String> result = new HashMap<>();
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        if(srtResult.startsWith("E00")){
            //返回null表示强制离线成功
            return null;
        }else{
            return "未预期错误";
        }
    }

    //如果缴费出现异常，则将添加的日志删除
    private void resetLog(String id){
        payService.deleteLog(id);
    }

    //如果变更套餐出现异常，则将添加的日志删除
    private void resetChange(String id){

    }

    private Map<String, String> zhanghaoFutong(String name, String liushuihao) {
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String para = "005" + name + "\t0\t6666\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        Map<String, String> result = new HashMap<>();
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

    private Map<String, String> feiYongYuJiao(String name, String amount, String liushuihao) {
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        String para = "010" + name + "\t" + amount + "\t0\t6666\t" + liushuihao;
        HttpGet httpGet2 = new HttpGet(cityAddr + toBase64(para));
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        Map<String, String> result = new HashMap<>();
        srtResult = executeClient(httpCilent, httpGet2, srtResult);
        String status = "f";
        if (srtResult.startsWith("E00")) {
            status = "t";
        } else if (srtResult.startsWith("E14")) {
            result.put("msg", "账号不存在");
        } else if (srtResult.startsWith("E32")) {
            result.put("msg", "套餐优惠ID非法");
        } else if (srtResult.startsWith("E33")) {
            result.put("msg", "套餐优惠金额不符");
        } else if (srtResult.startsWith("E43")) {
            result.put("msg", "金额非法");
        } else if (srtResult.startsWith("E44")) {
            result.put("msg", "终端代码非法");
        } else if (srtResult.startsWith("E45")) {
            result.put("msg", "流水号非法");
        } else if (srtResult.startsWith("E99")) {
            result.put("msg", "未预期错误");
        }
        result.put("status", status);
        return result;
    }

    private JSONObject kouFei(String name, String pwd, String amount) {
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        DecimalFormat df = new DecimalFormat("##0.00");
        String para = name + "/" + pwd + "/" + df.format(Double.parseDouble(amount));
        HttpGet httpGet2 = new HttpGet(schoolAddr + para);
        httpGet2.setConfig(requestConfig);
        String srtResult = "";
        try {
            HttpResponse httpResponse = httpCilent.execute(httpGet2);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
                JSONObject jsonObject = JSON.parseObject((String) JSON.parse(srtResult));
                return jsonObject;
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
        return null;
    }

    private String toBase64(String str){
        try {
            return new String( Base64Utils.encode(str.getBytes()),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
