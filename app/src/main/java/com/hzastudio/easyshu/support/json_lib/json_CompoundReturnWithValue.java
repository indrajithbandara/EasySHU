package com.hzastudio.easyshu.support.json_lib;

import com.google.gson.annotations.SerializedName;

public class json_CompoundReturnWithValue<T1,T2> {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private T1 data;
    @SerializedName("value")
    private T2 value;

    public String getStatus() {
        return status;
    }

    public T1 getData() {
        return data;
    }

    public T2 getValue() {
        return value;
    }
}
