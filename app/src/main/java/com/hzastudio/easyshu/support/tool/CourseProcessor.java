package com.hzastudio.easyshu.support.tool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.hzastudio.easyshu.support.data_bean.CurrentCourse;
import com.hzastudio.easyshu.support.data_bean.TableCourse;
import com.hzastudio.easyshu.support.data_bean.UserCourse;
import com.hzastudio.easyshu.support.program_const.CourseStatus;
import com.hzastudio.easyshu.support.universal.MainApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.hzastudio.easyshu.support.program_const.CourseStatus.COURSE_TIME_LIST;

public class CourseProcessor {

    /**
     * 将课表按照星期分组
     * @param Input UserCourse原始课表
     * @return “List<List<TableCourse>>” 分组后的课表集合
     */
    public static List<List<TableCourse>> ClassifyToTableCourseList(List<UserCourse> Input)
    {
        //初始化列表
        List<List<TableCourse>> ret = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            List<TableCourse> list=new ArrayList<>();
            for(int j=0;j<13;j++)
            {
                list.add(j,null);
            }
            ret.add(list);
        }
        //分类
        for(UserCourse Course:Input)
        {
            String temp=Course.getCourseTimeDetail();
            String[] CourseTimeDetail=temp.split("\\|");
            for(int i=0;i<CourseTimeDetail.length;i++)
            {
                List<TableCourse> WeekdayCourseList=ret.get(i);
                String[] CourseTimeDayDetail=CourseTimeDetail[i].split("&");
                for(String Time:CourseTimeDayDetail)
                {
                    if(!Time.equals("")) {
                        if(Time.contains("@")) {
                            String[] CourseTimeDayWeekDetail = Time.split("@");
                            String[] CourseTimeDay = CourseTimeDayWeekDetail[0].split(",");
                            for (String time : CourseTimeDay) {
                                TableCourse course = new TableCourse(Course);
                                course.setCourseTableWeek(CourseTimeDayWeekDetail[1]);
                                WeekdayCourseList.set(Integer.parseInt(time) - 1, course);
                            }
                        }
                        else
                        {
                            String[] CourseTimeDay = Time.split(",");
                            for (String time : CourseTimeDay) {
                                TableCourse course = new TableCourse(Course);
                                course.setCourseTableWeek("1,2,3,4,5,6,7,8,9,10");
                                WeekdayCourseList.set(Integer.parseInt(time) - 1, course);
                            }
                        }
                    }
                }
                ret.set(i,WeekdayCourseList);
            }
        }
        return ret;
    }

    /**
     * 依据当前课程时间返回课程位置和状态
     * @return
     */
    public static CurrentCourse GetCurrentCoursePos(List<UserCourse> mCourseList) {
        List<List<TableCourse>> TableCourseList=ClassifyToTableCourseList(mCourseList);
        CurrentCourse course = new CurrentCourse();
        //首先定位当前课程位置（课间取下一节为基准）
        int CourseTime = -1;
        int CourseTimeStatus = -1;
        for (int i = 0; i < COURSE_TIME_LIST.length; i++) {
            if (COURSE_TIME_LIST[i].CompareTo() == 0) {
                CourseTime=i;
                CourseTimeStatus = CourseStatus.STATUS_NOW;
                break;
            } else if (COURSE_TIME_LIST[i].CompareTo() == -1) {
                if (i == 0) {
                    CourseTime=0;
                    CourseTimeStatus = CourseStatus.STATUS_MORNING_COURSE;
                    break;
                } else {
                    if (COURSE_TIME_LIST[i - 1].CompareTo() == 1) {
                        CourseTime=i;
                        CourseTimeStatus = CourseStatus.STATUS_BEFORE;
                        break;
                    }
                }
            }
        }
        //Log.d("GetCurrentCoursePos","CourseTime:"+CourseTime);
        //Log.d("GetCurrentCoursePos","CourseTimeStatus:"+CourseTimeStatus);
        //从课表中获取当前周数
        int CurrentWeek = getCurrentWeek();
        //Log.d("GetCurrentCoursePos","CurrentWeek:"+CurrentWeek);
        //获取当前是星期几
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if(day!=1)day-=1;
        else day=7;
        //Log.d("GetCurrentCoursePos","CurrentDay:"+day);

        if(day>=6 || CourseTime==-1)
        {
            //双休日，不在上课时间
            course.setCurrentCourseStatus(CourseStatus.STATUS_NOT_COURSE_TIME);
            course.setCurrentCourseTime(-1);
            course.setCurrentCourseWeekday(-1);
            return course;
        }
        else
        {
            //获取当天课程
            List<TableCourse> CurrentDayCourseList = TableCourseList.get(day-1);
            if(CurrentDayCourseList.get(CourseTime)==null)
            {
                //没有课程
                course.setCurrentCourseTime(-1);
                course.setCurrentCourseWeekday(-1);
                course.setCurrentCourseStatus(CourseStatus.STATUS_NULL);
                return course;
            }
            else
            {
                if(getTableCourseWeek(CurrentDayCourseList.get(CourseTime)).contains(getCurrentWeek()))
                {
                    //当前周该课程有课
                    course.setCurrentCourseTime(CourseTime);
                    course.setCurrentCourseWeekday(day);
                    course.setCurrentCourseStatus(CourseTimeStatus);
                    return course;
                }
                else
                {
                    //没有课程
                    course.setCurrentCourseTime(-1);
                    course.setCurrentCourseWeekday(-1);
                    course.setCurrentCourseStatus(CourseStatus.STATUS_NULL);
                    return course;
                }

            }
        }
    }

    public static int getCurrentWeek()
    {
        //从课表中获取当前周数
        SharedPreferences app = MainApplication.getContext()
                .getSharedPreferences("application", Context.MODE_PRIVATE);
        String calendar = app.getString("calendar","");
        String[] week = calendar.split("\\|");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        int CurrentWeek;
        for(CurrentWeek=1;CurrentWeek<=week.length;CurrentWeek++)
        {
            if(week[CurrentWeek-1].contains(format.format(cal.getTime())))break;
        }
        //Log.d("CourseProcessor","CurrentWeek:"+CurrentWeek);
        return CurrentWeek;
    }

    public static List<Integer> getTableCourseWeek(TableCourse course)
    {
        String WeekListRaw = course.getCourseTableWeek();
        String[] WeekList = WeekListRaw.split(",");
        List<Integer> ResultList=new ArrayList<>();
        for(String element:WeekList)
        {
            ResultList.add(Integer.parseInt(element));
        }
        return ResultList;
    }
}
