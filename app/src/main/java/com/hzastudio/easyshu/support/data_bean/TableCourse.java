package com.hzastudio.easyshu.support.data_bean;

/**
 * 课表中的课程信息bean，用于显示
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class TableCourse{

    private String CourseName=null;
    private String TeacherName=null;
    private String CoursePlace=null;
    private String CourseTableWeek=null; //课程出现的周
    private Boolean CourseIsCurrent=false;//是否为当前课程

    public TableCourse() {
    }

    public TableCourse(UserCourse course) {
        setCourseName(course.getCourseName());
        setTeacherName(course.getTeacherName());
        setCoursePlace(course.getCourseRoom());
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getCoursePlace() {
        return CoursePlace;
    }

    public void setCoursePlace(String coursePlace) {
        CoursePlace = coursePlace;
    }

    public String getCourseTableWeek() {
        return CourseTableWeek;
    }

    public void setCourseTableWeek(String courseTableWeek) {
        CourseTableWeek = courseTableWeek;
    }

    public Boolean getCourseIsCurrent() {
        return CourseIsCurrent;
    }

    public void setCourseIsCurrent(Boolean courseIsCurrent) {
        CourseIsCurrent = courseIsCurrent;
    }
}
