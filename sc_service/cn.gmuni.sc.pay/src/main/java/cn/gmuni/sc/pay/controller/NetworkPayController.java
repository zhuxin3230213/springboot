package cn.gmuni.sc.pay.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.log.anntation.SysLog;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.log.utils.SysLogger;
import cn.gmuni.sc.pay.service.impl.BaseNetworkFeeService;
import cn.gmuni.sc.pay.utils.GmWXPayUtils;
import cn.gmuni.sc.pay.utils.PayFacade;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.AliPayUtil;
import com.alibaba.fastjson.JSON;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/networkPay")
public class NetworkPayController {

    @Value("${app.server.center.addr}")
    private String serverAddr;

    /**
     * 获取历史账单
     *
     * @return
     */
    @PostMapping("/listDetails")
    public BaseResponse<Object> listDetails() {
        return null;
    }

    /**
     * 获取上网记录
     *
     * @return
     */
    @PostMapping("/listNetPlays")
    public BaseResponse<Object> listNetPlays() {

        return null;
    }
    /**
     * 预约套餐
     *
     * @return
     */
    @PostMapping("/netSubCompo")
    public Object netSubCompo(String dateStr, String packageId) {
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        Map<String, String> param = new HashMap<>();
        param.put("time", dateStr);
        param.put("packageId", packageId);
        return service.subscribePlan(param);
    }
    /**
     * 账号复通
     *
     * @return
     */
    @PostMapping("/accountStart")
    public Object accountStart(String dateStr) {
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        if(!StringUtils.isEmpty(dateStr)){
            return service.startDelay(dateStr);
        }else{
            return service.startService(null);
        }
    }
    /**
     * 账号报停
     *
     * @return
     */
    @PostMapping("/accountStop")
    public Object accountStop(String dateStr) {
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        if(!StringUtils.isEmpty(dateStr)){
            return service.stopDelay(dateStr);
        }else{
            return service.stopService(null);
        }
    }
    /**
     * 获取缴费明细，根据需求只查询最近的50条数据
     *
     * @return
     */
    @PostMapping("/listPayDetails")
    public Object listPayDetails() {
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        return service.listPayDetails(null);
    }

    /**
     * 获取账号信息
     *
     * @return
     */
    @PostMapping("/getAccountInfo")
    public BaseResponse<Object> getAccountInfo(@RequestBody Map<String, String> param) {
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        Object model = service.queryAccountInfo(param);
        BaseResponse<Object> res = new BaseResponse<>();
        res.setData(model);
        return res;
    }

    /**
     * 查看上网账号是否存在
     *
     * @return
     */
    @PostMapping("/checkNetAccountExist")
    public BaseResponse<Map<String, String>> checkNetAccountExist(@RequestBody Map<String, String> param) {
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        Map<String, String> map = service.checkNetAccountExist(param);
        BaseResponse<Map<String, String>> res = new BaseResponse<>();
        res.setData(map);
        return res;
    }

    /**
     * 支付宝缴费回调
     *
     * @param request
     */
    @RequestMapping("/payNotice")
    public void payNotice(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进来回调了");
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        try {
            request.setCharacterEncoding("GBK");
            service.savePayInfo(request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.print("success");
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击缴费
     *
     * @return
     */
    @PostMapping("/prepay")
    @SysLog(desc = "网费充值", module = SysLogModule.PAY_LOG, type = SysLogType.OPERATOR_LOG)
    public void prepay(HttpServletRequest request, HttpServletResponse response){
        String account = request.getParameter("account");
        if (StringUtils.isEmpty(account)) {
            return;
        }
        String jine = request.getParameter("net_pay_jin_e");
        String productCode = "NET" + jine;
        if ("custom".equals(jine)) {
            jine = request.getParameter("custom_jin_e");
            productCode = "NET" + jine;
        }
        if (StringUtils.isEmpty(jine)) {
            return;
        }
        DecimalFormat df = new DecimalFormat("##0.00");
        jine = df.format(Double.parseDouble(jine));
        String paymentWay = request.getParameter("paymentWay");
        if (StringUtils.isEmpty(paymentWay)) {
            return;
        }
        String packageCode = request.getParameter("net_pay_package_code");
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        //支付宝缴费
        if ("alipay".equals(paymentWay)) {
            String loginCode = loginUserInfo.getIndentifier();
            String stuCode = loginUser.getStudentId();
            String schoolCode = loginUser.getSchool();
            Map<String, String> body = new HashMap<>();
            body.put("loginCode", loginCode);
            body.put("stuCode", stuCode);
            body.put("schoolCode", schoolCode);
            body.put("packageCode", packageCode);
            String subject = "网络预存" + jine + "元";
            AliPayUtil.doPay(response, account, productCode, serverAddr + "net/net_pay_success", serverAddr + "networkPay/payNotice", jine, subject, JSON.toJSONString(body));
        }
        // 微信缴费
        if ("wei_chat".equals(paymentWay)) {
            WXPay(request, response, jine);
        }
    }


    @RequestMapping("/wxPayRes")
    public void wxPayRes(HttpServletRequest request) {

    }


    /**
     * 微信支付
     *
     * @param request
     * @param response
     * @param jine
     */
    private void WXPay(HttpServletRequest request, HttpServletResponse response, String jine) {
        /**
         *   notifi_url:通知url
         *      *      body:商品描述
         *      *      attach:附加数据
         *      *      total_fee:总金额，单位为分
         */
        Map<String, String> data = new HashMap<>();
        //总金额
        data.put("total_fee", Integer.toString((int) (Double.parseDouble(jine) * 100)));
        //附加数据
        data.put("attach", "附加数据");
        //商品描述
        data.put("body", GmWXPayUtils.getBody("网费缴纳"));
        //通知url
        data.put("notifi_url", "https://localhost:8443/networkPay/wxPayRes");

        try {
            Map<String, String> res = GmWXPayUtils.pay(data);
            //如果支付成功
            if ("SUCCESS".equals(res.get("return_code")) && "SUCCESS".equals(res.get("result_code"))) {
                SysLogger.info("微信统一下单成功", SysLogModule.PAY_LOG, SysLogType.EXCEPTION_LOG);
                String mweb_url = res.get("mweb_url");
                response.sendRedirect(mweb_url);
            } else {
                SysLogger.error("微信统一下单失败", SysLogModule.PAY_LOG, SysLogType.EXCEPTION_LOG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
