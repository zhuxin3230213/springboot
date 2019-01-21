package cn.gmuni.sc.security.filter;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.security.cache.LoginUserCache;
import cn.gmuni.sc.security.constant.SecurityConst;
import cn.gmuni.sc.security.exception.TokenException;
import cn.gmuni.sc.security.token.SmsAuthenticationToken;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.JwtUtils;
import cn.gmuni.sc.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * app token
 * token鉴权是否登录
 */
public class AppTokenAuthenticationFilter extends BasicAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(AppTokenAuthenticationFilter.class);

    public AppTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 认证过程
     *  1、用户使用用户名和密码进行登录。
     *
     *        2、Spring Security将获取到的用户名和密码封装成一个实现了Authentication接口的UsernamePasswordAuthenticationToken。
     *
     *        3、将上述产生的token对象传递给AuthenticationManager进行登录认证。
     *
     *        4、AuthenticationManager认证成功后将会返回一个封装了用户权限等信息的Authentication对象。
     *
     *        5、通过调用SecurityContextHolder.getContext().setAuthentication(...)将AuthenticationManager返回的Authentication对象赋予给当前的SecurityContext。
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String device = request.getHeader("Request-Device");
        if(null==device || "".equals(device)){
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for(Cookie cookie : cookies){
                    if ("Request-Device".equals(cookie.getName())){
                        device = cookie.getValue();
                        break;
                    }
                }
            }

        }
        if(!"app".equals(device)){
            chain.doFilter(request,response);
            return;
        }
        String token = UserUtils.getToken(request);
        ThirdPartUserInfo info = UserUtils.getLoginUserInfo(request);
        //说明用户token已失效，则跳转到登录页
        if(token!=null && info==null){
            ResponseUtils.responseMsg(response,new BaseResponse(HttpServletResponse.SC_FORBIDDEN,"登录信息已失效"),HttpServletResponse.SC_OK);
            return;
        }

        if(info==null){
            logger.info("校验token，用户不存在");
            chain.doFilter(request, response);
            return;
        }
        //判断是否有token
        if (token == null || !token.startsWith(JwtUtils.BEARER)) {
            logger.info("校验token,token不存在");
            chain.doFilter(request, response);
        }else{
            try {
                AbstractAuthenticationToken authentication = getAuthentication(request, response);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request,response);
            } catch (TokenException e) {
                logger.info("校验token,校验异常");
                e.printStackTrace();
                ResponseUtils.responseMsg(response,new BaseResponse(HttpServletResponse.SC_FORBIDDEN,e.getMessage()),HttpServletResponse.SC_OK);
            }

        }
    }


    private AbstractAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws TokenException {
        ThirdPartUserInfo info = UserUtils.getLoginUserInfo(request);
        if (info == null) {
            return null;
        }
        String token = info.getToken();
        int identityType = info.getIdentityType();
        if(!LoginUserCache.verify(token,identityType)){
            throw new TokenException("登录信息已失效，请重新登录");
        }
        if(identityType == SecurityConst.IDENTITY_TYPE_PHONE){
            return new SmsAuthenticationToken(identityType,null,new ArrayList<>());
        }
        return null;
    }
}
