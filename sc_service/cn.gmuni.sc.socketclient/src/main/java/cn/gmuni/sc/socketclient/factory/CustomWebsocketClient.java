package cn.gmuni.sc.socketclient.factory;



import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

public class CustomWebsocketClient extends WebSocketClient {

    private String code;

    private StateListener listener;

    public CustomWebsocketClient(String code,URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
        this.code = code;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        if(listener!=null){
            listener.onOpen(serverHandshake,code);
        }
    }

    @Override
    public void onMessage(String s) {
        if(listener!=null){
            listener.onMessage(s,code);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        if(listener!=null){
            listener.onClose(code);
        }
    }

    @Override
    public void onError(Exception e) {
        if(listener!=null){
            listener.onError(e,code);
        }
    }

    public void setStateListener(StateListener listener){
        this.listener = listener;
    }

     public abstract static class StateListener{
        public abstract void onOpen(ServerHandshake handshake,String code);

        public abstract void onMessage(String msg,String code);

        public abstract void onError(Exception e,String code);

        public abstract void onClose(String code);
     }

}
