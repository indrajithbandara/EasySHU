package com.hzastudio.easyshu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseInfoAdapter;
import com.hzastudio.easyshu.support.data_bean.CourseInfo;

import java.util.ArrayList;

/**
 * Created by hasee on 2017/7/14.
 */

public class CourseTableDayFragment extends Fragment{

    private RecyclerView courseView;
    private CourseInfoAdapter adapter;
    private ArrayList<CourseInfo> CourseList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_course,container,false);
        courseView = (RecyclerView) view.findViewById(R.id.CourseView);
        courseView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter=new CourseInfoAdapter(CourseList);
        courseView.setAdapter(adapter);
        return view;
    }

    public void setCourseList(ArrayList<CourseInfo> courseList) {
        CourseList=new ArrayList<>();
        CourseList.addAll(courseList);
        if(courseView!=null)
        {
            adapter=new CourseInfoAdapter(CourseList);
            courseView.setAdapter(adapter);
        }
    }

}
