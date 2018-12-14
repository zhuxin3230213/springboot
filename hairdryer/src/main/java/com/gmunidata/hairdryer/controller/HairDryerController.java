package com.gmunidata.hairdryer.controller;

import com.gmunidata.hairdryer.port.PortPool;
import com.gmunidata.hairdryer.port.SerialPortManager;
import gnu.io.SerialPort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("hairDryer")
public class HairDryerController {

    @RequestMapping("open")
    @ResponseBody
    public Map<String, String> open(@RequestParam Map<String, String> param) {
        Map<String, String> res = new HashMap<>();
        String usbPort = param.get("usbPort");
        String serialNumber = param.get("serialNumber");
        if(StringUtils.isEmpty(usbPort) || StringUtils.isEmpty(serialNumber)){
            res.put("msg", "参数有误");
            return res;
        }
        try {
            SerialPort com = PortPool.getPort(usbPort);
            //先读一下状态，判断是否有人使用
            byte[] bytes = SerialPortManager.readFromPort(com);
            if (null != bytes && bytes.length > 0) {
                String stat = new String(bytes);
                if (stat.indexOf("!010100") > -1) {
                    res.put("msg", "已有人使用，请稍后尝试");
                    return res;
                }
            }
            String command = "#" + serialNumber + "01";
            byte[] b = command.getBytes();
            byte[] cmdBytes = new byte[8];
            for (int i = 0; i < b.length; i++) {
                cmdBytes[i] = b[i];
            }
            cmdBytes[7] = 13;

            SerialPortManager.sendToPort(com, cmdBytes);
            //必须要这个线程延迟，防止读不到工作状态
            Thread.sleep(100);
            //再读取一次状态，判断是否已供电
            bytes = SerialPortManager.readFromPort(com);
            if (null == bytes || bytes.length == 0) {
                res.put("msg", "吹风机供电失败");
                return res;
            }
            res.put("msg", "吹风机已供电，请放心使用");
        } catch (Exception e) {
            res.put("msg", "系统异常，请稍后尝试");
        }
        return res;
    }

    @RequestMapping("close")
    @ResponseBody
    public Map<String, String> close(@RequestParam Map<String, String> param) {
        Map<String, String> res = new HashMap<>();
        String usbPort = param.get("usbPort");
        String serialNumber = param.get("serialNumber");
        if(StringUtils.isEmpty(usbPort) || StringUtils.isEmpty(serialNumber)){
            res.put("msg", "参数有误");
            return res;
        }
        try {
            SerialPort com = PortPool.getPort(usbPort);
            String command = "#" + serialNumber + "00";
            byte[] b = command.getBytes();
            byte[] cmdBytes = new byte[8];
            for (int i = 0; i < b.length; i++) {
                cmdBytes[i] = b[i];
            }
            cmdBytes[7] = 13;
            SerialPortManager.sendToPort(com, cmdBytes);
            //读取一下状态，为了解决关闭之后，再次打开还能读到上次的开启状态
            SerialPortManager.readFromPort(com);
            res.put("msg", "吹风机已断开供电，请将吹风机放回位置");
        } catch (Exception e) {
            res.put("msg", "系统异常，请将吹风机放回位置");
        }
        return res;
    }

    @RequestMapping("index")
    public String index() {
        return "index";
    }

}
