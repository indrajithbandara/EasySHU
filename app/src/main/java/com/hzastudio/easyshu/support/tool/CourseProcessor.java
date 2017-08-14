package com.hzastudio.easyshu.support.tool;

import com.hzastudio.easyshu.support.data_bean.CurrentCourseInfo;
import com.hzastudio.easyshu.support.data_bean.TableCourse;
import com.hzastudio.easyshu.support.data_bean.UserCourse;
import com.hzastudio.easyshu.support.program_const.CourseStatus;

import java.util.ArrayList;
import java.util.List;

import static com.hzastudio.easyshu.support.program_const.CourseStatus.COURSE_TIME_LIST;

public class CourseProcessor {

    /**
     * 将课表按照星期分组
     * @param Input UserCourse原始课表
     * @return “List<List<TableCourse>>” 分组后的课表集合
     */
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

    /**
     * 依据当前课程时间返回课程位置和状态
     * @return
     */
    public static CurrentCourseInfo GetCurrentCoursePos(List<UserCourse> mCourseList) {
        //List<List<TableCourse>> list=ClassifyToTableCourseList(mCourseList);
        //首先定位当前课程位置（课间取下一节为基准）
        int CourseTime = -1;
        for (int i = 0; i < COURSE_TIME_LIST.length; i++) {
            if (COURSE_TIME_LIST[i].CompareTo() == 0) {
                CourseTime=i;
                break;
            } else if (COURSE_TIME_LIST[i].CompareTo() == -1) {
                if (i == 0) {
                    CourseTime=0;
                    break;
                } else {
                    if (COURSE_TIME_LIST[i - 1].CompareTo() == 1) {
                        CourseTime=i;
                        break;
                    }
                }
            }
        }
        //然后获取当前周数，当前星期

        return null;
    }
}
