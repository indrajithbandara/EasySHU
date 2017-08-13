package com.hzastudio.easyshu.task;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzastudio.easyshu.support.data_bean.TimeYearAndSeason;
import com.hzastudio.easyshu.support.data_bean.UserCourse;
import com.hzastudio.easyshu.support.json_lib.json_CompoundReturn;
import com.hzastudio.easyshu.support.json_lib.json_StandardReturn;
import com.hzastudio.easyshu.support.program_const.URL;
import com.hzastudio.easyshu.support.tool.HttpFramework;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TimeTasks {

    public static String Task_GetCurrentTermWeek()
    {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                .add("function","Time_getCurrentWeek")
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("Task_GetCurrentTermWeek","Result:"+result);
            Gson gson=new Gson();
            json_StandardReturn res=gson.fromJson(result,json_StandardReturn.class);
            Log.d("Task_GetCurrentTermWeek","Status:"+res.getStatus());
            Log.d("Task_GetCurrentTermWeek","Data:"+res.getData());
            return res.getData();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public static TimeYearAndSeason Task_GetYearAndSeason()
    {
        HttpFramework handler= HttpFramework.getInstance();
        RequestBody body=new FormBody.Builder()
                .add("function","Time_getCurrentYearAndSeason")
                .build();
        try {
            Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
            String result=response.body().string();
            Log.d("Task_GetYearAndSeason","Result:"+result);
            Gson gson=new Gson();
            Type CourseTableType = new TypeToken<json_CompoundReturn<TimeYearAndSeason>>(){}.getType();
            json_CompoundReturn<TimeYearAndSeason> res=gson.fromJson(result,CourseTableType);
            Log.d("Task_GetYearAndSeason","Status:"+res.getStatus());
            Log.d("Task_GetYearAndSeason","Data-Year:"+res.getData().getTermYear());
            Log.d("Task_GetYearAndSeason","Data-Season:"+res.getData().getTermSeason());
            return res.getData();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
