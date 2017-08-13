package com.hzastudio.easyshu.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzastudio.easyshu.support.data_bean.UserCourse;
import com.hzastudio.easyshu.support.json_lib.json_CompoundReturn;
import com.hzastudio.easyshu.support.program_const.URL;
import com.hzastudio.easyshu.support.tool.HttpFramework;
import com.hzastudio.easyshu.support.tool.SecurityTool;
import com.hzastudio.easyshu.support.universal.MainApplication;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CJTasks {

    private static String[] getUserSignature() throws NoSuchAlgorithmException
    {
        String[] Ret=new String[5];
        SharedPreferences user = MainApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        Ret[0]=user.getString("username","");
        Ret[1]=user.getString("password","");
        Ret[2]=SecurityTool.getRandomString(15);
        Ret[3]=String.valueOf(System.currentTimeMillis());
        Ret[4]=SecurityTool.SignatureGenerate(Ret[0],Ret[1],Ret[2],Ret[3]);
        return Ret;
    }

    public static List<UserCourse> Task_CJ_getCourseTable(String Username,String Year,String Season)
            throws NoSuchAlgorithmException,IOException,NullPointerException
    {
        HttpFramework handler= HttpFramework.getInstance();
        String[] user = getUserSignature();
        RequestBody body=new FormBody.Builder()
                .add("usr",user[0])
                .add("psw",user[1])
                .add("randstr",user[2])
                .add("timestamp",user[3])
                .add("sign",user[4])
                .add("function","CJ_getCourseTable")
                .add("data[usr]",Username)
                .add("data[year]",Year)
                .add("data[season]",Season)
                .build();

        Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
        String result=response.body().string();
        Log.d("Task_CJ_getCourseTable","Result:"+result);

        Gson gson=new Gson();
        Type CourseTableType = new TypeToken<json_CompoundReturn<List<UserCourse>>>(){}.getType();
        json_CompoundReturn<List<UserCourse>> res=gson.fromJson(result,CourseTableType);
        Log.d("Task_CJ_getCourseTable","Status:"+res.getStatus());
        return res.getData();

    }
}
