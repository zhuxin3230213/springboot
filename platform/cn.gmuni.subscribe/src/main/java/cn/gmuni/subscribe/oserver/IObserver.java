package cn.gmuni.subscribe.oserver;

public interface IObserver<T> {
    public void update(T t);
}
