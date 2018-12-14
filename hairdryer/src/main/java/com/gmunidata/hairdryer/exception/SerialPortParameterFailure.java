package com.gmunidata.hairdryer.exception;

public class SerialPortParameterFailure extends Exception {
    @Override
    public String getMessage() {
        return "设置串口参数失败";
    }
}
