package com.hzastudio.easyshu.module;


import android.util.Log;

import com.hzastudio.easyshu.support.data_bean.CourseOptionData;
import com.hzastudio.easyshu.support.data_bean.CourseQueryCourse;
import com.hzastudio.easyshu.support.data_bean.CourseQueryReturn;
import com.hzastudio.easyshu.support.data_bean.TableCourse;
import com.hzastudio.easyshu.task.XKTasks;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

public class XKSystemHandler {

    public static Observable<Boolean> CheckCourseGrading()
    {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(XKTasks.Task_XK_CheckCourseGrading());
                e.onComplete();
            }
        });
    }

    public static Observable<List<CourseQueryCourse>> QueryCourse(final CourseOptionData data)
    {
        return Observable.create(new ObservableOnSubscribe<List<CourseQueryCourse>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<CourseQueryCourse>> e) throws Exception {
                //Log.d("sss",data.toString());
                if(data.getPageCount().equals("")
                ||Integer.parseInt(data.getPageCount())>=Integer.parseInt(data.getPageIndex())) {
                    CourseQueryReturn Return = XKTasks.Task_XK_QueryCourse(data);
                    data.setPageCount(Return.getPageCount());
                    data.PageIndexChange();
                    e.onNext(Return.getCourseList());
                    e.onComplete();
                }
                else
                {
                    e.onError(new Throwable("NO MORE DATA"));
                }
            }
        });
    }

}
