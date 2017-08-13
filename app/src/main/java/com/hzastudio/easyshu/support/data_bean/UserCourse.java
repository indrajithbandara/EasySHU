package com.hzastudio.easyshu.support.data_bean;

import com.google.gson.annotations.SerializedName;

public class UserCourse {

    @SerializedName("course_num")
    private String CourseNum;
    @SerializedName("course_name")
    private String CourseName;
    @SerializedName("course_teacher_num")
    private String TeacherNum;
    @SerializedName("course_teacher_name")
    private String TeacherName;
    @SerializedName("course_time")
    private String CourseTime;
    @SerializedName("course_room")
    private String CourseRoom;
    @SerializedName("course_ques_time")
    private String CourseQuesTime;
    @SerializedName("course_ques_place")
    private String CourseQuesPlace;
    @SerializedName("course_time_detail")
    private String CourseTimeDetail;

    public String getCourseNum() {
        return CourseNum;
    }

    public void setCourseNum(String courseNum) {
        CourseNum = courseNum;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getTeacherNum() {
        return TeacherNum;
    }

    public void setTeacherNum(String teacherNum) {
        TeacherNum = teacherNum;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getCourseTime() {
        return CourseTime;
    }

    public void setCourseTime(String courseTime) {
        CourseTime = courseTime;
    }

    public String getCourseRoom() {
        return CourseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        CourseRoom = courseRoom;
    }

    public String getCourseQuesTime() {
        return CourseQuesTime;
    }

    public void setCourseQuesTime(String courseQuesTime) {
        CourseQuesTime = courseQuesTime;
    }

    public String getCourseQuesPlace() {
        return CourseQuesPlace;
    }

    public void setCourseQuesPlace(String courseQuesPlace) {
        CourseQuesPlace = courseQuesPlace;
    }

    public String getCourseTimeDetail() {
        return CourseTimeDetail;
    }

    public void setCourseTimeDetail(String courseTimeDetail) {
        CourseTimeDetail = courseTimeDetail;
    }
}
