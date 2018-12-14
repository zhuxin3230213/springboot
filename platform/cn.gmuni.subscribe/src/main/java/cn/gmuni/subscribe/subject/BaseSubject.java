package cn.gmuni.subscribe.subject;

import cn.gmuni.subscribe.oserver.IObserver;

import java.util.ArrayList;
import java.util.List;

public  class BaseSubject<T> {
    private List<IObserver<T>> observers = new ArrayList<>();
    public  void register(IObserver<T> observer){
        observers.add(observer);
    }

    public void remove(IObserver<T> observer){
        observers.remove(observer);
    }

   public void notifyObserver(T t){
        for(IObserver observer : observers){
            observer.update(t);
        }
    }
}
