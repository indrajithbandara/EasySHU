package com.hzastudio.easyshu.support.tool;

import com.hzastudio.easyshu.support.data_bean.TableCourse;
import com.hzastudio.easyshu.support.data_bean.UserCourse;

import java.util.ArrayList;
import java.util.List;

public class CourseClassify {

    public static List<List<TableCourse>> ClassifyToTableCourseList(List<UserCourse> Input)
    {
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

}
