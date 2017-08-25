package com.hzastudio.easyshu.module;

import android.content.SharedPreferences;
import android.util.Log;

import com.hzastudio.easyshu.support.data_bean.TableCourse;
import com.hzastudio.easyshu.support.data_bean.UserCourse;
import com.hzastudio.easyshu.support.tool.CourseProcessor;
import com.hzastudio.easyshu.support.universal.MainApplication;
import com.hzastudio.easyshu.task.CJTasks;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static android.content.Context.MODE_PRIVATE;

public class CJSystemHandler {

    public static Observable<List<TableCourse>> GetCourseTable() throws NullPointerException
    {
        return Observable.create(new ObservableOnSubscribe<List<TableCourse>>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<TableCourse>> e) throws Exception {
                //TODO:获取当前的学年，学期
                SharedPreferences app = MainApplication.getContext()
                        .getSharedPreferences("application", MODE_PRIVATE);
                String year = app.getString("year",null);
                String season = app.getString("season",null);
                Log.d("GetCourseTable","Year:"+year);
                Log.d("GetCourseTable","Season:"+season);
                List<UserCourse> courseList = CJTasks.Task_CJ_getCourseTable("16121683",year,season);
                //////////////////保存课表
                int Count=1;
                SharedPreferences preferences=MainApplication.getContext()
                        .getSharedPreferences("course_table",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                for(UserCourse a:courseList){
                    editor.putString("CourseNum"+Count,a.getCourseNum());
                    editor.putString("CourseName"+Count,a.getCourseName());
                    editor.putString("TeacherNum"+Count,a.getTeacherNum());
                    editor.putString("TeacherName"+Count,a.getTeacherName());
                    editor.putString("CourseTime"+Count,a.getCourseTime());
                    editor.putString("CourseRoom"+Count,a.getCourseRoom());
                    editor.putString("CourseQuesTime"+Count,a.getCourseQuesTime());
                    editor.putString("CourseQuesPlace"+Count,a.getCourseQuesPlace());
                    editor.putString("CourseTimeDetail"+Count,a.getCourseTimeDetail());
                    Count++;
                }
                editor.apply();
                /////////////////////////
                List<List<TableCourse>> result = CourseProcessor.ClassifyToTableCourseList(courseList);
                for(List<TableCourse> list : result)
                {
                    e.onNext(list);
                }
                e.onComplete();
            }
        });
    }
}
