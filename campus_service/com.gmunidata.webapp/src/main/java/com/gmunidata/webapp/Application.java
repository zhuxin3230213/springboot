package com.gmunidata.webapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableScheduling
@ComponentScan({ "cn.gmuni.org","cn.gmuni.enrollment","com.gmunidata.webapp","cn.gmuni","com.gmunidata"})
@MapperScan({"cn.gmuni","com.gmunidata"})
@EnableWebMvc
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
