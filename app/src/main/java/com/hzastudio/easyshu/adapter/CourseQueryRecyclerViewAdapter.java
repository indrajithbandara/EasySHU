package com.hzastudio.easyshu.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.support.data_bean.CourseQueryCourse;
import com.hzastudio.easyshu.support.program_const.CourseStatus;
import com.hzastudio.easyshu.ui.widget.TextProgressBar;

import java.util.List;

public class CourseQueryRecyclerViewAdapter
        extends RecyclerView.Adapter<CourseQueryRecyclerViewAdapter.ViewHolder> {

    private List<CourseQueryCourse> mCourseList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout QueryCourse;
        TextView QueryCourseName;
        TextView QueryCourseTeacher;
        TextView QueryCourseRoom;
        TextView QueryCourseTime;
        TextView QueryCourseChooseTxt;
        TextProgressBar QueryCourseChooseBar;

        public ViewHolder(View itemView) {
            super(itemView);
            QueryCourse = (LinearLayout) itemView.findViewById(R.id.Query_Course);
            QueryCourseName = (TextView) itemView.findViewById(R.id.Query_CourseName);
            QueryCourseTeacher = (TextView) itemView.findViewById(R.id.Query_CourseTeacher);
            QueryCourseRoom = (TextView) itemView.findViewById(R.id.Query_CourseRoom);
            QueryCourseTime = (TextView) itemView.findViewById(R.id.Query_CourseTime);
            QueryCourseChooseTxt = (TextView) itemView.findViewById(R.id.Query_CourseChoose_Txt);
            QueryCourseChooseBar = (TextProgressBar) itemView.findViewById(R.id.Query_CourseChoose_Bar);
        }

    }

    public CourseQueryRecyclerViewAdapter(List<CourseQueryCourse> mCourseList) {
        this.mCourseList = mCourseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_course_query_child_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.QueryCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:单击事件
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseQueryCourse course = mCourseList.get(position);
        holder.QueryCourseName.setText(course.getCourseName());
        holder.QueryCourseTeacher.setText(course.getTeacherName());
        holder.QueryCourseRoom.setText(course.getCourseRoom());
        holder.QueryCourseTime.setText(course.getCourseTime());
        switch (course.getCourseRestriction())
        {
            case CourseStatus.RESTRICT_NULL:
                holder.QueryCourseChooseTxt.setText("正常选课");
                break;
            case CourseStatus.RESTRICT_STOP_COURSE_CHOOSE:
                holder.QueryCourseChooseTxt.setText("禁止选课");
                break;
            case CourseStatus.RESTRICT_STUDENT_LIMIT:
                holder.QueryCourseChooseTxt.setText("限制人数");
                break;
            case CourseStatus.RESTRICT_STOP_COURSE_DROP:
                holder.QueryCourseChooseTxt.setText("禁止退课");
                break;
            case CourseStatus.RESTRICT_STOP_CHOOSE_AND_LIMIT:
                holder.QueryCourseChooseTxt.setText("限制人数 禁止选课");
                break;
            case CourseStatus.RESTRICT_STOP_CHOOSE_AND_DROP:
                holder.QueryCourseChooseTxt.setText("禁止选课 禁止退课");
                break;
            case CourseStatus.RESTRICT_STOP_DROP_AND_LIMIT:
                holder.QueryCourseChooseTxt.setText("限制人数 禁止退课");
                break;
            case CourseStatus.RESTRICT_ALL:
                holder.QueryCourseChooseTxt.setText("限制人数 禁止选课 禁止退课");
                break;
        }
        holder.QueryCourseChooseBar.setTextSize(12);
        holder.QueryCourseChooseBar.setTextColor(Color.BLACK);
        holder.QueryCourseChooseBar.setTextStyle(TextProgressBar.PROGRESS_TEXT_NUM);
        holder.QueryCourseChooseBar.setMax(course.getCourseCapacity());
        holder.QueryCourseChooseBar.setProgress(course.getCourseChosen());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }
}
