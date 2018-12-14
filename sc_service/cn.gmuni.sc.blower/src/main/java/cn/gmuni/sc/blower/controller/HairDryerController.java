package cn.gmuni.sc.blower.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:ZhuXin
 * @Description: app扫码，跳转吹风机支付页面
 * @Date:Create 2018/12/6 10:18
 * @Modified By:
 **/
@Controller
@RequestMapping(value = "/hairDryer")
public class HairDryerController {

    /**
     * 正常跳转
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
     * status "2"
     * 该用户未支付状态
     *
     * @param deviceId
     * @param status
     * @param startTime
     * @param workTime
     * @param model
     * @return
     */
    @RequestMapping(value = "/to/lock/{deviceId}/{status}/{startTime}/{workTime}")
    public String toLock(@PathVariable("deviceId") String deviceId, @PathVariable("status") String status,
                         @PathVariable("startTime") String startTime, @PathVariable("workTime") String workTime,
                         Model model) {
        model.addAttribute("status", status);
        model.addAttribute("startTime", startTime);
        model.addAttribute("workTime", workTime);
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
        model.addAttribute("status", status);
        model.addAttribute("deviceId", deviceId);
        model.addAttribute("startTime", startTime);
        model.addAttribute("workTime", workTime);
        return "hairDryer_countdown";
    }
}

