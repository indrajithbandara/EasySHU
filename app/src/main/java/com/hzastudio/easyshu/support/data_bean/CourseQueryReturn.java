package com.hzastudio.easyshu.support.data_bean;

import java.util.List;

/**
 * 课程查询返回bean
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class CourseQueryReturn {

    private List<CourseQueryCourse> CourseList;
    private int PageCount;

    public CourseQueryReturn(List<CourseQueryCourse> courseList, int pageCount) {
        CourseList = courseList;
        PageCount = pageCount;
    }

    public List<CourseQueryCourse> getCourseList() {
        return CourseList;
    }

    public int getPageCount() {
        return PageCount;
    }

}
