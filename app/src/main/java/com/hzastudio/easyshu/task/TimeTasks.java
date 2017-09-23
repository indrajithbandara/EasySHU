package com.hzastudio.easyshu.task;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzastudio.easyshu.support.data_bean.TermTime;
import com.hzastudio.easyshu.support.json_lib.json_CompoundReturn;
import com.hzastudio.easyshu.support.program_const.URL;
import com.hzastudio.easyshu.support.tool.HttpFramework;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 与服务器通信函数（时间获取）（阻塞）
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class TimeTasks {

    public static TermTime Task_GetTermTime()
    {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                .add("function","Time_getTermTime")
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("Task_GetTermTime","Result:"+result);
            Gson gson=new Gson();
            Type CourseTableType = new TypeToken<json_CompoundReturn<TermTime>>(){}.getType();
            json_CompoundReturn<TermTime> res=gson.fromJson(result,CourseTableType);
            Log.d("Task_GetTermTime","Status:"+res.getStatus());
            Log.d("Task_GetTermTime","Data-Year:"+res.getData().getTermYear());
            Log.d("Task_GetTermTime","Data-Season:"+res.getData().getTermSeason());
            Log.d("Task_GetTermTime","Data-Calendar:"+res.getData().getCalendar());
            return res.getData();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
