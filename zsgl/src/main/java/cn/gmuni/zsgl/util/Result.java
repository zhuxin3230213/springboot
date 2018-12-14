package cn.gmuni.zsgl.util;

/**
 * @Author:ZhuXin
 * @Description:返回数据j结果集的处理
 * @Date:Create in 14:54 2018/5/7
 * @Modified By:
 **/


public class Result<T> {
    private Integer status;//状态码
    private String msg;//描述状态的提示信息
    private T data;//该状态返回的数据

    public Result(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
