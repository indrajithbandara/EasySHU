package com.hzastudio.easyshu.support.data_bean;

public class CourseOptionData {

    private String CourseNum;
    private String CourseName;
    private String TeacherNum;
    private String TeacherName;
    private String CourseTime;
    private String CourseNotFull;
    private String CourseCredit;
    private String CourseCampus;
    private String CourseEnroll;
    private String CourseMinCapacity;
    private String CourseMaxCapacity;
    private String PageIndex;
    private String PageSize;

    //页数记录（初始化为空）,防止出现错误
    private String PageCount="";

    private CourseOptionData(Builder builder)
    {
        this.CourseNum=builder.CourseNum;
        this.CourseName=builder.CourseName;
        this.TeacherNum=builder.TeacherNum;
        this.TeacherName=builder.TeacherName;
        this.CourseTime=builder.CourseTime;
        this.CourseNotFull=builder.CourseNotFull;
        this.CourseCredit=builder.CourseCredit;
        this.CourseCampus=builder.CourseCampus;
        this.CourseEnroll=builder.CourseEnroll;
        this.CourseMinCapacity=builder.CourseMinCapacity;
        this.CourseMaxCapacity=builder.CourseMaxCapacity;
        this.PageIndex=builder.PageIndex;
        this.PageSize=builder.PageSize;
    }

    public String getCourseNum() {
        return CourseNum;
    }

    public String getCourseName() {
        return CourseName;
    }

    public String getTeacherNum() {
        return TeacherNum;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public String getCourseTime() {
        return CourseTime;
    }

    public String getCourseNotFull() {
        return CourseNotFull;
    }

    public String getCourseCredit() {
        return CourseCredit;
    }

    public String getCourseCampus() {
        return CourseCampus;
    }

    public String getCourseEnroll() {
        return CourseEnroll;
    }

    public String getCourseMinCapacity() {
        return CourseMinCapacity;
    }

    public String getCourseMaxCapacity() {
        return CourseMaxCapacity;
    }

    public String getPageIndex() {
        return PageIndex;
    }

    public String getPageSize() {
        return PageSize;
    }

    public static class Builder
    {
        private String CourseNum="";
        private String CourseName="";
        private String TeacherNum="";
        private String TeacherName="";
        private String CourseTime="";
        private String CourseNotFull="false";
        private String CourseCredit="";
        private String CourseCampus="0";
        private String CourseEnroll="";
        private String CourseMinCapacity="";
        private String CourseMaxCapacity="";
        private String PageIndex="1";
        private String PageSize="20";

        public Builder setCourseNum(String courseNum) {
            this.CourseNum = courseNum;
            return this;
        }

        public Builder setCourseName(String courseName) {
            this.CourseName = courseName;
            return this;
        }

        public Builder setTeacherNum(String teacherNum) {
            this.TeacherNum = teacherNum;
            return this;
        }

        public Builder setTeacherName(String teacherName) {
            this.TeacherName = teacherName;
            return this;
        }

        public Builder setCourseTime(String courseTime) {
            this.CourseTime = courseTime;
            return this;
        }

        public Builder setCourseNotFull(String courseNotFull) {
            this.CourseNotFull = courseNotFull;
            return this;
        }

        public Builder setCourseCredit(String courseCredit) {
            this.CourseCredit = courseCredit;
            return this;
        }

        public Builder setCourseCampus(String courseCampus) {
            this.CourseCampus = courseCampus;
            return this;
        }

        public Builder setCourseEnroll(String courseEnroll) {
            this.CourseEnroll = courseEnroll;
            return this;
        }

        public Builder setCourseCapacity(String courseMinCapacity,String courseMaxCapacity) {
            this.CourseMinCapacity = courseMinCapacity;
            this.CourseMaxCapacity = courseMaxCapacity;
            return this;
        }

        public Builder setPageIndex(String pageIndex) {
            this.PageIndex = pageIndex;
            return this;
        }

        public Builder setPageSize(String pageSize) {
            this.PageSize = pageSize;
            return this;
        }

        public CourseOptionData build()
        {
            return new CourseOptionData(this);
        }

    }

    //PageIndex自增
    public void PageIndexChange()
    {
        PageIndex=String.valueOf(Integer.parseInt(PageIndex)+1);
    }

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = String.valueOf(pageCount);
    }

    @Override
    public String toString() {
        return "CourseOptionData:\n"+
                "Page:"+getPageIndex()+"/"+getPageCount()+"\n"+
                "CourseNum:"+getCourseNum()+"\n"+
                "CourseName:"+getCourseName()+"\n"+
                "TeacherNum:"+getTeacherNum()+"\n"+
                "TeacherName:"+getTeacherName()+"\n"+
                "CourseCredit:"+getCourseCredit()+"\n"+
                "CourseNotFull:"+getCourseNotFull()+"\n"+
                "CourseCampus:"+getCourseCampus()+"\n"+
                "CourseEnroll:"+getCourseEnroll()+"\n"+
                "CourseCapacity:"+getCourseMinCapacity()+"-"+getCourseMaxCapacity();
    }
}
