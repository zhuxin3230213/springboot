package cn.gmuni.webapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@ComponentScan("cn.gmuni")
@MapperScan("cn.gmuni")
@EnableWebMvc
@EnableCaching
//@PropertySource(value={"file:application.properties"})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}
