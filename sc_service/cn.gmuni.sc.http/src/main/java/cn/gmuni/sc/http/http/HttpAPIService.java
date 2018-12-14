package cn.gmuni.sc.http.http;

import cn.gmuni.sc.http.handle.HttpHandler;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create in 14:02 2018/8/15
 * @Modified By:
 **/

@Service
public class HttpAPIService {

    @Autowired
    @Qualifier("httpClientBuilder")
    private HttpClientBuilder builder;

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;


    /**
     * 获取get请求的响应内容
     * @param url
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse get(String url) throws Exception{
        return get(url,null,new Header[]{});
    }

    /**
     * 带参数的get请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse get(String url,Map<String,Object> params) throws Exception{
        return get(url,params,new Header[]{});
    }


    public CloseableHttpResponse get(String url,Header[] headers) throws Exception{
        return get(url,null,headers);
    }

    public CloseableHttpResponse get(String url,Map<String,Object> params,Header[] headers) throws Exception{
        URIBuilder uriBuilder = new URIBuilder(url);

        if (params != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(uriBuilder.build().toString());
        //如果有headers，则新增headers
        if(headers!=null){
            for(Header header :headers){
                httpGet.addHeader(header);
            }
        }
        // 装载配置信息
        httpGet.setConfig(config);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体
            return response;
        }
        return null;
    }




    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url) throws Exception {
        return doGet(url,null,new Header[]{});
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> map) throws Exception {
        return doGet(url,map,new Header[]{});

    }

    public String doGet(String url, Header[] headers) throws Exception {
        return doGet(url,null,headers);

    }

    public String doGet(String url,Map<String,Object> params,Header[] headers) throws Exception {
        CloseableHttpResponse response = get(url,params,headers);
        if(response!=null){
            String s = EntityUtils.toString(response.getEntity(), "UTF-8");
            response.close();
            return s;
        }
        return null;
    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        HttpResult httpResult = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
        response.close();
        return httpResult;
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, null);
    }

    /**
     * 获取handler进行自定义操作
     * @return
     */
    public HttpHandler getHandler(){
        return new HttpHandler(builder,config);
    }
}
