package com.hzastudio.easyshu.support.data_bean;

public class CurrentCourse {

    /**
     * 存储当前课程的状态
     * STATUS_BEFORE:即将上课。在连续课程课组成的一节大课的课前，则出现此标记
     * STATUS_NOW:正在上课。在课程中，则出现此标记
     * STATUS_NOT_COURSE_TIME:不在上课的时间。周六，周日，每天的6点之前和晚上最后一节课后出现此标记
     * STATUS_MORNING_COURSE:有早课。早上6点之后，第一节有课则出现此标记
     * STATUS_NULL:没有课程。以上情况之外的情况，出现此标记
     */
    private int CurrentCourseStatus;

    /**
     * 存储当前课程的星期
     */
    private int CurrentCourseWeekday;

    /**
     * 存储当前课程的时间
     */
    private int CurrentCourseTime;

    public int getCurrentCourseStatus() {
        return CurrentCourseStatus;
    }

    public void setCurrentCourseStatus(int currentCourseStatus) {
        CurrentCourseStatus = currentCourseStatus;
    }

    public int getCurrentCourseWeekday() {
        return CurrentCourseWeekday;
    }

    public void setCurrentCourseWeekday(int currentCourseWeekday) {
        CurrentCourseWeekday = currentCourseWeekday;
    }

    public int getCurrentCourseTime() {
        return CurrentCourseTime;
    }

    public void setCurrentCourseTime(int currentCourseTime) {
        CurrentCourseTime = currentCourseTime;
    }
}
