package com.hzastudio.easyshu.task;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.hzastudio.easyshu.support.data_bean.CourseOptionData;
import com.hzastudio.easyshu.support.data_bean.CourseQueryCourse;
import com.hzastudio.easyshu.support.data_bean.CourseQueryReturn;
import com.hzastudio.easyshu.support.data_bean.TermTime;
import com.hzastudio.easyshu.support.json_lib.json_CompoundReturn;
import com.hzastudio.easyshu.support.json_lib.json_CompoundReturnWithValue;
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
 * 与服务器通信函数（选课系统处理）（阻塞）
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class XKTasks {

    public static Boolean Task_XK_CheckCourseGrading() throws Exception
    {
        HttpFramework handler= HttpFramework.getInstance();
        String[] user = SecurityTool.getUserSignature();
        RequestBody body=new FormBody.Builder()
                .add("usr",user[0])
                .add("psw",user[1])
                .add("randstr",user[2])
                .add("timestamp",user[3])
                .add("sign",user[4])
                .add("function","XK_CheckCourseGrading")
                .add("data[usr]",user[0])
                .build();
        Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
        String result=response.body().string();
        Log.d("XK_CheckCourseGrading","Result:"+result);

        Gson gson=new Gson();
        Type CourseTableType = new TypeToken<json_CompoundReturn<String>>(){}.getType();
        json_CompoundReturn<String> res=gson.fromJson(result,CourseTableType);
        if(res.getStatus().equals("1"))return true;
        else if(res.getStatus().equals("0") && res.getData().equals("GRADE NG"))return false;
        else throw new Exception("F**king Password Error,Wtf!");
    }

    public static CourseQueryReturn Task_XK_QueryCourse(CourseOptionData data) throws Exception
    {
        HttpFramework handler= HttpFramework.getInstance();
        String[] user = SecurityTool.getUserSignature();
        RequestBody body=new FormBody.Builder()
                .add("usr",user[0])
                .add("psw",user[1])
                .add("randstr",user[2])
                .add("timestamp",user[3])
                .add("sign",user[4])
                .add("function","XK_QueryCourse")
                .add("data[usr]",user[0])
                .add("data[course_num]",data.getCourseNum())
                .add("data[course_name]",data.getCourseName())
                .add("data[teacher_num]",data.getTeacherNum())
                .add("data[teacher_name]",data.getTeacherName())
                .add("data[course_time]",data.getCourseTime())
                .add("data[not_full]",data.getCourseNotFull())
                .add("data[credit]",data.getCourseCredit())
                .add("data[campus]",data.getCourseCampus())
                .add("data[enroll]",data.getCourseEnroll())
                .add("data[min_capacity]",data.getCourseMinCapacity())
                .add("data[max_capacity]",data.getCourseMaxCapacity())
                .add("data[page_index]",data.getPageIndex())
                .add("data[page_size]",data.getPageSize())
                .build();

        Response response = handler.httpPost(URL.SERVER_INTERFACE_URL,body).execute();
        String result=response.body().string();
        Log.d("Task_XK_QueryCourse","Result:"+result);

        Gson gson=new Gson();
        Type CourseTableType =
                new TypeToken<json_CompoundReturnWithValue<List<CourseQueryCourse>,Integer>>
                        (){}.getType();
        json_CompoundReturnWithValue<List<CourseQueryCourse>,Integer> res=
                gson.fromJson(result,CourseTableType);
        //Log.d("Task_XK_QueryCourse","Value:"+res.getValue());
        data.setPageCount(res.getValue());
        return new CourseQueryReturn(res.getData(),res.getValue());
    }

}
