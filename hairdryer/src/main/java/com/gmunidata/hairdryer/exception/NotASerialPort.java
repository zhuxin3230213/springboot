package com.gmunidata.hairdryer.exception;

public class NotASerialPort extends Exception {
    @Override
    public String getMessage() {
        return "端口指向设备不是串口类型";
    }
}
