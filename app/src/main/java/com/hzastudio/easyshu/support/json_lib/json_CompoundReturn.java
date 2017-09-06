package com.hzastudio.easyshu.support.json_lib;

import com.google.gson.annotations.SerializedName;

public class json_CompoundReturn<T> {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private T data;

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

}
