package cn.gmuni.security.config;

import cn.gmuni.security.filter.JWTAuthenticationFilter;
import cn.gmuni.security.filter.JWTLoginFilter;
import cn.gmuni.security.handler.CustomAuthenticationProvider;
import cn.gmuni.security.handler.CustomHttpStatusReturningLogoutSuccessHandler;
import cn.gmuni.security.handler.CustomLogoutHandler;
import cn.gmuni.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Autowired
    private CustomHttpStatusReturningLogoutSuccessHandler lougoutSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
//               .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/static/css/**","/static/img/**", "/static/js/**","/static/fonts/**","/swagger*/**","/webjars/springfox-swagger-ui/**","/v2/**","/images/**","/upload/download*/**","/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                //验证登陆
                .addFilter(new JWTLoginFilter(authenticationManager()))
                //验证token
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .formLogin()
                .loginPage("/index.html")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(lougoutSuccessHandler)
                .permitAll();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Configure global.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        // 使用自定义的 Authentication Provider
        auth.authenticationProvider(customAuthenticationProvider);
    }


}
