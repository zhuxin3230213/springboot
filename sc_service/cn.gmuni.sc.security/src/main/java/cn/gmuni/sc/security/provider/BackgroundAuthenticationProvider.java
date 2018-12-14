package cn.gmuni.sc.security.provider;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.org.model.UserInfo;
import cn.gmuni.sc.security.service.UserService;
import cn.gmuni.sc.security.token.BackgroundAuthenticationToken;
import cn.gmuni.utils.DesUtils;
import cn.gmuni.utils.Md5Util;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;

public class BackgroundAuthenticationProvider  implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserInfo userInfo = userService().login(username);
        password = DesUtils.decode(password, username);

        if(userInfo==null || !Md5Util.encode(password).equals(userInfo.getPassword())){
            throw new BadCredentialsException("nameOrPwdIncorrect");
        }
        return new UsernamePasswordAuthenticationToken(username,password,new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(BackgroundAuthenticationToken.class);
    }

    private UserService userService(){
        return (UserService) ApplicationContextProvider.getBean("loginUserService");
    }
}
