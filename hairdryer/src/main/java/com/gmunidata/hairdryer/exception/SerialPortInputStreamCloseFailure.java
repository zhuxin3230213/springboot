package com.gmunidata.hairdryer.exception;

public class SerialPortInputStreamCloseFailure extends Exception {
    @Override
    public String getMessage() {
        return "关闭串口对象输入流出错";
    }
}
