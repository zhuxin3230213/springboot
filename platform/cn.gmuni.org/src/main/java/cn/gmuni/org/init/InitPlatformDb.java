package cn.gmuni.org.init;

import cn.gmuni.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Order(value = 1)
public class InitPlatformDb {
    @Autowired
    SqlUtils sqlUtils;
    @PostConstruct
    public void init() {
        sqlUtils.execSqlFileByMysql("platform.sql", "pf_gmuni_");
    }
}
