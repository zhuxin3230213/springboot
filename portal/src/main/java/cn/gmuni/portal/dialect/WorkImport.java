package cn.gmuni.portal.dialect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class WorkImport {

    @Autowired
    private Environment env;

    @Bean
    public GmDialect gmDialect(){
        return new GmDialect(env);
    }
}
