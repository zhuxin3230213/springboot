package com.gmunidata.hairdryer.exception;

public class SendDataToSerialPortFailure extends Exception {
    @Override
    public String getMessage() {
        return "向串口发送数据失败";
    }
}
