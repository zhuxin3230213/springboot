package cn.gmuni.enrollment;

import cn.gmuni.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Order(value = 2)
public class InitEnrollmentDb {
    @Autowired
    SqlUtils sqlUtils;
    @PostConstruct
    public void init() {
        sqlUtils.execSqlFileByMysql("zs.sql", "zs_gmuni_");
    }
}
