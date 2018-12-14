package cn.gmuni.sc.umpush.android;

import cn.gmuni.sc.umpush.client.PushClient;
import cn.gmuni.sc.umpush.model.NotificationModel;
import cn.gmuni.sc.umpush.notification.IPushNotification;
import cn.gmuni.sc.utils.httputils.JsonUtil;

/**
 * android推送
 */
public class PushAndroidNotification implements IPushNotification {
    private AndroidBroadcast broadcast;
    private PushClient client;
    public PushAndroidNotification(AndroidBroadcast broadcast, PushClient client){
        this.broadcast = broadcast;
        this.client = client;
    }

    /**
     * 设置基本的推送消息
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public IPushNotification setNotification(NotificationModel model) throws Exception {
        this.broadcast.setTicker(model.getTicker());
        this.broadcast.setTitle(model.getTitle());
        this.broadcast.setText(model.getText());
        this.broadcast.setExtraField("extra", JsonUtil.object2Json(model.getExtraData()));
        //开发环境设置为测试模式
//        this.broadcast.setProductionMode();
        this.broadcast.setProductionMode();
        this.broadcast.goCustomAfterOpen(model.getFlag()+"");
        this.broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        return this;
    }


    /*后续需添加直接推送到安卓内部的方法*/

    /**
     * 发送消息
     * @return
     * @throws Exception
     */
    public boolean send() throws Exception {
        return this.client.send(this.broadcast);
    }
}