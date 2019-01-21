package cn.gmuni.sc.express.service.impl;

import cn.gmuni.sc.base.response.BaseResponse;
import cn.gmuni.sc.express.model.Express;
import cn.gmuni.sc.express.service.ExpressService;
import cn.gmuni.sc.http.http.HttpAPIService;
import cn.gmuni.sc.user.model.ThirdPartUser;
import cn.gmuni.sc.user.model.ThirdPartUserInfo;
import cn.gmuni.sc.user.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.*;

/**
 * @Author: zhuxin
 * @Date: 2018/9/18 10:46
 * @Description:
 */
@Service
public class Kd100QueryAPI {

    //请求url
    private String ReqURL = "http://www.kuaidi100.com";

    @Autowired
    HttpAPIService httpAPIService;

    @Autowired
    @Qualifier("expressServiceImpl")
    ExpressService expressService;

    private JSONObject jsonObject;


    private void send(String expNo, String type) {
        String param = "{\"com\":\"***\",\"num\":\"***\"}";
        String customer = "***";
        String key = "****";
        String resp;
        try {
            String sign = MD5(param + key + customer, "utf-8");
            HashMap params = new HashMap();
            params.put("param", param);
            params.put("sign", sign);
            params.put("customer", customer);
            resp = httpAPIService.doGet("http://poll.kuaidi100.com/poll/query.do", params);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过快递单号获取是哪个快递公司。
     * Json方式 单号识别接口
     *
     * @throws Exception
     */
    public List<Map<String, String>> getOrderTracesByJson(String expNo) throws Exception {
        if (StringUtils.isBlank(expNo)) {
            return null;
        }
        String typeResult = httpAPIService.doGet(ReqURL + "/autonumber/autoComNum?text=" + expNo);
        JSONObject typeJsonObject = (JSONObject) JSON.parse(typeResult);
        JSONArray typeDataArray = (JSONArray) typeJsonObject.get("auto");
        if (typeDataArray.isEmpty()) {
            return null;
        }
        JSONObject typeObject = (JSONObject) typeDataArray.get(0);
        String type = typeObject.getString("comCode");
        return queryInformation(expNo, type);
    }

    // 通过快递公司编号及快递单号获取物流信息。
    public List<Map<String, String>> queryInformation(String expNo, String type) {
        try {
            String kuaidiResult = httpAPIService.doGet(ReqURL + "/query?type=" + type + "&postid=" + expNo);
            jsonObject = (JSONObject) JSON.parse(kuaidiResult);
            String status = jsonObject.getString("status");
            String state = jsonObject.getString("state");
            if (!"200".equals(status)) {
                return null;
            }
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
            for (int i = 0; i < dataArray.size(); i++) {
                Map<String, String> tempMap = new HashMap<String, String>();
                JSONObject temp = (JSONObject) dataArray.get(i);
                tempMap.put("acceptTime", (String) temp.get("time"));
                tempMap.put("information", (String) temp.get("context"));
                tempMap.put("state", state);
                tempMap.put("expCode", type); //添加快递公司编号
                list.add(tempMap);
            }
            //添加或者更新用户查询的物流信息
            addOrUpdateExpress(expNo, type, state);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //添加或更新用户查询物流信息
    private void addOrUpdateExpress(String expNo, String type, String state) {
        //获取用户信息
        ThirdPartUserInfo loginUserInfo = UserUtils.getLoginUserInfo();
        ThirdPartUser loginUser = UserUtils.getLoginUser();
        //添加快递信息数据数据
        Map<String, String> param = new HashMap<>();
        param.put("userInfo", loginUserInfo.getIndentifier());
        param.put("expNo", expNo);
        param.put("expCode", type);
        BaseResponse<List<Express>> listExpress = expressService.listExpress(param);
        List<Express> data = listExpress.getData();
        if (data.size() == 0) {
            Express express = new Express();
            express.setBussinessId("");
            express.setExpCode(type);
            express.setExpNo(expNo);
            if ("shentong".equals(type)) {
                express.setExpName("申通快递");
            } else if ("huitongkuaidi".equals(type)) {
                express.setExpName("百世快递");
            } else if ("tiantian".equals(type)) {
                express.setExpName("天天快递");
            } else if ("shunfeng".equals(type)) {
                express.setExpName("顺丰快递");
            } else {
                express.setExpName("");
            }
            express.setExpLogo("");
            express.setOrderCode("");
            express.setState(state);
            express.setInformation(jsonObject.get("data").toString());
            express.setUserInfo(loginUserInfo.getIndentifier());
            express.setCampus(loginUser.getSchool());
            expressService.add(express);
        } else {
            Express express = data.get(0);
            express.setState(state);
            express.setInformation(jsonObject.get("data").toString());
            express.setClickThrough(express.getClickThrough() + 1);
            express.setUpdateTime(new Date());
            expressService.update(express);
        }
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
        return sb.toString().toUpperCase();
    }

}
