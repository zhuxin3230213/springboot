package cn.gmuni.sc.information.publish;

import cn.gmuni.base.ApplicationContextProvider;
import cn.gmuni.sc.information.model.MessageModel;
import cn.gmuni.sc.information.service.MessageService;
import cn.gmuni.sc.information.service.impl.InformationServiceImpl;
import cn.gmuni.sc.information.service.impl.MessageServiceImpl;
import cn.gmuni.sc.socketclient.constants.SocketClientFlagConst;
import cn.gmuni.sc.socketclient.model.WebSocketModel;
import cn.gmuni.sc.socketclient.subject.SocketObserver;
import cn.gmuni.sc.umpush.PushNotificationFactory;
import cn.gmuni.sc.umpush.model.NotificationModel;
import cn.gmuni.sc.utils.DateUtils;

import java.util.*;

/**
 * @Author:ZhuXin
 * @Description: 消息发送
 * @Date:Create 2018/11/21 16:58
 * @Modified By:
 **/
public class MessageClient extends SocketObserver {

    InformationServiceImpl informationService;

    MessageService messageService;

    @Override
    public void onMessage(WebSocketModel model, String code) {

        Map<String, Object> data = (Map<String, Object>) model.getData();
        if (model.getFlag() == SocketClientFlagConst.MESSAGE_FLAG) {
            List<String> studentCodes = (List<String>) data.get("studentCodes"); //学生列表
            //消息
            String title = String.valueOf(data.get("title"));
            String content = String.valueOf(data.get("content"));
            String publishTime = String.valueOf(data.get("createTime"));
            String publisher = String.valueOf(data.get("releaseUser"));
            String depteName = String.valueOf(data.get("deptName"));

            List<String> listIndentifier= new ArrayList<>();
            //添加消息详情
            for (String temp : studentCodes) {
                //temp :学号   code :学校编码
                Map<String, Object> map = new HashMap<>();
                map.put("studentId", temp);
                map.put("school", code);
                if (this.informationService == null) {
                    this.informationService = (InformationServiceImpl) ApplicationContextProvider.getBean("informationServiceImpl");
                }
                Map<String, Object> map1 = informationService.findIndentifier(map);
                //获取完善学籍信息学生列表
                if (map1 != null) {
                    String indentifier = String.valueOf(map1.get("indentifier")); //获取学生登录账号
                    listIndentifier.add(indentifier);
                    //添加消息详情
                    MessageModel messageModel = new MessageModel();
                    messageModel.setType("0"); //通知消息
                    messageModel.setTitle(title);
                    messageModel.setContent(content);
                    messageModel.setPublisher(publisher);
                    messageModel.setPublishTime(DateUtils.string2Date(publishTime, DateUtils.COMMON_DATETIME));
                    messageModel.setDeptName(depteName);
                    messageModel.setSchoolCode(code);
                    messageModel.setStudentCode(temp);
                    messageModel.setUserInfo(indentifier);
                    if (this.messageService == null) {
                        this.messageService = (MessageServiceImpl) ApplicationContextProvider.getBean("messageServiceImpl");
                    }
                    MessageModel add = messageService.add(messageModel);
                }
            }

            //推送消息至app端
            String[] strings = listIndentifier.toArray(new String[listIndentifier.size()]);
            MessageModel messageModel = new MessageModel();
            messageModel.setType("0"); //通知消息
            messageModel.setTitle(title);
            messageModel.setContent(content);
            messageModel.setPublisher(publisher);
            messageModel.setPublishTime(DateUtils.string2Date(publishTime, DateUtils.COMMON_DATETIME));
            messageModel.setDeptName(depteName);
            messageModel.setSchoolCode(code);

            NotificationModel mod = new NotificationModel();
            mod.setFlag(SocketClientFlagConst.MESSAGE_FLAG);
            mod.setTitle(title);
            mod.setTicker(title);
            mod.setText(content);
            mod.setExtraData(messageModel);
            try {
                PushNotificationFactory.buildWithGroup().setGroupNotification(mod, strings).send();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

    @Override
    public int getFlag() {
        return SocketClientFlagConst.MESSAGE_FLAG;
    }
}
