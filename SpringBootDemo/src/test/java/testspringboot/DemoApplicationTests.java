package testspringboot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import testspringboot.dao.User_zRespository;
import testspringboot.entity.User_z;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest

public class DemoApplicationTests {

    @Test
    public void contextLoads() {

    }

    private MockMvc mockMvc;//模拟mvc对象，通过MockMvcBuilders.WebAppContextSetup(this.wac).build()初始化
    @Autowired
    private WebApplicationContext wec;//注入WebApplicationContext
    @Autowired
    User_zRespository user_zRespository;


    @Before//开始测试之前工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wec).build();
    }

    @Test
    public void testLoginController() throws Exception {
        MvcResult result = mockMvc.perform(get("/login/test"))//获取url
                .andExpect(MockMvcResultMatchers.status().isOk())//模拟向testRest发送get请求
                .andReturn();//返回执行请求的结果
        System.out.println("_______________" + result.getResponse().getContentAsString());
    }

    @Test
    public void testUser_zControllerFindList() throws Exception {
        List<User_z> list = user_zRespository.findAll();
        for (User_z temp : list) {
            System.out.println(temp.getUsername());
        }
    }


}
