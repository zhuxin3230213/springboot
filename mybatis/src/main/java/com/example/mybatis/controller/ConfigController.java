package com.example.mybatis.controller;

import com.example.mybatis.model.Config;
import com.example.mybatis.service.ConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

/**
 * 联系我们控制类
 */

@Controller
@RequestMapping("/co")
public class ConfigController {


    @Autowired
    @Qualifier("configServiceImpl")
    ConfigService configService;


    @Value("${use.name}")
    String useName;
    @GetMapping("/list/{string}")
    public String listConfig(@PathVariable("string") String s){
        System.out.println("参数"+s);
        return "footer";
    }

    @RequestMapping(value = "/index")
    public String index(Model model){
       /* Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        System.out.println(calendar.getTime());
        Vector vector = new Vector();
        String[] arr = new String[]{"111","2222"};
        for (String temp:arr){
            System.out.println(temp);
        }
        vector.add(arr);
        vector.addElement("1");
        vector.addElement("w");
        System.out.println(vector);
        System.out.println(2*8);*/

        model.addAttribute("list",configService.listConfigs());
        model.addAttribute("useName",useName);
        return "index";
    }

}
