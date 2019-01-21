package cn.gmuni.sc.http.remote.impl;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.college.cache.CollegeCache;
import cn.gmuni.sc.college.model.College;
import cn.gmuni.sc.http.handle.HttpHandler;
import cn.gmuni.sc.http.http.HttpAPIService;
import cn.gmuni.sc.http.remote.ICampusEndService;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.RequestUtils;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.*;


@Service
public class CampusEndService implements ICampusEndService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpAPIService httpAPIService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CampusEndService.class);



    @Override
    public Object request(String url) throws IOException, URISyntaxException {
        return request(url,getParams(),getHeaders(),getCookies());
    }

    @Override
    public Object request() throws IOException, URISyntaxException {
        return request(getUrl());
    }

    @Override
    public Object request(String url, List<NameValuePair> params) throws IOException, URISyntaxException {
        return request(url,params,getHeaders(),getCookies());
    }

    @Override
    public Object request(List<NameValuePair> params) throws IOException, URISyntaxException {
        return request(getUrl(),params,getHeaders(),getCookies());
    }

    @Override
    public Object request(String url, List<NameValuePair> params, List<Header> headers, List<org.apache.http.cookie.Cookie> cookies) throws IOException, URISyntaxException {
        return request(url,params,headers,cookies,getMethod());
    }


    @Override
    public Object request(String url, List<NameValuePair> params, List<Header> headers, List<org.apache.http.cookie.Cookie> cookies, String method) throws IOException, URISyntaxException {

        return request(url,params,headers,cookies,method,true);
    }


    @Override
    public Object request(String url, List<NameValuePair> params, List<Header> headers, List<org.apache.http.cookie.Cookie> cookies, String method,boolean toJson) throws IOException, URISyntaxException {
        return request(url,params,headers,cookies,method, RequestUtils.getPayload(request),toJson);
    }

    @Override
    public Object request(String url, List<NameValuePair> params, List<Header> headers, List<org.apache.http.cookie.Cookie> cookies, String method, String payload, boolean toJson) throws IOException, URISyntaxException {
        LOGGER.info("远程访问地址为 : "+url);
        HttpHandler handler = httpAPIService.getHandler();
        if(headers!=null){
            handler.addHeaders(headers);
        }
        if(cookies!=null){
            handler.addCookies(cookies);
        }
        CloseableHttpResponse response = null;
        //如果是post请求，
        if("post".equals(method.toLowerCase())){
            response = handler.build().post(url,params,payload);
            if(toJson){
                String s = JsonUtil.object2Json(new BaseResponse<>(HttpHandler.getBody(response)));
                response.close();
                return s;
            }
            return response;
        }else{
            response = handler.build().get(url, params);
            if(toJson){
                BaseResponse<Object> bs = new BaseResponse<>(JSON.parse(HttpHandler.getBody(response)));
                response.close();
                return JsonUtil.object2Json(bs);
            }
            return response;
        }
    }

    @Override
    public CloseableHttpResponse download(String url) throws IOException, URISyntaxException {
        return (CloseableHttpResponse) request(url,getParams(),getHeaders(),getCookies(),getMethod(),false);
    }




    private static String getBaseUrl(){
        String code = UserUtils.getLoginUser().getSchool();
        LOGGER.info("传输学校编码为:"+code);
        if(code==null){
            return null;
        }
        for(College college : CollegeCache.getAllCollege()){
            if(college.getCode().equals(code)){
                LOGGER.info("获取学校访问地址为"+college.getUrl());
                return college.getUrl();
            }
        }
        return null;
    }

    /**
     * 获取学校的访问地址
     * @return
     */
    public  String getUrl(){
        return getUrl(request.getRequestURI());
    }

    /**
     * 获取学校的访问地址
     * @return
     */
    public String getUrl(String url){
        String baseUrl = getBaseUrl();
        if(baseUrl!=null){
            return baseUrl + url;
        }
        return null;
    }

    public String getMethod(){
        return  request.getMethod().toLowerCase();
    }


    public List<Header> getHeaders(){
        List<Header> headers = new ArrayList<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        List<String> excludes = Arrays.asList(new String[]{"app_token" ,"identitytype","usercollegecode","content-length","Host"});

        if(headerNames!=null){
            while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                if(excludes.indexOf(headerName)==-1){
                    headers.add(new BasicHeader(headerName,request.getHeader(headerName)));
                }
            }
        }

        headers.add(new BasicHeader("requestType","app"));
        headers.add(new BasicHeader("requestTime",String.valueOf(new Date().getTime())));
        headers.add(new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36"));

        try {
           String hostIp = request.getRemoteHost();
            if(hostIp.startsWith("0:0")||hostIp.equals("127.0.0.1")){
                hostIp = InetAddress.getLocalHost().getHostAddress();
            }
            if(hostIp!=null){
                headers.add(new BasicHeader("Proxy-Client-IP",hostIp));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //添加底层登录用户信息
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        if(loginUser.getEndpointToken()!=null){
            headers.add(new BasicHeader("token",loginUser.getEndpointToken()));
            headers.add(new BasicHeader("loginId",loginUser.getEndpointLoginId()));

        }
        return headers;
    }

    public List<org.apache.http.cookie.Cookie> getCookies(){
        List<org.apache.http.cookie.Cookie> cks = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("app_token")||cookie.getName().equals("Request-Device")){
                    continue;
                }
                cks.add(new BasicClientCookie(cookie.getName(),cookie.getValue()));
            }
        }
        return cks;
    }

    public List<NameValuePair> getParams(){
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<NameValuePair> list = new ArrayList<>();
        if(parameterMap!=null){
            for(Map.Entry<String,String[]> entry : parameterMap.entrySet()){
                String key = entry.getKey();
                String[] value = entry.getValue();
                for(String v : value){
                    NameValuePair n = new BasicNameValuePair(key,v);
                    list.add(n);
                }
            }
        }
        return list;
    }



}
