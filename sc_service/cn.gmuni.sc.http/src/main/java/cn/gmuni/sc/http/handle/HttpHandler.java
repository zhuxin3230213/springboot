package cn.gmuni.sc.http.handle;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpHandler {

    private HttpClientBuilder builder;

    private RequestConfig config;

    private CookieStore cookieStore;

    private List<Cookie> cookies;

    private List<Header> headers;

    private CloseableHttpClient httpClient;

    private HttpHandler(){}

    public HttpHandler(HttpClientBuilder builder,RequestConfig config){
        this.builder = builder;
        this.config = config;
        cookieStore = new BasicCookieStore();
        cookies = new ArrayList<>();
        headers = new ArrayList<>();
    }

    public HttpHandler addCookie(Cookie cookie){
       this.cookies.add(cookie);
       return this;
    }

    public HttpHandler addCookies(List<Cookie> cookies){
        this.cookies.addAll(cookies);
        return this;
    }

    public HttpHandler addHeaders(List<Header> headers){
        this.headers.addAll(headers);
        return this;
    }

    public HttpHandler addHeader(Header header){
        this.headers.add(header);
        return  this;
    }


    /**
     * 必须在执行get或post方法前调用
     * @return
     */
    public HttpHandler build(){
        for(Cookie cookie : cookies){
            cookieStore.addCookie(cookie);
        }
        httpClient = builder.setDefaultCookieStore(cookieStore).build();
        return this;
    }

    /**
     * 必须在执行get获取post方法后调用
     * @return
     */
    public List<Cookie> getCookies(){
        return cookieStore.getCookies();
    }


    public CloseableHttpResponse get(String url) throws URISyntaxException, IOException {
        return get(url,new ArrayList<>());
    }

    public CloseableHttpResponse get(String url,Map<String,Object> params) throws URISyntaxException, IOException {
        List<NameValuePair> list = new ArrayList<>();
        if (params != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
            }
        }
        return get(url,list);

    }

    public CloseableHttpResponse get(String url,List<NameValuePair> list) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if(list != null){
            uriBuilder.setParameters(list);
        }
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(uriBuilder.build().toString());

        // 装载配置信息
        httpGet.setConfig(config);

        if(headers!=null){
            for(Header header :headers){
                if(header.getName().equals("Host")){
                    continue;
                }
                httpGet.addHeader(header);
            }
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体
            return response;
        }
        return null;
    }

    public CloseableHttpResponse post(String url) throws IOException {
        return post(url,new ArrayList<>());
    }

    public CloseableHttpResponse post(String url, Map<String,Object> params) throws IOException {

        List<NameValuePair> list = new ArrayList<>();
        // params，不为空则进行遍历，封装from表单对象
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        // 发起请求
       return post(url,list);

    }

    public CloseableHttpResponse post(String url,List<NameValuePair> list) throws IOException {
        return post(url,list,null);
    }


    public CloseableHttpResponse post(String url,List<NameValuePair> list,String payload) throws IOException {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        if(list!=null && list.size()>0){
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);
        }
        if(payload!=null &&!"".equals(payload)){
            StringEntity entity = new StringEntity(payload,"utf-8");
            httpPost.setEntity(entity);
        }
        if(headers!=null){
            for(Header header :headers){
                if(header.getName().equals("Host")){
                    continue;
                }
                httpPost.addHeader(header);
            }
        }
        // 发起请求
        return this.httpClient.execute(httpPost);
    }

    public static String getBody(CloseableHttpResponse response){
        if(response==null){
            return null;
        }
        try {
            return EntityUtils.toString(response.getEntity(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
