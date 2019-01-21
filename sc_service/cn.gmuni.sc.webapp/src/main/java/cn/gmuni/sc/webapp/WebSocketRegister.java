package cn.gmuni.sc.webapp;

import cn.gmuni.sc.information.publish.MessageClient;
import cn.gmuni.sc.noticenews.service.NewsClient;
import cn.gmuni.sc.noticenews.service.NoticeClient;
import cn.gmuni.sc.socketclient.subject.WebSocketSubject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: zhuxin
 * @Date: 2018/9/29 15:47
 * @Description:
 * 注册推送监听
 */
@Component
public class WebSocketRegister {

    @PostConstruct
    public void init(){
        WebSocketSubject.getInstance().registerObserver(new NewsClient());
        WebSocketSubject.getInstance().registerObserver(new NoticeClient());
        WebSocketSubject.getInstance().registerObserver(new MessageClient());
    }
}
