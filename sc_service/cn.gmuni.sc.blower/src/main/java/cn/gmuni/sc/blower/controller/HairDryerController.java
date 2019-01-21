package cn.gmuni.sc.blower.controller;

import cn.gmuni.sc.blower.service.IBlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description: app扫码，跳转吹风机支付页面
 * @Date:Create 2018/12/6 10:18
 * @Modified By:
 **/
@Controller
@RequestMapping(value = "/hairDryer")
public class HairDryerController {


    @Autowired
    @Qualifier("blowerServiceImpl")
    IBlowerService blowerServiceImpl;

    /**
     * 正常跳转
     *
     * @param deviceId
     * @param model
     * @return
     */
    @RequestMapping(value = "/to/{deviceId}")
    public String HairDryer(@PathVariable("deviceId") String deviceId, Model model) {
        model.addAttribute("deviceId", deviceId);
        return "hairDryer";
    }

    /**
     * status "3"
     * 该用户正在正常使用
     *
     * @param deviceId
     * @param status
     * @param startTime
     * @param workTime
     * @param model
     * @return
     */
    @RequestMapping(value = "/to/use/{deviceId}/{status}/{startTime}/{workTime}")
    public String toUse(@PathVariable("deviceId") String deviceId, @PathVariable("status") String status,
                        @PathVariable("startTime") String startTime, @PathVariable("workTime") String workTime,
                        Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("workTime", workTime);
        map.put("time", startTime);
        map.put("mark", "IsUsing");
        map.put("flag","true");
        map.put("type","我的钱包");
        map.put("msg","继续使用");
        model.addAttribute("msg", map);
        return "hairDryer_success";
    }

    @RequestMapping(value = "/hairDryer_success")
    public ModelAndView hairDryerSuccess(HttpServletRequest request) {
        System.out.println("===================支付成功的returnUrl回调=================");
        String outTradeNo = request.getParameter("out_trade_no");
        String sign = request.getParameter("sign");
        Map<String, Object> map = blowerServiceImpl.refundALi(outTradeNo, sign);
        if (map.get("workTime") == "" || map.get("workTime") == null) {
            map.put("workTime", "");
        }
        if (map.get("time")==""||map.get("time")==null){
            map.put("time","");
        }
        ModelAndView model = new ModelAndView();
        map.put("type", "支付宝");
        map.put("mark", "");
        model.addObject("msg", map);
        model.setViewName("hairDryer_success");
        return model;
    }


}

