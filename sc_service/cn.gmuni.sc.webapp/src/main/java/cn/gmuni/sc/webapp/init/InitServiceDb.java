package cn.gmuni.sc.webapp.init;

import cn.gmuni.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitServiceDb {
    @Autowired
    SqlUtils sqlUtils;

    public InitServiceDb() {
    }

    @PostConstruct
    public void init() {
        this.sqlUtils.execSqlFileByMysql("scapp.sql", "sc_gmuni_");
    }

}
