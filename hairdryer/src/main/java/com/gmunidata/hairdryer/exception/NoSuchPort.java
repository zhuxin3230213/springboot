package com.gmunidata.hairdryer.exception;

public class NoSuchPort extends Exception {
    @Override
    public String getMessage() {
        return "没有该端口对应的串口设备";
    }
}
