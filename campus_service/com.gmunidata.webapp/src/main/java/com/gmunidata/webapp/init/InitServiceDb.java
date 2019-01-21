package com.gmunidata.webapp.init;

import cn.gmuni.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Order(value = 4)
public class InitServiceDb {
    @Autowired
    SqlUtils sqlUtils;

    public InitServiceDb() {
    }


    @PostConstruct
    public void init() {
        this.sqlUtils.execSqlFileByMysql("appservice.sql", "sc_gmuni_");
    }

}
