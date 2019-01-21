package cn.gmuni.sc.http.remote;


import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 校园端的请求
 */
public interface ICampusEndService {

    Object request() throws IOException, URISyntaxException;

    Object request(String url) throws IOException, URISyntaxException;

    Object request(String url, List<NameValuePair> params)  throws IOException, URISyntaxException;

    Object request(List<NameValuePair> params)   throws IOException, URISyntaxException;

    Object request(String url, List<NameValuePair> params,List<Header> headers, List<Cookie> cookies) throws IOException, URISyntaxException;

    Object request(String url, List<NameValuePair> params,List<Header> headers, List<Cookie> cookies,String method) throws IOException, URISyntaxException;

    Object request(String url, List<NameValuePair> params, List<Header> headers, List<org.apache.http.cookie.Cookie> cookies, String method,boolean toJson) throws IOException, URISyntaxException;

    Object request(String url, List<NameValuePair> params, List<Header> headers, List<org.apache.http.cookie.Cookie> cookies, String method,String payload,boolean toJson) throws IOException, URISyntaxException;


    CloseableHttpResponse download(String url) throws  IOException,URISyntaxException;

    String getUrl();

    String getUrl(String url);

    List<Header> getHeaders();

    List<NameValuePair> getParams();

    List<org.apache.http.cookie.Cookie> getCookies();

}
