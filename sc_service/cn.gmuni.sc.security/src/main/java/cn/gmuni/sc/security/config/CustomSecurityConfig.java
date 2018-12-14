package cn.gmuni.sc.security.config;

import cn.gmuni.sc.security.filter.*;
import cn.gmuni.sc.security.handler.CustomLogoutHandler;
import cn.gmuni.sc.security.handler.CustomLogoutSuccessHandler;
import cn.gmuni.sc.security.provider.BackgroundAuthenticationProvider;
import cn.gmuni.sc.security.provider.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Autowired
    private CustomLogoutSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //"/networkPay/payNotice,  wallet/payNotice"为支付宝支付结果通知接口，添加到例外
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/blower/blowerNotice","/wallet/payNotice","/networkPay/payNotice","/loginSms","/loginWX","/sendSms","/login","/favicon.ico","/default_img.png","/app/css/**","/app/js/**","/swagger*/**","/webjars/springfox-swagger-ui/**","/v2/**","/app/image/**","/app/default_img.png")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(smsAuthenticationProcessingFilter(), BackgroundTokenAuthenticationFilter.class)
                .addFilterBefore(weixinAuthenticationProcessingFilter(),SmsAuthenticationProcessingFilter.class)
                .addFilterBefore(backgroundAuthenticationProcessingFilter(),WeixinAuthenticationProcessingFilter.class)
                .addFilter(new AppTokenAuthenticationFilter(authenticationManager()))
                .addFilter(new BackgroundTokenAuthenticationFilter(authenticationManager()))
                .formLogin()
                .loginPage("/index.html")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(successHandler)
                .permitAll();




        //设置不使用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(smsAuthenticationProvider())
        .authenticationProvider(backgroundAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/static/**").antMatchers("/default_img.png").antMatchers("/favicon.ico");
    }

    @Bean
    public SmsAuthenticationProcessingFilter smsAuthenticationProcessingFilter(){
        SmsAuthenticationProcessingFilter filter = null;
        try {
            filter = new SmsAuthenticationProcessingFilter(authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filter;
    }

    public WeixinAuthenticationProcessingFilter weixinAuthenticationProcessingFilter(){
        WeixinAuthenticationProcessingFilter filter = null;
        try {
            filter = new WeixinAuthenticationProcessingFilter(authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  filter;
    }

    public BackgroundAuthenticationProcessingFilter backgroundAuthenticationProcessingFilter(){
        BackgroundAuthenticationProcessingFilter filter = null;
        try {
            filter = new BackgroundAuthenticationProcessingFilter(authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filter;
    }

    @Bean
    public SmsAuthenticationProvider smsAuthenticationProvider(){
        return new SmsAuthenticationProvider();
    }

    @Bean
    public BackgroundAuthenticationProvider backgroundAuthenticationProvider(){return new BackgroundAuthenticationProvider();}


}
