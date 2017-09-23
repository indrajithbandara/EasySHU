package com.hzastudio.easyshu.support.json_lib;

import com.google.gson.annotations.SerializedName;

/**
 * json复合返回(String status,Object data)
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
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
