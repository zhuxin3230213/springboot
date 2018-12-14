package cn.gmuni.sc.utils.httputils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @Author:ZhuXin
 * @Description:
 * HttpConnectUtil
 * @Date:Create in 11:55 2018/8/15
 * @Modified By:
 **/
public class HttpConnectUtil {

    /**
     * @param requestParam  请求时带的实体类，参数等
     * @param url           HTTP 请求地址
     * @param header        请求头，没有请求头的设置为null即可
     * @param requestMethod 请求方法 GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
     * @return 返回的是Object类型 其中ResponseEntity.getBody()请求返回json内容；ResponseEntity.getStatusCodeValue() 请求返回http状态码
     */
    public static <T> ResponseEntity tempHttp(T requestParam, String url, String header, String requestMethod) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", header);
        HttpEntity<T> requestEntity = new HttpEntity<>(requestParam, requestHeaders);
        ResponseEntity response;
        HttpMethod httpMethod;
        String method = requestMethod.toLowerCase();
        switch (method) {
            case "post":
                httpMethod = HttpMethod.POST;
                break;
            case "put":
                httpMethod = HttpMethod.PUT;
                break;
            case "delete":
                httpMethod = HttpMethod.DELETE;
                break;
            case "patch":
                httpMethod = HttpMethod.PATCH;
                break;
            case "head":
                httpMethod = HttpMethod.HEAD;
                break;
            case "options":
                httpMethod = HttpMethod.OPTIONS;
                break;
            case "trace":
                httpMethod = HttpMethod.TRACE;
                break;
            default:
                httpMethod = HttpMethod.GET;
        }

        response = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
        return response;
    }
}



