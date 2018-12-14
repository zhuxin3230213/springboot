package cn.gmuni.sc.express.service.impl;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.express.model.Express;
import cn.gmuni.sc.express.service.ExpressService;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

/**
 * @Author: zhuxin
 * @Date: 2018/9/17 14:59
 * @Description:
 */
@Service
public class KdniaoTrackQueryAPI {

    //电商ID
    private String EBusinessID = "1384150";
    //电商加密私钥
    private String AppKey = "5e84f107-c37f-4b0d-986e-6105c26784f8";
    //请求url
    private String ReqURL = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

    @Autowired
    @Qualifier("expressServiceImpl")
    ExpressService expressService;


    /**
     * Json方式 查询订单物流轨迹
     * OrderCode 订单编号
     * ShipperCode 快递公司编码
     * LogisticCode 物流单号
     *
     * @throws Exception
     */
    public List<Map<String, String>> getOrderTracesByJson(String expCode, String expNo) throws Exception {
        String requestData = "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8")); //请求内容，JSON或XML格式,须和DataType一致
        params.put("EBusinessID", EBusinessID); //用户ID
        params.put("RequestType", "1002"); //请求指令类型：1002
        String dataSign = encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8")); //数据内容签名
        params.put("DataType", "2"); //请求、返回数据类型：1-xml,2-json；默认为xml格式

        String result = sendPost(ReqURL, params);
        JSONObject typeJsonObject = (JSONObject) JSON.parse(result);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

      /*  System.out.println(typeJsonObject.get("LogisticCode"));
        System.out.println(typeJsonObject.get("ShipperCode"));
        System.out.println(typeJsonObject.get("State"));*/
        JSONArray dataArray = (JSONArray) typeJsonObject.get("Traces");
        for (int i = 0; i < dataArray.size(); i++) {
            Map<String, String> tempMap = new HashMap<String, String>();
            JSONObject temp = (JSONObject) dataArray.get(i);
            tempMap.put("information", (String) temp.get("AcceptStation"));
            tempMap.put("acceptTime", (String) temp.get("AcceptTime"));
            tempMap.put("state", (String) typeJsonObject.get("State"));
            list.add(tempMap);
        }
        //添加查询的物流信息
        addOrUpdateExpress(expNo, expCode, typeJsonObject);

        return list;
    }


    //添加或更新物流信息
    private void addOrUpdateExpress(String expNo, String expCode, JSONObject typeJsonObject) throws Exception {
        //添加物流信息
        String orderTracesByJson = getOrderTracesByJson(expNo);
        JSONObject jsonObject = JSONObject.parseObject(orderTracesByJson);
        Map map = jsonObject.toJavaObject(Map.class);
        Object shippers = map.get("Shippers");
        List<Map> maps = JSONArray.parseArray(JsonUtil.object2Json(shippers), Map.class);

        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();

        //先从数据库查询
        Map<String, String> param = new HashMap<>();
        param.put("userInfo", loginUserInfo.getIndentifier());
        param.put("expNo", expNo);
        param.put("expCode", expCode);
        BaseResponse<List<Express>> listExpress = expressService.listExpress(param);
        List<Express> data = listExpress.getData();

        if (maps != null) {
            if (maps.size() != 0 && maps.size() < 2) {
                String expName = String.valueOf(maps.get(0).get("ShipperName"));
                if (data.size() == 0) {
                    if ("STO".equals(expCode) || "SF".equals(expCode) || "HHTT".equals(expCode) || "HTKY".equals(expCode)) {
                    } else {
                        Express express = new Express();
                        express.setBussinessId(EBusinessID);
                        express.setExpCode(expCode);
                        express.setExpNo(expNo);
                        express.setExpName(expName);
                        express.setExpLogo(null);
                        express.setOrderCode(String.valueOf(typeJsonObject.get("OrderCode")));
                        express.setState(typeJsonObject.get("State").toString());
                        express.setInformation(typeJsonObject.get("Traces").toString());
                        express.setUserInfo(loginUserInfo.getIndentifier());
                        express.setCampus(loginUser.getSchool());
                        expressService.add(express);
                    }
                } else {
                    Express express = data.get(0);
                    express.setState(typeJsonObject.get("State").toString());
                    express.setInformation(typeJsonObject.get("Traces").toString());
                    express.setClickThrough(express.getClickThrough() + 1);
                    express.setUpdateTime(new Date());
                    expressService.update(express);
                }
            } else {
                for (Map temp : maps) {
                    if (temp.get("ShipperCode").equals(expCode)) {
                        String expName = String.valueOf(temp.get("ShipperName"));
                        if (data.size() == 0) {
                            if ("STO".equals(expCode) || "SF".equals(expCode) || "HHTT".equals(expCode) || "HTKY".equals(expCode)) {
                            } else {
                                Express express = new Express();
                                express.setBussinessId(EBusinessID);
                                express.setExpCode(expCode);
                                express.setExpName(expName);
                                express.setExpNo(expNo);
                                express.setExpLogo(null);
                                express.setOrderCode(String.valueOf(typeJsonObject.get("OrderCode")));
                                express.setState(typeJsonObject.get("State").toString());
                                express.setInformation(typeJsonObject.get("Traces").toString());
                                express.setUserInfo(loginUserInfo.getIndentifier());
                                express.setCampus(loginUser.getSchool());
                                expressService.add(express);
                            }
                        } else {
                            Express express = data.get(0);
                            express.setInformation(typeJsonObject.get("Traces").toString());
                            express.setState(typeJsonObject.get("State").toString());
                            express.setClickThrough(express.getClickThrough() + 1);
                            express.setUpdateTime(new Date());
                            expressService.update(express);
                        }
                    }

                }
            }
        }

    }

    /**
     * Json方式 单号识别接口
     *
     * @throws Exception
     */
    public String getOrderTracesByJson(String expNo) throws Exception {
        String requestData = "{'LogisticCode':'" + expNo + "'}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "2002");
        String dataSign = encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = sendPost(ReqURL, params);

        //根据公司业务处理返回的信息......

        return result;
    }

    /**
     * MD5加密
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     *
     * @param content  内容
     * @param keyValue Appkey
     * @param charset  编码方式
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    @SuppressWarnings("unused")
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("MessContent-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    //System.out.println(entry.getKey()+":"+entry.getValue());
                }
                //System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }


    private static char[] base64EncodeChars = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }
}
