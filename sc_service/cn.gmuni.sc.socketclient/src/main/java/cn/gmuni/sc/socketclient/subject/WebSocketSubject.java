package cn.gmuni.sc.socketclient.subject;

import cn.gmuni.sc.socketclient.model.WebSocketModel;

import java.util.ArrayList;
import java.util.List;

public class WebSocketSubject {

    private List<SocketObserver> observers = new ArrayList<>();

    private static WebSocketSubject instance = null;

    private WebSocketSubject(){

    }

    public void registerObserver(SocketObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(SocketObserver observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(WebSocketModel model,String code){
        for(SocketObserver observer : observers){
            observer.update(model,code);
        }
    }

    public static WebSocketSubject getInstance(){
        if(instance==null){
            synchronized (WebSocketSubject.class){
                if(instance==null){
                    instance = new WebSocketSubject();
                }
            }
        }
        return instance;
    }
}
