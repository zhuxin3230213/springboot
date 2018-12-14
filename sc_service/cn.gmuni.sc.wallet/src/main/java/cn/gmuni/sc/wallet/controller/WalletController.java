package cn.gmuni.sc.wallet.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.log.anntation.SysLog;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.AliPayUtil;
import cn.gmuni.sc.wallet.model.Wallet;
import cn.gmuni.sc.wallet.service.IWalletService;
import com.alibaba.fastjson.JSON;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wallet")
public class WalletController {
    @Value("${app.server.center.addr}")
    private String serverAddr;

    @Autowired
    private IWalletService walletService;

    /**
     * 获取钱包信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/getWallet")
    @ResponseBody
    public BaseResponse<Wallet> getWallet(HttpServletRequest request) {
        Wallet wallet = walletService.getWallet();
        return new BaseResponse<>(wallet);
    }

    /**
     * 开卡
     *
     * @param param
     * @return
     */
    @RequestMapping("/openCard")
    @ResponseBody
    public BaseResponse<Wallet> openCard(@RequestBody Map<String, String> param) {
        Wallet wallet = walletService.openCard(param.get("pwd"));
        return new BaseResponse<>(wallet);
    }

    /**
     * 点击缴费
     *
     * @return
     */
    @PostMapping("/prepay")
    @SysLog(desc = "钱包充值", module = SysLogModule.PAY_LOG, type = SysLogType.OPERATOR_LOG)
    public void prepay(HttpServletRequest request, HttpServletResponse response) {
        String jine = request.getParameter("school_card_money");
        if (StringUtils.isEmpty(jine)) {
            return;
        }
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        //支付宝缴费
        String loginCode = loginUserInfo.getIndentifier();
        String stuCode = loginUser.getStudentId();
        String schoolCode = loginUser.getSchool();
        Map<String, String> body = new HashMap<>();
        body.put("loginCode", loginCode);
        body.put("stuCode", stuCode);
        body.put("schoolCode", schoolCode);
        String subject = "钱包缴费" + jine + "元";
        AliPayUtil.doPay(response, loginUserInfo.getIndentifier(), "wallet", serverAddr + "wallet/pay_success", serverAddr + "wallet/payNotice", jine, subject, JSON.toJSONString(body));
    }

    /**
     * 支付宝缴费回调
     *
     * @param request
     */
    @RequestMapping("/payNotice")
    public void payNotice(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进来回调了");
        try {
            request.setCharacterEncoding("GBK");
            walletService.savePayInfo(request.getParameterMap());
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
    @RequestMapping("/pay")
    public String pay(HttpServletRequest request, Model model) {
//        ModelAndView view = new ModelAndView("/school_card_pay");
        return "school_card_pay";
    }


    @RequestMapping("/pay_detail")
    public String payDetail(HttpServletRequest request, Model model) {
//        ModelAndView view = new ModelAndView("/school_card_pay_detail");
//        view.addObject("list",walletService.getRechargeRecord());
        model.addAttribute("list",walletService.getRechargeRecord());
        return "school_card_pay_detail";
    }

    @RequestMapping("/consume_detail")
    public String consumeDetail(HttpServletRequest request, Model model) {
//        ModelAndView view = new ModelAndView("/school_card_consume_detail");
//        view.addObject("list",walletService.getConsumeRecord());
        model.addAttribute("list",walletService.getConsumeRecord());
        return "school_card_consume_detail";
    }

    @RequestMapping("/pay_success")
    public String paySuccess(HttpServletRequest request, Model model) {
//        ModelAndView view = new ModelAndView("/school_card_pay_success");
//        view.addObject("total_amount", request.getParameter("total_amount"));
        model.addAttribute("total_amount", request.getParameter("total_amount"));
        return "school_card_pay_success";
    }

}
