package cn.gmuni.sc.umpush.notification;

import cn.gmuni.sc.umpush.model.NotificationModel;

public interface IPushGroupNotification {
     /**
     * 通过制定的标签进行推送
     * @param model
     * @param tags
     * @return
     */
     IPushGroupNotification setGroupNotification(NotificationModel model, String... tags) throws Exception;

    boolean send() throws Exception;
}
