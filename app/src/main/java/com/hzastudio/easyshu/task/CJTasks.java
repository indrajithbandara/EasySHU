package com.hzastudio.easyshu.task;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzastudio.easyshu.support.data_bean.UserCourse;
import com.hzastudio.easyshu.support.json_lib.json_CompoundReturn;
import com.hzastudio.easyshu.support.program_const.URL;
import com.hzastudio.easyshu.support.tool.HttpFramework;
import com.hzastudio.easyshu.support.tool.SecurityTool;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 与服务器通信函数（教务系统处理）（阻塞）
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class CJTasks {

    public static List<UserCourse> Task_CJ_getCourseTable(String Year,String Season)
            throws NoSuchAlgorithmException,IOException,NullPointerException
    {
        HttpFramework handler= HttpFramework.getInstance();
        String[] user = SecurityTool.getUserSignature();
        RequestBody body=new FormBody.Builder()
                .add("usr",user[0])
                .add("psw",user[1])
                .add("randstr",user[2])
                .add("timestamp",user[3])
                .add("sign",user[4])
                .add("function","CJ_getCourseTable")
                .add("data[usr]",user[0])
                .add("data[year]",Year)
                .add("data[season]",Season)
                .build();

        Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
        String result=response.body().string();
        Log.d("Task_CJ_getCourseTable","Result:"+result);

        Gson gson=new Gson();
        Type CourseTableType = new TypeToken<json_CompoundReturn<List<UserCourse>>>(){}.getType();
        json_CompoundReturn<List<UserCourse>> res=gson.fromJson(result,CourseTableType);
        //Log.d("Task_CJ_getCourseTable","Status:"+res.getStatus());
        return res.getData();

    }
}
