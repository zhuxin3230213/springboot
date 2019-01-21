package cn.gmuni.sc.socketclient.model;

import java.io.Serializable;

/**
 * websocket返回对象
 * @param <T>
 */
public class WebSocketModel<T> implements Serializable {

    private int flag;

    private T data;

    private boolean success = true;

    public WebSocketModel(){}

    public WebSocketModel(int flag, T data) {
        this.flag = flag;
        this.data = data;
    }

    public WebSocketModel(int flag, boolean success) {
        this.flag = flag;
        this.success = success;
    }

    public WebSocketModel(int flag, T data, boolean success) {
        this.flag = flag;
        this.data = data;
        this.success = success;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
