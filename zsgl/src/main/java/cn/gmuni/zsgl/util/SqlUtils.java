package cn.gmuni.zsgl.util;

/**
 * @Author:ZhuXin
 * @Description:
 * 初始化sql工具类
 * @Date:Create in 17:19 2018/8/3
 * @Modified By:
 **/

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

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

    public SqlUtils() {
    }

    public  void execSqlFileByMysql(String sqlFilePath, String tablePrefix) {
        Connection conn = null;

        try {
            Class.forName(this.driver);
            conn = DriverManager.getConnection(this.url, this.userName, this.pwd);
            ResultSet resultSet = conn.createStatement().executeQuery("SHOW TABLES LIKE '" + tablePrefix + "%'");
            if (resultSet.next()) {
                System.out.println("已存在相关表，不进行初始化");
                return;
            }

            System.out.println("初始化" + tablePrefix + "相关表");
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setAutoCommit(true);
            runner.setFullLineDelimiter(false);
            runner.setDelimiter(";");
            runner.setSendFullScript(false);
            runner.setStopOnError(false);
            Resource resource = new ClassPathResource("initsql/" + sqlFilePath);
            runner.runScript(new InputStreamReader(resource.getInputStream()));
            this.close(conn);
        } catch (Exception var10) {
            var10.printStackTrace();
            System.exit(1);
        } finally {
            this.close(conn);
        }

    }

    private void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception var3) {
            if (conn != null) {
                conn = null;
            }
        }

    }
}
