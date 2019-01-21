package cn.gmuni.sc.umpush.android;

import cn.gmuni.sc.umpush.client.PushClient;
import cn.gmuni.sc.umpush.model.NotificationModel;
import cn.gmuni.sc.umpush.notification.IPushGroupNotification;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

public class PushAndroidGroupNotification implements IPushGroupNotification {
    private AndroidGroupcast groupcast;
    private PushClient client;
    public PushAndroidGroupNotification(AndroidGroupcast groupcast, PushClient client){
        this.groupcast = groupcast;
        this.client = client;
    }
    @Override
    public IPushGroupNotification setGroupNotification(NotificationModel model, String... tags) throws Exception {
        if(tags.length==0){
            throw  new Exception("请至少添加一个标签");
        }
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
        JSONArray tagArray = new JSONArray();
        for(String tag : tags){
            JSONObject to = new JSONObject();
            to.put("tag",tag);
            tagArray.add(to);
        }
        whereJson.put("and",tagArray);
        filterJson.put("where",whereJson);
        this.groupcast.setFilter(filterJson);
        this.groupcast.setTicker(model.getTicker());
        this.groupcast.setTitle(model.getTitle());
        this.groupcast.setText(model.getText());
        this.groupcast.setExtraField("extra", JsonUtil.object2Json(model.getExtraData()));
        //开发环境设置为测试模式
//        this.broadcast.setProductionMode();
        this.groupcast.setProductionMode();
        this.groupcast.goCustomAfterOpen(model.getFlag()+"");
        this.groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        return this;
    }


    @Override
    public boolean send() throws Exception {
        return this.client.send(this.groupcast);
    }

    public static void main(String[] args){
        System.out.println(Arrays.toString(new String[]{"A","b"}));
    }
}
