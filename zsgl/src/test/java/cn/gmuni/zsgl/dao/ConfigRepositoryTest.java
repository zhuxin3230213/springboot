package cn.gmuni.zsgl.dao;

import cn.gmuni.zsgl.entity.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;


/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create in 11:18 2018/7/13
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigRepositoryTest {

    @Autowired
    ConfigRepository configRepository;
    @Test
    public void listConfigsByCode() {
        List<Config> configs = configRepository.listConfigs();
        System.out.println(configs.size());
       /* ConfigUtil configUtil = new ConfigUtil();
        for (Config temp:configs){
            if ("gmuni_contact_us_address".equals(temp.getCode())){
                configUtil.setAddress(temp.getValue());
            }else if ("gmuni_contact_us_phone".equals(temp.getCode())){
                configUtil.setTel(temp.getValue());
            }else if ("gmuni_contact_us_fax".equals(temp.getCode())){
                configUtil.setFax(temp.getValue());
            }else if ("gmuni_contact_us_post".equals(temp.getCode())){
                configUtil.setPost(temp.getValue());
            }else if ("gmuni_contact_us_email".equals(temp.getCode())){
                configUtil.setEmail(temp.getValue());
            }else if ("gmuni_contact_us_micro_blog".equals(temp.getCode())){
                configUtil.setWeibo(temp.getValue());
            }else if("gmuni_contact_us_official_website".equals(temp.getCode())){
                configUtil.setNetAddress(temp.getValue());
            }
        }
        System.out.println(configUtil);*/
    }
}