package cn.gmuni.sc.noticenews.service;

import cn.gmuni.sc.socketclient.constants.SocketClientFlagConst;
import cn.gmuni.sc.socketclient.model.WebSocketModel;
import cn.gmuni.sc.socketclient.subject.SocketObserver;
import cn.gmuni.sc.umpush.PushNotificationFactory;
import cn.gmuni.sc.umpush.model.NotificationModel;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Author: zhuxin
 * @Date: 2018/9/29 15:17
 * @Description:
 */
public class NewsClient extends SocketObserver {

    @Override
    public void onMessage(WebSocketModel model,String code) {
        try {
            Map<String, Object> data = (Map<String, Object>) model.getData();//获取数据
            if (model.getFlag() == SocketClientFlagConst.NEWS_FLAG) {
                NotificationModel mod = new NotificationModel();
                mod.setFlag(SocketClientFlagConst.NEWS_FLAG);
                mod.setTitle(data.get("title").toString());
                if (StringUtils.isEmpty(data.get("description").toString())) {
                    mod.setText("");
                } else {
                    mod.setText(data.get("description").toString());
                }
                mod.setTicker(data.get("title").toString());
                mod.setExtraData(data);
                PushNotificationFactory.buildWithGroup().setGroupNotification(mod,code).send();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getFlag() {
        return SocketClientFlagConst.NEWS_FLAG;
    }

}
