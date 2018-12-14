package com.gmunidata.hairdryer.exception;

public class ReadDataFromSerialPortFailure extends Exception {
    @Override
    public String getMessage() {
        return "从串口读取数据时出错";
    }
}
