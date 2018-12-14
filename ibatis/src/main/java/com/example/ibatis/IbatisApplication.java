package com.example.ibatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.example.ibatis.mapper")
public class IbatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(IbatisApplication.class, args);
    }
}
