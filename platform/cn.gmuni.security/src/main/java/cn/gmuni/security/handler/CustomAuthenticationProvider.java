package cn.gmuni.security.handler;

import cn.gmuni.org.model.UserInfo;
import cn.gmuni.security.filter.UserCodeCache;
import cn.gmuni.security.service.UserService;
import cn.gmuni.utils.DesUtils;
import cn.gmuni.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    /**
     * Validate user info is correct form database
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        //通过用户名从数据库中查询该用户
        UserInfo userDetails  = userService.loadUserByUsername(username);
        if(null == userDetails){
            throw new BadCredentialsException("nameOrPwdIncorrect");
        }
        if("0".equals(userDetails.getState())){
            throw new BadCredentialsException("userDisabled");
        }
        //判断密码(这里是md5加密方式)是否正确
        String dbPassword = userDetails.getPassword();
        password = DesUtils.decode(password, userDetails.getCode());
        if(!Md5Util.encode(password).equals(dbPassword)){
            throw new BadCredentialsException("nameOrPwdIncorrect");
        }
        System.out.println("系统授权登录人数为：" + UserCodeCache.authorizedUserNum + ",当前登录人数为：" + UserCodeCache.USER_CODE_MAP.size());
        if(UserCodeCache.USER_CODE_MAP.size() >= UserCodeCache.authorizedUserNum){
            System.out.println("请注销之后重试");
            throw new BadCredentialsException("tooMuchUser");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
        return auth;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
