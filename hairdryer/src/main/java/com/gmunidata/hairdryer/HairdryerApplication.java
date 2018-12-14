package com.gmunidata.hairdryer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@ComponentScan({"com.gmunidata"})
@EnableWebMvc
@EnableConfigurationProperties
public class HairdryerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairdryerApplication.class, args);
    }
}
