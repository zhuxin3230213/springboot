package cn.gmuni.sc.transpond.controller;


import cn.gmuni.sc.http.remote.ICampusEndService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;


/**
 * @Author:ZhuXin
 * @Description:
 * @Date:Create in 14:12 2018/8/15
 * @Modified By:
 **/

@Controller
public class HttpClientController {



    @Autowired
    ICampusEndService campusEndService;

    @Autowired
    HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(HttpClientController.class);




    @RequestMapping(value = "/api/**",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object sendMessages1(HttpServletResponse response) {
        return send(response);
    }

    @RequestMapping(value = "/api-download/**",produces="application/json;charset=UTF-8")
    @ResponseBody
    public void download(HttpServletRequest request,HttpServletResponse response){
        String url = request.getRequestURI();
        if(url.startsWith("/api-download/")){
            url = campusEndService.getUrl(url.substring("/api-download".length()));
        }
        try {
            CloseableHttpResponse closeResponse = campusEndService.download(url);
            download(response,closeResponse);
            closeResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Object send(HttpServletResponse response){
        String url = request.getRequestURI();
        logger.info("url+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+url);
        if(url.startsWith("/api/")){
            url = campusEndService.getUrl(url.substring(4));
        }
        try {
            if(isDownload(request)){
                CloseableHttpResponse closeResponse = campusEndService.download(url);
                download(response,closeResponse);
                closeResponse.close();
            }else{
               return campusEndService.request(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isDownload(HttpServletRequest request){
        String header = request.getHeader("Custom-Operator");
        if(null!=header && !"".equals(header)){
            return header.equals("download");
        }
        return false;
    }


    private void download(HttpServletResponse res,CloseableHttpResponse closeResponse) throws IOException {
        HttpEntity entity = closeResponse.getEntity();
        InputStream fis = entity.getContent();
        Header[] allHeaders = closeResponse.getAllHeaders();
        String contentType = "application/octet-stream";
        String contentDisposition = "";
        for(Header header : allHeaders){
            if(header.getName().toLowerCase().equals("content-type")){
                contentType = header.getValue();
            }
            if(header.getName().toLowerCase().equals("content-disposition")){
                contentDisposition = header.getValue();
            }
        }
        res.setHeader("content-type", contentType);
        res.setContentType(contentType);
        res.setHeader("Content-Disposition", contentDisposition);

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(fis);
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff,0,i);
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
