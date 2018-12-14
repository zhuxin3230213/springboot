package cn.gmuni.zsgl.init;


import cn.gmuni.zsgl.util.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @Author:ZhuXin
 * @Description:
 * 初始化sql
 * @Date:Create in 10:10 2018/7/31
 * @Modified By:
 **/

//@Component
@Order(value = 3)
public class InitEnrollmentDb {

     @Autowired
     SqlUtils sqlUtils;

    //@PostConstruct
    public void  init(){
        System.out.println(this.getClass().getName() + "我第3个执行");
        sqlUtils.execSqlFileByMysql("zs.sql", "zs_gmuni_");
    }


}
