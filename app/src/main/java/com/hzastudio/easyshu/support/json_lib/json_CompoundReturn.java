package com.hzastudio.easyshu.support.json_lib;

public class json_CompoundReturn<T> {
    private String status;
    private T data;

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

}
