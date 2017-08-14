package com.hzastudio.easyshu.support.data_bean;

import java.util.Calendar;

public class CourseTime {

    private int StartHour;
    private int StartMinute;
    private int EndHour;
    private int EndMinute;

    /**
     * 比较当前时间和课程时间
     * @return 0->在课程时间内 1->在课程时间后 -1->在课程时间前
     */
    public int CompareTo()
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int inputRes=hour*60+minute;
        int startRes=StartHour*60+StartMinute;
        int endRes=EndHour*60+EndMinute;
        if(inputRes<startRes)
        {
            return -1;
        }
        else if(inputRes>endRes)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    /**
     * 比较输入时间和课程时间（重载）
     * @param Hour 小时
     * @param Minute 分钟
     * @return 0->在课程时间内 1->在课程时间后 -1->在课程时间前
     */
    public int CompareTo(int Hour,int Minute)
    {
        Calendar calendar = Calendar.getInstance();
        int inputRes=Hour*60+Minute;
        int startRes=StartHour*60+StartMinute;
        int endRes=EndHour*60+EndMinute;
        if(inputRes<startRes)
        {
            return -1;
        }
        else if(inputRes>endRes)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public CourseTime(int startHour, int startMinute, int endHour, int endMinute) {
        StartHour = startHour;
        StartMinute = startMinute;
        EndHour = endHour;
        EndMinute = endMinute;
    }

    public int getStartHour() {
        return StartHour;
    }

    public void setStartHour(int startHour) {
        StartHour = startHour;
    }

    public int getStartMinute() {
        return StartMinute;
    }

    public void setStartMinute(int startMinute) {
        StartMinute = startMinute;
    }

    public int getEndHour() {
        return EndHour;
    }

    public void setEndHour(int endHour) {
        EndHour = endHour;
    }

    public int getEndMinute() {
        return EndMinute;
    }

    public void setEndMinute(int endMinute) {
        EndMinute = endMinute;
    }
}
