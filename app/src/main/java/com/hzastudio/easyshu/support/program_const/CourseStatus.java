package com.hzastudio.easyshu.support.program_const;

import com.hzastudio.easyshu.support.data_bean.CourseTime;

public class CourseStatus {

    public static final int STATUS_BEFORE=0;
    public static final int STATUS_NOW=1;
    public static final int STATUS_BREAK=2;
    public static final int STATUS_NOT_COURSE_TIME=3;
    public static final int STATUS_MORNING_COURSE=4;
    public static final int STATUS_NULL=5;

    public static final CourseTime[] COURSE_TIME_LIST={new CourseTime(8,0,8,45),
    new CourseTime(8,55,9,40),new CourseTime(10,0,10,45),new CourseTime(10,55,11,40),
    new CourseTime(12,10,12,55),new CourseTime(13,5,13,50),new CourseTime(14,10,14,55),
    new CourseTime(15,5,15,50),new CourseTime(16,0,16,45),new CourseTime(16,55,17,40),
    new CourseTime(18,0,18,45),new CourseTime(18,55,19,40),new CourseTime(19,50,20,35)};

}
