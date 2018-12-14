package com.gmunidata.hairdryer.exception;

public class SerialPortOutputStreamCloseFailure extends Exception{
    @Override
    public String getMessage() {
        return "关闭串口对象的输出流出错";
    }
}
