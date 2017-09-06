package com.hzastudio.easyshu.support.program_const;

import com.hzastudio.easyshu.support.data_bean.CourseTime;

public class CourseStatus {

    /*课程状态*/
    public static final int STATUS_BEFORE=0;
    public static final int STATUS_NOW=1;
    public static final int STATUS_BREAK=2;
    public static final int STATUS_NOT_COURSE_TIME=3;
    public static final int STATUS_MORNING_COURSE=4;
    public static final int STATUS_NULL=5;

    /*课程时间*/
    public static final CourseTime[] COURSE_TIME_LIST={new CourseTime(8,0,8,45),
    new CourseTime(8,55,9,40),new CourseTime(10,0,10,45),new CourseTime(10,55,11,40),
    new CourseTime(12,10,12,55),new CourseTime(13,5,13,50),new CourseTime(14,10,14,55),
    new CourseTime(15,5,15,50),new CourseTime(16,0,16,45),new CourseTime(16,55,17,40),
    new CourseTime(18,0,18,45),new CourseTime(18,55,19,40),new CourseTime(19,50,20,35)};

    /*校区*/
    public static final int DISTRICT_ALL=0;
    public static final int DISTRICT_BEN_BU=1;
    public static final int DISTRICT_YAN_CHANG=2;
    public static final int DISTRICT_JIA_DIN=3;
    public static final int DISTRICT_XU_JING=4;
    public static final int DISTRICT_BAO_SHAN_DONG=5;

    /*选课限制*/
    public static final int RESTRICT_STUDENT_LIMIT=0x01;
    public static final int RESTRICT_STOP_COURSE_CHOOSE=0x02;
    public static final int RESTRICT_STOP_COURSE_DROP=0x04;
}
