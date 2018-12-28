package com.gmunidata.base.response;

//返回内容的util
public class Content {
    //标识符
    private int  flag;
    //信息
    private String message;
    //返回的内容
    private Object content;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static Content gettSuccess(){
        Content con = new Content();
        con.setFlag(0);
        con.setMessage("请求成功！");
        return con;
    }

    public static Content gettError(){
        Content con = new Content();
        con.setFlag(1);
        con.setMessage("请求失败！");
        return con;
    }
}
