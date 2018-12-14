package com.gmunidata.hairdryer.exception;

public class TooManyListeners extends Exception {
    @Override
    public String getMessage() {
        return "监听类对象过多";
    }
}
