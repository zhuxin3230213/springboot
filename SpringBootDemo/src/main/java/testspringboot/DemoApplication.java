package testspringboot;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//程序的入口
@SpringBootApplication
/**
 * 不在springboot的扫描包下的方式调用bean对象
 * 在启动类中使用@Import进行导入
 * 而且在SpringUtil是不需要添加@Component注解
 */
//@ServletComponentScan
//@Import(SpringUtil.class)
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
