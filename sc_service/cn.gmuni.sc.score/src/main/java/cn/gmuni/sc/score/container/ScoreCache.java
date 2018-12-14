package cn.gmuni.sc.score.container;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.redis.client.RedisCacheUtils;
import cn.gmuni.sc.http.handle.HttpHandler;
import cn.gmuni.sc.http.http.HttpAPIService;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class ScoreCache {

    public static final String SC_SCORE_COMPUTER_SUBJECT="SC_SCORE_COMPUTER_SUBJECT";

    @Autowired
    private HttpAPIService httpAPIService;

    //每月凌晨四点自动更新
    @Scheduled(cron = "0 0 4 1 * ?")
    @PostConstruct
    public void initComputerCache() throws IOException, URISyntaxException {
        try{
            //读取考试时间
            HttpHandler handler = httpAPIService.getHandler().build();
            handler.addHeader(new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36"));
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("act","doQueryCond"));
            pairs.add(new BasicNameValuePair("pram","certi"));
            pairs.add(new BasicNameValuePair("community","Home"));
            pairs.add(new BasicNameValuePair("sid","300"));
            CloseableHttpResponse response = handler.get("http://search.neea.edu.cn/QueryMarkUpAction.do",pairs);

            String body = HttpHandler.getBody(response);
            List<Map<String,Object>> times = new ArrayList<>();

            if(body!=null){
                Document html = Jsoup.parse(body);
                Element select = html.getElementById("examselect");
                Elements options = select.getElementsByTag("option");
                for(Element element : options){
                    Map<String,Object> time = new HashMap<>();
                    time.put("value",element.attr("value"));
                    time.put("text",element.text());
//                Map map1 = initComputerSubject((String)time.get("value"));
                    time.put("subject",new HashMap<>());
                    times.add(time);
                }
            }
            response.close();
            RedisCacheUtils.build().put(SC_SCORE_COMPUTER_SUBJECT,times);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static List<Map<String,Object>> getTimes(){
        return RedisCacheUtils.build().get(SC_SCORE_COMPUTER_SUBJECT,List.class);
    }

    public static Map<String,Object> getSubject(String examid) throws IOException, URISyntaxException {
        List<Map<String,Object>> times = getTimes();
        for(Map<String,Object> mp :times){
            if(examid.equals(mp.get("value"))){
                Map<String,Object> subjects = (Map<String, Object>) mp.get("subject");
                if(subjects==null || subjects.size()==0){
                    synchronized (ScoreCache.class){
                        subjects = (Map<String, Object>) mp.get("subject");
                        if(subjects==null || subjects.size()==0){
                            subjects = initComputerSubject(examid);
                            mp.put("subject",subjects);
                        }
                    }
                }
                return (Map<String, Object>) mp.get("subject");
            }
        }
        return null;
    }




    private static Map<String,Object> initComputerSubject(String examid) throws IOException, URISyntaxException {
        HttpAPIService service = (HttpAPIService) ApplicationContextProvider.getBean("httpAPIService");
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("act","doQuerySfBkjb"));
        pairs.add(new BasicNameValuePair("examid",examid));
        HttpHandler handler = service.getHandler().build();
        CloseableHttpResponse response = handler.get("http://search.neea.edu.cn/QueryDataAction.do", pairs);
        String result = HttpHandler.getBody(response);
//          String result = httpAPIService.doGet("http://search.neea.edu.cn/QueryDataAction.do",pairs);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Map map = jsonObject.toJavaObject(Map.class);
        return map;

    }
}
