package cn.gmuni.sc.pay.controller;

import cn.gmuni.sc.pay.service.impl.BaseNetworkFeeService;
import cn.gmuni.sc.pay.utils.PayFacade;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/net")
public class NetWorkPayPageController {
    @RequestMapping("/pay")
    public String pay(Model model){
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        model.addAttribute("data", loginUser.getNetAccount());
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        Object o = service.queryAccountInfo(null);
        model.addAttribute("info", o);
        return "net_pay";
    }

    /**
     * 支付明细
     * @return
     */
    @RequestMapping("/net_pay_detail")
    public String payDetail(Model model){
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        Object o = service.listPayDetails(null);
        model.addAttribute("data", o);
        return "net_pay_detail";
    }

    /**
     * 上网记录
     * @return
     */
    @RequestMapping("/net_record")
    public String netRecord(){
        return "net_record";
    }

    /**
     * 上网记录明细
     * @return
     */
    @RequestMapping("/net_record_detail")
    public String netRecordDetail(){
        return "net_record_detail";
    }

    /**
     * 报停
     * @return
     */
    @RequestMapping("/net_stop")
    public String netStop(Model model){
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        Object o = service.queryAccountInfo(null);
        model.addAttribute("data", o);
        return "net_stop";
    }

    /**
     * 报停记录
     * @return
     */
    @RequestMapping("/net_stop_detail")
    public String netStopDetail(){
        return "net_stop_detail";
    }

    /**
     * 历史账单
     * @return
     */
    @RequestMapping("/net_history_bill")
    public String netHistoryBill(){
        return "net_history_bill";
    }

    /**
     * 预约套餐
     * @return
     */
    @RequestMapping("/net_sub_combo")
    public String netSubCompo(Model model){
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        Object o = service.listPlans(null);
        model.addAttribute("data", o);
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        if (null != loginUser) {
            Map<String, String> param = new HashMap<>();
            param.put("account", loginUser.getNetAccount());
            model.addAttribute("account", service.queryAccountInfo(param));
        }
        return "net_sub_combo";
    }

    /**
     * 预约套餐记录
     * @return
     */
    @RequestMapping("/net_sub_record")
    public String netSubRecord(){
        return "net_sub_record";
    }


    /**
     * 缴费成功
     * @return
     */
    @RequestMapping("/net_pay_success")
    public String netPaySuccess(HttpServletRequest request, Model model){
        model.addAttribute("total_amount", request.getParameter("total_amount"));
        return "net_pay_success";
    }

    /**
     * 复通
     * @return
     */
    @RequestMapping("/net_start_using")
    public String netStartUsing(Model model){
        BaseNetworkFeeService service = PayFacade.getService(PayFacade.NET_WORK_SERVICE_FLAG);
        Object o = service.queryAccountInfo(null);
        model.addAttribute("data", o);
        return "net_start_using";
    }
}
