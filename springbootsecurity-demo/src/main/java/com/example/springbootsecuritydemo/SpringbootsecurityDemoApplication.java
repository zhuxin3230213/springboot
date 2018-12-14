package com.example.springbootsecuritydemo;

import com.example.springbootsecuritydemo.util.Appctx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootsecurityDemoApplication {

    public static void main(String[] args) {

        //SpringApplication.run(SpringbootsecurityDemoApplication.class, args);
        SpringApplication app = new SpringApplication(SpringbootsecurityDemoApplication.class);

        Appctx.ctx = app.run(args);
        /*UserService suserService = (UserService) Appctx.ctx.getBean("suserService");
        SysUser su= suserService.findByName("TEST");
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);//将密码加密 可以先设置初始密码：000000
        su.setPassword(bc.encode(su.getPassword()));//然后使用密码为key值进行加密，运行主类后，会自动加密密码，可连接数据库查看。
        System.out.println("密码"+su.getPassword());
        suserService.update(su);//运行一次后记得注释这段重复加密会无法匹配*/
    }

}
