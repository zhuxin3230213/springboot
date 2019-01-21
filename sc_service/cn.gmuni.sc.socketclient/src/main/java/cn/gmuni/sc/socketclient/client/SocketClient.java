package cn.gmuni.sc.socketclient.client;

import cn.gmuni.sc.college.cache.CollegeCache;
import cn.gmuni.sc.college.model.College;
import cn.gmuni.sc.socketclient.factory.CustomWebsocketClient;
import cn.gmuni.sc.socketclient.factory.WebSocketClientFactory;
import cn.gmuni.sc.socketclient.model.WebSocketModel;
import cn.gmuni.sc.socketclient.subject.WebSocketSubject;
import cn.gmuni.sc.utils.httputils.JsonUtil;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class SocketClient {

    private Map<String, CustomWebsocketClient> clientMap = new HashMap<>();

    /**
     * 五分钟检测一次连接状态，连接失败的重连
     */
    @Scheduled(fixedDelay = 1000*60*5)
    protected  void  connection(){
        List<College> allCollege = CollegeCache.getAllCollege();
        for(College college : allCollege){
            if(clientMap.get(college.getCode())==null){
                try {
                    String burl = college.getUrl().replace("http:","ws:").replace("https:","ws:");
                    if(burl.endsWith("/")){
                        burl = burl.substring(0,burl.length()-1);
                    }
                    String url = burl+"/websocket/server";
                    CustomWebsocketClient client = WebSocketClientFactory.create(college.getCode(),url);
                    client.setStateListener(new CustomWebsocketClient.StateListener(){

                        @Override
                        public void onOpen(ServerHandshake handshake,String code) {
                            clientMap.put(code,client);
                        }

                        @Override
                        public void onMessage(String msg,String code) {
                            WebSocketSubject.getInstance().notifyObservers(JsonUtil.json2Object(msg, WebSocketModel.class),code);
                        }

                        @Override
                        public void onError(Exception e,String code) {

                        }

                        @Override
                        public void onClose(String code) {
                            clientMap.remove(code);
                        }
                    });
                    client.connect();

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
