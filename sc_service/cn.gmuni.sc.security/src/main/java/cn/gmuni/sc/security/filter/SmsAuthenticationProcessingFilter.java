package cn.gmuni.sc.security.filter;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.log.constant.LoginLogType;
import cn.gmuni.sc.log.constant.LoginMethod;
import cn.gmuni.sc.log.constant.LoginOperator;
import cn.gmuni.sc.log.model.LoginLog;
import cn.gmuni.sc.log.utils.LoginLogger;
import cn.gmuni.sc.security.cache.LoginUserCache;
import cn.gmuni.sc.security.constant.SecurityConst;
import cn.gmuni.sc.security.token.SmsAuthenticationToken;
import cn.gmuni.sc.user.container.UserCache;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.service.IThirdUserService;
import cn.gmuni.sc.utils.JwtUtils;
import cn.gmuni.sc.utils.ResponseUtils;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import cn.gmuni.utils.DesUtils;
import cn.gmuni.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmsAuthenticationProcessingFilter  extends AbstractAuthenticationProcessingFilter  {

    private Logger logger = LoggerFactory.getLogger(SmsAuthenticationProcessingFilter.class);

    public SmsAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/loginSms","POST"));
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        String phoneNumber = request.getParameter("phoneNumber");
        String verificationCode = request.getParameter("verificationCode");
        String deviceUniqueId = request.getParameter("deviceUniqueId");
        if(phoneNumber == null){
            phoneNumber = "";
        }
        if(verificationCode == null){
            verificationCode = "";
        }
        if(deviceUniqueId == null){
            deviceUniqueId = "";
        }
        verificationCode = DesUtils.decode(verificationCode,phoneNumber);
        if(!request.getMethod().equals(SecurityConst.POST)){
            throw new AuthenticationServiceException( "Authentication method not supported: " + request.getMethod());
        }

        SmsAuthenticationToken token = new SmsAuthenticationToken(phoneNumber, verificationCode, new ArrayList<>());
        //携带额外参数
        Map<String,Object> map = new HashMap<>();
        map.put("verificationCode",verificationCode);
        map.put("deviceUniqueId",deviceUniqueId);
        token.setDetails(map);
        Authentication authenticate = this.getAuthenticationManager().authenticate(token);
        return authenticate;
    }




    /**
     * 短信验证码验证成功
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String phoneNumber = authResult.getName();
        //获取额外参数，从中取出验证码
        Map<String,Object> details = (Map<String, Object>) authResult.getDetails();
        String verificationCode = details.get("verificationCode").toString();
        //登录设备唯一标志
        String deviceUniqueId = details.get("deviceUniqueId").toString();
        //通过电话号码和验证码生成token
        String token = JwtUtils.generate(phoneNumber +"-"+ verificationCode);
        //保存当前用户
        ThirdPartUser user = saveUser(phoneNumber, token, verificationCode,request,deviceUniqueId);
        if(user != null){
            response.addHeader(JwtUtils.TOKEN_NAME,token);
            Map<String, Object> map = new HashMap<>();
            map.put(JwtUtils.TOKEN_NAME, response.getHeader(JwtUtils.TOKEN_NAME));
            map.put(SecurityConst.IDENTITY_TYPE,SecurityConst.IDENTITY_TYPE_PHONE);
            map.put(SecurityConst.PHONE_NUMBER,phoneNumber);
            map.put("userInfo",user);
            ResponseUtils.responseMsg(response,new BaseResponse(map),HttpServletResponse.SC_OK);
        }else{
            //TODO 如果保存用户失败，需要返回提示信息

        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
       //登录失败日志
        String phoneNumber = request.getParameter("phoneNumber");
        String verificationCode = request.getParameter("verificationCode");
        String deviceUniqueId = request.getParameter("deviceUniqueId");
        LoginLog loginLog = getLog(phoneNumber,DesUtils.decode(verificationCode,phoneNumber),deviceUniqueId);
        loginLog.setLogDesc(failed.getMessage());
        loginLog.setLoginType(LoginMethod.PHONE.getCode());
        loginLog.setOperator(LoginOperator.LOGIN.getCode());
        loginLog.setLogType(LoginLogType.EXCEPTION_LOG.getCode());
        LoginLogger.log(loginLog,request);
        ResponseUtils.responseMsg(response,new BaseResponse(BaseResponse.AUTH_CODE,failed.getMessage()),HttpServletResponse.SC_OK);
    }


    /**
     * 保存登录用户信息
     * @param phoneNumber
     * @param token
     * @param code
     * @return
     */
    private ThirdPartUser saveUser(String phoneNumber,String token,String code,HttpServletRequest request,String deviceUniqueId){
        IThirdUserService userServiceImpl = (IThirdUserService) ApplicationContextProvider.getBean("thirdUserServiceImpl");
        String id = IdGenerator.getId();
        boolean saveUserSuccess;
        boolean isUpdateUser = false;
        ThirdPartUserInfo newUserInfo =userServiceImpl.getUserInfo(phoneNumber);
        ThirdPartUser user = new ThirdPartUser();
        LoginLog log = getLog(phoneNumber,code,deviceUniqueId);
        log.setLogDesc("用户"+phoneNumber+"登录成功");
        log.setUsername(phoneNumber);
        log.setToken(token);
        if(newUserInfo!=null){
            //如果设备有变化，则短信通知
            if(newUserInfo.getDeviceUniqueId()!=null && !newUserInfo.getDeviceUniqueId().equals(deviceUniqueId)){
                noticeUser(phoneNumber,code,deviceUniqueId,request);
            }
            //更新当前用户
            newUserInfo.setToken(token);
            newUserInfo.setCredential(code);
            newUserInfo.setState("1");
            newUserInfo.setDeviceUniqueId(deviceUniqueId);
            isUpdateUser = true;
            saveUserSuccess =  userServiceImpl.updateUserInfo(newUserInfo);
            if(saveUserSuccess){
                LoginUserCache.add(newUserInfo);
            }
            user = userServiceImpl.getUserById(newUserInfo.getUserId());
            //不是首次登录
            log.setFirstLogin("1");
            log.setSchoolCode(user.getSchool());
            //保存登录日志
            LoginLogger.log(log,request);
        }else{
            user.setPhoneNumber(phoneNumber);
            user.setName("ch_"+IdGenerator.getId().substring(6,12));
            user.setId(id);
            saveUserSuccess = userServiceImpl.saveUser(user);
            if(saveUserSuccess){
                UserCache.addUser(user);
            }
        }
        if(!isUpdateUser && saveUserSuccess ){
            ThirdPartUserInfo userInfo = new ThirdPartUserInfo();
            userInfo.setId(IdGenerator.getId());
            userInfo.setCredential(code);
            userInfo.setIdentityType(SecurityConst.IDENTITY_TYPE_PHONE);
            userInfo.setIndentifier(phoneNumber);
            userInfo.setToken(token);
            userInfo.setUserId(user.getId());
            userInfo.setDeviceUniqueId(deviceUniqueId);
            saveUserSuccess = userServiceImpl.saveUserInfo(userInfo);
            if(saveUserSuccess){
                LoginUserCache.add(userInfo);
            }
            log.setFirstLogin("0");
            LoginLogger.log(log,request);
        }
        return saveUserSuccess?user:null;
    }

    private LoginLog getLog(String phoneNumber,String authCode,String deviceUniqueId){
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(phoneNumber);
        loginLog.setLoginType(LoginMethod.PHONE.getCode());
        loginLog.setOperator(LoginOperator.LOGIN.getCode());
        loginLog.setLogType(LoginLogType.LOGIN_LOG.getCode());
        Map<String,String> params = new HashMap<>();
        params.put("phoneNumber",phoneNumber);
        params.put("authCode",authCode);
        params.put("deviceUniqueId",deviceUniqueId);
        loginLog.setParams(JsonUtil.object2Json(params));
        return loginLog;
    }

    //通知用户登录设备有变化
    private void noticeUser(String phoneNumber,String authCode,String deviceUniqueId,HttpServletRequest request){
        //记录日志
        LoginLog log = getLog(phoneNumber, authCode, deviceUniqueId);
        log.setLogType(LoginLogType.EXCEPTION_LOG.getCode());
        log.setLogDesc("用户:"+phoneNumber+"在其他设备登录");
        LoginLogger.log(log,request);
        logger.info("尊敬的用户您好，系统检测到您的账号在其他设备登录，如非本人操作，请立即联系我们");
        //发送短信通知
//        Map<String,Object> params = new HashMap<>();
//        HttpAPIService httpAPIService = (HttpAPIService) ApplicationContextProvider.getBean("httpAPIService");
//        try {
//            params.put("tpl_id","113547");
//            params.put("key","5bf0d9cae2096742606dbc447a952976");
//            params.put("dtype","json");
//            params.put("mobile",phoneNumber);
//            httpAPIService.doPost("http://v.juhe.cn/sms/send", params);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

}
