package com.gmunidata.push.server;

import com.gmunidata.push.config.WebSocketConfig;
import com.gmunidata.push.model.WebSocketModel;
import com.gmunidata.utils.JsonUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Controller
@ServerEndpoint(value = "/websocket/server" ,configurator = WebSocketConfig.class)
public class WebSocketServer {

    private static WebSocketServer webSocketServer = null;

    private Session session;

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketServer = this;
    }


    @OnClose
    public void onClose(){
        webSocketServer = null;
    }

    @OnMessage
    public void onMessage(String message,Session session){

    }

    @OnError
    public void onError(Session session,Throwable err){
        err.printStackTrace();
    }

    public void sendMessage(WebSocketModel model) throws IOException {
        if(webSocketServer!=null){
            this.session.getBasicRemote().sendText(JsonUtil.object2Json(model));
        }
    }

    /**
     * 发送消息
     * @param model
     * @throws IOException
     */
    public static void sendInfo(WebSocketModel model) throws IOException {
        if(webSocketServer!=null){
            webSocketServer.sendMessage(model);
        }
    }

    public static boolean isClose(){
        return webSocketServer == null;
    }

}
