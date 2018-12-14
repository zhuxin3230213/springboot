package testspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import testspringboot.entity.User_z;

/**
 * 为了测试
 * 在启动的时候先通过代码方式给spring容器注入一个bean
 */
@Configuration
public class BeanConfig {
    @Bean(name ="user")
    public User_z generateUser_z(){
          User_z user_z = new User_z();
          user_z.setId(456);
          user_z.setUsername("张三");
          user_z.setSex("男");
          user_z.setAge(99);
          user_z.setJob("职业人");
          return user_z;
      }

}
