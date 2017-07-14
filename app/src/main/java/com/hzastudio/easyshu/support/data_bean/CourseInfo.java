package com.hzastudio.easyshu.support.data_bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Programmed by Zean Huang
 * Github: https://github.com/thunderbird1997
 */

/**
 * 课表中的课程信息，与选课信息分开为了更快的处理速度
 */
public class CourseInfo implements Parcelable {

    private String Course_Num;
    private String Course_Name;
    private String Teacher_No;
    private String Teacher_Name;
    private String Course_Time;
    private String Course_Credit;
    private String Course_Place;
    private String Course_District;
    private String Course_Ques_Time;
    private String Course_Ques_Place;

    //课程表排序用参数
    private String Course_Start_Time; //课程上课开始时间
    private String Course_End_Time; //课程上课开始时间
    private String Course_Table_Week; //课程出现的周
    private Boolean Course_Is_Null=false;//课程为空
    private Boolean Course_Is_Current=false;//是否为当前课程

    public Boolean getCourse_Is_Current() {
        return Course_Is_Current;
    }

    public void setCourse_Is_Current(Boolean course_Is_Current) {
        Course_Is_Current = course_Is_Current;
    }

    public CourseInfo() {
        super();
    }

    public String getCourse_Num() {
        return Course_Num;
    }

    public void setCourse_Num(String course_Num) {
        Course_Num = course_Num;
    }

    public String getCourse_Name() {
        return Course_Name;
    }

    public void setCourse_Name(String course_Name) {
        Course_Name = course_Name;
    }

    public String getTeacher_No() {
        return Teacher_No;
    }

    public void setTeacher_No(String teacher_No) {
        Teacher_No = teacher_No;
    }

    public String getTeacher_Name() {
        return Teacher_Name;
    }

    public void setTeacher_Name(String teacher_Name) {
        Teacher_Name = teacher_Name;
    }

    public String getCourse_Time() {
        return Course_Time;
    }

    public void setCourse_Time(String course_Time) {
        Course_Time = course_Time;
    }

    public String getCourse_Credit() {
        return Course_Credit;
    }

    public void setCourse_Credit(String course_Credit) {
        Course_Credit = course_Credit;
    }

    public String getCourse_Place() {
        return Course_Place;
    }

    public void setCourse_Place(String course_Place) {
        Course_Place = course_Place;
    }

    public String getCourse_District() {
        return Course_District;
    }

    public void setCourse_District(String course_District) {
        Course_District = course_District;
    }

    public String getCourse_Ques_Time() {
        return Course_Ques_Time;
    }

    public void setCourse_Ques_Time(String course_Ques_Time) {
        Course_Ques_Time = course_Ques_Time;
    }

    public String getCourse_Ques_Place() {
        return Course_Ques_Place;
    }

    public void setCourse_Ques_Place(String course_Ques_Place) {
        Course_Ques_Place = course_Ques_Place;
    }

    public String getCourse_Start_Time() {
        return Course_Start_Time;
    }

    public void setCourse_Start_Time(String course_Start_Time) {
        Course_Start_Time = course_Start_Time;
    }

    public String getCourse_End_Time() {
        return Course_End_Time;
    }

    public void setCourse_End_Time(String course_End_Time) {
        Course_End_Time = course_End_Time;
    }

    public String getCourse_Table_Week() {
        return Course_Table_Week;
    }

    public void setCourse_Table_Week(String course_Table_Week) {
        Course_Table_Week = course_Table_Week;
    }

    public Boolean getCourse_Is_Null() {
        return Course_Is_Null;
    }

    public void setCourse_Is_Null(Boolean course_Is_Null) {
        Course_Is_Null = course_Is_Null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Course_Num);
        dest.writeString(Course_Name);
        dest.writeString(Teacher_No);
        dest.writeString(Teacher_Name);
        dest.writeString(Course_Time);
        dest.writeString(Course_Credit);
        dest.writeString(Course_Place);
        dest.writeString(Course_District);
        dest.writeString(Course_Ques_Time);
        dest.writeString(Course_Ques_Place);
    }

    public static final Creator<CourseInfo> CREATOR = new Creator<CourseInfo>() {
        @Override
        public CourseInfo createFromParcel(Parcel source) {
            CourseInfo info = new CourseInfo();
            info.setCourse_Num(source.readString());
            info.setCourse_Name(source.readString());
            info.setTeacher_No(source.readString());
            info.setTeacher_Name(source.readString());
            info.setCourse_Time(source.readString());
            info.setCourse_Credit(source.readString());
            info.setCourse_Place(source.readString());
            info.setCourse_District(source.readString());
            info.setCourse_Ques_Time(source.readString());
            info.setCourse_Ques_Place(source.readString());
            return info;
        }

        @Override
        public CourseInfo[] newArray(int size) {
            return new CourseInfo[size];
        }
    };

}
