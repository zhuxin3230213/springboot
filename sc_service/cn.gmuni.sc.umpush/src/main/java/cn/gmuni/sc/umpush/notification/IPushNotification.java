package cn.gmuni.sc.umpush.notification;

import cn.gmuni.sc.umpush.model.NotificationModel;

public interface IPushNotification {


    IPushNotification setNotification(NotificationModel model) throws Exception;

//    /**
//     * 通过制定的标签进行推送
//     * @param model
//     * @param tags
//     * @return
//     */
//    IPushNotification setGroupNotification(NotificationModel model,String... tags) throws Exception;
//
//    /**
//     * 通过学校进行推送
//     * @param model
//     * @return
//     */
//    IPushNotification setNotificationWithSchool(NotificationModel model);

    boolean send() throws Exception;
}
