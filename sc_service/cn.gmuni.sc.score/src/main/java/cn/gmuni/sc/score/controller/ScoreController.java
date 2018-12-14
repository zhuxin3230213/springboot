package cn.gmuni.sc.score.controller;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.http.handle.HttpHandler;
import cn.gmuni.sc.http.http.HttpAPIService;
import cn.gmuni.sc.http.http.HttpResult;
import cn.gmuni.sc.log.anntation.SysLog;
import cn.gmuni.sc.log.constant.SysLogModule;
import cn.gmuni.sc.log.constant.SysLogType;
import cn.gmuni.sc.log.utils.SysLogger;
import cn.gmuni.sc.score.container.ScoreCache;
import cn.gmuni.sc.score.model.CetScoreModel;
import cn.gmuni.sc.score.model.CumputerScoreModel;
import cn.gmuni.sc.score.model.PutonghuaModel;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/score")
public class ScoreController {

    private static final String PUTONGHUA_URL = "http://www.cltt.org/StudentScore/ScoreResult";

    private static final String COMPUTER_REFERER = "http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryResults";

    private static final String CET_REFERER = "https://www.chsi.com.cn/cet/";

    @Autowired
    private HttpAPIService httpAPIService;

    //获取普通话考试成绩
    @PostMapping("/getPutonghuaScore")
    @ResponseBody
    @SysLog(desc = "查询普通话考试成绩",module = SysLogModule.SCORE_LOG,type = SysLogType.VISIT_LOG)
    public BaseResponse<PutonghuaModel> getPutonghuaScore(@RequestBody Map<String,String> map){
        PutonghuaModel model = new PutonghuaModel();
        try {
            Map<String,Object> params = new HashMap<>();
            params.putAll(map);
//            CloseableHttpResponse post = httpAPIService.getHandler().post(PUTONGHUA_URL, params);
            HttpResult httpResult = httpAPIService.doPost(PUTONGHUA_URL, params);
            Document doc = Jsoup.parse(httpResult.getBody());
//            post.close();
            //先读取考试时间，省份，测试站的父容器
            Elements contBoxTit = doc.getElementsByClass("cont-box-tit");
            if(contBoxTit.size()==0){
                return new BaseResponse<>(500,doc.getElementsByTag("h2").get(0).text());
            }
            Elements spans = contBoxTit.get(0).getElementsByTag("span");
            for (Element span : spans){
                String text = span.text();
                if(text.startsWith("考试时间：")){
                    model.setTime(text.replace("考试时间：",""));
                }else if(text.startsWith("测试省份：")){
                    model.setProvince(text.replace("测试省份：",""));
                }else if(text.startsWith("测试站：")){
                    model.setStation(text.replace("测试站：",""));
                }
            }
            //获取姓名分数等信息
            Element scoreInfo = doc.getElementsByClass("cont-box-tab").first();
            Elements trs = scoreInfo.getElementsByTag("tr");
            //获取姓名分数
            Element tr = trs.get(2);
            spans = tr.getElementsByTag("span");
            model.setName(spans.get(0).text());
            model.setScore(spans.get(1).text());
            //获取性别等级
            tr = trs.get(3);
            spans = tr.getElementsByTag("span");
            model.setSex(spans.get(0).text());
            model.setGrade(spans.get(1).text());
            //获取准考证号，证书编号
            tr = trs.get(4);
            spans = tr.getElementsByTag("span");
            model.setExamCardNum(spans.get(0).text());
            model.setSeriesNum(spans.get(1).text());
            //获取身份证号
            tr = trs.get(5);
            spans = tr.getElementsByTag("span");
            model.setIdNum(spans.get(0).text());
        } catch (Exception e) {
            SysLogger.error("查询普通话考试成绩",e,SysLogModule.SCORE_LOG,SysLogType.EXCEPTION_LOG);
            e.printStackTrace();
        }
        BaseResponse<PutonghuaModel> response = new BaseResponse<>();
        response.setData(model);
        return response;
    }


    /**
     * 初始化计算机等级考试时间列表
     * @return
     */
    @GetMapping("/initComputer")
    @ResponseBody
    public BaseResponse<Map<String,Object>> initComputer(){
        try {
            HttpHandler handler = httpAPIService.getHandler().build();
            handler.addHeader(new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36"));
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("act","doQueryCond"));
            pairs.add(new BasicNameValuePair("pram","certi"));
            pairs.add(new BasicNameValuePair("community","Home"));
            pairs.add(new BasicNameValuePair("sid","300"));
            CloseableHttpResponse response = handler.get("http://search.neea.edu.cn/QueryMarkUpAction.do",pairs);
            Map<String,Object> map = new HashMap<>();
            if(response!=null){
                //设置cookie与referer信息
                List<Cookie> cookies = handler.getCookies();
                StringBuilder str = new StringBuilder();
                str.append("UM_distinctid=1653170b66b1a6-08a32e79ded8d5-5e442e19-1fa400-1653170b66c29; language=1;");
                str.append("Hm_lvt_dc1d69ab90346d48ee02f18510292577=1535158477,1535166712,1535166726,1535337142; ");
                String esessionid = "";
                String big = "";
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("esessionid")){
                        esessionid = cookie.getValue();
                    }else if("BIGipServersearchtest.neea.edu.cn_search.neea.cn_pool".equals(cookie.getName())){
                        big = cookie.getValue();
                    }
                }
                str.append("esessionid="+esessionid+";");
                str.append("BIGipServersearchtest.neea.edu.cn_search.neea.cn_pool="+big+";");
                map.put("cookie",str.toString());
                List<Map<String,Object>> times = new ArrayList<>();
                List<Map<String,Object>> list = ScoreCache.getTimes();
                for(Map<String,Object> map1 : list){
                    map1.remove("subject");
            }
                map.put("times",list);
                return new BaseResponse<>(map);
            }
        } catch (URISyntaxException e) {
            SysLogger.error("初始化计算机等级考试",e,SysLogModule.SCORE_LOG,SysLogType.EXCEPTION_LOG);
            e.printStackTrace();
            return new BaseResponse<>(500,"初始化计算机等级考试界面异常");
        } catch (IOException e) {
            SysLogger.error("初始化计算机等级考试",e,SysLogModule.SCORE_LOG,SysLogType.EXCEPTION_LOG);
            e.printStackTrace();
            return new BaseResponse<>(500,"初始化计算机等级考试界面异常");
        }
        return null;
    }

    /**
     * 根据考试时间获取计算机等级考试科目
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping("/listComputerSubject")
    public BaseResponse<Map<String,Object>> listComputerSubject(@RequestBody Map<String,String> params){
        String examid = params.get("examid");
        try {
            return new BaseResponse<>(ScoreCache.getSubject(examid));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询计算机等级考试成绩
     * @param params
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/getCumputerScore")
    @SysLog(desc = "查询计算机等级考试成绩",module = SysLogModule.SCORE_LOG,type = SysLogType.VISIT_LOG)
    public BaseResponse<CumputerScoreModel> getCumputerScore(@RequestBody Map<String,String> params) throws IOException {
        String cookie = params.get("cookie");
       Map<String,Object> ps = new HashMap<>();
       ps.putAll(params);
       ps.remove("cookie");
       String nexturl = "/QueryMarkUpAction.do?act=doQueryCond&sid="+params.get("sid")+
               "&pram="+params.get("pram")+"&ksnf="+params.get("ksnf")+
               "bkjb="+params.get("bkjb")+"&sfzh="+params.get("sfzh")+
               "&name="+params.get("name");
       ps.put("nexturl",nexturl);
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Referer",COMPUTER_REFERER));
        headers.add(new BasicHeader("Cookie",cookie));
        CloseableHttpResponse response = httpAPIService.getHandler().addHeaders(headers).build()
                .post("http://search.neea.edu.cn/QueryMarkUpAction.do?act=doQueryResults",ps);
        String body = HttpHandler.getBody(response);
        response.close();
        //解析返回的结果
        CumputerScoreModel model = new CumputerScoreModel();
        Document doc = Jsoup.parse(body);
        Element wrap = doc.getElementById("container_certi");
        if(wrap!=null){
            Elements style12 = wrap.getElementsByClass("style12");
            model.setName(style12.get(0).text());
            model.setIdNum(style12.get(1).text());
            model.setSeriesNum(style12.get(8).text());
            model.setGrade(style12.get(9).text());
            return new BaseResponse<>(model);
        }else{
            return new BaseResponse<>(500,"查询失败，请检查您输入的信息是否正确");
        }



    }


    @ResponseBody
    @GetMapping("/initCet")
    public BaseResponse<Map<String,String>> initCet(){
        Map<String,String> result = new HashMap<>();
        CloseableHttpResponse response = null;
        try {
            HttpHandler build = httpAPIService.getHandler().build();
            response = build.get("https://www.chsi.com.cn/cet/");
            String body = HttpHandler.getBody(response);
            Document doc = Jsoup.parse(body);
            String text = doc.getElementsByClass("m_cnt_m").text();
            result.put("warning",text.split("\\[")[0]);
            List<Cookie> cookies = build.getCookies();

            String jSessionId = null;
            String acwTc = null;
            String aliyunfTc = null;
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")){
                    jSessionId = cookie.getValue();
                }else if(cookie.getName().equals("acw_tc")){
                    acwTc = cookie.getValue();
                }else if(cookie.getName().equals("aliyungf_tc")){
                    aliyunfTc = cookie.getValue();
                }
            }
            StringBuilder str = new StringBuilder();
            str.append("JSESSIONID=");
            str.append(jSessionId);
            str.append(";aliyungf_tc=");
            str.append(aliyunfTc);
            str.append(";acw_tc=");
            str.append(acwTc);
            str.append(";");
            result.put("cookie",str.toString());
            response.close();
        } catch (Exception e) {
            SysLogger.error("初始化四六级考试异常",e,SysLogModule.SCORE_LOG,SysLogType.EXCEPTION_LOG);
            e.printStackTrace();
            return new BaseResponse<>(500,"初始化四六级考试界面异常");
        }finally {
            if (response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new BaseResponse<>(result);
    }


    @PostMapping("/getCetScore")
    @ResponseBody
    @SysLog(desc = "查询四六级考试成绩",module = SysLogModule.SCORE_LOG,type = SysLogType.VISIT_LOG)
    public BaseResponse<CetScoreModel> getCetScore(@RequestBody Map<String,String> params){
        Map<String,Object> ps = new HashMap<>();
        ps.putAll(params);
        ps.remove("cookie");
        List<Header> headers = new ArrayList<>();
        String[] ck = params.get("cookie").split(";");
        headers.add(new BasicHeader("Referer","https://www.chsi.com.cn/cet/query"));
        headers.add(new BasicHeader("User-Agent","User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36"));
        headers.add(new BasicHeader("Host","www.chsi.com.cn"));
        List<Cookie> cookies = new ArrayList<>();
        for(String str : ck){
            if(!"".equals(str)){
                String[] st = str.split("=");
                if(st.length>1){
                    BasicClientCookie cookie = new BasicClientCookie(st[0],st[1]);
                    cookie.setDomain("www.chsi.com.cn");
                    cookie.setPath("/cet/");
                    cookies.add(cookie);
                }
            }
        }
        CloseableHttpResponse response = null;
        try {
             response = httpAPIService.getHandler().addHeaders(headers)
                    .addCookies(cookies)
                    .build()
                    .get("https://www.chsi.com.cn/cet/query",ps);
            String body = HttpHandler.getBody(response);
            response.close();
            Document doc = Jsoup.parse(body);
            Elements cnt = doc.getElementsByClass("cetTable");
            if(cnt.size()>0){
                CetScoreModel scoreModel = new CetScoreModel();
                Element ct = cnt.first();
                Elements trs = ct.getElementsByTag("tr");
                scoreModel.setName(trs.get(0).getElementsByTag("td").last().text());
                scoreModel.setSchool(trs.get(1).getElementsByTag("td").last().text());
                scoreModel.setType(trs.get(2).getElementsByTag("td").last().text());
                scoreModel.setWriteCardNum(trs.get(4).getElementsByTag("td").last().text());
                scoreModel.setWriteScore(trs.get(5).getElementsByTag("td").last().text());
                scoreModel.setListeningScore(trs.get(6).getElementsByTag("td").last().text());
                scoreModel.setReadingScore(trs.get(7).getElementsByTag("td").last().text());
                scoreModel.setWritingScore(trs.get(8).getElementsByTag("td").last().text());
                scoreModel.setOralCardNum(trs.get(10).getElementsByTag("td").last().text());
                scoreModel.setOralGrade(trs.get(11).getElementsByTag("td").last().text());
                return new BaseResponse<>(scoreModel);
            }else {
                Elements error = doc.getElementsByClass("error");
                if(error.size()>0){
                    return new BaseResponse<>(500,error.text().trim());
                }
                return new BaseResponse<>(500,"无法找到对应的分数，请确认您输入的准考证号及姓名无误。");
            }
        } catch (Exception e) {
            SysLogger.error("查询四六级考试成绩",e,SysLogModule.SCORE_LOG,SysLogType.EXCEPTION_LOG);
            e.printStackTrace();
            return new BaseResponse<>(500,"无法找到对应的分数，请确认您输入的准考证号及姓名无误。");
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
