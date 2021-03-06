package com.hzastudio.easyshu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseTableRecyclerViewAdapter;
import com.hzastudio.easyshu.support.data_bean.TableCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * 课表Fragment
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class CourseTableDayFragment extends Fragment{

    private RecyclerView courseView;
    private CourseTableRecyclerViewAdapter adapter;
    private LinearLayoutManager courseViewManager;
    private List<TableCourse> CourseList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_course,container,false);
        courseView = (RecyclerView) view.findViewById(R.id.CourseView);
        courseViewManager = new LinearLayoutManager(this.getActivity());
        courseView.setLayoutManager(courseViewManager);
        adapter=new CourseTableRecyclerViewAdapter(CourseList);
        courseView.setAdapter(adapter);
        return view;
    }

    public void setCourseList(List<TableCourse> courseList) {
        CourseList=new ArrayList<>();
        CourseList.addAll(courseList);
        if(courseView!=null)
        {
            adapter=new CourseTableRecyclerViewAdapter(CourseList);
            courseView.setAdapter(adapter);
        }
    }

    public void CourseViewScrollTo(int position)
    {
        courseView.scrollToPosition(position);
    }

    public RecyclerView getCourseView() {
        return courseView;
    }
}
