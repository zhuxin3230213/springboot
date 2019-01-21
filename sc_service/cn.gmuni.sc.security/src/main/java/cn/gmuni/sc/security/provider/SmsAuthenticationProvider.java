package cn.gmuni.sc.security.provider;

import cn.gmuni.sc.security.cache.VerifyCodeCache;
import cn.gmuni.sc.security.token.SmsAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getName();
        String verifyCode = authentication.getCredentials().toString();
        //如果验证码验证成功
        if(VerifyCodeCache.verify(phoneNumber,verifyCode)){

        }else{
            throw new BadCredentialsException("验证码错误");
        }
        return new UsernamePasswordAuthenticationToken(phoneNumber,verifyCode,new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(SmsAuthenticationToken.class);
    }
}
