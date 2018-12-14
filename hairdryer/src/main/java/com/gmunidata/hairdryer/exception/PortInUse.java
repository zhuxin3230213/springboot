package com.gmunidata.hairdryer.exception;

public class PortInUse extends Exception {
    @Override
    public String getMessage() {
        return "端口已被占用";
    }
}
