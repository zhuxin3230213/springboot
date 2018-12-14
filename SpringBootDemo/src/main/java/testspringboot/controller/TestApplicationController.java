package testspringboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import testspringboot.entity.User_z;
import testspringboot.util.SpringBootUtil;

/**
 *测试
 * 普通类调用spring bean对象
 */
@RestController
public class TestApplicationController {

    @RequestMapping("/test")
    public Object testSpringUtil(){
     return SpringBootUtil.getBean("user");
  }

  @RequestMapping("/test1")
   public Object test1(){
     return  SpringBootUtil.getBean(User_z.class);
   }

}
