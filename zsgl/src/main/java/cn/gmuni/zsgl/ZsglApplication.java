package cn.gmuni.zsgl;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//开启缓存
@EnableCaching
//加载指定的属性文件
//@PropertySource(value={"file:application.properties"})
@ComponentScan("cn.gmuni")
public class ZsglApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZsglApplication.class, args);
    }
}
