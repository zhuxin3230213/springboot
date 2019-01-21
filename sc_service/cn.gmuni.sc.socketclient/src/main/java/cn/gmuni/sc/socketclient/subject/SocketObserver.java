package cn.gmuni.sc.socketclient.subject;

import cn.gmuni.sc.socketclient.model.WebSocketModel;

public abstract class SocketObserver {

    public abstract void onMessage(WebSocketModel model,String code);

    public abstract int getFlag();

    public void update(WebSocketModel model,String code){
        if(model.getFlag() == getFlag()){
            onMessage(model,code);
        }
    }
}
