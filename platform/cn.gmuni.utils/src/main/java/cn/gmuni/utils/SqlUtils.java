package cn.gmuni.utils;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

@Service
public class SqlUtils {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String pwd;

    /**
     *
     * @param sqlFilePath sql脚本文件名
     * @param tablePrefix 表名前缀，用于检测表是否存在
     */
    public void execSqlFileByMysql(String sqlFilePath, String tablePrefix){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, pwd);
            //检查表是否存在
            ResultSet resultSet = conn.createStatement().executeQuery("SHOW TABLES LIKE '" + tablePrefix + "%'");
            if(resultSet.next()){
                System.out.println("已存在相关表，不进行初始化");
                return;
            }
            System.out.println("初始化" + tablePrefix + "相关表");
            ScriptRunner runner = new ScriptRunner(conn);
            //下面配置不要随意更改，否则会出现各种问题
            runner.setAutoCommit(true);//自动提交
            runner.setFullLineDelimiter(false);
            runner.setDelimiter(";");////每条命令间的分隔符
            runner.setSendFullScript(false);
            runner.setStopOnError(false);
            //	runner.setLogWriter(null);//设置是否输出日志
            //如果又多个sql文件，可以写多个runner.runScript(xxx),
            Resource resource = new ClassPathResource("initsql/" + sqlFilePath);
            runner.runScript(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            close(conn);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }finally{
            close(conn);
        }
    }
    private void close(Connection conn){
        try {
            if(conn != null){
                conn.close();
            }
        } catch (Exception e) {
            if(conn != null){
                conn = null;
            }
        }
    }

}
