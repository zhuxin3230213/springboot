package cn.gmuni.webapp;

import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.SecurityInfo;
import io.undertow.servlet.api.TransportGuaranteeType;
import io.undertow.servlet.api.WebResourceCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfiguration {

    @Value("${spring.profiles.active}")
    private String httpVersion;

    @Value("${server.port}")
    private String port;

    @Value("${server.http.port}")
    private String httpPort;

    @Bean
    public ServletWebServerFactory undertowFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        if(httpVersion==null || "http".equals(httpVersion)){
            return factory;
        }
        if(port==null){
            port = "8443";
        }
        if(null == httpPort){
            httpPort = "8080";
        }
        factory.addBuilderCustomizers((UndertowBuilderCustomizer) builder -> builder.addHttpListener(Integer.parseInt(httpPort), "0.0.0.0"));
        factory.addDeploymentInfoCustomizers(deploymentInfo -> deploymentInfo.addSecurityConstraint(new SecurityConstraint()
                .addWebResourceCollection(new WebResourceCollection()
                        .addUrlPattern("/*")).setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
                .setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
                .setConfidentialPortManager(exchange -> Integer.parseInt(port)));
        return factory;
    }
}